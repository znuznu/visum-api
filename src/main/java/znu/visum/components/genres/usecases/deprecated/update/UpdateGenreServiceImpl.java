package znu.visum.components.genres.usecases.deprecated.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.components.genres.usecases.deprecated.update.domain.UpdateGenreService;

import java.util.NoSuchElementException;

@Service
public class UpdateGenreServiceImpl implements UpdateGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public UpdateGenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public Genre update(Genre genre) {
    genreRepository
        .findById(genre.getId())
        .orElseThrow(
            () -> new NoSuchElementException(String.format("No Genre with id %d", genre.getId())));
    return genreRepository.save(genre);
  }
}
