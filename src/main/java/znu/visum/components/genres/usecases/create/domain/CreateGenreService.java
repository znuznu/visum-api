package znu.visum.components.genres.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.errors.GenreAlreadyExistsException;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;

@Service
public class CreateGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public CreateGenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public Genre save(Genre genre) {
    if (genreRepository.findByType(genre.getType()).isPresent()) {
      throw new GenreAlreadyExistsException();
    }

    return genreRepository.save(genre);
  }
}
