package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.IndexFunctionDao;
import com.anyun.cloud.model.dto.IndexFunctionDto;
import com.anyun.cloud.model.entity.*;
import com.google.inject.Inject;
import com.iciql.SubQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.List;



public class IndexFunctionDaoImpl extends AbstractIciqlDao implements IndexFunctionDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(IndexFunctionDaoImpl.class);

    @Inject
    public IndexFunctionDaoImpl(Database database) {
        super(database);
    }

    @Override
    public DataCenterEntity selectDateNameById(int id) throws DatabaseException {
        DataCenterEntity da = new DataCenterEntity();
        return db.from(da).where(da.centerId).is(id).selectFirst();
    }

    @Override
    public Boolean selectEnvironmentBySid(String sid) throws DatabaseException {

       /* String sql = "select p.environmental " +
                "from pro_basic p left join pro_branch b on p.id =b.project_id " +
                "left join ser_basics s on b.id=s.branch_id  where s.id=" +sid;*/
        ServiceEntity serviceEntity = new ServiceEntity();
        ServiceEntity se = db.from(serviceEntity).where(serviceEntity.id).is(sid).selectFirst();
        ServiceBranchEntity serviceBranchEntity = new ServiceBranchEntity();
        ServiceBranchEntity sb = db.from(serviceBranchEntity).where(serviceBranchEntity.id).is(se.getBranchId()).selectFirst();
        ServiceProjectEntity serviceProjectEntity = new ServiceProjectEntity();
        ServiceProjectEntity sp = db.from(serviceProjectEntity).where(serviceProjectEntity.id).is(sb.getProjectId()).selectFirst();
        return  sp.getEnvironmental();
    }

    @Override
    public IndexFunctionDto selectFunctionNum() throws DatabaseException{
        IndexFunctionDto dto  =new IndexFunctionDto();

        //生产环境
        ServiceProjectEntity sp = new ServiceProjectEntity();
        ServiceBranchEntity sb = new ServiceBranchEntity();
        /*//查询生产环境下服务类型的项目
        ServiceEntity ser = new ServiceEntity();
        SubQuery<ServiceProjectEntity, Integer> i = db.from(sp).where(sp.type).is("service").and(sp.environmental).is(true).subQuery(sp.getId());
        LOGGER.debug("subQuery1:[{}]", i.toString());
        SubQuery<ServiceBranchEntity, Integer> q = db.from(sb).where(sb.projectId).in(i).subQuery(sb.id);
        LOGGER.debug("subQuery2:[{}]", q.toString());
        Long scount = db.from(ser).where(ser.branchId).in(q).selectCount();
        LOGGER.debug("scount:[{}]", scount);
        dto.setService(scount.intValue());*/

        //查询生产环境下任务类型的项目
        TaskBasicsEntity ta = new TaskBasicsEntity();
        SubQuery<ServiceProjectEntity, Integer> it = db.from(sp).where(sp.type).is("task").and(sp.environmental).is(true).subQuery(sp.getId());
        LOGGER.debug("subQuery1:[{}]", it.toString());
        SubQuery<ServiceBranchEntity, Integer> qt = db.from(sb).where(sb.projectId).in(it).subQuery(sb.id);
        LOGGER.debug("subQuery2:[{}]", qt.toString());
        Long tcount = db.from(ta).where(ta.branchId).in(qt).selectCount();
        dto.setTask(tcount.intValue());

        //平台应用数量
        dto.setApplication(0);

        //测试环境
        ServiceProjectEntity tsp = new ServiceProjectEntity();
        ServiceBranchEntity tsb = new ServiceBranchEntity();
        TaskBasicsEntity task = new TaskBasicsEntity();
        SubQuery<ServiceProjectEntity, Integer> ti = db.from(tsp).where(tsp.type).is("task").and(tsp.environmental).is(false).subQuery(tsp.getId());
        LOGGER.debug("testsubQuery1:[{}]", ti.toString());
        SubQuery<ServiceBranchEntity, Integer> tq = db.from(tsb).where(tsb.projectId).in(ti).subQuery(tsb.id);
        LOGGER.debug("testsubQuery2:[{}]", tq.toString());
        Long count = db.from(task).where(task.branchId).in(tq).selectCount();
        dto.setTesttask(count.intValue());

        dto.setTestapplication(0);
        LOGGER.debug("dto:[{}]",dto.asJson());
        return dto;
    }


}
