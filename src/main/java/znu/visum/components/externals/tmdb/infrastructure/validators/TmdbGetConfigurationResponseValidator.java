package znu.visum.components.externals.tmdb.infrastructure.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbGetConfigurationResponse;

public class TmdbGetConfigurationResponseValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return TmdbGetConfigurationResponse.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TmdbGetConfigurationResponse response = (TmdbGetConfigurationResponse) target;

    if (response.getImages().getPosterSizes().isEmpty()) {
      errors.rejectValue("images.posterSizes", "empty");
    }
  }
}
