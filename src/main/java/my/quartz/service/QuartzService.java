package my.quartz.service;

import my.quartz.dto.JobRequest;

public interface QuartzService {

    void addScheduleJob( JobRequest jobReques);

    boolean updateScheduleJob(JobRequest jobRequest);

    boolean deleteScheduleJob(JobRequest jobRequest);

    boolean pauseScheduleJob(JobRequest jobRequest);

    boolean resumeScheduleJob(JobRequest jobRequest);

    boolean immediatelyJob(JobRequest jobRequest);

    boolean isJobRunning(JobRequest jobRequest);

    boolean isJobExists(JobRequest jobRequest);

    String getScheduleState(JobRequest jobRequest);
}