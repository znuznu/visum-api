package znu.visum.core.specifications.infrastructure;

import org.springframework.data.jpa.domain.Specification;
import znu.visum.core.models.common.Criteria;
import znu.visum.core.models.common.JoinCriteria;
import znu.visum.core.specifications.CriteriaFilters;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification<T> implements Specification<T> {
  private final CriteriaFilters criteriaFilters;

  public SearchSpecification(CriteriaFilters criteriaFilters) {
    this.criteriaFilters = criteriaFilters;
  }

  private static <T> void addJoinCriteria(
      List<Predicate> predicates, JoinCriteria joinFilter, CriteriaBuilder builder, Root<T> root) {
    Criteria criteria = joinFilter.criteria();
    Join<Object, Object> joinParent = root.join(joinFilter.joinColumnName());

    String key = criteria.pair().key();
    Path expression = joinParent.get(key);

    addPredicate(predicates, criteria, builder, expression);
  }

  private static <T> void addPredicates(
      List<Predicate> predicates, Criteria criteria, CriteriaBuilder builder, Root<T> root) {
    String key = criteria.pair().key();
    Path expression = root.get(key);

    addPredicate(predicates, criteria, builder, expression);
  }

  private static void addPredicate(
      List<Predicate> predicates, Criteria criteria, CriteriaBuilder builder, Path expression) {
    switch (criteria.operator()) {
      case EQUAL:
        predicates.add(builder.equal(expression, criteria.pair().value()));
        break;

      case IN:
        predicates.add(builder.in(expression).value(criteria.pair().value()));
        break;
      default:
        // TODO handle
        throw new UnsupportedOperationException(
            String.format("Unsupported operator found '%s'", criteria.operator()));
    }
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    List<JoinCriteria> joinFilters = this.criteriaFilters.joinFilters();

    if (joinFilters != null && !joinFilters.isEmpty()) {
      for (JoinCriteria joinFilter : joinFilters) {
        addJoinCriteria(predicates, joinFilter, builder, root);
      }
    }

    List<Criteria> filters = this.criteriaFilters.filters();

    if (filters != null && !filters.isEmpty()) {
      for (final Criteria filter : filters) {
        addPredicates(predicates, filter, builder, root);
      }
    }

    if (predicates.isEmpty()) {
      return builder.conjunction();
    }

    return builder.and(predicates.toArray(new Predicate[0]));
  }
}
