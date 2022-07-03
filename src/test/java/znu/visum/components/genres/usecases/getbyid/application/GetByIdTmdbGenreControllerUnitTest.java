package znu.visum.components.genres.usecases.getbyid.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.NoSuchGenreIdException;
import znu.visum.components.genres.usecases.getbyid.domain.GetGenreById;

@ExtendWith(MockitoExtension.class)
class GetByIdTmdbGenreControllerUnitTest {
  private GetGenreByIdController controller;

  @Mock private GetGenreById getGenreById;

  @BeforeEach
  void setup() {
    this.controller = new GetGenreByIdController(getGenreById);
  }

  @Test
  void givenAGenreId_whenTheGenreWithTheIdExists_thenItShouldReturnTheGenre() {
    Mockito.when(getGenreById.process(1L)).thenReturn(new Genre(1L, "Something"));

    Assertions.assertThat(controller.getGenreById(1))
        .usingRecursiveComparison()
        .isEqualTo(new GetGenreByIdResponse(1L, "Something"));
  }

  @Test
  void givenAGenreId_whenNoGenreWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchGenreIdException("1")).when(getGenreById).process(1L);

    Assertions.assertThatThrownBy(() -> controller.getGenreById(1))
        .isInstanceOf(NoSuchGenreIdException.class);
  }
}
