package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.IpPoolDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpPoolEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/18 19:27
 */
public class TestIpPoolDao extends BaseTest {
    private IpPoolDao ipPoolDao;

    @Before
    public void init0() {
        ipPoolDao = ControllerIOC.getIOC().getInstance(IpPoolDao.class);
    }

    @Test
    public void selectDetailsById() {
        int id = 3;
        IpPoolEntity p = ipPoolDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }

    @Test
    public void deleteById(){
        int id=3;
        ipPoolDao.deleteById(id);
    }
    @Test
    public void insert(){
        IpPoolEntity p =new IpPoolEntity();
        p.setPoolName("好");
        p.setEnvironment("生产");
        p.setCategory("docker");
      //  p.setCenterName("调用接口");
        IpPoolEntity newP=ipPoolDao.insert(p);
        System.out.println(newP);
    }

    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        int centerId=1;
        PageDto<IpPoolEntity> pageDto= ipPoolDao.selectPageList( index,  limit,  sortBy,  sortDirection,centerId);
        System.out.print(pageDto.asJson());

    }

    @Test
    public void getPoolListByCondition(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        int centerId=1;
        String category = "";
        String poolName="";
        String environment="";
        PageDto<IpPoolEntity>pageDto = ipPoolDao.selectPoolListByCondition(index,  limit,  sortBy,  sortDirection,category,poolName,environment,centerId);
        System.out.println(pageDto.asJson());
    }
}
