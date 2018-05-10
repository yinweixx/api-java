package test.com.anyun.cloud.base;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.HostManagementDao;
import com.anyun.cloud.model.entity.*;
import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author myb  mayanbin@proxzone.com
 * @version 1.0
 * @date 2018/1/29 20:30
 */
public class TestHostManagementDao extends BaseTest {
    private HostManagementDao hostManagementDao;

    @Before
    public void init0() {
        hostManagementDao = ControllerIOC.getIOC().getInstance(HostManagementDao.class);
    }

    @Test
    //根据id查询已注册宿主机
    public void selectDetailsById() {
        int id = 4;
        HostBaseEntity p = hostManagementDao.selectDetailsById(id);
        System.out.println(p.asJson());
    }
    @Test
    //根据id查询已注册宿主机LXD信息
    public void selectLxdDetailsById() {
        int id = 1;
        HostLxdEntity p = hostManagementDao.selectLxdDetailsById(id);
        System.out.println(p.asJson());
    }
    @Test
    //根据宿主机id查询注册宿主机docker的信息
    public void selectDockerDetailsById() {
        int id = 1;
        HostDockerEntity p = hostManagementDao.selectDockerDetailsById(id);
        System.out.println(p.asJson());
    }

    @Test
    //查询宿主机硬件信息
    public void selectHardwareDetailsById() {
        int id = 1;
        HostHardwareEntity p = hostManagementDao.selectHardwareDetailsById(id);
        System.out.println(p.asJson());
    }

    @Test
    //查询宿主机软件信息
    public void selectSoftwareDetailsById() {
        int id = 1;
        HostSoftwareEntity p = hostManagementDao.selectSoftwareDetailsById(id);
        System.out.println(p.asJson());

    }
    @Test
    public void deleteById(){
        int id = 8;
        hostManagementDao.cancelById(id);
    }
    @Test
    public void selectHostsByCenterId() {
        int centerId = 1;
        List<HostBaseEntity> p = hostManagementDao.selectHostsListByCenterName(centerId);
        System.out.println(JSONArray.fromObject(p));

    }

    }
