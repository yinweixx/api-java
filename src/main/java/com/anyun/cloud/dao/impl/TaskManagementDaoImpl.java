package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.dao.TaskManagementDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.TaskDetailDto;
import com.anyun.cloud.model.dto.TaskListDto;
import com.anyun.cloud.model.entity.ServiceBranchEntity;
import com.anyun.cloud.model.entity.ServiceProjectEntity;
import com.anyun.cloud.model.entity.TaskBasicsEntity;
import com.anyun.cloud.model.entity.TaskTimedEntity;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TaskManagementDaoImpl extends AbstractIciqlDao implements TaskManagementDao{
    final static Logger LOGGER= LoggerFactory.getLogger(TaskManagementDaoImpl.class);
    @Inject
    public TaskManagementDaoImpl(Database database) {
        super(database);
    }

    @Override
    public void updateService(String id, boolean status) {
        TaskBasicsEntity t = new TaskBasicsEntity();
        db.from(t).set(t.status).to(status).where(t.id).is(id).update();
    }

    @Override
    public Object QueryConditionService(int index, int limit, String sortBy, String sortDirection, String taskName, String shortUrl, String branchName, String projectName) {
        PageDto<TaskListDto> pageDto = new PageDto<>();
        List<TaskListDto> data ;
        String sql = "SELECT *,b.name as branchName,p.name as projectName,b.id as bid,t.branch_id as branchId,b.project_id as projectId,p.id as pid,p.app_id as appId FROM task_basics t left join pro_branch b on b.id = t.branch_id left join pro_basic p on p.id = b.project_id where 1=1";
        if(taskName !=null && !taskName.equals("")){
            sql += " and t.name like '%"+taskName+"%'" ;
        }
        if(branchName !=null && !branchName.equals("")){
            sql += " and b.name like '%"+branchName+"%'" ;
        }
        if(projectName !=null &&!projectName.equals("")){
            sql += " and p.name like '%"+projectName+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        if (sortDirection.equals("desc") && !sortDirection.equals("")){
            if (sortBy.equals(""))
                sortBy= "p.id";
            sql +=" order by "+sortBy+" desc limit  "+limit+" offset "+(index-1)*limit;
        }
        else {
            if (sortBy.equals(""))
                sortBy= "p.id";
            sql +=" order by "+sortBy+" limit  "+limit+" offset "+(index-1)*limit;
        }
        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
//        LOGGER.debug("ResultSet rs:[{}]", JsonUtil.toJson(rs));
        data = db.buildObjects(TaskListDto.class, rs);
        LOGGER.debug("data:",data);

        String sql1 = "SELECT *,b.name as branchName,p.name as projectName,b.id as bid,t.branch_id as branchId,b.project_id as projectId,p.id as pid,p.app_id as appId FROM task_basics t left join pro_branch b on b.id = t.branch_id left join pro_basic p on p.id = b.project_id where 1=1";
        if(taskName !=null && !taskName.equals("")){
            sql1 += " and t.name like '%"+taskName+"%'" ;
        }
        if(branchName !=null && !branchName.equals("")){
            sql1 += " and b.name like '%"+branchName+"%'" ;
        }
        if(projectName !=null &&!projectName.equals("")){
            sql1 += " and p.name like '%"+projectName+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql1 += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        int total ;
        List<TaskListDto>  l= db.executeQuery(TaskListDto.class,sql1);
        if(l==null){
            total=0;
        }else{
            total  = l.size();
        }
        LOGGER.debug("total:[{}]：",total);

        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug("pagedto:[{}]：",pageDto.asJson());

        return pageDto;
    }

    @Override
    public Object selectTaskDetail(String id) {
        TaskDetailDto dto = null;
        TaskBasicsEntity s = new TaskBasicsEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        dto = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).where(s.id).is(id).selectFirst(new TaskDetailDto(){
            {
                taskName = s.name;
                status = s.status;
                branchName = b.name;
                desc = s.desc;
                projectName = p.name;
                gitUrl = p.gitUrl;
                currentBranch = p.branch;
                crontabExpression = s.crontabExpression;
            }
        });
        return dto;
    }

    @Override
    public void updateTaskCron(String id, String cron,String timdes) {
        TaskBasicsEntity t = new TaskBasicsEntity();
        db.from(t).set(t.crontabExpression).to(cron).set(t.getTimed()).to(timdes).where(t.id).is(id).update();
    }

    @Override
    public void insertTaskTimed(TaskTimedEntity t) throws DatabaseException {
        db.insert(t);
    }

    @Override
    public List<TaskTimedEntity> selectTaskTimed(String id) throws DatabaseException {
        TaskTimedEntity t = new TaskTimedEntity();
        return db.from(t).where(t.getTaskId()).is(id).select();
    }

    @Override
    public void deleteTime(String id) {
        TaskTimedEntity t = new TaskTimedEntity();
        db.from(t).where(t.getTaskId()).is(id).delete();
    }
}
