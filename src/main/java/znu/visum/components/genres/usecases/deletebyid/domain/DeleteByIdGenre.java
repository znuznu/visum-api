package znu.visum.components.genres.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.genres.domain.NoSuchGenreIdException;

@Service
public class DeleteByIdGenre {
  private final GenreRepository genreRepository;

  @Autowired
  public DeleteByIdGenre(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public void process(long id) {
    if (!genreRepository.existsById(id)) {
      throw new NoSuchGenreIdException(Long.toString(id));
    }

    genreRepository.deleteById(id);
  }
}
