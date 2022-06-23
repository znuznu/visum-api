package znu.visum.core.jobs.updater.movie.metadata;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import znu.visum.components.movies.domain.MovieMetadata;

@Configuration
@EnableBatchProcessing
public class MovieMetadataUpdaterBatchConfiguration {

  @Bean
  public Step readTmdbAndUpdateMovieMetadataStep(
      ItemReader<MovieMetadata> reader,
      ItemWriter<MovieMetadata> writer,
      StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory
        .get("readTmdbAndUpdateMovieMetadataStep")
        .<MovieMetadata, MovieMetadata>chunk(1)
        .reader(reader)
        .writer(writer)
        .build();
  }

  @Bean
  public Job updateMovieMetadataJob(Step jobStep, JobBuilderFactory jobBuilderFactory) {
    return jobBuilderFactory
        .get("updateMovieMetadataJob")
        .incrementer(new RunIdIncrementer())
        .flow(jobStep)
        .end()
        .build();
  }
}
