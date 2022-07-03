package znu.visum.core.jobs.updater.movie.metadata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import znu.visum.components.externals.tmdb.usecases.getmoviebyid.domain.GetTmdbMovieById;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieMetadata;
import znu.visum.components.movies.domain.MovieQueryRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TmdbMovieItemReader implements ItemReader<MovieMetadata> {

  private final GetTmdbMovieById getTmdbMovieById;
  private final MovieQueryRepository movieQueryRepository;
  private int nextMetadataIndex = 0;
  private List<MovieMetadata> movieMetadataList;

  @Autowired
  public TmdbMovieItemReader(
      GetTmdbMovieById getTmdbMovieById, MovieQueryRepository movieQueryRepository) {
    this.getTmdbMovieById = getTmdbMovieById;
    this.movieQueryRepository = movieQueryRepository;
  }

  @Override
  public MovieMetadata read() {
    boolean hasTmdbMoviesNotBeenFetched = this.movieMetadataList == null;
    if (hasTmdbMoviesNotBeenFetched) {
      this.movieMetadataList = this.fetchTmdbMovies();
    }

    MovieMetadata nextMetadata = null;

    if (nextMetadataIndex < movieMetadataList.size()) {
      nextMetadata = movieMetadataList.get(nextMetadataIndex);
      nextMetadataIndex++;
    } else {
      nextMetadataIndex = 0;
      movieMetadataList = null;
    }

    if (nextMetadata != null) {
      log.info("Found external movie with TMDb id : {}", nextMetadata.getTmdbId());
    }

    return nextMetadata;
  }

  private List<MovieMetadata> fetchTmdbMovies() {
    var movies = this.movieQueryRepository.findAll();
    log.info("Found {} movies", movies.size());

    Function<Movie, MovieMetadata> mapper =
        (movie) ->
            this.getTmdbMovieById
                .process(movie.getMetadata().getTmdbId())
                .getMetadata()
                .toMovieMetadataWithMovieId(movie.getId());

    return movies.stream().map(mapper).collect(Collectors.toList());
  }
}
