package test.com.anyun.cloud.VideoSourceTest;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.VideoSourceDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.VideoSourceEntity;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

public class TestVideoSourceDao extends BaseTest {
    private VideoSourceDao videoSourceDao;

    @Before
    public void  init0(){
        videoSourceDao =  ControllerIOC.getIOC().getInstance(VideoSourceDao.class);
    }

    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        PageDto<VideoSourceEntity> list = videoSourceDao.selectPageList(index,  limit,  sortBy,  sortDirection);
        System.out.println(list.asJson());
    }

    @Test
    public void selectVideoByCondition(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        String intersection ="";
        String orientation ="";
        String desc="dasda";
        String product="";
        String vender="";
        String dataCenter = "1";
        PageDto<VideoSourceEntity> pageDto = videoSourceDao.selectVideoByCondition(index,limit,sortBy,sortDirection,intersection,orientation,desc,product,vender,dataCenter);
        System.out.println(pageDto.getData().size());
        System.out.println(pageDto.asJson());
    }

}
