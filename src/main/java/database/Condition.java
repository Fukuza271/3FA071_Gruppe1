package database;

public class Condition {
    private final String column;
    private final String operator;
    private final String value;
    private final String logicalOperator; // AND, OR

    public Condition(String column, String operator, String value, String logicalOperator) {
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.logicalOperator = logicalOperator;
    }

    public String getColumn() {
        return column;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public String getLogicalOperator() {
        return logicalOperator != null ? logicalOperator : "";
    }
}
