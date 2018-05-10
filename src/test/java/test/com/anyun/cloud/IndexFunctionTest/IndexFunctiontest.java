package test.com.anyun.cloud.IndexFunctionTest;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.common.etcd.Etcd;
import com.anyun.cloud.common.json.JsonUtil;
import com.anyun.cloud.common.sys.Response;

import com.anyun.cloud.service.IndexFunctionService;

import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;



public class IndexFunctiontest extends BaseTest {
    public IndexFunctionService indexFunctionService;

    @Before
    public void init0(){
        indexFunctionService = ControllerIOC.getIOC().getInstance(IndexFunctionService.class);
    }

    @Test
    public void getFunctionTest(){
      Response list = indexFunctionService.getFunctionSituation(1);
        System.out.println(JsonUtil.toJson(list));
    }
}
