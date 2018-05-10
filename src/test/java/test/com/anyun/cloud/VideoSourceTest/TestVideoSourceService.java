package test.com.anyun.cloud.VideoSourceTest;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.sys.Response;
import com.anyun.cloud.model.entity.VideoSourceEntity;
import com.anyun.cloud.model.param.VideoSourceCreateParam;
import com.anyun.cloud.model.param.VideoSourceUpdateParam;
import com.anyun.cloud.service.VideoSourceService;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

public class TestVideoSourceService extends BaseTest {
    private VideoSourceService videoSourceService;

    @Before
    public void init0(){
        videoSourceService = ControllerIOC.getIOC().getInstance(VideoSourceService.class);
    }


    @Test
    public void getVideoSource(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        Response r = videoSourceService.getPageList(index,limit,sortBy,sortDirection);
        System.out.println(r.asJson());
    }

    @Test
    public void queryVideoByCondition(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="desc";
        String intersection ="";
        String orientation ="";
        String desc="";
        String product="";
        String vender="";
        String dataCenter = "";
        Response response = videoSourceService.queryVideoByCondition(index,limit,sortBy,sortDirection,intersection,orientation,desc,product,vender,dataCenter);
        System.out.println(response.asJson());
    }


    @Test
    public void deleteAllVideo(){
        Response response = videoSourceService.deleteAllVideo();
        System.out.println(response.asJson());
    }

    @Test
    public void insertVideo(){
        VideoSourceCreateParam param = new VideoSourceCreateParam();
        param.setNationalId("\t150200000013208002013");
        param.setShortNumber("800209");
        param.setIntersection("文明北路-广场街");
        param.setOrientation("北向南");
        param.setDescription("20米-球");
        param.setIpAddress("15.46.70.153");
        param.setSourceAddress("15.46.70.153/onvif/device_service");
        param.setTcp("是");
        param.setUserName("admin");
        param.setPassword("hik12345+");
        param.setIfForword("已转发");
        param.setForwardUrl("rtsp://15.45.255.170:24281/ch15020000001320800209");
        param.setIfLink("已连接");
        param.setIfInclude("已收录");
        param.setStorageUrl("rtsp://15.45.255.170:24281/ch15020000001320800209");
        param.setDataCenter("1");
        param.setWarrantyDate("2018/3/8");
        param.setProduct("海康");
        param.setVender("安云");
        param.setBitRate("4096");
        param.setResolvingPower("1980*1080");
        param.setType("实时流");
        Response response =videoSourceService.insertVideo(param.asJson());
        System.out.println(response.asJson());
    }

    @Test
    public void updateVideo(){
        VideoSourceUpdateParam param = new VideoSourceUpdateParam();
        param.setCameraId("VQPLQMLP");
        param.setNationalId("\t15020000001320800209");
        param.setShortNumber("800209");
        param.setIntersection("文明北路-广场街");
        param.setOrientation("北向南");
       // param.setDesc("20米-球");
        param.setIpAddress("15.46.70.153");
        param.setSourceAddress("15.46.70.153/onvif/device_service");
        param.setTcp("是");
        param.setUserName("admin");
        param.setPassword("hik12345+");
        param.setIfForword("已转发");
        param.setForwardUrl("rtsp://15.45.255.170:24281/ch15020000001320800209");
        param.setIfLink("已连接");
        param.setIfInclude("已收录");
        param.setStorageUrl("rtsp://15.45.255.170:24281/ch15020000001320800209");
        param.setDataCenter("1");
        param.setWarrantyDate("2018/3/8");
        param.setProduct("海康");
        param.setVender("安云");
        param.setBitRate("4096");
        param.setResolvingPower("1980*1080");
        param.setType("实时流");
        Response response= videoSourceService.updateVideo(param.asJson());
        System.out.println(response.asJson());
    }
}
