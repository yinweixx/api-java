package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.context.Context;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.dao.TaskManagementDao;
import com.anyun.cloud.model.ErrorCode;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.TaskDetailDto;
import com.anyun.cloud.model.dto.TaskListDto;
import com.anyun.cloud.model.entity.TaskTimedEntity;
import com.anyun.cloud.model.param.TaskCronParam;
import com.anyun.cloud.model.param.TaskUpdateParam;
import com.anyun.cloud.service.TaskManagementService;
import com.google.inject.Inject;
import org.quartz.CronTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class TaskManagementServiceImpl implements TaskManagementService {
    private final static Logger LOGGER = LoggerFactory.getLogger(TaskManagementServiceImpl.class);
    private TaskManagementDao taskManagementDao;
    private Context context;
    private Response response;

    @Inject
    public TaskManagementServiceImpl(Context context) {
        this.context = context;
        this.taskManagementDao = context.getBeanByClass(TaskManagementDao.class);
    }

    @Override
    public Response update(String body) {
        //判断body是否为空
        LOGGER.debug("body:[{}]", body);
        if (StringUtils.isEmpty(body)) {
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        TaskUpdateParam param = JsonUtil.fromJson(TaskUpdateParam.class, body);
        try {
            response = new Response<>();
            taskManagementDao.updateService(param.getId(),param.isStatus());
            response.setCode(ErrorCode.NO_ERROR.code());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response queryTaskByCondition(int index, int limit, String sortBy, String sortDirection, String taskName, String shortUrl, String branchName, String projectName) {
        try {
            response = new Response<PageDto<TaskListDto>>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(taskManagementDao.QueryConditionService(index,limit,sortBy,sortDirection,taskName,shortUrl,branchName,projectName));
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response getDetail(String id) {
        LOGGER.debug("id[{}]",id);
        if(StringUtils.isEmpty(id)){
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "id is empty");
            return response;
        }
        try {
            response = new Response<TaskDetailDto>();
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent(taskManagementDao.selectTaskDetail(id));
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.QUERY_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.QUERY_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }

    @Override
    public Response TaskCronAnalysis(String body) {
        LOGGER.debug("body[{}]",body);
        if(StringUtils.isEmpty(body)){
            response = new Response<String>();
            response.setCode(ErrorCode.PARAMETER_ERROR.code());
            response.setContent(ErrorCode.PARAMETER_ERROR.name() + "body is empty");
            return response;
        }
        TaskCronParam param = JsonUtil.fromJson(TaskCronParam.class, body);
        LOGGER.debug("cron:[{}]:",param.getCron());
        LOGGER.debug("id:[{}]:",param.getId());
        try {
            response = new Response<>();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            LOGGER.debug("df[{}]",df);

            CronTrigger cronTrigger = newTrigger().withSchedule(cronSchedule(param.getCron())).build();
            Date start = new Date();
            List<String> timed =new ArrayList<>();
            for (int i=0;i<5;i++){
                start = cronTrigger.getFireTimeAfter(start);
                System.out.println(df.format(start));
                LOGGER.debug("cron[{}]",start);
//                TaskTimedEntity t= new TaskTimedEntity();
//                UUID uuid = UUID.randomUUID();
//                t.setId(uuid.toString());
//                t.setTimed(df.format(start));
//                t.setTaskId(param.getId());
//                taskManagementDao.insertTaskTimed(t);
                timed.add(df.format(start));
            }

            String timeds = timed.toString();
            //存入数据库
            taskManagementDao.updateTaskCron(param.getId(),param.getCron(),timeds);
//            List<TaskTimedEntity> list = taskManagementDao.selectTaskTimed(param.getId());
            response.setCode(ErrorCode.NO_ERROR.code());
            response.setContent("");
            LOGGER.debug(response.asJson());
            return response;
        } catch (Exception e) {
            response = new Response<String>();
            response.setCode(ErrorCode.POST_RESOURCE_ERROR.code());
            response.setContent(ErrorCode.POST_RESOURCE_ERROR.name() + e.getMessage());
            return response;
        }
    }
}
