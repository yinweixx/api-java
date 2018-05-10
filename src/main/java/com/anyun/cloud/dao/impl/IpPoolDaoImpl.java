package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.IpPoolDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.model.entity.HostBaseEntity;
import com.anyun.cloud.model.entity.IpBlockEntity;
import com.anyun.cloud.model.entity.IpPoolEntity;
import com.anyun.cloud.model.param.HostDistributionParam;
import com.google.inject.Inject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 19:24
 */
public class IpPoolDaoImpl extends AbstractIciqlDao implements IpPoolDao {
    private  final static Logger LOGGER= LoggerFactory.getLogger(IpPoolDaoImpl.class);
    @Inject
    public IpPoolDaoImpl(Database database) {
        super(database);
    }

    @Override
    public IpPoolEntity selectDetailsById(int id) throws DatabaseException {
        IpPoolEntity p = new IpPoolEntity();
        int i = (int) db.from(p).where(p.poolId).is(id).selectCount();
        if (i > 0)
            return db.from(p).where(p.poolId).is(id).selectFirst();
        else
            return null;
    }
    @Override
    public void deleteById(int id) throws DatabaseException {
        IpPoolEntity p =new IpPoolEntity();
        db.from(p).where(p.poolId).is(id).delete();
    }

    @Override
    public IpPoolEntity insert(IpPoolEntity p) throws DatabaseException {
        long key = db.insertAndGetKey(p);
        return db.from(p).where(p.poolId).is((int) key).select().get(0);
    }

    @Override
    public IpPoolEntity update(IpPoolEntity p) throws DatabaseException {
        db.update(p);
        int id = p.getPoolId();
        p = new IpPoolEntity();
        return db.from(p).where(p.poolId).is(id).selectFirst();
    }

    /**
     * 分页显示IP池信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @return
     */
    @Override
    public PageDto<IpPoolEntity> selectPageList(int index, int limit, String sortBy, String sortDirection,int centerId) {
        //定义变量
        PageDto<IpPoolEntity> pageDto = new PageDto<>();
        List<IpPoolEntity> data = null;
        IpPoolEntity p = new IpPoolEntity();
        int total = (int) db.from(p).where(p.centerId).is (centerId).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).where(p.centerId).is (centerId).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(p).where(p.centerId).is (centerId).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        return pageDto;
    }

    /**
     * 条件查询IP池信息
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param poolName
     * @param environment
     * @param category
     * @param centerId
     * @return
     */
    @Override
    public PageDto<IpPoolEntity> selectPoolListByCondition(int index, int limit, String sortBy, String sortDirection, String poolName, String environment, String category,int centerId) {
        PageDto<IpPoolEntity> pageDto = new PageDto<>();

        String sql1 = "select *  from ip_pool where centerId="+centerId;
        if (poolName != null && !poolName.equals("")){
            sql1 += " and pool_name like '%"+poolName+"%'" ;
        }
        if (environment !=null && !environment.equals("")){
            sql1 += " and environment like  '%"+environment+"%'";
        }
        if (category !=null && !category.equals("")){
            sql1 += " and category like  '%"+category+"%'";
        }


        LOGGER.debug("sql1[{}]",sql1);
        int total ;
        List<IpPoolEntity>  l= db.executeQuery(IpPoolEntity.class,sql1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }

        //根据索引条件查询应用
        List<IpPoolEntity> list ;
        String sql = "select *  from ip_pool where centerId="+centerId;
        if (poolName != null && !poolName.equals("")) {
            sql += " and pool_name like '%" + poolName + "%'";
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
        LOGGER.debug("sql:--------------------------------------------------------------------------------[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
        list= db.buildObjects(IpPoolEntity.class,rs);
        DataCenterEntity da = new DataCenterEntity();
        for ( IpPoolEntity ip: list) {
            da = db.from(da).where(da.centerId).is(centerId).selectFirst();
            LOGGER.debug("da:[{}]：", JSONSerializer.toJSON(da));
            ip.setCenterName(da.getCenterName());
            LOGGER.debug("ip:[{}]：", JSONSerializer.toJSON(ip));
        }
        LOGGER.debug("list:[{}]：", JSONSerializer.toJSON(list).toString());
        LOGGER.debug("total:[{}]：",total);
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(list);
        return pageDto;
    }

    /**
     * 查询IP池id--（调度使用）
     * @param param
     * @return
     * @throws DatabaseException
     */
//    @Override
//    public List<IpPoolEntity> selectPoolIdByCondition(HostDistributionParam param) throws DatabaseException {
//        String sql = "select id from ip_pool where category = '" + param.getHostCategory()
//                +"' and centerId = (select id from data_center where dns_name = '"+ param.getDnsName()+"')";
//        LOGGER.debug("sql[{}]",sql);
//        List<IpPoolEntity> list = db.executeQuery(IpPoolEntity.class,sql);
//
//        return list;
//    }

    /**
     *  查询IP段信息--（调度使用）
     * @param blockCategory
     * @param poolId
     * @return
     * @throws DatabaseException
     */
//    @Override
//    public List<IpBlockEntity> selectIpBlockByCondition(String blockCategory, int poolId) throws DatabaseException {
//        IpBlockEntity i= new IpBlockEntity();
//        List<IpBlockEntity> list = db.from(i).where(i.blockCategory).is(blockCategory).and(i.poolId).is(poolId).select();
//        return list;
//    }

    /**
     * 查询宿主机信息--（调度使用）
     * @return
     * @throws DatabaseException
     */
//    @Override
//    public List<HostBaseEntity> selectHostBase() throws DatabaseException {
//        HostBaseEntity h = new HostBaseEntity();
//        return db.from(h).select();
//    }

    /**
     * 根据dns名称查询数据中心信息--（调度使用）
     * @param dnsName
     * @return
     * @throws DatabaseException
     */
    @Override
    public DataCenterEntity selectDataCenterByDns(String dnsName) throws DatabaseException {
        DataCenterEntity d  =  new DataCenterEntity();
        return db.from(d).where(d.dnsName).is(dnsName).selectFirst();
    }

    /**
     * 根据条件查询宿主机信息--（调度使用）
     * @param hostCategory
     * @param environment
     * @param centerName
     * @return
     * @throws DatabaseException
     */
    @Override
    public List<HostBaseEntity> selectHostBase(String hostCategory, String environment, String centerName) throws DatabaseException {
        HostBaseEntity h = new HostBaseEntity();
        List<HostBaseEntity> list =
                db.from(h).where(h.category).is(hostCategory)
                        .and(h.CenterName).is(centerName).and(h.environment).is(environment).select();
        return list;
    }

}
