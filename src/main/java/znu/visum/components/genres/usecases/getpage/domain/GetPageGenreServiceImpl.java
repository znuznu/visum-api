package znu.visum.components.genres.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.domain.ports.GenreRepository;
import znu.visum.core.pagination.domain.VisumPage;
import znu.visum.core.pagination.infrastructure.PageSearch;

@Service
public class GetPageGenreServiceImpl implements GetPageGenreService {
  private final GenreRepository genreRepository;

  @Autowired
  public GetPageGenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public VisumPage<Genre> findPage(PageSearch<Genre> pageSearch) {
    return genreRepository.findPage(pageSearch);
  }
}
