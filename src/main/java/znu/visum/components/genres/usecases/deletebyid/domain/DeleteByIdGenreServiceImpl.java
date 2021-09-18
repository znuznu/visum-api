package znu.visum.components.genres.usecases.deletebyid.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.errors.NoSuchGenreIdException;
import znu.visum.components.genres.domain.ports.GenreRepository;

@Service
public class DeleteByIdGenreServiceImpl implements DeleteByIdGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public DeleteByIdGenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public void deleteById(long id) {
    if (genreRepository.findById(id).isEmpty()) {
      throw new NoSuchGenreIdException(Long.toString(id));
    }

    genreRepository.deleteById(id);
  }
}
