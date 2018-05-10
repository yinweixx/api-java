package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.DataCenterDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.DataCenterEntity;
import com.anyun.cloud.service.DataCenterService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/17 11:18
 */
public class TestDataCenterDao extends BaseTest {

    private DataCenterDao dataCenterDao;

    @Before
    public void init0() {
        dataCenterDao = ControllerIOC.getIOC().getInstance(DataCenterDao.class);
    }

    @Test

    public void selectDetailsById() {
        int id = 1;
        DataCenterEntity p = dataCenterDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }

    @Test
    public void deleteById() {
        int id = 1;
        dataCenterDao.deleteById(id);
    }

    @Test
    public void insert() {
        DataCenterEntity p = new DataCenterEntity();
        p.setCenterName("车管所");
        p.setDnsName("che");
        p.setCenterAddress("青山");
        DataCenterEntity newP = dataCenterDao.insert(p);
        System.out.print(newP);
    }

    @Test
    public void update() {
        DataCenterEntity p = new DataCenterEntity();
        p.setCenterId(2);
        p.setCenterName("公安局");
        p.setDnsName("gonganju");
        p.setCenterAddress("青山");
        DataCenterEntity dataCenterEntity = dataCenterDao.update(p);
        System.out.println(dataCenterEntity.asJson());

    }

    @Test
    public void selectPageList() {
        int index = 1;
        int limit = 10;
        String sortBy = "";
        String sortDirection = "";
        PageDto<DataCenterEntity> pageDto = dataCenterDao.selectPageList(index, limit, sortBy, sortDirection);
        System.out.print(pageDto.asJson());
    }

}