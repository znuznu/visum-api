package znu.visum.core.specifications.infrastructure;

import org.springframework.data.jpa.domain.Specification;
import znu.visum.core.models.common.Criteria;
import znu.visum.core.models.common.JoinCriteria;
import znu.visum.core.specifications.CriteriaFilters;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification<T> implements Specification<T> {
  private final CriteriaFilters criteriaFilters;

  public SearchSpecification(CriteriaFilters criteriaFilters) {
    this.criteriaFilters = criteriaFilters;
  }

  private static <T> void addJoinCriteria(
      List<Predicate> predicates, JoinCriteria joinFilter, CriteriaBuilder builder, Root<T> root) {
    Criteria criteria = joinFilter.getCriteria();
    Join<Object, Object> joinParent = root.join(joinFilter.getJoinColumnName());

    String key = criteria.getPair().getKey();
    Path expression = joinParent.get(key);

    addPredicate(predicates, criteria, builder, expression);
  }

  private static <T> void addPredicates(
      List<Predicate> predicates, Criteria criteria, CriteriaBuilder builder, Root<T> root) {
    String key = criteria.getPair().getKey();
    Path expression = root.get(key);

    addPredicate(predicates, criteria, builder, expression);
  }

  private static void addPredicate(
      List<Predicate> predicates, Criteria criteria, CriteriaBuilder builder, Path expression) {
    switch (criteria.getOperator()) {
      case EQUAL:
        predicates.add(builder.equal(expression, criteria.getPair().getValue()));
        break;

      case IN:
        predicates.add(builder.in(expression).value(criteria.getPair().getValue()));
        break;
      default:
        // TODO handle
        throw new UnsupportedOperationException(
            String.format("Unsupported operator found '%s'", criteria.getOperator()));
    }
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    List<Predicate> predicates = new ArrayList<>();

    List<JoinCriteria> joinFilters = this.criteriaFilters.getJoinFilters();

    if (joinFilters != null && !joinFilters.isEmpty()) {
      for (JoinCriteria joinFilter : joinFilters) {
        addJoinCriteria(predicates, joinFilter, builder, root);
      }
    }

    List<Criteria> filters = this.criteriaFilters.getFilters();

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
