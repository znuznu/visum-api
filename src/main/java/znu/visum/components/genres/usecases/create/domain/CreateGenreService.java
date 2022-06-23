package znu.visum.components.genres.usecases.create.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.GenreAlreadyExistsException;
import znu.visum.components.genres.domain.GenreRepository;

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
