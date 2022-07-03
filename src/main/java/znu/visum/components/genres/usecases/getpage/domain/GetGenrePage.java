package znu.visum.components.genres.usecases.getpage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.GenreRepository;
import znu.visum.core.pagination.domain.VisumPage;

@Service
public class GetGenrePage {
  private final GenreRepository genreRepository;

  @Autowired
  public GetGenrePage(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public VisumPage<Genre> process(int limit, int offset, Sort sort, String search) {
    return genreRepository.findPage(limit, offset, sort, search);
  }
}
