package com.anyun.cloud.service;

import com.anyun.cloud.common.etcd.spi.entity.api.ApiEntity;
import com.anyun.cloud.service.impl.DefaultApiRamlParser;
import org.raml.v2.api.model.v10.api.Api;

import java.io.File;


public interface RamlApiRamlParser {
    String ENCODING = "utf-8";
    /**
     * @param raml
     * @return
     */
    DefaultApiRamlParser withRamlString(String raml);

    /**
     * @param file
     * @return
     */
    DefaultApiRamlParser withRamlFile(File file);

    /**
     * @param encoding
     * @return
     */
    DefaultApiRamlParser withEncoding(String encoding);

    /**
     * @return
     * @throws Exception
     */
    Api buildV10Api() throws Exception;

    /**
     * @return
     * @throws Exception
     */
    ApiEntity buildApi() throws Exception;
}
