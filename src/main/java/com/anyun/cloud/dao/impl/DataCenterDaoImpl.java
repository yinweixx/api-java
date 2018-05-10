package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.DataCenterDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.*;
import com.google.inject.Inject;
import com.iciql.SubQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 10:09
 */
public class DataCenterDaoImpl extends AbstractIciqlDao implements DataCenterDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(DataCenterDaoImpl.class);
    @Inject
    public DataCenterDaoImpl(Database database) {

        super(database);
    }

    @Override
    public DataCenterEntity selectDetailsById(int id) throws DatabaseException {
        DataCenterEntity p = new DataCenterEntity();
        int i = (int) db.from(p).where(p.centerId).is(id).selectCount();
        if (i > 0)
            return db.from(p).where(p.centerId).is(id).selectFirst();
        else
            return null;
    }


    @Override
    public void deleteById(int id) throws DatabaseException {
        DataCenterEntity p = new DataCenterEntity();
        db.from(p).where(p.centerId).is(id).delete();

    }

    @Override
    public DataCenterEntity insert(DataCenterEntity p) throws DatabaseException {
        long key = db.insertAndGetKey(p);
        return db.from(p).where(p.centerId).is((int) key).select().get(0);
    }


    @Override
    public DataCenterEntity update(DataCenterEntity p) {
        db.update(p);
        int id = p.getCenterId();
        p = new DataCenterEntity();
        return db.from(p).where(p.centerId).is(id).selectFirst();
    }

    /**
     * 获取数据中心信息
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Override
    public PageDto<DataCenterEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<DataCenterEntity> pageDto = new PageDto<>();
        List<DataCenterEntity> data = null;
        DataCenterEntity p = new DataCenterEntity();
        IpUseEntity u = new IpUseEntity();
        IpPoolEntity s = new IpPoolEntity();
        IpBlockEntity b = new IpBlockEntity();
        int total = (int) db.from(p).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        //Ip数目统计
        for (DataCenterEntity aa : data) {
            LOGGER.debug("data:[{}]", data);
            int id = aa.getCenterId();
            LOGGER.debug("id:[{}]", id);
            //查询每个数据中心宿主机数目
            SubQuery<IpPoolEntity, Integer> i = db.from(s).where(s.centerId).is(id).subQuery(s.getPoolId());
            LOGGER.debug("subQuery1:[{}]", i.toString());
            SubQuery<IpBlockEntity, Integer> q = db.from(b).where(b.poolId).in(i).subQuery(b.blockId);
            LOGGER.debug("subQuery2:[{}]", q.toString());
            long ipCount = db.from(u).where(u.blockId).in(q).selectCount();
            //数据中心下已使用Ip总数
            List<IpBlockEntity> list = db.from(b).where(b.blockId).in(q).select();

            long count = 0;
            long hostNumber = 0;
            HostBaseEntity h = new HostBaseEntity();
            List<HostBaseEntity> hostBaseEntities = db.from(h).select();
            for (IpBlockEntity blockEntity : list) {
                if (blockEntity == null)
                    continue;
                Long start = IP2Long.ipToLong(blockEntity.getBlockStartIp());
                Long end = IP2Long.ipToLong(blockEntity.getBlockEndIp());
                long sum = end - start + 1;
                LOGGER.debug("sum--------------------------------------[{}]", sum);
                count = count + sum;
                for (HostBaseEntity host : hostBaseEntities) {
                    Long ip = IP2Long.ipToLong(host.getHostIp());
                    if (ip <= end && ip >= start) {
                        hostNumber++;
                    }
                }
            }
            // HostBaseEntity h = new HostBaseEntity();
            //  long hostNumber = db.from(h).where(h.CenterName).is(selectDetailsById(id).centerName).selectCount();
            //数据中心下已使用IP总数
            aa.setIpNum(ipCount);
            //数据中心下宿主机数目
            aa.setHostsNum(hostNumber);
            //数据中心IP总数
            aa.setIpTotal(count);
            //数据中心IP使用率
            LOGGER.debug("count--------------------------------------[{}]", count);
            if (count==0) {
                aa.setCenterPct("0");
            } else {
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(0);
                String centerPercent = (numberFormat.format((float) (ipCount) / (float) count * 100));
                float cenPercent = (float) (ipCount) / (float) count * 100;
                LOGGER.debug("cenPercent--------------------------------------[{}]", cenPercent);
                if (cenPercent > 0 && cenPercent < 1) {
                    aa.setCenterPct("1");
                } else {
                    aa.setCenterPct(centerPercent);
                }
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public DataCenterEntity selectDataCenterByCategory(String centerCategory) throws DatabaseException {

        DataCenterEntity p = new DataCenterEntity();
        int i = (int) db.from(p).where(p.centerCategory).is(centerCategory).selectCount();
        if (i > 0)
            return db.from(p).where(p.centerCategory).is(centerCategory).selectFirst();
        else
            return null;
    }

    @Override
    public long selectIpUsedByCenterId(int id) throws DatabaseException {
        IpUseEntity u = new IpUseEntity();
        IpPoolEntity s = new IpPoolEntity();
        IpBlockEntity b = new IpBlockEntity();
        HostBaseEntity h=new HostBaseEntity();
        SubQuery<IpPoolEntity, Integer> i = db.from(s).where(s.centerId).is(id).subQuery(s.getPoolId());
        SubQuery<IpBlockEntity, Integer> q = db.from(b).where(b.poolId).in(i).subQuery(b.blockId);
        long ipCount = db.from(u).where(u.blockId).in(q).selectCount();
       // long hostNumber = db.from(h).where(h.CenterName).is(selectDetailsById(id).centerName).selectCount();
        List<IpBlockEntity> list = db.from(b).where(b.blockId).in(q).select();
        long hostNumber=0;
        List<HostBaseEntity> hostBaseEntities=db.from(h).select();
        for (IpBlockEntity blockEntity : list) {
            if (blockEntity == null)
                continue;
            Long start = IP2Long.ipToLong(blockEntity.getBlockStartIp());
            Long end = IP2Long.ipToLong(blockEntity.getBlockEndIp());
            for (HostBaseEntity host : hostBaseEntities) {
                Long ip = IP2Long.ipToLong(host.getHostIp());
                if (ip <= end && ip >= start) {
                    hostNumber++;
                }
            }
        }
        long ipUsed=ipCount+hostNumber;
        return  ipUsed;
    }

    @Override
    public DataCenterEntity selectDetailsByName(String centerName) throws DatabaseException {
        DataCenterEntity p = new DataCenterEntity();
        int i = (int) db.from(p).where(p.centerName).is(centerName).selectCount();
        if (i > 0)
            return db.from(p).where(p.centerName).is(centerName).selectFirst();
        else
            return null;
    }
}