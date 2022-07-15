package znu.visum.core.specifications;

import znu.visum.core.models.common.Criteria;
import znu.visum.core.models.common.JoinCriteria;

import java.util.List;

public record CriteriaFilters(List<Criteria> filters, List<JoinCriteria> joinFilters) {
}
