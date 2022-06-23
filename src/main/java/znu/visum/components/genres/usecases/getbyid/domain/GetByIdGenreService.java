package znu.visum.components.genres.usecases.getbyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.genres.domain.NoSuchGenreIdException;

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
