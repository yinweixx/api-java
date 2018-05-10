package test.com.anyun.cloud.apiManagement;

import com.anyun.cloud.model.entity.*;
import com.anyun.cloud.model.param.*;
import com.anyun.cloud.model.param.api.*;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import java.util.*;

/**
 * Created by jt on 18-1-18.
 */
public class TestApiManagementApi extends BaseApiTest {

//    创建api
    @Test
    public void create() throws Exception {
     CreateParam p =getOrDeleteMethod();
//        CreateParam p= putOrPostMethod();
        System.out.println("CreateParam p:" + p.asJson());
        String json = hostWebClient.put("/apimanagement/create/api", p.asJson());
        System.out.println(json);

    }
    private CreateParam getOrDeleteMethod() {

        //version
        List<Version> versionList=new ArrayList<>();

        Version version = new Version();

        version.setPath("deer");
        version.setName("v1.0.8");
        version.setMethod("GET");
        version.setStatus(true);
        version.setServiceId("768B7F958033E3B548F4E4B221354940");

        // 返回格式---------------------------------------------
        //返回参数列表
        List<TypeProp> typePropList = new LinkedList<>();
        TypeProp typeProp = new TypeProp();
        typeProp.setType("string");
        typeProp.setRequired(true);
        typeProp.setExample("dfa");
        typePropList.add(typeProp);


        //返回参数类型
//        Type type = new Type();
//        type.setName("Type2");
//        type.setTypePropList(typePropList);

        Response response = new Response();
        response.setCode(200);
        response.setContentType("application/json");
        response.setDescription("");

        List<Response> responseList=new ArrayList<>();
        responseList.add(response);
        version.setResponseList(responseList);

        //请求参数列表--------------------------------------------
        List<RequestHeader> headerList = new ArrayList<>();
        RequestHeader param;
        //参数p1 描述
        param = new RequestHeader();
        param.setName("p1");
        param.setDescription("参数1的描述");
        param.setRequired(true);
        param.setType("integer");
        param.setExample("1");
        headerList.add(param);
        //参数p2 描述
        param = new RequestHeader();
        param.setName("p2");
        param.setDescription("参数2的描述");
        param.setRequired(false);
        param.setType("string");
        param.setExample("querystring2");
        headerList.add(param);
        version.setHeaderList(headerList);

        //MediaType
        List<MediaType> mediaTypeList=new ArrayList<>();
        MediaType  mediaType=new MediaType();
        mediaType.setName("application/json");
        mediaTypeList.add(mediaType);
        version.setMediaTypeList(mediaTypeList);



        //Documentation
        List<Documentation> documentationList=new ArrayList<>();
        Documentation documentation=new Documentation();
        documentation.setTitle("test");
        documentation.setContent("test");
        documentationList.add(documentation);
        version.setDocumentationList(documentationList);



        versionList.add(version);

        //拼接　创建参数CreateParam----------------------------
        CreateParam p = new CreateParam();

      p.setAppId(Long.parseLong("27"));
      p.setDisplayName(" api1231");
      p.setDescription("api1的描述");
      p.setVersionList(versionList);
      return p;
    }
    private CreateParam putOrPostMethod() {
        // 返回格式---------------------------------------------

        //version
        List<Version> versionList=new ArrayList<>();

        Version  version=new Version();
        version.setName("v1.00");
        version.setMethod("POST");
        version.setStatus(true);
        version.setServiceId("768B7F958033E3B548F4E4B221354940");

        //返回参数列表
//        List<TypeProp> typePropList = new LinkedList<>();
//        TypeProp typeProp = new TypeProp();
//        typeProp.setType("string");
//        typeProp.setRequired(true);
//        typeProp.setExample("fd");
//        typePropList.add(typeProp);
//
//
//        //返回参数类型
//        Type type = new Type();
//        type.setName("Type2");
//        type.setTypePropList(typePropList);

        Response response = new Response();
        response.setCode(200);
        response.setContentType("application/json");
        response.setDescription("");

        List<Response>  setResponseList=new ArrayList<>();
        setResponseList.add(response);
        version.setResponseList(setResponseList);

        //请求参数列表--------------------------------------------
//        List<TypeProp> typePropList1 = new LinkedList<>();
//        TypeProp typeProp1 = new TypeProp();
//        typeProp1.setType("string");
//        typeProp1.setRequired(true);
//        typeProp1.setExample("fd");
//        typePropList1.add(typeProp);
//
//        Type type1 = new Type();
//        type1.setName("Type2");
//        type1.setTypePropList(typePropList1);

        RequestBody   requestBody=new RequestBody();
        requestBody.setContentType("application/json");
        requestBody.setDescription("");
        version.setRequestBody(requestBody);





        //MediaType
        List<MediaType> mediaTypeList=new ArrayList<>();
        MediaType  mediaType=new MediaType();
        mediaType.setName("application/json");
        mediaTypeList.add(mediaType);
        version.setMediaTypeList(mediaTypeList);

        //Documentation
        List<Documentation> documentationList=new ArrayList<>();
        Documentation documentation=new Documentation();
        documentation.setTitle("test");
        documentation.setContent("test");
        documentationList.add(documentation);
        version.setDocumentationList(documentationList);


        versionList.add(version);


        //拼接　创建参数CreateParam----------------------------
        CreateParam p = new CreateParam();
        p.setAppId(Long.parseLong("61"));
        p.setDisplayName("sample api2");
        p.setDescription("api2的描述");
        p.setVersionList(versionList);
        return p;
    }

