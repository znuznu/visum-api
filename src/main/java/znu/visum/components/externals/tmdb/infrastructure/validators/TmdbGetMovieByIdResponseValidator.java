package znu.visum.components.externals.tmdb.infrastructure.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetMovieByIdResponse;

public class TmdbGetMovieByIdResponseValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return TmdbGetMovieByIdResponse.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TmdbGetMovieByIdResponse response = (TmdbGetMovieByIdResponse) target;

    if (response.getTitle().isBlank()) {
      errors.rejectValue("title", "blank");
    }

    if (response.getBudget() < 0) {
      errors.rejectValue("budget", "negative");
    }

    if (response.getRevenue() < 0) {
      errors.rejectValue("revenue", "negative");
    }

    if (response.getOriginalLanguage().isBlank()) {
      errors.rejectValue("original_language", "blank");
    }
  }
}
