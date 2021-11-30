package znu.visum.components.externals.tmdb.infrastructure.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbSearchMoviesResponse;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbSearchResponse;

public class TmdbSearchMoviesResponseValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TmdbSearchResponse.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TmdbSearchMoviesResponse response = (TmdbSearchMoviesResponse) target;

    if (response.getPage() < 1) {
      errors.rejectValue("page", "inferior.to.one");
    }

    if (response.getTotalPages() < 0) {
      errors.rejectValue("totalPages", "negative");
    }

    if (response.getTotalResults() < 0) {
      errors.rejectValue("totalResults", "negative");
    }

    // TODO refactor with ValidationUtils.rejectsIfEmpty() ?
    if (response.getResults() == null) {
      errors.rejectValue("results", "null");
    }
  }
}
