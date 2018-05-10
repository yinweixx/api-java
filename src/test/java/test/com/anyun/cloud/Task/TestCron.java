package test.com.anyun.cloud.Task;

import org.quartz.CronTrigger;

import test.com.anyun.cloud.BaseTest;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;


import java.text.SimpleDateFormat;

import java.util.Date;



public class TestCron extends BaseTest {

    public static void main(String[] args) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CronTrigger cronTrigger = newTrigger().withSchedule(cronSchedule("0 1 * ? * 5")).build();
        Date start = new Date();
        for (int i=0;i<5;i++){
            start = cronTrigger.getFireTimeAfter(start);
            System.out.println(df.format(start));
        }
    }
}