    @Test
    public void testExistApiVersion()throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("baseUrl","/camera-app-rtp");
        param.put("path","/api2/der");
        param.put("apiVersionName","v1.0");
        String json = hostWebClient.get("/apimanagement/test/isexist", param);
        System.out.println("json:" + json);
    }


    //创建api版本
    @Test
    public void createVersion() throws Exception {
        Version v =versionGetOrDeleteMethod();
//      Version v= versionputOrPostMethod();
        System.out.println("VersionCreateParam v:" + v.asJson());
        String json = hostWebClient.put("/apimanagement/create/apiversion", v.asJson());
        System.out.println(json);

    }
    private Version versionGetOrDeleteMethod() {

        //version

        Version version = new Version();

        version.setPath("/path");
        version.setName("v1.0.344");
        version.setMethod("GET");
        version.setApiId(115);
        version.setStatus(true);
        version.setServiceId("768B7F958033E3B548F4E4B221354940");

        // 返回格式---------------------------------------------
        //返回参数列表
        List<TypeProp> typePropList = new LinkedList<>();
        TypeProp typeProp = new TypeProp();
        typeProp.setType("string");
        typeProp.setRequired(true);
        typeProp.setExample("dfa");
        typePropList.add(typeProp);


        //返回参数类型
//        Type type = new Type();
//        type.setName("Type2");
//        type.setTypePropList(typePropList);

        Response response = new Response();
        response.setCode(200);
        response.setContentType("application/json");
        response.setDescription("");

        List<Response> responseList=new ArrayList<>();
        responseList.add(response);
        version.setResponseList(responseList);

        //请求参数列表--------------------------------------------
        List<RequestHeader> headerList = new ArrayList<>();
        RequestHeader param;
        //参数p1 描述
        param = new RequestHeader();
        param.setName("p1");
        param.setDescription("参数1的描述");
        param.setRequired(true);
        param.setType("integer");
        param.setExample("1");
        headerList.add(param);
        //参数p2 描述
        param = new RequestHeader();
        param.setName("p2");
        param.setDescription("参数2的描述");
        param.setRequired(false);
        param.setType("string");
        param.setExample("querystring2");
        headerList.add(param);
        version.setHeaderList(headerList);

        //MediaType
        List<MediaType> mediaTypeList=new ArrayList<>();
        MediaType  mediaType=new MediaType();
        mediaType.setName("application/json");
        mediaTypeList.add(mediaType);
        version.setMediaTypeList(mediaTypeList);



        //Documentation
        List<Documentation> documentationList=new ArrayList<>();
        Documentation documentation=new Documentation();
        documentation.setTitle("test");
        documentation.setContent("test");
        documentationList.add(documentation);
        version.setDocumentationList(documentationList);


        return version;
    }
    private Version versionputOrPostMethod() {

        Version  version=new Version();
        version.setName("v1.0");
        version.setApiId(57);
        version.setMethod("POST");
        version.setStatus(true);
        version.setServiceId("768B7F958033E3B548F4E4B221354940");

        //返回参数列表
        List<TypeProp> typePropList = new LinkedList<>();
        TypeProp typeProp = new TypeProp();
        typeProp.setType("string");
        typeProp.setRequired(true);
        typeProp.setExample("fd");
        typePropList.add(typeProp);


        //返回参数类型
        Type type = new Type();
        type.setName("Type2");
        type.setTypePropList(typePropList);

        Response response = new Response();
        response.setCode(200);
        response.setContentType("application/json");
        response.setDescription("342");

        List<Response>  setResponseList=new ArrayList<>();
        setResponseList.add(response);
        version.setResponseList(setResponseList);

        //请求参数列表--------------------------------------------
        RequestBody   requestBody=new RequestBody();
        requestBody.setContentType("application/json");
        requestBody.setDescription("");
        version.setRequestBody(requestBody);


        //MediaType
        List<MediaType> mediaTypeList=new ArrayList<>();
        MediaType  mediaType=new MediaType();
        mediaType.setName("application/json");
        mediaTypeList.add(mediaType);
        version.setMediaTypeList(mediaTypeList);

        //Documentation
        List<Documentation> documentationList=new ArrayList<>();
        Documentation documentation=new Documentation();
        documentation.setTitle("test");
        documentation.setContent("test");
        documentationList.add(documentation);
        version.setDocumentationList(documentationList);




        return version;
    }





    //查询

    /**
     * 5.按条件查询api
     */
    @Test
    public void queryByCondition()throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","b.name");
        param.put("sortDirection","desc");
        param.put("displayName","");
