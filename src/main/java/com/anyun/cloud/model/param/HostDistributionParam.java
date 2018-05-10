package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

public class HostDistributionParam extends AbstractEntity {
    private String BUSINESS=BUSINESS_ENUM.getHostIp.name(); //对应调用方法的名称
    private String hostcategory;        //宿主机分类       --docker、lxd、融合
    private String blockcategory;       //IP段用途        --管理、宿主机、业务、网关
    private String dnsname;             //数据中心域名
    private String environment;         //环境            --生产环境、测试环境
    private Integer number;              //需求数量


    public String getHostcategory() {
        return hostcategory;
    }

    public void setHostcategory(String hostcategory) {
        this.hostcategory = hostcategory;
    }

    public String getBlockcategory() {
        return blockcategory;
    }

    public void setBlockcategory(String blockcategory) {
        this.blockcategory = blockcategory;
    }

    public String getDnsname() {
        return dnsname;
    }

    public void setDnsname(String dnsname) {
        this.dnsname = dnsname;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
