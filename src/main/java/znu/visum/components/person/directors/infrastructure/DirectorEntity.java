package znu.visum.components.person.directors.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import znu.visum.components.externals.domain.models.ExternalDirector;
import znu.visum.components.movies.domain.DirectorFromMovie;
import znu.visum.components.movies.infrastructure.MovieEntity;
import znu.visum.components.person.directors.domain.Director;
import znu.visum.components.person.directors.domain.MovieFromDirector;
import znu.visum.components.person.directors.usecases.getpage.domain.PageDirector;
import znu.visum.components.person.domain.Identity;
import znu.visum.components.person.infrastructure.PeopleEntity;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "director", uniqueConstraints = @UniqueConstraint(columnNames = {"tmdbId"}))
@NoArgsConstructor
@Getter
public class DirectorEntity extends PeopleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "director_id_seq")
  private Long id;

  @ManyToMany
  @JoinTable(
      name = "movie_director",
      joinColumns = @JoinColumn(name = "director_id"),
      inverseJoinColumns = @JoinColumn(name = "movie_id"))
  private Set<MovieEntity> movieEntities;

  @Embedded private DirectorMetadataEntity metadataEntity;

  @Builder
  public DirectorEntity(
      Long id,
      Set<MovieEntity> movieEntities,
      String name,
      String forename,
      DirectorMetadataEntity metadataEntity) {
    super(name, forename);
    this.id = id;
    this.movieEntities = movieEntities;
    this.metadataEntity = metadataEntity;
  }

  public static DirectorEntity from(Director director) {
    return DirectorEntity.builder()
        .id(director.getId())
        .name(director.getIdentity().getName())
        .forename(director.getIdentity().getForename())
        .movieEntities(
            new HashSet<>(
                director.getMovies().stream().map(MovieEntity::from).collect(Collectors.toSet())))
        .metadataEntity(DirectorMetadataEntity.from(director.getMetadata()))
        .build();
  }

  public static DirectorEntity from(DirectorFromMovie directorFromMovie) {
    return DirectorEntity.builder()
        .id(directorFromMovie.getId())
        .name(directorFromMovie.getIdentity().getName())
        .forename(directorFromMovie.getIdentity().getForename())
        .metadataEntity(DirectorMetadataEntity.from(directorFromMovie.getMetadata()))
        .build();
  }

  public static DirectorEntity from(ExternalDirector director) {
    return DirectorEntity.builder()
        .forename(director.getIdentity().getForename())
        .name(director.getIdentity().getName())
        .metadataEntity(
            DirectorMetadataEntity.builder()
                .posterUrl(director.getPosterUrl())
                .tmdbId(director.getId())
                .build())
        .id(null)
        .movieEntities(null)
        .build();
  }

  public PageDirector toPageDirector() {
    return PageDirector.builder()
        .id(this.id)
        .identity(Identity.builder().forename(this.getForename()).name(this.getName()).build())
        .posterUrl(this.metadataEntity.getPosterUrl())
        .tmdbId(this.metadataEntity.getTmdbId())
        .build();
  }

  public Director toDomain() {
    return Director.builder()
        .id(this.id)
        .identity(Identity.builder().forename(this.getForename()).name(this.getName()).build())
        .movies(
            this.movieEntities.stream()
                .map(MovieEntity::toMovieFromDirector)
                .sorted(Comparator.comparing(MovieFromDirector::getReleaseDate))
                .collect(Collectors.toList()))
        .metadata(this.metadataEntity.toDomain())
        .build();
  }

  public DirectorFromMovie toDirectorFromMovieDomain() {
    return DirectorFromMovie.builder()
        .id(this.id)
        .identity(Identity.builder().forename(this.getForename()).name(this.getName()).build())
        .metadata(this.metadataEntity.toDomain())
        .build();
  }

  @Override
  public int hashCode() {
    return 42;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    DirectorEntity other = (DirectorEntity) obj;
    if (id == null) {
      return false;
    } else {
      return id.equals(other.id);
    }
  }
}
