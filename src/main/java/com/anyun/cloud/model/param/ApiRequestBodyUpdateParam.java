package com.anyun.cloud.model.param;

import com.anyun.cloud.common.tools.db.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jt on 18-3-7.
 */
public class ApiRequestBodyUpdateParam extends AbstractEntity {
    /**
     * 主键  (api表主键)
     */
    private long apiVersionId;
    /**
     *请求body 类型
     */
    private String contentType;
    private String name;
    private String description;
//    private List<ApiTypePropUpdateParam> typePropList = new LinkedList<>();

    public void setApiVersionId(long apiVersionId) {
        this.apiVersionId = apiVersionId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getApiVersionId() {
        return apiVersionId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    //
//    public List<ApiTypePropUpdateParam> getTypePropList() {
//        return typePropList;
//    }
//
//    public void setTypePropList(List<ApiTypePropUpdateParam> typePropList) {
//        this.typePropList = typePropList;
//    }
}
