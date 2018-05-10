package test.com.anyun.cloud.Task;

import com.anyun.cloud.common.context.ControllerIOC;
import com.anyun.cloud.dao.TaskManagementDao;
import com.anyun.cloud.model.dto.PageDto;
import com.anyun.cloud.model.dto.TaskListDto;
import org.junit.Before;
import org.junit.Test;
import test.com.anyun.cloud.BaseTest;

public class TestTaskDao extends BaseTest {
    private TaskManagementDao taskManagementDao;

    @Before
    public void init0() {
        taskManagementDao = ControllerIOC.getIOC().getInstance(TaskManagementDao.class);
    }


    @Test
    public void QueryConditionService(){
        int index =1;
        int limit =10;
        String sortBy ="";
        String sortDirection="";
        String taskName="";
        String gitUrl="";
        String branchName="";
        String projectName="";
        Object pageDto = taskManagementDao.QueryConditionService(index,limit,sortBy,sortDirection,taskName,gitUrl,branchName,projectName);
        System.out.println(pageDto);
    }
}
