package online.nwen.server.service.api;

public interface IJobService {
    interface IJobConstant {
        enum Job {
            SECURITY("JOB-SECURITY", "TRIGGER-SECURITY"),
            ADJUST_LABEL_POPULAR_FACTOR("JOB-ADJUST_LABEL_POPULAR_FACTOR", "TRIGGER-ADJUST_LABEL_POPULAR_FACTOR");
            private String jobIdFormat;
            private String triggerIdFormat;

            Job(String jobIdFormat, String triggerIdFormat) {
                this.jobIdFormat = jobIdFormat;
                this.triggerIdFormat = triggerIdFormat;
            }

            public String generateJobId(String token) {
                return String.format(this.jobIdFormat, token);
            }

            public String generateTriggerId(String token) {
                return String.format(this.triggerIdFormat, token);
            }
        }
    }

    void initializeJobs();
}
