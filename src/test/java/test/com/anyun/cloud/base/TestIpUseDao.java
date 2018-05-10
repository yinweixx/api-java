package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.IpUseDao;
import com.anyun.cloud.model.entity.IpUseEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/2/2 16:05
 */
public class TestIpUseDao extends BaseTest {
    private IpUseDao ipUseDao;

    @Before
    public void init0() {
        ipUseDao = ControllerIOC.getIOC().getInstance(IpUseDao.class);
    }

    @Test
    public void selectDetailsById() {
        int id = 1;
        IpUseEntity p = ipUseDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }
}
