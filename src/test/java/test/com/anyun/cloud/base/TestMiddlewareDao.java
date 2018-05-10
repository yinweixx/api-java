package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.MiddlewareDao;
import com.anyun.cloud.model.entity.MiddlewareEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/23 14:43
 */
public class TestMiddlewareDao extends BaseTest{
    private MiddlewareDao middlewareDao;

    @Before
    public void init0() {
        middlewareDao = ControllerIOC.getIOC().getInstance(MiddlewareDao.class);
    }

    @Test
    public void selectDetailsById() {
        int id = 1;
        MiddlewareEntity p = middlewareDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }
}
