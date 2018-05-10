package com.anyun.cloud.common.etcd.spi.entity.api;

import com.anyun.cloud.common.tools.db.AbstractEntity;
import org.raml.v2.api.model.v10.bodies.MimeType;

import java.util.LinkedList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
public class ApiEntity extends AbstractEntity{
    private String title = "";
    private String description = "";
    private String version = "";
    private String baseUrl = "";
    private List<MimeType> mimeTypeList;
    private List<ApiDocuementEntity> documents = new LinkedList<>();
    private List<ApiResourceEntity> resources = new LinkedList<>();

    public List<MimeType> getMimeTypeList() {
        return mimeTypeList;
    }

    public void setMimeTypeList(List<MimeType> mimeTypeList) {
        this.mimeTypeList = mimeTypeList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiDocuementEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(List<ApiDocuementEntity> documents) {
        this.documents = documents;
    }

    public List<ApiResourceEntity> getResources() {
        return resources;
    }

    public void setResources(List<ApiResourceEntity> resources) {
        this.resources = resources;
    }

    public void addResource(ApiResourceEntity resourceEntity) {
        resources.add(resourceEntity);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String toString() {
        return "ApiEntity {" + "\n" +
                "    title='" + title + "\'\n" +
                "    description='" + description + "\'\n" +
                "    version='" + version + "\'\n" +
                "    baseUrl='" + baseUrl + "\'\n" +
                "    documents=" + documents + "\n" +
                "    resources=" + resources + "\n" +
                '}';
    }
}
