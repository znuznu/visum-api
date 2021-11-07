package znu.visum.core.pagination.infrastructure;

import org.springframework.data.jpa.domain.Specification;
import znu.visum.core.pagination.domain.VisumOperator;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.regex.Matcher;

public class SearchSpecification<T> implements Specification<T> {
  private final String attribute;
  private final VisumOperator operator;
  private final Object value;

  public SearchSpecification(String field, VisumOperator operator, Object value) {
    this.attribute = field;
    this.operator = operator;
    this.value = value;
  }

  /**
   * Create a Specification based on an expression. Currently, only OR and AND are supported. This
   * method is way too simple and isn't scalable.
   *
   * @param exp The expression to parse
   * @return a Specification made of the parsed expression
   */
  public static <T> Specification<T> parse(String exp) {
    Specification<T> specification = Specification.where(null);

    if (exp == null) {
      return specification;
    }

    List<Specification<T>> orSpecifications = new ArrayList<>();

    // Split on `*` (AND)
    String[] andAttributes = exp.split("\\*");

    for (String andAttribute : andAttributes) {
      Specification<T> orSpecification = Specification.where(null);

      // Split on `,` (OR)
      String[] orAttributes = andAttribute.split(",");

      for (String orAttribute : orAttributes) {
        orSpecification = orSpecification.or(parseExpression(orAttribute));
      }

      // Add the entire current (OR)
      orSpecifications.add(orSpecification);
    }

    // Add an (AND) on all previous (OR)
    for (Specification<T> s : orSpecifications) {
      specification = specification.and(s);
    }

    return specification;
  }

  public static <T> Specification<T> parseExpression(String exp) {
    return Arrays.stream(VisumOperator.values())
        .map(
            o -> {
              Matcher matcher = o.getPattern().matcher(exp);
              if (matcher.matches()) {
                String attributeName = matcher.group(1);
                String value = matcher.group(2);
                return new SearchSpecification<T>(attributeName, o, value);
              }

              return null;
            })
        .filter(Objects::nonNull)
        .findFirst()
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    String.format("No operator matching expression '%s'", exp)));
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
    Path<String> path = resolvePath(root, this.attribute);

    var searchValue = this.value.toString();

    switch (operator) {
      case LT:
        return cb.lessThan(path, searchValue);
      case LTE:
        return cb.lessThanOrEqualTo(path, searchValue);
      case GT:
        return cb.greaterThan(path, searchValue);
      case GTE:
        return cb.greaterThanOrEqualTo(path, searchValue);
      case EQ:
        return cb.equal(path, searchValue);
      case EQB:
        return cb.equal(path, Boolean.parseBoolean(searchValue));
      case LIKE:
        return cb.like(cb.lower(path), "%" + searchValue.toLowerCase() + "%");
      case SW:
        return cb.like(cb.lower(path), searchValue.toLowerCase() + "%");
      case EW:
        return cb.like(cb.lower(path), "%" + searchValue.toLowerCase());
      case IN:
        return cb.in(path).value(searchValue);
      default:
        throw new UnsupportedOperationException(
            String.format("Unsupported operator found '%s'", operator));
    }
  }

  private Path<String> resolvePath(Root<T> root, String attribute) {
    String[] parts = attribute.split("\\.");

    Path<String> path = root.get(parts[0]);

    for (int i = 1; i < parts.length; i++) {
      path = path.get(parts[i]);
    }

    return path;
  }
}
