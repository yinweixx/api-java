package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.IpBlockDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.IpBlockEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/22 9:07
 */
public class TestIpBlockDao extends BaseTest {
    private IpBlockDao ipBlockDao;
    @Before
    public void init0() {
        ipBlockDao = ControllerIOC.getIOC().getInstance(IpBlockDao.class);
    }

    @Test
    public void selectDetailsById(){
        int id = 1;
        IpBlockEntity p = ipBlockDao.selectDetailsById(id);
        System.out.println(p.asJson());

    }
    @Test
    public void deleteById(){
        int id = 3;
        ipBlockDao.deleteById(id);
    }
    @Test
    public void insert(){
        IpBlockEntity p =new IpBlockEntity();
        p.setBlockGateway("192.168.0.29");
        p.setBlockNetMask("调用数据中心接口");
        p.setBlockStartIp("sd");
        p.setBlockEndIp("2");
        p.setPoolId(3);
        IpBlockEntity newP =ipBlockDao.insert (p);
        System.out.println(newP);

    }
    @Test
    public void update(){
        IpBlockEntity p =new IpBlockEntity();
        p.setBlockId(1);
        p.setBlockGateway("192.168.0.56/95");
        p.setBlockNetMask("常州中心");
        p.setBlockStartIp("sd");
        p.setBlockEndIp("785");
        p.setPoolId(2);
        IpBlockEntity newP=ipBlockDao.update(p);
        System.out.println(newP.asJson());
    }
    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        PageDto<IpBlockEntity> pageDto= ipBlockDao.selectPageList( index,  limit,  sortBy,  sortDirection);
        System.out.print(pageDto.asJson());
    }
}
