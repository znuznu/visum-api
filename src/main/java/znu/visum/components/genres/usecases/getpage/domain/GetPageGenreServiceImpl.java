package znu.visum.components.genres.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetPageGenreServiceImpl implements GetPageGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public GetPageGenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public VisumPage<Genre> findPage(int limit, int offset, Sort sort, String search) {
    return genreRepository.findPage(limit, offset, sort, search);
  }
}
