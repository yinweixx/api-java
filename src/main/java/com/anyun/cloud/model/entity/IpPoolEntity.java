package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 10:00
 */
@Iciql.IQTable(name = "ip_pool")
public class IpPoolEntity extends AbstractEntity {
    @Iciql.IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    public int poolId ;   //Ip池Id
    @Iciql.IQColumn(name = "pool_name")
    public String poolName;  //Ip池名称
    @Iciql.IQColumn(name = "environment", length = 200)
    public String  environment;  //Ip池环境
    @Iciql.IQColumn(name = "category")
    public  String  category; // Ip池分类
  //  @Iciql.IQColumn(name = "centerName")
    public String centerName;  //Ip池所属数据中心
    @Iciql.IQColumn(name = "centerId")
    public int centerId;

    public IpPoolEntity(){

    }

    public int getPoolId() {
        return poolId;
    }

    public void setPoolId(int poolId) {
        this.poolId = poolId;
    }

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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

}
