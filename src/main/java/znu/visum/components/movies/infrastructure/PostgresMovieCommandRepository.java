package znu.visum.components.movies.infrastructure;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import znu.visum.components.externals.domain.ExternalCastMember;
import znu.visum.components.externals.domain.ExternalDirector;
import znu.visum.components.externals.domain.ExternalMovie;
import znu.visum.components.genres.infrastructure.DataJpaGenreRepository;
import znu.visum.components.genres.infrastructure.GenreEntity;
import znu.visum.components.movies.domain.Movie;
import znu.visum.components.movies.domain.MovieCommandRepository;
import znu.visum.components.person.actors.infrastructure.ActorEntity;
import znu.visum.components.person.actors.infrastructure.DataJpaActorRepository;
import znu.visum.components.person.directors.infrastructure.DataJpaDirectorRepository;
import znu.visum.components.person.directors.infrastructure.DirectorEntity;
import znu.visum.core.exceptions.domain.InternalException;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class PostgresMovieCommandRepository implements MovieCommandRepository {

  private final DataJpaMovieRepository dataJpaMovieRepository;
  private final DataJpaActorRepository dataJpaActorRepository;
  private final DataJpaGenreRepository dataJpaGenreRepository;
  private final DataJpaDirectorRepository dataJpaDirectorRepository;

  public PostgresMovieCommandRepository(
      DataJpaMovieRepository dataJpaMovieRepository,
      DataJpaActorRepository dataJpaActorRepository,
      DataJpaGenreRepository dataJpaGenreRepository,
      DataJpaDirectorRepository dataJpaDirectorRepository) {
    this.dataJpaMovieRepository = dataJpaMovieRepository;
    this.dataJpaActorRepository = dataJpaActorRepository;
    this.dataJpaGenreRepository = dataJpaGenreRepository;
    this.dataJpaDirectorRepository = dataJpaDirectorRepository;
  }

  @Override
  public void deleteById(long id) {
    dataJpaMovieRepository.deleteById(id);
  }

  @Transactional
  @Override
  public void updateFavorite(long movieId, boolean isFavorite) {
    var movie =
        dataJpaMovieRepository
            .findById(movieId)
            .orElseThrow(
                () -> InternalException.withMessage("Movie with id " + movieId + " no found."));
    movie.setFavorite(isFavorite);
  }

  @Transactional
  @Override
  public void updateToWatch(long movieId, boolean isToWatch) {
    var movie =
        dataJpaMovieRepository
            .findById(movieId)
            .orElseThrow(
                () -> InternalException.withMessage("Movie with id " + movieId + " no found."));
    movie.setShouldWatch(isToWatch);
  }

  // TODO(#41) isFavorite and isToWatch should not be linked to the movie
  @Override
  public Movie save(ExternalMovie movie, boolean isFavorite, boolean isToWatch) {
    var members =
        movie.getCredits().getCast().getMembers().stream()
            .map(
                member -> {
                  var actor = getOrSaveActor().apply(member);
                  var castMemberId = new CastMemberId(null, null, member.getRole().getCharacter());
                  return CastMemberEntity.builder()
                      .actor(actor)
                      .roleOrder(member.getRole().getOrder())
                      .castMemberId(castMemberId)
                      .build();
                })
            .toList();

    Set<DirectorEntity> directors =
        movie.getCredits().getDirectors().stream()
            .map(getOrSaveDirector())
            .collect(Collectors.toUnmodifiableSet());

    Set<GenreEntity> genres =
        movie.getGenres().stream().map(getOrSaveGenre()).collect(Collectors.toUnmodifiableSet());

    var movieToSave =
        MovieEntity.builder()
            .releaseDate(movie.getReleaseDate())
            .title(movie.getTitle())
            .genreEntities(genres)
            .directorEntities(directors)
            .isFavorite(isFavorite)
            .shouldWatch(isToWatch)
            .viewingHistory(new ArrayList<>())

            // In order to see them
            .castMembersEntity(null)
            .movieMetadataEntity(null)
            .id(null)
            .review(null)
            .build();

    members.forEach(
        member -> {
          member.setMovie(movieToSave);
        });
    movieToSave.setCastMembers(members);

    var metadata = MovieMetadataEntity.from(movie.getMetadata());
    metadata.setMovie(movieToSave);
    movieToSave.setMovieMetadataEntity(metadata);

    return dataJpaMovieRepository.save(movieToSave).toDomain();
  }

  private Function<ExternalCastMember, ActorEntity> getOrSaveActor() {
    return actor ->
        dataJpaActorRepository
            .findByMetadataEntity_TmdbId(actor.getId())
            .orElseGet(() -> dataJpaActorRepository.save(ActorEntity.from(actor)));
  }

  private Function<ExternalDirector, DirectorEntity> getOrSaveDirector() {
    return director ->
        dataJpaDirectorRepository
            .findByMetadataEntity_TmdbId(director.getId())
            .orElseGet(() -> dataJpaDirectorRepository.save(DirectorEntity.from(director)));
  }

  private Function<String, GenreEntity> getOrSaveGenre() {
    return genre ->
        dataJpaGenreRepository
            .findByType(genre)
            .orElseGet(() -> dataJpaGenreRepository.save(new GenreEntity(null, genre, null)));
  }
}
