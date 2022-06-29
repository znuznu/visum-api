package znu.visum.components.genres.usecases.getbyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.Genre;
import znu.visum.components.genres.domain.NoSuchGenreIdException;
import znu.visum.components.genres.usecases.getbyid.application.GetByIdGenreController;
import znu.visum.components.genres.usecases.getbyid.application.GetByIdGenreResponse;
import znu.visum.components.genres.usecases.getbyid.domain.GetByIdGenre;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdGenreControllerUnitTest")
class GetByIdTmdbGenreControllerUnitTest {
  private GetByIdGenreController controller;

  @Mock private GetByIdGenre service;

  @BeforeEach
  void setup() {
    this.controller = new GetByIdGenreController(service);
  }

  @Test
  void givenAGenreId_whenTheGenreWithTheIdExists_thenItShouldReturnTheGenre() {
    Mockito.when(service.process(1L)).thenReturn(new Genre(1L, "Something"));

    assertThat(controller.getGenreById(1))
        .usingRecursiveComparison()
        .isEqualTo(new GetByIdGenreResponse(1L, "Something"));
  }

  @Test
  void givenAGenreId_whenNoGenreWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchGenreIdException("1")).when(service).process(1L);

    Assertions.assertThrows(NoSuchGenreIdException.class, () -> controller.getGenreById(1));
  }
}
