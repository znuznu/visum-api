package znu.visum.components.genres.usecases.getbyid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import znu.visum.components.genres.domain.errors.NoSuchGenreIdException;
import znu.visum.components.genres.domain.models.Genre;
import znu.visum.components.genres.usecases.getbyid.application.GetByIdGenreController;
import znu.visum.components.genres.usecases.getbyid.application.GetByIdGenreResponse;
import znu.visum.components.genres.usecases.getbyid.domain.GetByIdGenreService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetByIdGenreControllerUnitTest")
public class GetByIdTmdbGenreControllerUnitTest {
  private GetByIdGenreController controller;

  @Mock private GetByIdGenreService service;

  @BeforeEach
  void setup() {
    this.controller = new GetByIdGenreController(service);
  }

  @Test
  public void givenAGenreId_whenTheGenreWithTheIdExists_thenItShouldReturnTheGenre() {
    Mockito.when(service.findById(1L)).thenReturn(new Genre(1L, "Something"));

    assertThat(controller.getGenreById(1))
        .usingRecursiveComparison()
        .isEqualTo(new GetByIdGenreResponse(1L, "Something"));
  }

  @Test
  public void givenAGenreId_whenNoGenreWithTheIdExists_thenItShouldThrow() {
    Mockito.doThrow(new NoSuchGenreIdException("1")).when(service).findById(1L);

    Assertions.assertThrows(NoSuchGenreIdException.class, () -> controller.getGenreById(1));
  }
}
