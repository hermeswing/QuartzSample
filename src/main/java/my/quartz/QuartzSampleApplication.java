package my.quartz;

import my.quartz.sample.MyJobListener;
import my.quartz.sample.MyQuartzJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzSampleApplication {

    public static void main( String[] args ) throws SchedulerException {
        SpringApplication.run( QuartzSampleApplication.class, args );

        // Scheduler 사용을 위한 인스턴스화
        SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        /**
         * JobDetail 은 Job이 스케줄러에 추가될 때 Quartz Client에 의해 작성 (작업 인스턴스 정의)
         *
         * 또한 Job에 대한 다양한 속성 설정과 JobDataMap을 포함할 수 있으며,
         * JobDataMap은 Job 클래스의 특정 인스턴스에 대한 상태 정보를 저장하는 데 사용
         *     - 작업 인스턴스가 실행될 때 사용하고자 하는 데이터 개체를 원하는 만큼 보유
         *     - Java Map interface를 구현한 것으로 원시 유형의 데이터를 저장하고 검색하기 위한 몇 가지 편의 방법이 추가
         */
        JobDetail job01 = JobBuilder.newJob( MyQuartzJob.class )
                .withIdentity( "myJob01", "myGroup01" )
                .usingJobData( "jobString", "Hello World!" ) //JobDataMap
                .usingJobData( "jobFloatValue", 3.141f )
                .build();

        // JOB Data 객체
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put( "jobString", "Say Hello World!" );
        jobDataMap.put( "jobFloatValue", 3.1415f );

        JobDetail job02 = JobBuilder.newJob( MyQuartzJob.class )
                .withIdentity( "myJob02", "myGroup02" )
                .setJobData( jobDataMap )
                .build();

        /**
         * Job의 실행을 trigger
         *
         * 작업을 예약하려면 트리거를 인스턴스화하고 해당 속성을 조정하여 예약 요구 사항을 구성
         *
         * - 특정시간 또는 특정 횟수 반복: SimpleTrigger
         * - 주기적 반복: CronTrigger (초 분 시 일 월 요일 연도)
         */
        Trigger trigger01 = TriggerBuilder.newTrigger()
                .withIdentity( "myTrigger01" )
                .startNow()
                .withSchedule( SimpleScheduleBuilder.simpleSchedule()
                        //.withIntervalInSeconds( 60 )            // 60초마다 반복
                        .withIntervalInMinutes( 5 )               // 5분마다 반복
                        //.withIntervalInHours(2)                 // 2시간마다 반복
                        .repeatForever() )                        // 반복제한 없음
                .build();

        Trigger trigger02 = TriggerBuilder.newTrigger()
                .withIdentity( "myTrigger02" )
                .startNow()
                .withSchedule( SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds( 60 )            // 60초마다 반복
                        //.withIntervalInMinutes( 5 )               // 5분마다 반복
                        //.withIntervalInHours(2)                 // 2시간마다 반복
                        .repeatForever() )                        // 반복제한 없음
                .build();

        //리스너 달기
        scheduler.getListenerManager().addJobListener( new MyJobListener() );

        scheduler.scheduleJob( job01, trigger01 );
        scheduler.scheduleJob( job02, trigger02 );

        scheduler.start();
    }

}
