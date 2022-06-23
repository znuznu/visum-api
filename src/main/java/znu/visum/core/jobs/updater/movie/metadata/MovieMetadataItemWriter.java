package znu.visum.core.jobs.updater.movie.metadata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.movies.domain.NoSuchMovieIdException;
import znu.visum.components.movies.infrastructure.DataJpaMovieRepository;

import java.util.List;

@Slf4j
@Component
public class MovieMetadataItemWriter implements ItemWriter<MovieMetadata> {

  private final DataJpaMovieRepository dataJpaMovieRepository;

  @Autowired
  public MovieMetadataItemWriter(DataJpaMovieRepository dataJpaMovieRepository) {
    this.dataJpaMovieRepository = dataJpaMovieRepository;
  }

  @Override
  public void write(List<? extends MovieMetadata> list) {
    for (MovieMetadata metadata : list) {
      long movieId = metadata.getMovieId();
      log.info("Writing metadata for movie with id {}", movieId);

      var movie =
          this.dataJpaMovieRepository
              .findById(movieId)
              .orElseThrow(() -> new NoSuchMovieIdException(Long.toString(movieId)));

      movie.getMovieMetadataEntity().setMovieId(metadata.getMovieId());
      movie.getMovieMetadataEntity().setTmdbId(metadata.getTmdbId());
      movie.getMovieMetadataEntity().setImdbId(metadata.getImdbId());
      movie.getMovieMetadataEntity().setOriginalLanguage(metadata.getOriginalLanguage());
      movie.getMovieMetadataEntity().setPosterUrl(metadata.getPosterUrl());
      movie.getMovieMetadataEntity().setOverview(metadata.getOverview());
      movie.getMovieMetadataEntity().setTagline(metadata.getTagline());
      movie.getMovieMetadataEntity().setBudget(metadata.getBudget());
      movie.getMovieMetadataEntity().setRevenue(metadata.getRevenue());
      movie.getMovieMetadataEntity().setRuntime(metadata.getRuntime());
    }
  }
}
