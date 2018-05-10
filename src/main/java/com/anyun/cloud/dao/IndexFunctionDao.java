package com.anyun.cloud.dao;

import com.anyun.cloud.common.exception.DatabaseException;
import com.anyun.cloud.model.dto.IndexFunctionDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.model.entity.ServiceEntity;

import java.util.List;
import java.util.Map;

public interface IndexFunctionDao {

    /**
     * 查询运行情况
     * @return
     * @throws DatabaseException
     */
    IndexFunctionDto selectFunctionNum() throws DatabaseException;

    /**
     * 查询数据中心信息
     * @param id
     * @return
     * @throws DatabaseException
     */
    DataCenterEntity selectDateNameById(int id) throws DatabaseException;

    Boolean selectEnvironmentBySid(String sid) throws DatabaseException;
}
