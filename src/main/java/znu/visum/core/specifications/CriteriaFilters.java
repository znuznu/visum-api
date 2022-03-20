package znu.visum.core.specifications;

import znu.visum.core.models.common.Criteria;
import znu.visum.core.models.common.JoinCriteria;

import java.util.List;

public class CriteriaFilters {
  private List<Criteria> filters;
  private List<JoinCriteria> joinFilters;

  public CriteriaFilters(List<Criteria> filters, List<JoinCriteria> joinFilters) {
    this.filters = filters;
    this.joinFilters = joinFilters;
  }

  public List<Criteria> getFilters() {
    return filters;
  }

  public void setFilters(List<Criteria> filters) {
    this.filters = filters;
  }

  public List<JoinCriteria> getJoinFilters() {
    return joinFilters;
  }

  public void setJoinFilters(List<JoinCriteria> joinFilters) {
    this.joinFilters = joinFilters;
  }
}
