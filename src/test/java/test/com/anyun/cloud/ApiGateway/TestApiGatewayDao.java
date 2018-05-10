package test.com.anyun.cloud.ApiGateway;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.tools.db.AbstractEntity;
import com.anyun.cloud.dao.ApiGatewayDao;
import com.anyun.cloud.model.dto.ApiVerificationQueryDto;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.entity.ApiGatewayEntity;
import com.anyun.cloud.model.entity.ApiInfoEntity;
import com.anyun.cloud.model.entity.ElasticSettingEntity;
import com.anyun.cloud.model.param.ElasticSettingParam;
import com.anyun.cloud.model.param.ElasticSettingUpdateParam;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

import java.util.List;

public class TestApiGatewayDao extends BaseTest {
    private ApiGatewayDao apiGatewayDao;

    @Before
    public void init0() {
        apiGatewayDao = ControllerIOC.getIOC().getInstance(ApiGatewayDao.class);
    }

    @Test
    public void selectDetailsByName(){
        String name = "网关1";
        AbstractEntity abstractEntity = apiGatewayDao.selectDetailsByName(name);
        System.out.println(abstractEntity);
    }

    @Test
    public void selectPageList(){
        int index=1;
        int limit=10;
        String sortBy="";
        String sortDirection="";
        PageDto<ApiGatewayEntity> pageDto= apiGatewayDao.selectPageList( index,  limit,  sortBy,  sortDirection);
        System.out.print(pageDto.asJson());
    }

    @Test
    public void  selectVerificationParam(){
        List<ApiVerificationQueryDto> list =apiGatewayDao.selectVerificationParam();
        System.out.print(JsonUtil.toJson(list));
    }

    @Test
    public void  createElasticSetting(){
        ElasticSettingParam param = new ElasticSettingParam();
        param.setMinLink(10);
        param.setMaxLink(100);
        ElasticSettingEntity es = apiGatewayDao.insertElasticSetting(param);
        System.out.println(es.asJson());
    }

    @Test
    public void updateElasticSetting(){
        ElasticSettingUpdateParam param = new ElasticSettingUpdateParam();
        param.setId((long) 1);
        param.setMinLink(20);
        param.setMaxLink(150);
        ElasticSettingEntity es =apiGatewayDao.updateElasticSetting(param);
        System.out.println(es.asJson());
    }
}
