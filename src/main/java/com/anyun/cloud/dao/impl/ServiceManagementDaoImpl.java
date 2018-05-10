package com.anyun.cloud.dao.impl;

import com.anyun.cloud.common.db.Database;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.ServiceManagementDao;
import com.anyun.cloud.model.dto.*;
import com.anyun.cloud.model.entity.ServiceBranchEntity;
import com.anyun.cloud.model.entity.ServiceEntity;
import com.anyun.cloud.model.entity.ServiceProjectEntity;
import com.anyun.cloud.model.entity.TaskBasicsEntity;
import com.google.inject.Inject;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceManagementDaoImpl extends  AbstractIciqlDao implements ServiceManagementDao{
    final static Logger LOGGER= LoggerFactory.getLogger(ServiceManagementDaoImpl.class);
    @Inject
    public ServiceManagementDaoImpl(Database database) {
        super(database);
    }

    @Override
    public void updateService(String id, boolean status) {
        LOGGER.debug("statusL[{}]",status);
        LOGGER.debug("id[{}]",id);

        ServiceEntity s=new ServiceEntity();

        db.from(s).set(s.status).to(status).where(s.id).is(id).update();
    }


    @Override
    public void deletedService(String id) {
        ServiceEntity s=new ServiceEntity();
        db.from(s).where(s.id).is(id).delete();
    }

    @Override
    public Object selectPageList(int index, int limit, String sortBy, String sortDirection) {
        //定义变量
        PageDto<ServiceListDto> pageDto = new PageDto<>();
        List<ServiceListDto> data = null;
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int total = (int) db.from(s).selectCount();

        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).orderByDesc(sortBy).limit(limit).offset((index - 1)*limit).select(new ServiceListDto(){
                    {
                        name = s.name;
                        isPrivate = s.isPrivate;
                        status = s.status;

                        branchName = b.name;
                        desc = s.desc;
                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        sid = s.id;

                    }
                });
            } else {//正序
                data = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).orderBy(sortBy).limit(limit).offset((index - 1)*limit).select(new ServiceListDto(){
                    {
                        name = s.name;
                        isPrivate = s.isPrivate;
                        status = s.status;

                        branchName = b.name;
                        desc = s.desc;
                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        sid = s.id;

                    }
                });
            }
        }
        if(sortDirection.equals("desc")) {
            LOGGER.debug(sortBy);
            Comparator<?> cmp = ComparableComparator.getInstance();
            cmp = ComparatorUtils.nullLowComparator(cmp);
            cmp = ComparatorUtils.reversedComparator(cmp);
            Collections.sort(data, new BeanComparator(sortBy, cmp));
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug(pageDto.asJson());
        return pageDto;
    }


    @Override
    public Object selectServiceDetail(String id , String user) {
        ServiceDetailDto dto = null;
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        dto = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).where(s.id).is(id).and(p.user).is(user).selectFirst(new ServiceDetailDto(){
                {
                    name = s.name;
                    isPrivate = s.isPrivate;
                    status = s.status;
                    user = p.user;
                    branchName = b.name;
                    desc = s.desc;
                    projectName = p.name;
                    gitUrl = p.gitUrl;
                }
        });
        return dto;
    }

    @Override
    public Object selectPageListByUser(int index, int limit, String sortBy, String sortDirection,String user) {
        PageDto<ServiceListDto> pageDto = new PageDto<>();
        List<ServiceListDto> data = null;
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int total = (int) db.from(s).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).where(p.user).is(user).orderByDesc(sortBy).limit(limit).offset((index - 1)*limit).select(new ServiceListDto(){
                    {
                        name = s.name;
                        isPrivate = s.isPrivate;
                        status = s.status;
                        branchName = b.name;
                        desc = s.desc;
                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        sid = s.id;

                    }
                });
            } else {//正序
                data = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).where(p.user).is(user).orderBy(sortBy).limit(limit).offset((index - 1)*limit).select(new ServiceListDto(){
                    {
                        name = s.name;
                        isPrivate = s.isPrivate;
                        status = s.status;
                        branchName = b.name;
                        desc = s.desc;
                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        sid = s.id;
                    }
                });
            }
        }
        if(sortDirection.equals("desc")) {
            LOGGER.debug(sortBy);
            Comparator<?> cmp = ComparableComparator.getInstance();
            cmp = ComparatorUtils.nullLowComparator(cmp);
            cmp = ComparatorUtils.reversedComparator(cmp);
            Collections.sort(data, new BeanComparator(sortBy, cmp));
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug(pageDto.asJson());
        return pageDto;
    }

    @Override
    public int insertProject(ServiceProjectEntity p) {
        int pid = (int)db.insertAndGetKey(p);
        return pid;
    }

    @Override
    public void insertBranch(List<ServiceBranchEntity> entityList) {
        db.insertAll(entityList);
    }

    @Override
    public void insertService(List<ServiceEntity> serviceList) {
        db.insertAll(serviceList);
    }

    @Override
    public int selectProjectId(String s) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        int id = db.from(p).where(p.gitUrl).is(s).selectFirst(p.id);
        return id;
    }

    @Override
    public int selectBranchId(String s,int pid) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceEntity ss = new ServiceEntity();
        int bid= db.from(b).leftJoin(p).on(p.id).is(b.projectId).where(b.name).is(s).and(p.id).is(pid).selectFirst(b.id);
        return bid;
    }

    @Override
    public void deletedBranch(int pid) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        db.from(b).where(b.projectId).is(pid).delete();
    }

    @Override
    public void deleteProject(int s) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).where(p.id).is(s).delete();
    }

    @Override
    public int selectProjectName(String s) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        int a = (int)db.from(p).where(p.gitUrl).is(s).selectCount();
        LOGGER.debug("a[{}]",a);
        return a;
    }

    @Override
    public void rollbackService(int pid, String name) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(b).set(b.name).to(name).where(b.id).is(pid).update();
    }

    @Override
    public PageDto<ServiceConditionDto> QueryConditionService(int index, int limit, String sortBy, String sortDirection,String sname,String bname,String pname,String user,String shortUrl) {
        PageDto<ServiceConditionDto> pageDto = new PageDto<>();
        List<ServiceConditionDto> data ;
        String sql = "SELECT *,b.id as bid,p.id as pid,s.name as sname,b.name as bname,p.name as pname,branch_id as branchId,project_id as projectId,app_id as appId,is_private as isPrivate FROM   ser_basics s left join  pro_branch b on b.id = s.branch_id left join pro_basic p on p.id = b.project_id where 1=1";
        if(sname !=null && !sname.equals("")){
            sql += " and s.name like '%"+sname+"%'" ;
        }
        if(bname !=null && !bname.equals("")){
            sql += " and b.name like '%"+bname+"%'" ;
        }
        if(pname !=null &&!pname.equals("")){
            sql += " and p.name like '%"+pname+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        if( user !=null && !user.equals("")){
            sql +=String.format(" and p.user='"+user+"'");
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
        data = db.buildObjects(ServiceConditionDto.class, rs);
        LOGGER.debug("data:",data);

        String sql1 = "SELECT *,b.id as bid,p.id as pid,s.name as sname,b.name as bname,p.name as pname,branch_id as branchId,project_id as projectId,app_id as appId ,is_private as isPrivate FROM   ser_basics s left join  pro_branch b on b.id = s.branch_id left join pro_basic p on p.id = b.project_id where 1=1";
        if(sname !=null && !sname.equals("")){
            sql1 += " and s.name like '%"+sname+"%'" ;
        }
        if(bname !=null && !bname.equals("")){
            sql1 += " and b.name like '%"+bname+"%'" ;
        }
        if(pname !=null &&!pname.equals("")){
            sql1 += " and p.name like '%"+pname+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql1 += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        if( user !=null && !user.equals("")){
            sql1 +=String.format(" and p.user='"+user+"'");
        }
        int total ;
        List<ServiceConditionDto>  l= db.executeQuery(ServiceConditionDto.class,sql1);
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
    public void deletedService1(int id) {
        ServiceEntity s = new ServiceEntity();
        db.from(s).where(s.branchId).is(id).delete();
    }

    @Override
    public void deletedBranch1(int id) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        db.from(b).where(b.projectId).is(id).delete();
    }

    @Override
    public void deleteProject1(int id) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).where(p.id).is(id).delete();
    }

    @Override
    public List<Integer> queryBranchId(int id) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        List<Integer> list = null;
        list = db.from(b).where(b.projectId).is(id).select(b.id);
        return list;
    }

    @Override
    public String queryUserById(int id) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        String users = db.from(p).where(p.id).is(id).selectFirst(p.user);
        return users;
    }

    @Override
    public Integer queryUserByServiceId(String id) {
        ServiceEntity  s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int ids = db.from(s).where(s.id).is(id).selectFirst(s.branchId);
        return ids;
    }

    @Override
    public String queryUserByBranchId(int id) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int ids = db.from(b).where(b.id).is(id).selectFirst(b.projectId);
        LOGGER.debug("id------[{}]",ids);
        String users = db.from(p).where(p.id).is(ids).selectFirst(p.user);
        LOGGER.debug("users--------[{}]",users);
        return users;
    }

    @Override
    public Object selectPageProjectList(int index, int limit, String sortBy, String sortDirection) {
        PageDto<ProjectListDto> pageDto = new PageDto<>();
        List<ProjectListDto> data = null;
        ServiceProjectEntity p = new ServiceProjectEntity();
        ServiceEntity  s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        int total = (int) db.from(p).selectCount();
        //判断有无数据
        if (total > 0) { //有数据
            if (sortDirection.equals("desc")) { //倒序
                data = db.from(p).orderByDesc(sortBy).limit(limit).offset((index - 1)*limit).select(new ProjectListDto(){
                    {
                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        pid = p.id;
                        branch = p.branch;
                    }
                });
            } else {//正序
                data = db.from(p).orderBy(sortBy).limit(limit).offset((index - 1)*limit).select(new ProjectListDto(){
                    {

                        projectName = p.name;
                        gitUrl = p.gitUrl;
                        pid = p.id;
                        branch = p.branch;
                    }
                });
            }
        }
        if(sortDirection.equals("desc")) {
            LOGGER.debug(sortBy);
            Comparator<?> cmp = ComparableComparator.getInstance();
            cmp = ComparatorUtils.nullLowComparator(cmp);
            cmp = ComparatorUtils.reversedComparator(cmp);
            Collections.sort(data, new BeanComparator(sortBy, cmp));
        }
        pageDto.setIndex(index);
        pageDto.setLimit(limit);
        pageDto.setTotal(total);
        pageDto.setData(data);
        LOGGER.debug(pageDto.asJson());
        return pageDto;
    }

    @Override
    public int selectBranchIdByName(String name,int id,String user) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        int a = db.from(b).leftJoin(p).on(p.id).is(id).where(b.name).is(name).and(p.user).is(user).selectFirst(b.id);
        return a;
    }

    @Override
    public void updateBranchId(int a,int bid) {
        ServiceEntity s= new ServiceEntity();
        db.from(s).set(s.branchId).to(a).where(s.branchId).is(bid).update();
    }

    @Override
    public String selectProjectGitId(int id) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        String gitUrl = db.from(p).where(p.id).is(id).selectFirst(p.gitUrl);
        return gitUrl;
    }

    @Override
    public void updatePrivate(String id, boolean aPrivate, String user) {
        LOGGER.debug("aPrivate[{}]",aPrivate);
        LOGGER.debug("id[{}]",id);
        ServiceEntity s=new ServiceEntity();
        db.from(s).set(s.isPrivate).to(aPrivate).where(s.id).is(id).update();
    }

    @Override
    public List<String> queryBranchByProjectId(int id) {
        ServiceBranchEntity b = new ServiceBranchEntity();
        List<String> data = null;
        data = db.from(b).where(b.projectId).is(id).select(b.name);
        return data;
    }

    @Override
    public List<ServiceListDto> selectServiceList(Integer integer) {
        ServiceEntity s = new ServiceEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        List<ServiceListDto> list = null;
        list = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).select(new ServiceListDto() {
            {
                name = s.name;
                isPrivate = s.isPrivate;
                status = s.status;
                branchName = b.name;
                desc = s.desc;
                projectName = p.name;
                gitUrl = p.gitUrl;
                sid = s.id;
            }
        });
        return list;
}

    @Override
    public void updateProjectBranch(String branch,int id) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).set(p.branch).to(branch).where(p.id).is(id).update();
    }

    @Override
    public Object QueryConditionProject(int index, int limit, String sortBy, String sortDirection, String pname, String shortUrl, String type) {
        PageDto<ProjectListDto> pageDto = new PageDto<>();
        List<ProjectListDto> data ;
        String sql = "SELECT *,id as pid,name as projectName,app_id as appId FROM pro_basic where 1=1";
        if(pname !=null && !pname.equals("")){
            sql += " and name like '%"+pname+"%'" ;
        }
        if(type !=null &&!type.equals("")){
            sql += " and type like '%"+type+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        if (sortDirection.equals("desc") && !sortDirection.equals("")){
            if (sortBy.equals(""))
                sortBy= "id";
            sql +=" order by "+sortBy+" desc limit  "+limit+" offset "+(index-1)*limit;
        }
        else {
            if (sortBy.equals(""))
                sortBy= "id";
            sql +=" order by "+sortBy+"  limit  "+limit+" offset "+(index-1)*limit;
        }
        LOGGER.debug("sql[{}]",sql);
        ResultSet rs = db.executeQuery(sql);
//        LOGGER.debug("ResultSet rs:[{}]", JsonUtil.toJson(rs));
        data = db.buildObjects(ProjectListDto.class, rs);
        LOGGER.debug("data:",data);


        String sql1 = "SELECT *,id as pid,name as projectName,app_id as appId FROM pro_basic where 1=1";
        if(pname !=null && !pname.equals("")){
            sql1 += " and name like '%"+pname+"%'" ;
        }
        if(type !=null &&!type.equals("")){
            sql1 += " and type like '%"+type+"%'" ;
        }
        if(shortUrl !=null &&!shortUrl.equals("")){
            sql1 += " and shortUrl like '%"+shortUrl+"%'" ;
        }
        int total ;
        List<ProjectListDto>  l= db.executeQuery(ProjectListDto.class,sql1);
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
    public void updateProjectType(String type,int pid) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).set(p.type).to(type).where(p.id).is(pid).update();
    }

    @Override
    public void insertTask(List<TaskBasicsEntity> taskList) {
        db.insertAll(taskList);
    }

    @Override
    public void deleteTask(int id) {
        TaskBasicsEntity t = new TaskBasicsEntity();
        db.from(t).where(t.branchId).is(id).delete();
    }

    @Override
    public void updateProjectTypeIsTask(String type, int pid) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).set(p.type).to(type).set(p.user).to(null).where(p.id).is(pid).update();
    }

    @Override
    public List<TaskListDto> selectTypeList(Integer integer) {
        TaskBasicsEntity s = new TaskBasicsEntity();
        ServiceBranchEntity b = new ServiceBranchEntity();
        ServiceProjectEntity p = new ServiceProjectEntity();
        List<TaskListDto> list = null;
        list = db.from(s).leftJoin(b).on(s.branchId).is(b.id).leftJoin(p).on(b.projectId).is(p.id).select(new TaskListDto() {
            {
                name = s.name;
                status = s.status;
                branchName = b.name;
                desc = s.desc;
                projectName = p.name;
                gitUrl = p.gitUrl;
                id = s.id;
                crontabExpression = s.crontabExpression;
            }
        });
        return list;
    }

    @Override
    public PageDto QueryConditionService(int index, int limit, String sortBy, String sortDirection, String sname,String pname,String environmental) {
        PageDto<ServiceConditionDto> pageDto = new PageDto<>();
        List<ServiceConditionDto> data ;
        String sql = "SELECT *,b.id as bid,p.id as pid,s.name as sname,b.name as bname,p.name as pname,branch_id as branchId,project_id as projectId,app_id as appId,is_private as isPrivate FROM   ser_basics s left join  pro_branch b on b.id = s.branch_id left join pro_basic p on p.id = b.project_id where is_private = true and status = true";
        if(sname !=null && !sname.equals("")){
            sql += " and s.name like '%"+sname+"%'" ;
        }
        if(pname !=null && !pname.equals("")){
            sql += " and p.name like '%"+pname+"%'" ;
        }
        if(environmental !=null && !environmental.equals("")){
            if(environmental.equals("true")){
                sql += " and environmental = true " ;
            }
            if(environmental.equals("false")){
                sql += " and environmental = false " ;
            }
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
        data = db.buildObjects(ServiceConditionDto.class, rs);
        LOGGER.debug("data:",data);

        String sql1 = "SELECT *,b.id as bid,p.id as pid,s.name as sname,b.name as bname,p.name as pname,branch_id as branchId,project_id as projectId,app_id as appId ,is_private as isPrivate FROM   ser_basics s left join  pro_branch b on b.id = s.branch_id left join pro_basic p on p.id = b.project_id where is_private = true and status = true";
        if(sname !=null && !sname.equals("")){
            sql1 += " and s.name like '%"+sname+"%'" ;
        }
        if(pname !=null && !pname.equals("")){
            sql1 += " and p.name like '%"+pname+"%'" ;
        }
        if(environmental !=null && !environmental.equals("")){
            if(environmental.equals("true")){
                sql1 += " and environmental = true " ;
            }
            if(environmental.equals("false")){
                sql1 += " and environmental = false " ;
            }
        }
        int total ;
        List<ServiceConditionDto>  l= db.executeQuery(ServiceConditionDto.class,sql1);
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
    public void updateProject(int id, boolean environmental) {
        ServiceProjectEntity p = new ServiceProjectEntity();
        db.from(p).set(p.environmental).to(environmental).where(p.id).is(id).update();
    }
}
