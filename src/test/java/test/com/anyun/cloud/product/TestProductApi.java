package test.com.anyun.cloud.product;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.model.param.ProductCreateParam;
import com.anyun.cloud.model.param.ProductUpdateParam;
import org.junit.Test;
import test.com.anyun.cloud.BaseApiTest;

import javax.ws.rs.POST;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProductApi extends BaseApiTest {

    @Test
    public void getDetails() throws Exception {
        String id = "24";
        String json = hostWebClient.get("/products/details/" + id, null);
        System.out.println("json:" + json);
    }


    @Test
    public void getPageList() throws Exception {
        Map<String,Object>  param=new HashMap<>();
        param.put("index",1);
        param.put("limit",10);
        param.put("sortBy","");
        param.put("sortDirection","asc");
        String json = hostWebClient.get("/products/page" , param);
        System.out.println("json:" + json);
    }

    @Test
    public void delete() throws Exception {
        String json = hostWebClient.delete("/products/delete/26", null);
        System.out.println(json);
    }


    @Test
    public void create() throws Exception {
        ProductCreateParam param = new ProductCreateParam();
        param.setProductName("test");
        param.setCategory("test");
        param.setReorderQuantity(909);
        param.setUnitPrice(90.0);
        param.setUnitsInStock(9090);
        String json = hostWebClient.put("/products/create/single", param.asJson());
        System.out.println(json);
    }


    @Test
    public void createAll() throws Exception {
        List<ProductCreateParam> l = new ArrayList<>();
        ProductCreateParam param1 = new ProductCreateParam();
        param1.setProductName("test");
        param1.setCategory("test");
        param1.setReorderQuantity(909);
        param1.setUnitPrice(90.0);
        param1.setUnitsInStock(9090);
        ProductCreateParam param2 = new ProductCreateParam();
        param2.setProductName("test");
        param2.setCategory("test");
        param2.setReorderQuantity(909);
        param2.setUnitPrice(90.0);
        param2.setUnitsInStock(9090);
        l.add(param1);
        l.add(param2);
        String json = hostWebClient.put("/products/create/batch", JsonUtil.toJson(l));
        System.out.println(json);
    }

    @Test
    public void update() throws Exception {
        ProductUpdateParam param = new ProductUpdateParam();
        param.setProductId(24);
        param.setCategory("3");
        param.setReorderQuantity(90);
        param.setUnitPrice(0.0);
        String json = hostWebClient.post("/products/update/single", param.asJson());
        System.out.println(json);
    }


    @Test
    public void updateAll() throws Exception {
        List<ProductUpdateParam> list = new ArrayList<>();
        ProductUpdateParam param1 = new ProductUpdateParam();
        param1.setProductId(24);
        param1.setCategory("category");
        param1.setReorderQuantity(90);
        param1.setUnitPrice(0.0);
        ProductUpdateParam param2 = new ProductUpdateParam();
        param2.setProductId(25);
        param2.setCategory("category");
        param2.setReorderQuantity(90);
        param2.setUnitPrice(0.0);
        list.add(param1);
        list.add(param2);
        String json = hostWebClient.post("/products/update/batch", JsonUtil.toJson(list));
        System.out.println(json);
    }

    @Test
    public void  post() throws Exception {
        Map<String,Object>  map=new HashMap<>();
        map.put("name","jack");
        String json = hostWebClient.post("/message/yf", JsonUtil.toJson(map));
        System.out.println(json);
    }

}
