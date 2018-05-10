package test.com.anyun.cloud.AppBasicsTest;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.dao.AppBasicsDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.AppBasicsEntity;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestAppBasicsDao extends BaseTest {
    private AppBasicsDao appBasicsDao;

    @Before
    public void init0() {
        appBasicsDao = ControllerIOC.getIOC().getInstance(AppBasicsDao.class);
    }


    @Test
    public void insert() {
        AppBasicsEntity a = new AppBasicsEntity();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date1 = sdf.format(date);
        a.setName("测试");
        a.setShortName("app-test");
        a.setDesc("应用测试");
        a.setCreateTime(date1);
        a.setLastModifyTime(date1);
        AppBasicsEntity appBasicsEntity = appBasicsDao.insert(a);
        System.out.print(appBasicsEntity);
    }

    @Test
    public void update(){
        AppBasicsEntity a = new AppBasicsEntity();
        a.setAppId((long) 15);
        a.setName("测试");
        a.setDesc("再做一次应用修改");
        AppBasicsEntity appBasicsEntity= appBasicsDao.update(a);
        System.out.print(appBasicsEntity);
    }

    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        PageDto<AppBasicsEntity> pageDto= appBasicsDao.selectPageList( index,  limit,  sortBy,  sortDirection);
        System.out.print(pageDto.asJson());
    }

    @Test
    public void delete(){
        long id = 1;
        appBasicsDao.deleteById(id);
    }

    @Test
    public void selectVagueListByName(){
        String condition = "测";
        List<AppBasicsEntity> list = appBasicsDao.selectVagueListByName(condition);
        System.out.println(list);
    }

    @Test
    public void selectAppListByCondition(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        String name = "";
        String shortName="a";
        String startTime = "";
        String endTime = "";
        PageDto<AppBasicsEntity> pageDto = appBasicsDao.selectAppListByCondition( index,  limit,  sortBy,  sortDirection,name,shortName,startTime,endTime);
        System.out.print(pageDto.asJson());
    }


}
