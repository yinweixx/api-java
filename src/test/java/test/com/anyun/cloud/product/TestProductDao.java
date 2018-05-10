package test.com.anyun.cloud.product;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.ProductDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ProductEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class TestProductDao extends BaseTest {
    private ProductDao productDao;

    @Before
    public void init0() {
        productDao = ControllerIOC.getIOC().getInstance(ProductDao.class);
    }

    @Test
    public void selectDetailsById() {
        int id = 25;
        ProductEntity p = productDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }

    @Test
    public void deleteById() {
        int id = 3;
        productDao.deleteById(id);
    }

    @Test
    public void insert() {
        ProductEntity p = new ProductEntity();
        p.setCategory("category");
        p.setReorderQuantity(90);
        p.setUnitsInStock(1000);
        p.setProductName("name");
        p.setUnitPrice(10.09);
        ProductEntity newP = productDao.insert(p);
        System.out.print(newP);
    }

    @Test
    public void insertAll() {
        List<ProductEntity> list = new ArrayList<>();
        ProductEntity p;

        System.out.println("1-----------------------------------");
        p = new ProductEntity();
        p.setCategory("category1");
        p.setReorderQuantity(901);
        p.setUnitsInStock(10001);
        p.setProductName("name1");
        p.setUnitPrice(10.091);
        list.add(p);

        System.out.println("2-----------------------------------");
        p = new ProductEntity();
        p.setCategory("category2");
        p.setReorderQuantity(902);
        p.setUnitsInStock(10002);
        p.setProductName("name2");
        p.setUnitPrice(10.092);
        list.add(p);

        System.out.println("3-----------------------------------");
        p = new ProductEntity();
        p.setCategory("category3");
        p.setReorderQuantity(903);
        p.setUnitsInStock(10003);
        p.setProductName("name3");
        p.setUnitPrice(10.093);
        list.add(p);

        List<ProductEntity> newList = productDao.insert(list);
        newList.forEach(productEntity -> {
            System.out.print(productEntity.asJson());
        });
    }


    @Test
    public void update() {
        ProductEntity p = new ProductEntity();
        p.setProductId(10);
        p.setUnitPrice(0.0);
        p.setProductName("namae");
        p.setReorderQuantity(90);
        p.setUnitsInStock(90);
        ProductEntity productEntity = productDao.update(p);
        System.out.println(productEntity.asJson());
    }

    @Test
    public void updateAll() {
        List<ProductEntity> param = new ArrayList<>();

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setProductId(12);
        productEntity1.setUnitsInStock(90);
        productEntity1.setProductName("dfd");
        productEntity1.setUnitPrice(9.00);

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setProductId(13);
        productEntity2.setUnitsInStock(90);
        productEntity2.setProductName("dfd");
        productEntity2.setUnitPrice(9.00);

        param.add(productEntity1);
        param.add(productEntity2);

        List<ProductEntity> newList = productDao.update(param);
        newList.forEach(newl -> {
            System.out.println(newl.asJson());
        });
    }

    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        PageDto<ProductEntity> pageDto= productDao.selectPageList( index,  limit,  sortBy,  sortDirection);
        System.out.print(pageDto.asJson());
    }

    @Test
    public void selectList(){
        List<ProductEntity>  l=productDao.selectList(1,"","");
        l.forEach(x->{
            System.out.println(x.asJson());
        });

    }


}
