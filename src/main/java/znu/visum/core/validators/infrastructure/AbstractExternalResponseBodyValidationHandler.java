package znu.visum.core.validators.infrastructure;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;
import znu.visum.components.externals.domain.ExternalApi;
import znu.visum.core.errors.domain.ExternalApiUnexpectedResponseBodyException;

public abstract class AbstractExternalResponseBodyValidationHandler<T, U extends Validator> {

  private final Class<T> validationClass;

  private final U validator;

  private final ExternalApi externalApi;

  protected AbstractExternalResponseBodyValidationHandler(
      Class<T> clazz, U validator, ExternalApi externalApi) {
    this.validationClass = clazz;
    this.validator = validator;
    this.externalApi = externalApi;
  }

  public Mono<T> validate(Mono<T> mono) {
    return mono.flatMap(
        body -> {
          Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
          this.validator.validate(body, errors);

          if (errors.getAllErrors().isEmpty()) {
            return Mono.just(body);
          }

          return Mono.error(
              ExternalApiUnexpectedResponseBodyException.withMessageForApi(
                  errors.getAllErrors().toString(), this.externalApi));
        });
  }
}
