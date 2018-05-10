package test.com.anyun.cloud.product;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.param.ProductCreateParam;
import com.anyun.cloud.model.param.ProductUpdateParam;
import com.anyun.cloud.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class TestProductService extends BaseTest {
    private ProductService service;

    @Before
    public void init0() {
        service = ControllerIOC.getIOC().getInstance(ProductService.class);
    }

    @Test
    public void getDetails() {
        Response r = service.getDetails(1);
        System.out.print(r.asJson());
    }

    @Test
    public void delete() {
        Response r = service.delete(25);
        System.out.println(r.asJson());
    }


    @Test
    public void create() {
        ProductCreateParam param = new ProductCreateParam();
        param.setProductName("test");
        param.setCategory("test");
        param.setReorderQuantity(909);
        param.setUnitPrice(90.0);
        param.setUnitsInStock(9090);
        Response r = service.create(param.asJson());
        System.out.println(r.asJson());
    }


    @Test
    public void createAll() {
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
        Response r = service.batchCreate(JsonUtil.toJson(l));
        System.out.println(r.asJson());
    }

    @Test
    public void update() {
        ProductUpdateParam param = new ProductUpdateParam();
        param.setProductId(24);
        param.setCategory("category");
        param.setReorderQuantity(90);
        param.setUnitPrice(0.0);
        Response r = service.update(param.asJson());
        System.out.print(r.asJson());
    }


    @Test
    public void updateAll() {
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
        Response r = service.batchUpdate(JsonUtil.toJson(list));
        System.out.print(r.asJson());
    }


    @Test
    public void getPageList() {
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        Response r = service.getPageList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }


}
