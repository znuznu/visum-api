package znu.visum.components.genres.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.errors.NoSuchGenreIdException;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;

@Service
public class GetByIdGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public GetByIdGenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public Genre findById(long id) {
    return genreRepository
        .findById(id)
        .orElseThrow(() -> new NoSuchGenreIdException(Long.toString(id)));
  }
}
