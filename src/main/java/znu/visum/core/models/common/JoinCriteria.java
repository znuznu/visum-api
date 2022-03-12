package znu.visum.core.models.common;

public class JoinCriteria {
  private String joinColumnName;
  private Criteria criteria;

  public JoinCriteria(String joinColumnName, Criteria criteria) {
    this.joinColumnName = joinColumnName;
    this.criteria = criteria;
  }

  public String getJoinColumnName() {
    return joinColumnName;
  }

  public void setJoinColumnName(String joinColumnName) {
    this.joinColumnName = joinColumnName;
  }

  public Criteria getCriteria() {
    return criteria;
  }

  public void setCriteria(Criteria criteria) {
    this.criteria = criteria;
  }
}
