package znu.visum.components.reviews.usecases.deprecated.getbyid;

import helpers.factories.reviews.ReviewFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.reviews.domain.errors.NoSuchReviewIdException;
import znu.visum.components.reviews.usecases.deprecated.getbyid.application.GetByIdMovieReviewController;
import znu.visum.components.reviews.usecases.deprecated.getbyid.application.GetByIdMovieReviewResponse;
import znu.visum.components.reviews.usecases.deprecated.getbyid.domain.GetByIdMovieReviewService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdMovieReviewControllerUnitTest")
class GetByIdReviewControllerUnitTest {
  private GetByIdMovieReviewController controller;

  @Mock private GetByIdMovieReviewService service;

  @BeforeEach
  void setup() {
    controller = new GetByIdMovieReviewController(service);
  }

  @Test
  void givenAMovieReviewId_whenAMovieReviewWithIdExists_thenItShouldReturnTheMovieReview() {
    Mockito.when(service.findById(1L))
        .thenReturn(ReviewFactory.INSTANCE.getOneWithIdAndMovieId(1L, 1L));

    GetByIdMovieReviewResponse expectedResponse =
        new GetByIdMovieReviewResponse(
            1L,
            7,
            "Bla bla bla. \n Blo blo blo. \n Wow !",
            new GetByIdMovieReviewResponse.ResponseMovie(
                1L, "Fake movie", LocalDate.of(2021, 6, 12)),
            LocalDateTime.of(2021, 12, 12, 5, 10),
            LocalDateTime.of(2021, 12, 12, 7, 10));

    assertThat(controller.getMovieReviewById(1))
        .usingRecursiveComparison()
        .isEqualTo(expectedResponse);
  }

  @Test
  void givenAMovieReviewId_whenNoMovieReviewWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchReviewIdException("1")).when(service).findById(1L);

    Assertions.assertThrows(NoSuchReviewIdException.class, () -> controller.getMovieReviewById(1));
  }
}
