package com.anyun.cloud.service.impl;

import com.anyun.cloud.common.etcd.spi.entity.api.*;
import com.anyun.cloud.common.string.StringUtils;
import com.anyun.cloud.service.RamlApiRamlParser;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.api.DocumentationItem;
import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.ObjectTypeDeclaration;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DefaultApiRamlParser implements RamlApiRamlParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApiRamlParser.class);
    private static final String ENCODING = "raml.parser.encoding";
    private String ramlContent;
    private File ramlFile;
    private Api api;
    private StringBuilder dsb;
    private List<ApiResourceEntity> apiResourceEntities = new ArrayList<>();

   /* @com.google.inject.Inject
    public ProductServiceImpl(Context context) {
        this.context = context;
        this.productDao = context.getBeanByClass(ProductDao.class);
        this.ramlApiRamlParser = context.getBeanByClass(RamlApiRamlParser.class);
    }*/

    @Inject
    public DefaultApiRamlParser() {
        System.setProperty(ENCODING, "UTF-8");
    }

    @Override
    public DefaultApiRamlParser withRamlString(String ramlContent) {
        this.ramlContent = ramlContent;
        return this;
    }

    @Override
    public DefaultApiRamlParser withRamlFile(File file) {
        this.ramlFile = file;
        return this;
    }

    @Override
    public DefaultApiRamlParser withEncoding(String encoding) {
        System.setProperty(ENCODING, encoding);
        return this;
    }

    @Override
    public Api buildV10Api() throws Exception {
        RamlModelResult ramlModelResult = null;
        if (ramlFile != null && ramlFile.exists() && ramlFile.isFile()) {
            LOGGER.debug("RAML file [{}]", ramlFile);
            ramlModelResult = new RamlModelBuilder().buildApi(ramlFile);
        } else if (StringUtils.isNotEmpty(ramlContent)) {
            LOGGER.debug("RAML content [{}]", ramlContent);
            ramlModelResult = new RamlModelBuilder().buildApi(ramlContent);
        }
        StringBuilder errorBuilder = new StringBuilder();
        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                errorBuilder.append(validationResult.getMessage());
            }
            throw new Exception(errorBuilder.toString());
        } else {
            api = ramlModelResult.getApiV10();
            return api;
        }
    }

    @Override
    public ApiEntity buildApi() throws Exception {
        dsb = new StringBuilder();
        Api api = buildV10Api();
        ApiEntity entity = new ApiEntity();
        entity.setTitle(api.title().value());
        entity.setDescription(api.description().value().trim());
        entity.setVersion(api.version().value());
//        String baseUrlStr = api.baseUri().value();
//                .replace("{version}", entity.getVersion())
//                .replace(".", "_");
        entity.setBaseUrl(api.baseUri().value());
        entity.setMimeTypeList(api.mediaType());
        dsb.append("TITLE: " + entity.getTitle()).append("\n");
        dsb.append("VERSION: " + entity.getVersion()).append("\n");
        dsb.append("BASE URL: " + entity.getBaseUrl()).append("\n");
        dsb.append("DESCRIPTION: " + entity.getDescription()).append("\n");
        entity.setDocuments(getDocs());
        parseApiResource();
        entity.setResources(apiResourceEntities);
        LOGGER.debug("Build debug \n{}", dsb.toString());
        return entity;
    }

    private List<ApiDocuementEntity> getDocs() {
        List<ApiDocuementEntity> docs = new LinkedList<>();
        for (DocumentationItem doc : api.documentation()) {
            docs.add(new ApiDocuementEntity(doc.title().value(), doc.content().value().trim()));
        }
        return docs;
    }

    private void parseApiResource() throws Exception {
        for (Resource resource : api.resources()) {
            if (resource.resources().size() != 0)
                getResources(resource.resources());
            getResource(resource);
        }
    }

    private void getResources(List<Resource> resources) throws Exception {
        for (Resource resource : resources) {
            if (resource.resources().size() != 0)
                getResources(resource.resources());
            getResource(resource);
        }
    }

    private void getResource(Resource resource) throws Exception {
        ApiResourceEntity resourceEntity = new ApiResourceEntity();
        dsb.append("Path: " + resource.resourcePath()).append("\n");
        dsb.append("Name: " + resource.displayName().value()).append("\n");
        dsb.append("Desc: " + resource.description().value()).append("\n");
        resourceEntity.setPath(resource.resourcePath());
        resourceEntity.setName(resource.displayName().value());
        resourceEntity.setDesc(resource.description().value().trim());
        Method method = resource.methods().get(0);
        parseMethodEntity(method, resourceEntity);
        parseRequestBody(method, resourceEntity);
        parseResponses(method, resourceEntity);
        apiResourceEntities.add(resourceEntity);
        dsb.append("----------------------------------------------------------------------------------").append("\n");
    }

    private void parseMethodEntity(Method method, ApiResourceEntity resourceEntity) {
        dsb.append("Method Name: " + method.method()).append("\n");
        resourceEntity.setMethod(method.method().toUpperCase());
        for (TypeDeclaration typeDeclaration : method.queryParameters()) {
            dsb.append("  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^").append("\n");
            dsb.append("  Param Name: " + typeDeclaration.name()).append("\n");
            dsb.append("  Param Desc: " + typeDeclaration.description().value()).append("\n");
            dsb.append("  Param Type: " + typeDeclaration.type()).append("\n");
            dsb.append("  Param Required: " + typeDeclaration.required()).append("\n");
            ApiMethodParamEntity paramEntity = new ApiMethodParamEntity();
            paramEntity.setName(typeDeclaration.name());
            paramEntity.setDescription(typeDeclaration.description().value());
            paramEntity.setType(typeDeclaration.type());
            paramEntity.setRequired(typeDeclaration.required());
            if (typeDeclaration.example() != null) {
                dsb.append("  Param Example: " + typeDeclaration.example().value()).append("\n");
                paramEntity.setExample(typeDeclaration.example().value());
            }
            resourceEntity.addParameter(paramEntity);
        }
    }

    private void parseRequestBody(Method method, ApiResourceEntity resourceEntity) {
        if (!method.method().equals("post") && !method.method().equals("put"))
            return;
        ApiRequestBody requestBody = new ApiRequestBody();
        dsb.append("****************************Request body********************************").append("\n");
        TypeDeclaration body = method.body().get(0);
        dsb.append("    Content Type: " + body.name()).append("\n");
        dsb.append("    Type Name: " + body.type()).append("\n");
        requestBody.setContentType(body.name());
        ApiTypeEntity apiTypeEntity = new ApiTypeEntity();
        apiTypeEntity.setName(body.type());
        parserObjectTypeByApiTypeEntity(apiTypeEntity);
        requestBody.setApiTypeEntity(apiTypeEntity);
        resourceEntity.setRequestBody(requestBody);
    }

    private void parseResponses(Method method, ApiResourceEntity resourceEntity) {
        dsb.append("^^^^^^^^^^^^^^^^^^Response^^^^^^^^^^^^^^^^^^^^^^^^").append("\n");
        for (Response response : method.responses()) {
            dsb.append("Response code: " + response.code().value()).append("\n");
            for (TypeDeclaration body : response.body()) {
                ApiResponseEntity responseEntity = new ApiResponseEntity();
                responseEntity.setCode(Integer.valueOf(response.code().value()));
                responseEntity.setContentType(body.name());
                dsb.append("Body Content Type: " + body.name()).append("\n");
                dsb.append("Body Type: " + body.type()).append("\n");
                ApiTypeEntity apiTypeEntity = new ApiTypeEntity();
                apiTypeEntity.setName(body.type());
                parserObjectTypeByApiTypeEntity(apiTypeEntity);
                responseEntity.setTypeEntity(apiTypeEntity);
                resourceEntity.addResponse(responseEntity);
            }
            dsb.append("**************************************************").append("\n");
        }
    }

    private void parserObjectTypeByApiTypeEntity(ApiTypeEntity typeEntity) {
        ObjectTypeDeclaration type = null;
        for (TypeDeclaration typeDeclaration : api.types()) {
            if (typeDeclaration.name().equals(typeEntity.getName())) {
                type = (ObjectTypeDeclaration) typeDeclaration;
                break;
            }
        }
        if (type == null)
            return;
        for (TypeDeclaration prop : type.properties()) {
            dsb.append("      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^").append("\n");
            dsb.append("      Prop Type:" + prop.type()).append("\n");
            dsb.append("      Prop Required:" + prop.required()).append("\n");
            ApiTypePropEntity propEntity = new ApiTypePropEntity();
            propEntity.setType(prop.type());
            propEntity.setRequired(prop.required());
            if (prop.example() != null) {
                dsb.append("      Prop Example:" + prop.example().value()).append("\n");
                propEntity.setExample(prop.example().value());
            }
            typeEntity.addPropEntity(propEntity);
        }
    }
}
