package znu.visum.business.categories.movies.services.impl;

import org.hibernate.PersistentObjectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.business.categories.movies.persistence.MovieDao;
import znu.visum.business.categories.movies.services.MovieService;
import znu.visum.business.genres.models.Genre;
import znu.visum.business.genres.persistence.GenreDao;
import znu.visum.business.people.actor.models.Actor;
import znu.visum.business.people.actor.persistence.ActorDao;
import znu.visum.business.people.actor.presentation.ActorController;
import znu.visum.business.people.director.models.Director;
import znu.visum.business.people.director.persistence.DirectorDao;
import znu.visum.core.pagination.PageSearch;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieDao movieDao;
    private final ActorDao actorDao;
    private final DirectorDao directorDao;
    private final GenreDao genreDao;

    private final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    @Autowired
    public MovieServiceImpl(MovieDao movieDao, ActorDao actorDao, DirectorDao directorDao, GenreDao genreDao) {
        this.movieDao = movieDao;
        this.actorDao = actorDao;
        this.directorDao = directorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Page<Movie> findPage(PageSearch<Movie> pageSearch) {
        return movieDao.findPage(pageSearch);
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public Optional<Movie> findById(long id) {
        return movieDao.findById(id);
    }

    @Override
    public Movie createWithIds(Movie movie) {
        if (movie.getId() != null) {
            throw new PersistentObjectException("Can't create Movie with fixed id");
        }

        return movieDao.save(movie);
    }

    @Override
    public Movie createWithNames(Movie movieWithNames) {
        boolean movieAlreaydExist = movieDao.findByTitleAndReleaseDate(
                movieWithNames.getTitle(), movieWithNames.getReleaseDate()
        ).isPresent();

        if (movieAlreaydExist) {
            logger.error("Trying to add an existing movie.");
            throw new DataIntegrityViolationException("Movie title already exists.");
        }

        Set<Actor> actors = new HashSet<>();
        Set<Director> directors = new HashSet<>();
        Set<Genre> genres = new HashSet<>();

        movieWithNames.getActors().forEach(actor -> {
            Actor actorWithIdOnly = new Actor();
            Optional<Actor> actorFound = actorDao.findByNameAndForename(actor.getName(), actor.getForename());

            if (actorFound.isPresent()) {
                actorWithIdOnly.setId(actorFound.get().getId());
            } else {
                actorWithIdOnly.setId(actorDao.save(actor).getId());
            }

            actors.add(actorWithIdOnly);
        });

        movieWithNames.getDirectors().forEach(director -> {
            Director directorWithIdOnly = new Director();
            Optional<Director> directorFound = directorDao.findByNameAndForename(director.getName(), director.getForename());

            if (directorFound.isPresent()) {
                directorWithIdOnly.setId(directorFound.get().getId());
            } else {
                directorWithIdOnly.setId(directorDao.save(director).getId());
            }

            directors.add(directorWithIdOnly);
        });

        movieWithNames.getGenres().forEach(genre -> {
            Genre genreWithIdOnly = new Genre();
            Optional<Genre> genreFound = genreDao.findByType(genre.getType());

            if (genreFound.isPresent()) {
                genreWithIdOnly.setId(genreFound.get().getId());
            } else {
                genreWithIdOnly.setId(genreDao.save(genre).getId());
            }

            genres.add(genreWithIdOnly);
        });

        return movieDao.save(new Movie.Builder()
                .title(movieWithNames.getTitle())
                .actors(actors)
                .directors(directors)
                .genres(genres)
                .isFavorite(movieWithNames.isFavorite())
                .shouldWatch(movieWithNames.isShouldWatch())
                .releaseDate(movieWithNames.getReleaseDate())
                .review(movieWithNames.getReview())
                .build()
        );
    }

    @Override
    public Movie update(Movie movie) {
        movieDao.findById(movie.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("No Movie with id %d", movie.getId())));
        return movieDao.save(movie);
    }

    @Override
    public void deleteById(long id) {
        movieDao.deleteById(id);
    }
}
