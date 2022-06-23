package znu.visum.components.genres.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.components.genres.domain.NoSuchGenreIdException;

@Service
public class DeleteByIdGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public DeleteByIdGenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public void deleteById(long id) {
    if (genreRepository.findById(id).isEmpty()) {
      throw new NoSuchGenreIdException(Long.toString(id));
    }

    genreRepository.deleteById(id);
  }
}
