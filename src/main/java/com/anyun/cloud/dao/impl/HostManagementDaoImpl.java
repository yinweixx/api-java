package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.tools.IP2Long;
import com.anyun.cloud.dao.DataCenterDao;
import com.anyun.cloud.dao.HostManagementDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.UnregisteredHostsDto;
import com.anyun.cloud.model.dto.host.Host;
import com.anyun.cloud.model.entity.*;
import com.anyun.cloud.service.HostManagementService;
import com.google.inject.Inject;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;



/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/24 9:27
 */
public class HostManagementDaoImpl extends AbstractIciqlDao implements HostManagementDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(HostManagementDaoImpl.class);
    private HostManagementService hostManagementService;
    private Context context;
    private DataCenterDao dataCenterDao;

    @Inject
    public HostManagementDaoImpl(Context context, Database database) {
        super(database);
        this.context = context;
        this.hostManagementService = context.getBeanByClass(HostManagementService.class);
        this.dataCenterDao = context.getBeanByClass(DataCenterDao.class);
    }

    @Override
    public HostBaseEntity selectDetailsById(int id) throws DatabaseException {
        HostBaseEntity p = new HostBaseEntity();
        return db.from(p).where(p.hostId).is(id).select().get(0);

    }

    @Override
    public HostBaseEntity insert(HostBaseEntity h) throws DatabaseException {
        long key = db.insertAndGetKey(h);
        LOGGER.debug("key-----------------------------------[{}]", key);
        return db.from(h).where(h.hostId).is((int) key).select().get(0);

    }

    @Override
    public HostLxdEntity selectLxdDetailsById(int id) {
        HostLxdEntity p = new HostLxdEntity();
        return db.from(p).where(p.hostId).is(id).select().get(0);
    }

    @Override
    public HostLxdEntity insert(HostLxdEntity hostLxdEntity) throws DatabaseException {
        db.insert(hostLxdEntity);
        HostLxdEntity h = new HostLxdEntity();
        return db.from(h).where(h.hostId).is(hostLxdEntity.getHostId()).selectFirst();

    }

    @Override
    public HostDockerEntity insert(HostDockerEntity hostDockerEntity) throws DatabaseException {
        db.insert(hostDockerEntity);
        HostDockerEntity h = new HostDockerEntity();
        return db.from(h).where(h.hostId).is(hostDockerEntity.getHostId()).selectFirst();
    }

    @Override
    public HostDockerEntity selectDockerDetailsById(int id) {
        HostDockerEntity p = new HostDockerEntity();
        return db.from(p).where(p.hostId).is(id).select().get(0);
    }

    @Override
    public HostSoftwareEntity insert(HostSoftwareEntity hostSoftwareEntity) throws DatabaseException {
        db.insert(hostSoftwareEntity);
        HostSoftwareEntity h = new HostSoftwareEntity();
        return db.from(h).where(h.hostId).is(hostSoftwareEntity.getHostId()).selectFirst();
    }

    @Override
    public HostBaseEntity selectHostBaseEntityByHostIp(String ip) {
        HostBaseEntity h = new HostBaseEntity();
        long count = db.from(h).where(h.hostIp).is(ip).selectCount();
        if (count == 0)
            return null;
        return db.from(h).where(h.hostIp).is(ip).selectFirst();
    }

    @Override
    public HostSoftwareEntity selectSoftwareDetailsById(int id) {
        HostSoftwareEntity p = new HostSoftwareEntity();
        return db.from(p).where(p.hostId).is(id).select().get(0);

    }

    @Override
    public HostHardwareEntity insert(HostHardwareEntity hostHardwareEntity) throws DatabaseException {
        db.insert(hostHardwareEntity);
        HostHardwareEntity h = new HostHardwareEntity();
        return db.from(h).where(h.hostId).is(hostHardwareEntity.getHostId()).selectFirst();
    }

    @Override
    public HostHardwareEntity selectHardwareDetailsById(int id) {
        HostHardwareEntity p = new HostHardwareEntity();
        return db.from(p).where(p.hostId).is(id).select().get(0);
    }

    /**
     * 分页显示
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Override
    public PageDto<HostBaseEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<HostBaseEntity> pageDto = new PageDto<>();
        List<HostBaseEntity> data = null;
        HostBaseEntity p = new HostBaseEntity();

        int total = (int) db.from(p).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        //页面统计信息显示
        List<IpBlockEntity> list;
        String sql = "select * from ip_block";
        LOGGER.debug("sql[{}]", sql);
        ResultSet rs = db.executeQuery(sql);
        list = db.buildObjects(IpBlockEntity.class, rs);
        LOGGER.debug("list:[{}]：", list);
        //判断宿主机状态（是否可用）和查询属于哪个数据中心
        for (HostBaseEntity bb : data) {
            String a = bb.getHostIp();
            LOGGER.debug("a:[{}]", a);
            long o = IP2Long.ipToLong(a);
            LOGGER.debug("o:[{}]", o);
            String status = "运行故障";
            List<Host> list1 = hostManagementService.getHosts();
            LOGGER.debug("b:[{}]", list1);
            for (Host h : list1) {
                if (a.equals(h.getAddress())) {
                    status = "正常运行";
                }
            }
            for (IpBlockEntity i : list) {
                long start = IP2Long.ipToLong(i.getBlockStartIp());
                long end = IP2Long.ipToLong(i.getBlockEndIp());
                if (o <= end && o >= start) {
                    bb.setBlockId(i.getBlockId());
                    IpPoolEntity m = new IpPoolEntity();
                    int pool = i.getPoolId();
                    LOGGER.debug("poolId________________________________________:[{}]", pool);
                    List<IpPoolEntity> data1 = db.from(m).where(m.poolId).is(pool).select();
                    LOGGER.debug("data1________________________________________:[{}]", data1);
                /*    for (IpPoolEntity cc : data1) {
                      //  String centerName = cc.getCenterName();
                        LOGGER.debug("centerName________________________________________:[{}]", centerName);
                        bb.setCenterName(centerName);
                    }*/
                    break;
                }
                bb.setCenterName("无");
            }
            LOGGER.debug("status:[{}]", status);
            bb.setState(status);
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    @Override
    public void cancelById(int id) throws DatabaseException {
        HostBaseEntity p = new HostBaseEntity();
        HostLxdEntity m = new HostLxdEntity();
        HostDockerEntity n = new HostDockerEntity();
        HostHardwareEntity w = new HostHardwareEntity();
        HostSoftwareEntity s = new HostSoftwareEntity();
        IpUseEntity i = new IpUseEntity();
        db.from(p).where(p.hostId).is(id).delete();
        db.from(m).where(m.hostId).is(id).delete();
        db.from(w).where(w.hostId).is(id).delete();
        db.from(s).where(s.hostId).is(id).delete();
        db.from(n).where(n.hostId).is(id).delete();
        db.from(i).where(i.hostId).is(id).delete();

    }

    /**
     * 根据条件分页查询
     *
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param ip
     * @return
     */
    @Override
    public PageDto<HostBaseEntity> selectHostsListByCondition(int index, int limit, String sortBy, String sortDirection, String ip, String environment, String category, String centerName) {
        PageDto<HostBaseEntity> pageDto = new PageDto<>();
        String sql1 = "select * from host_base where 1=1";
        if (ip != null && !ip.equals("")) {
            sql1 += " and host_ip like '%" + ip + "%'";
        }
        if (environment != null && !environment.equals("")) {
            sql1 += " and environment like  '%" + environment + "%'";
        }
        if (category != null && !category.equals("")) {
            sql1 += " and category like  '%" + category + "%'";
        }
        LOGGER.debug("sql1[{}]", sql1);
        int total;
        List<HostBaseEntity> l = db.executeQuery(HostBaseEntity.class, sql1);
        if (l == null) {
            total = 0;
        } else {
            total = l.size();
        }

        //根据条件查询宿主机
        List<HostBaseEntity> list;
        String sql = "select *from host_base where 1=1";
        if (ip != null && !ip.equals("")) {
            sql += " and host_ip like '%" + ip + "%'";
        }
        if (environment != null && !environment.equals("")) {
            sql += " and environment like  '%" + environment + "%'";
        }
        if (category != null && !category.equals("")) {
            sql += " and category like  '%" + category + "%'";
        }

        if (sortDirection.equals("desc") && !sortDirection.equals("")) {
            if (sortBy.equals(""))
                sortBy = "id";
            sql += " order by " + sortBy + " desc limit  " + limit + " offset " + (index - 1) * limit;
        } else {
            sql += " order by " + sortBy + " limit  " + limit + " offset " + (index - 1) * limit;
        }
        LOGGER.debug("sql[{}]", sql);
        ResultSet rs = db.executeQuery(sql);
        list = db.buildObjects(HostBaseEntity.class, rs);
        LOGGER.debug("list:[{}]：", list);
        LOGGER.debug("total:[{}]：", total);
        //判断宿主机状态（是否可用）和查询属于哪个数据中心
        List<IpBlockEntity> list1;
        String sql2 = "select * from ip_block";
        LOGGER.debug("sql2[{}]", sql2);
        ResultSet rs1 = db.executeQuery(sql2);
        list1 = db.buildObjects(IpBlockEntity.class, rs1);
        LOGGER.debug("list1:[{}]：", list1);

        for (HostBaseEntity bb : list) {
            String a = bb.getHostIp();
            LOGGER.debug("a:[{}]", a);
            long o = IP2Long.ipToLong(a);
            LOGGER.debug("o:[{}]", o);
            String status = "运行故障";
            List<Host> list2 = hostManagementService.getHosts();
            LOGGER.debug("list2---------------------------------------------------------------:[{}]", list2);
            for (Host h : list2) {
                if (a.equals(h.getAddress())) {
                    status = "正常运行";
                }
            }

            for (IpBlockEntity i : list1) {
                long start = IP2Long.ipToLong(i.getBlockStartIp());
                long end = IP2Long.ipToLong(i.getBlockEndIp());
                if (o <= end && o >= start) {
                    bb.setBlockId(i.getBlockId());
                    IpPoolEntity m = new IpPoolEntity();
                    int pool = i.getPoolId();
                    LOGGER.debug("poolId________________________________________:[{}]", pool);
                    List<IpPoolEntity> data1 = db.from(m).where(m.poolId).is(pool).select();
                    LOGGER.debug("data1________________________________________:[{}]", data1);
                    for (IpPoolEntity cc : data1) {
                        String cenName = dataCenterDao.selectDetailsById(cc.getCenterId()).centerName;
                        String en = cc.getEnvironment();
                        String cate = cc.getCategory();
                        LOGGER.debug("centerName________________________________________:[{}]", cenName);
                        bb.setCenterName(cenName);
                        bb.setEnvironment(en);
                        bb.setCategory(cate);
                    }
                    break;
                }
                bb.setCenterName("无");
            }
            bb.setState(status);
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(list);
        return pageDto;
    }

    @Override
    public long selectHostsNumByBlockId(int id) throws DatabaseException {
        HostBaseEntity p = new HostBaseEntity();
        return db.from(p).where(p.blockId).is(id).selectCount();
    }

    @Override
    public List<HostBaseEntity> selectHostsListByCenterName(int centerId) {

        List<HostBaseEntity> data = null;
        HostBaseEntity p = new HostBaseEntity();
        if (centerId == 0) {
            data = db.from(p).select();
        } else {

            data = db.from(p).where(p.centerId).is(centerId).select();

        }
        LOGGER.debug("data:-----------------------------------------------------------------------[{}]",JSONArray.fromObject(data));
        List<HostBaseEntity> list = new ArrayList<>();
        List<Host> list2 = hostManagementService.getHosts();
        for (HostBaseEntity bb : data) {
            String a = bb.getHostIp();
            LOGGER.debug("a:[{}]", a);
            long o = IP2Long.ipToLong(a);
            LOGGER.debug("o:[{}]", o);
            LOGGER.debug("list2---------------------------------------------------------------:[{}]", list2);
            for (Host h : list2) {
                if (a.equals(h.getAddress())) {
                    list.add(bb);
                }
            }
        }
        return list;
    }

    @Override
    public IpUseEntity insert(IpUseEntity ipUseEntity) throws DatabaseException {
        db.insert(ipUseEntity);
        IpUseEntity h = new IpUseEntity();
        return db.from(h).selectFirst();
    }

    @Override
    public PageDto<UnregisteredHostsDto> selectHostsList(int index, int limit) {
        PageDto<UnregisteredHostsDto> pageDto = new PageDto<>();
        List<Host> list = hostManagementService.getHosts();
        LOGGER.debug("list------------------------------------------------:[{}]", list);
        List<UnregisteredHostsDto> l = new ArrayList<>();
        list.forEach(x -> {
            HostBaseEntity b = selectHostBaseEntityByHostIp(x.getAddress());
            if (b == null) {
                UnregisteredHostsDto u = new UnregisteredHostsDto();
                u.setName(x.getName());
                u.setAddress(x.getAddress());
                u.setHost(x.getHost());
                l.add(u);
            }
        });
        LOGGER.debug("list------------------------------------------------:[{}]", JSONArray.fromObject(l));
        int  pageBegin=index<=1 ?0 :(limit-1)*limit;
        List<UnregisteredHostsDto> data = l.subList(index<=1 ?0 :(limit-1)*limit, index * limit> l.size() ?l.size():pageBegin + limit);
   //     LOGGER.debug("data------------------------------------------------:[{}]",  JSONArray.fromObject(l.subList(0,2)));
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(l.size());
        pageDto.setData(data);
        return pageDto;
    }
}