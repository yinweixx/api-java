package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.MiddlewareDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.MiddlewareEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 14:19
 */
public class MiddlewareDaoImpl extends AbstractIciqlDao implements MiddlewareDao{
    private  final static Logger LOGGER= LoggerFactory.getLogger(MiddlewareDaoImpl.class);
    @Inject
    public MiddlewareDaoImpl(Database database) {
        super(database);
    }

    @Override
    public MiddlewareEntity selectDetailsById(int id) throws DatabaseException {
        MiddlewareEntity p = new MiddlewareEntity();
        int i = (int) db.from(p).where(p.middlewareId).is(id).selectCount();
        if (i > 0)
            return db.from(p).where(p.middlewareId).is(id).selectFirst();
        else
            return null;
    }


    @Override
    public PageDto<MiddlewareEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<MiddlewareEntity> pageDto = new PageDto<>();
        List<MiddlewareEntity> data = null;
        MiddlewareEntity p = new MiddlewareEntity();
        int total = (int) db.from(p).selectCount();

        //判断有无数据
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
     * 条件查询中间件
     * @param index
     * @param limit
     * @param sortBy
     * @param sortDirection
     * @param middlewareIp
     * @param middlewareName
     * @param middlewareState
     * @param middlewareType
     * @return
     */
    @Override
    public PageDto<MiddlewareEntity> selectMiddlewareListByCondition(int index, int limit, String sortBy, String sortDirection, String middlewareIp, String middlewareName, String middlewareState, String middlewareType) {
        PageDto<MiddlewareEntity> pageDto = new PageDto<>();
        String sql1 = "select * from middleware where 1=1";
        if (middlewareIp != null && !middlewareIp.equals("")){
            sql1 += " and middleware_ip like '%"+middlewareIp+"%'" ;
        }
        if (middlewareName !=null && !middlewareName.equals("")){
            sql1 += " and middleware_name like  '%"+middlewareName+"%'";
        }
        if (middlewareState !=null && !middlewareState.equals("")){
            sql1 += " and middleware_state like  '%"+middlewareState+"%'";
        }
        if (middlewareType !=null && !middlewareType.equals("")){
            sql1 += " and middleware_type like  '%"+middlewareType+"%'";
        }

        LOGGER.debug("sql1[{}]",sql1);
        int total ;
        List<MiddlewareEntity>  l= db.executeQuery(MiddlewareEntity.class,sql1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }

        //根据索引条件查询应用
        List<MiddlewareEntity> list ;
        String sql = "select * from middleware where 1=1";
        if (middlewareIp != null && !middlewareIp.equals("")){
            sql += " and middleware_ip like '%"+middlewareIp+"%'" ;
        }
        if (middlewareName !=null && !middlewareName.equals("")){
            sql += " and middleware_name like  '%"+middlewareName+"%'";
        }
        if (middlewareState !=null && !middlewareState.equals("")){
            sql += " and middleware_state like  '%"+middlewareState+"%'";
        }
        if (middlewareType !=null && !middlewareType.equals("")){
            sql += " and middleware_type like  '%"+middlewareType+"%'";
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
        list= db.buildObjects(MiddlewareEntity.class,rs);
        LOGGER.debug("list:[{}]：",list);
        LOGGER.debug("total:[{}]：",total);
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(list);
        return pageDto;

    }

}
