package com.anyun.cloud.service;

import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.dto.IndexFunctionDto;

import java.util.List;

public interface IndexFunctionService {

    /**
     * 查询运行情况
     *
     * @return
     */
    Response getFunctionSituation(int id);
}
