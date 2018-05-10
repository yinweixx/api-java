package com.anyun.cloud.model.entity;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.iciql.Iciql;
import com.iciql.Iciql.IQColumn;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 13:43
 */
@Iciql.IQTable(name = "middleware")
public class MiddlewareEntity extends AbstractEntity{
    @IQColumn(name = "id", primaryKey = true, autoIncrement = true)
    public int middlewareId;  //中间件Id
    @IQColumn(name = "middleware_name")
    public String middlewareName; //中间件名称
    @IQColumn(name = "middleware_state")
    public String middlewareState;   //状态
    @IQColumn(name = "middleware_ip")
    public String  middlewareIp;//ip地址
    @IQColumn(name = "middleware_type")  //中间件类型
    public String middlewareType;


    public int getMiddlewareId() {
        return middlewareId;
    }

    public void setMiddlewareId(int middlewareId) {
        this.middlewareId = middlewareId;
    }

    public String getMiddlewareName() {
        return middlewareName;
    }

    public void setMiddlewareName(String middlewareName) {
        this.middlewareName = middlewareName;
    }

    public String getMiddlewareState() {
        return middlewareState;
    }

    public void setMiddlewareState(String middlewareState) {
        this.middlewareState = middlewareState;
    }

    public String getMiddlewareIp() {
        return middlewareIp;
    }

    public void setMiddlewareIp(String middlewareIp) {
        this.middlewareIp = middlewareIp;
    }

    public String getMiddlewareType() {
        return middlewareType;
    }

    public void setMiddlewareType(String middlewareType) {
        this.middlewareType = middlewareType;

}
}
