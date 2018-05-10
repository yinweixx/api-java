package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.dao.AppBasicsDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.*;
import com.google.inject.Inject;
import com.iciql.SubQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppBasicsDaoImpl extends AbstractIciqlDao implements AppBasicsDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(AppBasicsDaoImpl.class);

    @Inject
    public AppBasicsDaoImpl(Database database) {
        super(database);
    }

    @Override
    public AppBasicsEntity insert(AppBasicsEntity a) throws DatabaseException {
        LOGGER.debug("AppBasicsEntity:[{}]", a);
        Long key = db.insertAndGetKey(a);
        LOGGER.debug("key:" + key);
        return db.from(a).where(a.getAppId()).is(key).select().get(0);
    }

    @Override
    public AppBasicsEntity update(AppBasicsEntity a) throws DatabaseException {
        AppBasicsEntity app = new AppBasicsEntity();
        db.from(app).set(app.getName()).to(a.getName()).set(app.getDesc()).to(a.getDesc()).set(app.getLastModifyTime())
                .to(a.getLastModifyTime()).where(app.getAppId()).is(a.getAppId()).update();
        Long id = a.getAppId();
        a = new AppBasicsEntity();
        return db.from(a).where(a.getAppId()).is(id).selectFirst();
    }

    @Override
    public void deleteById(long id) throws DatabaseException {
        AppBasicsEntity a = new AppBasicsEntity();
        db.from(a).where(a.getAppId()).is(id).delete();
    }

    @Override
    public PageDto<AppBasicsEntity> selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<AppBasicsEntity> pageDto = new PageDto<>();
        List<AppBasicsEntity> data = null;
        AppBasicsEntity a = new AppBasicsEntity();
        //api相关参数类--做关联（查询数量）
        ApiInfoEntity api = new ApiInfoEntity();
        //服务相关参数类--做关联（查询数量）
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int total = (int) db.from(a).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(a).orderByDesc(sortBy).limit(limit).offset((index - 1) * limit).select();
            } else {//正序
                data = db.from(a).orderBy(sortBy).limit(limit).offset((index - 1) * limit).select();
            }
        }

        for (AppBasicsEntity aa : data) {
            LOGGER.debug("data:[{}]", data);
            long id = aa.getAppId();
            LOGGER.debug("id:[{}]", id);
            //查询每个应用下api数量
            Long apiCount = db.from(api).where(api.getAppId()).is(id).selectCount();
            aa.setApiNum(apiCount);

            //查询每个应用下服务数量
            SubQuery<ServiceProjectEntity, Integer> i = db.from(p).where(p.appId).is(id).subQuery(p.getId());
            LOGGER.debug("subQuery1:[{}]", i.toString());
            SubQuery<ServiceBranchEntity, Integer> q = db.from(b).where(b.projectId).in(i).subQuery(b.id);
            LOGGER.debug("subQuery2:[{}]", q.toString());
            Long scount = db.from(s).where(s.branchId).in(q).selectCount();
            LOGGER.debug("scount:[{}]", scount);
            aa.setServiceNum(scount);

            //查询每个应用下任务数量
            Long tcount = Long.valueOf(0);
            aa.setTaskNum(tcount);
        }

        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug("pagedto:[{}]：", pageDto.asJson());

        return pageDto;
    }

    @Override
    public AppBasicsEntity selectDetailsByName(String name) throws DatabaseException {
        AppBasicsEntity a = new AppBasicsEntity();
        return db.from(a).where(a.getName()).is(name).selectFirst();
    }

    @Override
    public AppBasicsEntity selectAppNameById(Long appId) throws DatabaseException {
        AppBasicsEntity a = new AppBasicsEntity();
        return db.from(a).where(a.getAppId()).is(appId).selectFirst();
    }

    @Override
    public List<AppBasicsEntity> selectVagueListByName(String condition) throws DatabaseException {
        List<AppBasicsEntity> list =
                db.executeQuery(AppBasicsEntity.class, "select * from app_basics where name like ?", "%" + condition + "%");
        return list;
    }

    @Override
    public PageDto<AppBasicsEntity> selectAppListByCondition(int index, int limit, String sortBy, String sortDirection, String name, String shortName, String startTime, String endTime) {
        PageDto<AppBasicsEntity> pageDto = new PageDto<>();

        String sql1 = "select * from app_basics where 1=1";
        if (name != null && !name.equals("")) {
            sql1 += " and name like '%" + name + "%'";
        }
        if (shortName != null && !shortName.equals("")) {
            sql1 += " and short_name like  '%" + shortName + "%'";
        }
        if (startTime != null && !startTime.equals("") && !endTime.equals("") && endTime != null) {
            sql1 += " and create_time between '" + startTime + "' and '" + endTime + "'";
        }
        LOGGER.debug("sql1[{}]", sql1);
        int total;
        List<AppBasicsEntity> l = db.executeQuery(AppBasicsEntity.class, sql1);
        if (l == null) {
            total = 0;
        } else {
            total = l.size();
        }

        //根据索引条件查询应用
        List<AppBasicsEntity> list;
        String sql = "select *from app_basics where 1=1";
        if (name != null && !name.equals("")) {
            sql += " and name like '%" + name + "%'";
        }
        if (shortName != null && !shortName.equals("")) {
            sql += " and short_name like  '%" + shortName + "%'";
        }
        if (startTime != null && !startTime.equals("") && !endTime.equals("") && endTime != null) {
            sql += " and create_time between '" + startTime + "' and '" + endTime + "'";
        }
        if (sortDirection.equals("desc") && !sortDirection.equals("")) {
            if (sortBy.equals(""))
                sortBy = "id";
            sql += " order by " + sortBy + " desc limit  " + limit + " offset " + (index - 1) * limit;
        } else {
            if (sortBy.equals(""))
                sortBy = "id";
            sql += " order by " + sortBy + " limit  " + limit + " offset " + (index - 1) * limit;
        }
        LOGGER.debug("sql[{}]", sql);
        ResultSet rs = db.executeQuery(sql);
        list = db.buildObjects(AppBasicsEntity.class, rs);
        LOGGER.debug("list:[{}]：", list);

        LOGGER.debug("total:[{}]：", total);
        //api相关参数类--做关联（查询数量）
        ApiInfoEntity api = new ApiInfoEntity();
        //服务相关参数类--做关联（查询数量）
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();

        //循环获取id查询数量
        for (AppBasicsEntity aa : list) {
            LOGGER.debug("data:[{}]", list);
            long id = aa.getAppId();
            LOGGER.debug("id:[{}]", id);
            //查询每个应用下api数量
            Long apiCount = db.from(api).where(api.getAppId()).is(id).selectCount();
            aa.setApiNum(apiCount);

            //查询每个应用下服务数量
            SubQuery<ServiceProjectEntity, Integer> i = db.from(p).where(p.appId).is(id).subQuery(p.getId());
            LOGGER.debug("subQuery1:[{}]", i.toString());
            SubQuery<ServiceBranchEntity, Integer> q = db.from(b).where(b.projectId).in(i).subQuery(b.id);
            LOGGER.debug("subQuery2:[{}]", q.toString());
            Long scount = db.from(s).where(s.branchId).in(q).selectCount();
            LOGGER.debug("scount:[{}]", scount);
            aa.setServiceNum(scount);

            //查询每个应用下任务数量
            Long tcount = Long.valueOf(0);
            aa.setTaskNum(tcount);
        }

        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(list);
        LOGGER.debug("pagedto:[{}]：", pageDto.asJson());

        return pageDto;
    }

    @Override
    public AppBasicsEntity selectAppBasicsEntityById(Long appId) {
        AppBasicsEntity a = new AppBasicsEntity();
        Long count = db.from(a).where(a.getAppId()).is(appId).selectCount();
        if (count > 0)
            return db.from(a).where(a.getAppId()).is(appId).selectFirst();
        return null;
    }
}
