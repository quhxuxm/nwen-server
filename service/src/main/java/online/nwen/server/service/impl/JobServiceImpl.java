package online.nwen.server.service.impl;

import online.nwen.server.bo.ResponseCode;
import online.nwen.server.common.ServerConfiguration;
import online.nwen.server.service.api.IJobService;
import online.nwen.server.service.exception.ServiceException;
import online.nwen.server.service.job.AutoSecurityJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
class JobServiceImpl implements IJobService {
    private ServerConfiguration serverConfiguration;
    private Scheduler scheduler;

    JobServiceImpl(ServerConfiguration serverConfiguration, Scheduler scheduler) {
        this.serverConfiguration = serverConfiguration;
        this.scheduler = scheduler;
    }

    private void doScheduleJob(JobDetail job,
                               Trigger trigger) {
        try {
            this.scheduler.scheduleJob(job,
                    Set.of(trigger), true);
        } catch (SchedulerException e) {
            throw new ServiceException(e, ResponseCode.SYSTEM_ERROR);
        }
    }

    private void doTriggerJob(String jobKeyName) throws ServiceException {
        JobKey jobKey = JobKey.jobKey(jobKeyName);
        try {
            if (this.scheduler.checkExists(jobKey)) {
                this.scheduler.triggerJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new ServiceException(e, ResponseCode.SYSTEM_ERROR);
        }
    }

    private void saveSecurityJob() throws ServiceException {
        JobDetail securityJob = JobBuilder.newJob().ofType(AutoSecurityJob.class)
                .withIdentity(IJobConstant.Job.SECURITY.generateJobId(null)).storeDurably().build();
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(
                this.serverConfiguration.getTimerIntervalForSecurityJob()).repeatForever();
        Trigger securityJobTrigger =
                TriggerBuilder.newTrigger().withIdentity(IJobConstant.Job.SECURITY.generateTriggerId(null))
                        .forJob(securityJob).startNow()
                        .withSchedule(simpleScheduleBuilder)
                        .build();
        doScheduleJob(securityJob,
                securityJobTrigger);
    }

    private void saveAdjustLabelPopularFactorJob() throws ServiceException {
        JobDetail adjustLabelPopularFactorJob = JobBuilder.newJob().ofType(AutoSecurityJob.class)
                .withIdentity(IJobConstant.Job.ADJUST_LABEL_POPULAR_FACTOR.generateJobId(null)).storeDurably().build();
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(
                this.serverConfiguration.getTimerIntervalForSecurityJob()).repeatForever();
        Trigger adjustLabelPopularFactorJobTrigger =
                TriggerBuilder.newTrigger().withIdentity(IJobConstant.Job.ADJUST_LABEL_POPULAR_FACTOR.generateTriggerId(null))
                        .forJob(adjustLabelPopularFactorJob).startNow()
                        .withSchedule(simpleScheduleBuilder)
                        .build();
        doScheduleJob(adjustLabelPopularFactorJob,
                adjustLabelPopularFactorJobTrigger);
    }

    @PostConstruct
    @Override
    public void initializeJobs() {
        this.saveSecurityJob();
        this.saveAdjustLabelPopularFactorJob();
    }
}
