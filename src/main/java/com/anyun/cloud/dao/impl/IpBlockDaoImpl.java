package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpPoolEntity;
import com.anyun.cloud.model.entity.IpUseEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;


/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 9:03
 */
public class IpBlockDaoImpl extends AbstractIciqlDao implements IpBlockDao{
    private final static Logger LOGGER = LoggerFactory.getLogger(IpBlockDaoImpl.class);

    @Inject
    public IpBlockDaoImpl(Database database) {
        super(database);
    }

    @Override
    public IpBlockEntity selectDetailsById(int id) throws DatabaseException {
        IpBlockEntity p = new IpBlockEntity();
        Long  count= db.from(p).where(p.blockId).is(id).selectCount();
        if(count==0){
            return null;
        }
        return db.from(p).where(p.blockId).is(id).selectFirst();
    }

    /**
     * Ip池下Ip段的显示
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @return
     */
    @Override
    public PageDto<IpBlockEntity> selectBlockList(int index, int limit, String sortBy, String sortDirection, int poolId) {
        PageDto<IpBlockEntity> pageDto = new PageDto<>();
        List<IpBlockEntity> data = null;
        IpBlockEntity p = new IpBlockEntity();
        IpUseEntity u =new IpUseEntity();
        int total = (int) db.from(p).where(p.poolId).is(poolId).selectCount();
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).where(p.poolId).is(poolId).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).where(p.poolId).is(poolId).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }

        for (IpBlockEntity aa: data) {
            LOGGER.debug("data:[{}]", data);
            int id =aa.getBlockId();
            LOGGER.debug("id:[{}]", id);
            String category =aa.getBlockCategory();
            LOGGER.debug("category:[{}]", category);
            if(category.equals("业务")) {
                //查询每个业务IP段下已使用的IP个数
                long usedNum = (int) db.from(u).where(u.blockId).is(id).selectCount();
                aa.setUsedCount(usedNum);
                //获取每个IP段下未使用的IP个数
                String start = aa.getBlockStartIp();
                String end = aa.getBlockEndIp();
                long sum = IP2Long.ipToLong(end) - IP2Long.ipToLong(start) + 1;
                LOGGER.debug("sum:[{}]", sum);
                LOGGER.debug("end:[{}]", IP2Long.ipToLong(end));
                LOGGER.debug("start:[{}]", IP2Long.ipToLong(start));
                aa.setUnusedCount(sum - usedNum);
            }//获取宿主机Ip段下Ip使用情况
           else{
                int hostIpNumber=0;
                LOGGER.debug("hostIpNumber:[{}]", hostIpNumber);
                Long start= IP2Long.ipToLong(aa.getBlockStartIp()) ;
                Long end= IP2Long.ipToLong(aa.getBlockEndIp()) ;
                long sum = end-start + 1;
                LOGGER.debug("end:[{}]", end);
                HostBaseEntity  h=new HostBaseEntity();
                List<HostBaseEntity> hostBaseEntities=db.from(h).select();
                for(HostBaseEntity host : hostBaseEntities){
                    Long ip=IP2Long.ipToLong(host.getHostIp());
                    if(ip<=end && ip >= start){
                        hostIpNumber++;
                    }
                }
                aa.setUsedCount(hostIpNumber);
                aa.setUnusedCount(sum-hostIpNumber);
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public void deleteById(int id) throws DatabaseException {
        IpBlockEntity p = new IpBlockEntity();
        db.from(p).where(p.blockId).is(id).delete();
    }

    @Override
    public IpBlockEntity insert(IpBlockEntity p) throws DatabaseException {
        long key = db.insertAndGetKey(p);
        return db.from(p).where(p.blockId).is((int) key).select().get(0);
    }

    @Override
    public IpBlockEntity update(IpBlockEntity p) throws DatabaseException {
        db.update(p);
        int id = p.getBlockId();
        p = new IpBlockEntity();
        return db.from(p).where(p.blockId).is(id).selectFirst();
    }

    /**
     * 分页查询
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Override
    public PageDto<IpBlockEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        PageDto<IpBlockEntity> pageDto = new PageDto<>();
        List<IpBlockEntity> data = null;
        IpBlockEntity p = new IpBlockEntity();
        int total = (int) db.from(p).selectCount();
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    /**
     * 条件查询Ip段
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolId
     * @param blockStartIp
     * @param blockEndIp
     * @param blockGateway
     * @param blockNetMask
     * @param blockCategory
     * @return
     */
    @Override
    public PageDto<IpBlockEntity> selectBlockListByCondition(int index, int limit, String sortBy, String sortDirection, int poolId, String blockStartIp, String blockEndIp, String blockGateway, String blockNetMask, String blockCategory) {
        PageDto<IpBlockEntity> pageDto = new PageDto<>();
        String sql1 = "select * from ip_block where pool_id="+poolId;
        if (blockStartIp != null && !blockStartIp.equals("")){
            sql1 += " and startIp like '%"+blockStartIp+"%'" ;
        }
        if (blockEndIp !=null && !blockEndIp.equals("")){
            sql1 += " and endIp like  '%"+blockEndIp+"%'";
        }
        if ( blockGateway!=null && !blockGateway.equals("")){
            sql1 += " and gateway like  '%"+blockGateway+"%'";
        }
        if (blockNetMask !=null && !blockNetMask.equals("")){
            sql1 += " and netMask like  '%"+blockNetMask+"%'";
        }
        if (blockCategory !=null && !blockCategory.equals("")){
            sql1 += " and block_category like  '%"+blockCategory+"%'";
        }
        LOGGER.debug("sql1[{}]",sql1);
        int total ;
        List<IpBlockEntity>  l= db.executeQuery(IpBlockEntity.class,sql1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }

        //根据索引条件查询IP段
        List<IpBlockEntity> list ;

        String sql = "select *from ip_block where pool_id="+poolId;
        if (blockStartIp != null && !blockStartIp.equals("")){
            sql += " and startIp like '%"+blockStartIp+"%'" ;
        }
        if (blockEndIp !=null && !blockEndIp.equals("")){
            sql += " and endIp like  '%"+blockEndIp+"%'";
        }
        if ( blockGateway!=null && !blockGateway.equals("")){
            sql += " and gateway like  '%"+blockGateway+"%'";
        }
        if (blockNetMask !=null && !blockNetMask.equals("")){
            sql += " and netMask like  '%"+blockNetMask+"%'";
        }
        if (blockCategory !=null && !blockCategory.equals("")){
            sql += " and block_category like  '%"+blockCategory+"%'";
        }
        if (sortDirection.equals("desc") && !sortDirection.equals("")) {
            if (sortBy.equals(""))
                sortBy = "id";
            sql += " order by " + sortBy + " desc limit  " + limit + " offset " + (index - 1) * limit;
        } else {
            sql += " order by " + sortBy + " limit  " + limit + " offset " + (index - 1) * limit;
        }
        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
        list= db.buildObjects(IpBlockEntity.class,rs);
        LOGGER.debug("list:[{}]：",list);
        LOGGER.debug("total:[{}]：",total);
        //Ip段使用情况统计
        IpUseEntity u =new IpUseEntity();
        for (IpBlockEntity aa: list) {
            LOGGER.debug("data:[{}]", list);
            int id = aa.getBlockId();
            LOGGER.debug("id:[{}]", id);
            //查询每个IP段下已使用的IP个数
            long usedNum = (int) db.from(u).where(u.blockId).is(id).selectCount();
            LOGGER.debug("usedNum:[{}]", usedNum);
                Long start = IP2Long.ipToLong(aa.getBlockStartIp());
                Long end = IP2Long.ipToLong(aa.getBlockEndIp());
                long sum = end - start + 1;
                aa.setUsedCount(usedNum);
                aa.setUnusedCount(sum -usedNum);
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(list);
        return pageDto;
    }

    @Override
    public IpBlockEntity selectIpBlockByPoolId(int id) throws DatabaseException {
        IpBlockEntity p = new IpBlockEntity();
        return db.from(p).where(p.poolId).is(id).selectFirst();
    }
}



