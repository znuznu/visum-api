package znu.visum.core.models.common;

public class Criteria {
  private Pair<String, Object> pair;
  private Operator operator;

  public Criteria(Pair<String, Object> pair, Operator operator) {
    this.pair = pair;
    this.operator = operator;
  }

  public Pair<String, Object> getPair() {
    return pair;
  }

  public void setPair(Pair<String, Object> pair) {
    this.pair = pair;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }
}
