package znu.visum.components.externals.tmdb.infrastructure.validators.getpersonmoviecredits;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetPersonMovieCreditsResponse;

public class TmdbGetPersonMovieCreditsResponseValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return TmdbGetPersonMovieCreditsResponse.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TmdbGetPersonMovieCreditsResponse response = (TmdbGetPersonMovieCreditsResponse) target;

    if (response.getCrew() == null) {
      errors.rejectValue("crew", "null");
    }
  }
}
