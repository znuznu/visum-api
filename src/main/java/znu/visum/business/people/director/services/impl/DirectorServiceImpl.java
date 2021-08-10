package znu.visum.business.people.director.services.impl;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import znu.visum.business.people.director.models.Director;
import znu.visum.business.people.director.persistence.DirectorDao;
import znu.visum.business.people.director.services.DirectorService;
import znu.visum.core.pagination.PageSearch;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorDao directorDao;

    @Autowired
    public DirectorServiceImpl(DirectorDao directorDao) {
        this.directorDao = directorDao;
    }

    @Override
    public Page<Director> findPage(PageSearch<Director> pageSearch) {
        return directorDao.findPage(pageSearch);
    }

    @Override
    public Optional<Director> findById(long id) {
        return directorDao.findById(id);
    }

    @Override
    public Director create(Director director) {
        if (director.getId() != null) {
            throw new PersistentObjectException("Can't create Director with fixed id");
        }

        return directorDao.save(director);
    }

    @Override
    public Director update(Director director) {
        directorDao.findById(director.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("No Director with id %d", director.getId())));
        return directorDao.save(director);
    }

    @Override
    public void deleteById(long id) {
        directorDao.deleteById(id);
    }
}
