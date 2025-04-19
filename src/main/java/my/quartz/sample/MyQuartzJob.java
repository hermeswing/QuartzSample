package my.quartz.sample;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.util.ArrayList;
import java.util.Date;

//@Component
public class MyQuartzJob implements Job {

    /**
     * Job interface 구현체
     * Job의 trigger 실행 시 execute() Method는 scheduler의 스레드 중 하나에 의해 호출
     *
     * @param JobExecutionContext 런타임 환경에 대한 정보, 이 환경을 실행한 Scheduler에 대한 핸들, 실행을 트리거한 트리거에 대한 핸들, 작업의 JobDetail 개체 및 기타 몇 가지 항목을 작업 인스턴스에 제공
     */
    @Override
    public void execute( JobExecutionContext context ) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();

        // JobData에 접근
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobString = dataMap.getString( "jobString" );
        float jobFloatValue = dataMap.getFloat( "jobFloatValue" );

        System.out.println( "Instance " + key + " of DumbJob says: " + jobString + ", and val is: " + jobFloatValue + ", Excute Time :: " + new Date() );
    }

}
