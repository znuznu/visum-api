package znu.visum.business.reviews.movies.presentation;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.reviews.Review;
import znu.visum.business.reviews.movies.models.MovieReview;
import znu.visum.business.reviews.movies.services.MovieReviewService;
import znu.visum.core.pagination.PageSearch;
import znu.visum.core.pagination.PageWrapper;
import znu.visum.core.pagination.SearchSpecification;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/reviews/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(MovieReview.class)
public class MovieReviewController {
    private final MovieReviewService movieReviewService;

    @Autowired
    public MovieReviewController(MovieReviewService movieReviewService) {
        this.movieReviewService = movieReviewService;
    }

    @GetMapping
    @JsonView(PageWrapper.Views.Reviews.class)
    public PageWrapper<MovieReview> fetchPage(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "20") int limit,
            @RequestParam(required = false, defaultValue = "") String search,
            @SortDefault Sort sort
    ) {
        Specification<MovieReview> searchSpecification = SearchSpecification.parse(search);

        PageSearch<MovieReview> pageSearch = new PageSearch.Builder<MovieReview>()
                .search(searchSpecification)
                .offset(offset)
                .limit(limit)
                .sorting(sort)
                .build();

        return new PageWrapper<>(movieReviewService.findPage(pageSearch));
    }

    @GetMapping("/{id}")
    @JsonView(Review.Views.Full.class)
    public MovieReview getMovieReview(@PathVariable long id) {
        return movieReviewService
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("No MovieReview with id '%d'", id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Review.Views.Full.class)
    public MovieReview create(@RequestBody MovieReview review) {
        return movieReviewService.create(review);
    }

    @PutMapping("/{id}")
    @JsonView(Review.Views.Full.class)
    public MovieReview updateOne(@PathVariable long id, @RequestBody MovieReview movieReview) {
        movieReview.setId(id);
        return movieReviewService.update(movieReview);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        movieReviewService.deleteById(id);
    }
}