//        param.put("baseUrl","");
//        param.put("description","");
//        param.put("method","");
//        param.put("path","");
        param.put("appName","");
        String json = hostWebClient.get("/apimanagement/get/apilistbycondition", param);
        System.out.println(json);
    }

/*
* sortBy：参数
* a.id
*a.display_name
* a.description
* b.name
* v.name
* v.final_path
* v.id
* */

    @Test
    public void queryVersionByCondition()throws Exception{
        Map<String,Object> param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","a.display_name");
        param.put("sortDirection","desc");
        param.put("displayName","");
        param.put("appName","11");
        String json = hostWebClient.get("/apimanagement/get/apiversioncatalog", param);
        System.out.println(json);
    }


    /**
     * 10.查询版本
     */
    @Test
    public void queryoneapiversion() throws Exception {
        long id = 41;
        String json = hostWebClient.get("/apimanagement/get/apiversion/" + id, null);
        System.out.println("json:" + json);
    }





















    //删除API
    @Test
    public void delete() throws Exception {
        String id = "93,94,95   ";
        String json = hostWebClient.delete("/apimanagement/delete/api/" + id, null);
        System.out.println("json:" + json);
    }


   //.删除版本
    @Test
    public void deleteversion() throws Exception {
        String id = "29";
        String json = hostWebClient.delete("/apimanagement/delete/apiversion/" + id, null);
        System.out.println("json:" + json);
    }


    //更新api
    @Test
    public void updateApi() throws Exception {
        ApiUpdateParam apiUpdateParm = new ApiUpdateParam();
        apiUpdateParm.setId((long)61);
        apiUpdateParm.setAppId("27");
        apiUpdateParm.setDescription("");
        apiUpdateParm.setDisplayName("");
            String json = hostWebClient.post("/apimanagement/update/api", apiUpdateParm.asJson());
        System.out.println(json);
    }


    //修改版本
    @Test
    public void updateapiversion() throws Exception {
        ApiVersionUpdateParam param = new ApiVersionUpdateParam();
        param.setId((long) 53);
        param.setStatus("false");
        param.setName("");
        param.setServiceId("SDADSA");
        param.setMethod("POST");
        //get  or delete
        List<ApiInfoMethodParamEntity> paramUpdateParam = new ArrayList<>();
        ApiInfoMethodParamEntity param1 = new ApiInfoMethodParamEntity();
        param1.setDescription("sda");
        param1.setExample("dsa");
        param1.setName("dsa");
        param1.setType("dsa");
        param1.setRequired(true);
        paramUpdateParam.add(param1);

        param1.setDescription("dsa");
        param1.setExample("da");
        param1.setName("sad");
        param1.setType("wdwd");
        param1.setRequired(true);
        paramUpdateParam.add(param1);
        param.setParametersList(paramUpdateParam);
        //put  or  post
        ApiInfoRequestBodyEntity entity = new ApiInfoRequestBodyEntity();
        entity.setContentType("sda");

        entity.setDescription("dsad");
//        param.setRequestBody(entity);
        List<Response> responseList=new ArrayList<>();

        Response response = new Response();
        response.setCode(200);
        response.setContentType("application/json");
        response.setDescription("");
        responseList.add(response);

        Response response1 = new Response();
        response1.setCode(200);
        response1.setContentType("application/json");
        response1.setDescription("");

        responseList.add(response1);
        param.setResponseList(responseList);


        String json = hostWebClient.post("/apimanagement/update/apiversion", param.asJson());
        System.out.println(json);
    }


    //修改doc
    @Test
    public void updateApiDoc() throws Exception {
        ApiInfoDocumentationEntity apiInfoDocumentationEntity = new ApiInfoDocumentationEntity();
        apiInfoDocumentationEntity.setId((long)57);
        apiInfoDocumentationEntity.setTitle("test4");
        apiInfoDocumentationEntity.setContent("你好");
        String json = hostWebClient.post("/apimanagement/update/doc", apiInfoDocumentationEntity.asJson());
        System.out.println(json);
    }


    //修改media
    @Test
    public void updateApiMedia() throws Exception {
        ApiInfoMediaTypeEntity apiInfoMediaTypeEntity = new ApiInfoMediaTypeEntity();
        apiInfoMediaTypeEntity.setId((long)28);
        apiInfoMediaTypeEntity.setName("233");
        String json = hostWebClient.post("/apimanagement/update/media", apiInfoMediaTypeEntity.asJson());
        System.out.println(json);
    }

    //修改修改get/delete 请求hender列表
    @Test
    public void updateApiMethodParamList() throws Exception {
        ApiMethodParamUpdateParam param = new ApiMethodParamUpdateParam();
        param.setId((long)35);
        param.setName("");
        param.setExample("23");
        param.setDescription("dsa");
        param.setRequired("das");
        param.setType("dsa");
        String json = hostWebClient.post("/apimanagement/update/hender", param.asJson());
        System.out.println(json);
    }

    //修改get/delete 请求body列表

    @Test
    public void updateApiRequestBody() throws Exception {
        ApiRequestBodyUpdateParam param = new ApiRequestBodyUpdateParam();
        param.setApiVersionId((long)76);
        param.setContentType("sdaww");
        param.setName("dsada");
        param.setDescription("");
        String json = hostWebClient.post("/apimanagement/update/requestbody", param.asJson());
        System.out.println(json);
    }

    //修改response返回参数列表
    @Test
    public void updateApiResponse() throws Exception {
        ApiResponseUpdateParam param = new ApiResponseUpdateParam();
        param.setId((long)11);
        param.setContentType("sdaww");
//        param.setName("dsada");
        param.setCode("");
//        //typePROP
//        List<ApiTypePropUpdateParam> list = new ArrayList<>();
//        ApiTypePropUpdateParam param1 = new ApiTypePropUpdateParam();
//        param1.setId((long)42);
//        param1.setExample("");
//        param1.setRequired("");
//        param1.setType("dsaw");
//        list.add(param1);
        param.setDescription("");
        String json = hostWebClient.post("/apimanagement/update/response", param.asJson());
        System.out.println(json);
    }




    //查询
    //查询ａｐｉ


   //查询ａｐｉ详情

    @Test
    public void queryone() throws Exception {
        long id = 105;
        String json = hostWebClient.get("/apimanagement/get/api/" + id, null);
        System.out.println("json:" + json);
    }





    @Test
    public void queryList() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", 1);
        param.put("limit", 10);
        param.put("sortBy", "");
        param.put("sortDirection", "");
        String json = hostWebClient.get("/apimanagement/get/apilist", param);
        System.out.println("json:" + json);
    }




    /**
     *11. 根据ａｉＩｄ查询API版本列表
     * @parm index          String    1      页码 （默认第一页）
     * @parm limit          String    10     每页记录数目（默认每页限制10条）
     * @parm sortBy         String          排序字段
     * @parm sortDirection  String    asc     排序方向asc:正序（默认）desc ：倒序
     * @return
     */
    @Test
    public void queryapiVersionLiet() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("index", "1");
        param.put("limit", "10");
        param.put("apiId","8");
        param.put("sortDirection", "desc");
        String json = hostWebClient.get("/apimanagement/get/apiversionbyapi", param);
        System.out.println("json:" + json);
    }


     @Test
     public void testS()throws Exception{
         String json = hostWebClient.get("/apimanagement/query/servicenameandid" , null);
         System.out.println("json:" + json);
     }
    @Test
    public void testA()throws Exception{
        String json = hostWebClient.get("/apimanagement/query/apinameandid" , null);
        System.out.println("json:" + json);
    }

    @Test
    public void testexitapiVersion()throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("baseUrl", "");
        param.put("appId", "27");
        param.put("apiVersionName", "v1.0.0");
        param.put("path","/api1");
        String json = hostWebClient.get("/apimanagement/test/apiversionisexist" , param);
        System.out.println("json:" + json);
    }

    @Test
    public void testexitApi()throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("appId", (long)27);
        param.put("displayname", "2123 api1");
        String json = hostWebClient.get("/apimanagement/test/apiisexist" , param);
        System.out.println("json:" + json);
    }

    @Test
    public void testfinalPath()throws Exception{
        String appId = "31";
        String json = hostWebClient.get("/apimanagement/get/finalpath/" +appId, null);
        System.out.println("json:" + json);
    }
}
