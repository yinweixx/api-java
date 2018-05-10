package com.anyun.cloud.model.dto;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 18:46
 */
public class UnregisteredHostsDto extends AbstractEntity {
    public String host; //id
    public String address; //ip
    public String name;//名称

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
