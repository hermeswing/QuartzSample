package my.quartz.sample;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyQuartzJob implements Job {
    @Override
    public void execute( JobExecutionContext context ) throws JobExecutionException {
        System.out.println( "Spring Boot + Quartz Job 실행 중..." + new Date() );
    }
}
