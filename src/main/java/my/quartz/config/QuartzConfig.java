package my.quartz.config;

import my.quartz.sample.MyQuartzJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    /**
     * <pre>
     * Tomcat이 죽었다가 다시 복구되는 경우, 기본적으로 현재 실행 중인 작업은 종료된 것으로 간주되며 복구되지 않습니다.
     * 하지만, requestRecovery(true) 로 설정하면 Quartz가 충돌(즉, "하드 셧다운")이 발생한 경우
     * 실행 중인 Job을 복구하도록 할 수 있습니다.
     *
     * RequestsRecovery - 작업이 "복구를 요청"하고 스케줄러의 '하드 셧다운' 시간 동안 실행
     * (즉, 실행 중인 프로세스가 충돌하거나 머신이 꺼짐) 스케줄러가 다시 시작될 때 다시 실행됩니다.
     * 이 경우 JobExecutionContext.isRecovering() 메서드는 true를 반환합니다.
     * </pre>
     *
     * @return JobDetail
     */
    @Bean
    public JobDetail myJobDetail() {
        return JobBuilder.newJob( MyQuartzJob.class )
                .withIdentity( "myJob" )
                .requestRecovery(true) // Comment 참조
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger myJobTrigger( JobDetail myJobDetail ) {
        return TriggerBuilder.newTrigger()
                .forJob( myJobDetail )
                .withIdentity( "myTrigger" )
                .withSchedule( SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds( 60 ) // 초단위
                        .repeatForever() )
                .build();
    }
}