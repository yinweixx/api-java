package com.anyun.cloud.model.dto.host;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 14:21
 */
public class Host  extends AbstractEntity{
    public String host;
    public String address;
    public String name;
    public Software  software;
    public Hardware   hardware;

    public String getHost(

    ) {
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

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
}
