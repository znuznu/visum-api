package znu.visum.business.genres.services.impl;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import znu.visum.business.genres.models.Genre;
import znu.visum.business.genres.persistence.GenreDao;
import znu.visum.business.genres.services.GenreService;
import znu.visum.core.pagination.PageSearch;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Page<Genre> findPage(PageSearch<Genre> pageSearch) {
        return genreDao.findPage(pageSearch);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Genre create(Genre genre) {
        if (genre.getId() != null) {
            throw new PersistentObjectException("Can't create Genre with fixed id");
        }

        return genreDao.save(genre);
    }

    @Override
    public Genre update(Genre genre) {
        genreDao.findById(genre.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("No Genre with id %d", genre.getId())));
        return genreDao.save(genre);
    }

    @Override
    public void deleteById(long id) {
        genreDao.deleteById(id);
    }
}
