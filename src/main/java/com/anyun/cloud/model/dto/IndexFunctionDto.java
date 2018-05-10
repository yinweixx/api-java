package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class IndexFunctionDto extends AbstractEntity {
    private Integer service;
    private Integer serviceNode;
    private Integer task;
    private Integer application;
    private Integer testservice;
    private Integer testserviceNode;
    private Integer testtask;
    private Integer testapplication;
    private String dataCenter;
    private String environment;

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getServiceNode() {
        return serviceNode;
    }

    public void setServiceNode(Integer serviceNode) {
        this.serviceNode = serviceNode;
    }

    public Integer getTask() {
        return task;
    }

    public void setTask(Integer task) {
        this.task = task;
    }

    public Integer getApplication() {
        return application;
    }

    public void setApplication(Integer application) {
        this.application = application;
    }


    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Integer getTestservice() {
        return testservice;
    }

    public void setTestservice(Integer testservice) {
        this.testservice = testservice;
    }

    public Integer getTestserviceNode() {
        return testserviceNode;
    }

    public void setTestserviceNode(Integer testserviceNode) {
        this.testserviceNode = testserviceNode;
    }

    public Integer getTesttask() {
        return testtask;
    }

    public void setTesttask(Integer testtask) {
        this.testtask = testtask;
    }


    public Integer getTestapplication() {
        return testapplication;
    }

    public void setTestapplication(Integer testapplication) {
        this.testapplication = testapplication;
    }

}
