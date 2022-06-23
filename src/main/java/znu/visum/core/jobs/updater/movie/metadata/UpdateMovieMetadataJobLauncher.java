package znu.visum.core.jobs.updater.movie.metadata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class UpdateMovieMetadataJobLauncher {

  private final Job job;
  private final JobLauncher jobLauncher;

  @Autowired
  public UpdateMovieMetadataJobLauncher(Job job, JobLauncher jobLauncher) {
    this.job = job;
    this.jobLauncher = jobLauncher;
  }

  @Scheduled(cron = "${visum.update-movie-metadata-job-cron}")
  public void runJob()
      throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
          JobRestartException, JobInstanceAlreadyCompleteException {
    log.info("Job started");
    jobLauncher.run(job, newExecution());
    log.info("Job stopped");
  }

  private JobParameters newExecution() {
    Map<String, JobParameter> parameters = new HashMap<>();
    JobParameter parameter = new JobParameter(new Date());
    parameters.put("currentTime", parameter);

    return new JobParameters(parameters);
  }
}
