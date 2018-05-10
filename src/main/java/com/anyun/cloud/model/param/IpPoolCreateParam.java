package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 10:19
 */
public class IpPoolCreateParam extends AbstractEntity {
    public String poolName;
    public String environment;
    public String category;
  //  public String centerName;
    public int centerId;

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
/*
    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
*/
    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }
}
