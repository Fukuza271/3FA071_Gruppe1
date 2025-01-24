package database;

public class Condition {
    private final String column;            //  Ausgewählte Spalte
    private final String operator;          //  Ob etwas Größer, kleiner, gleich oder nicht gleich ist
    private final String value;             //  Hiermit wird die spalte verglichen
    private final String logicalOperator;   // AND, OR  für weitere Konditionen


    public Condition(String column, String operator, String value, String logicalOperator) {   // Konstruktor für Konditionen
        this.column = column;
        this.operator = operator;
        this.value = value;
        this.logicalOperator = logicalOperator;
    }

    public String getColumn() {
        return column;
    }   //Getter

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public String getLogicalOperator() {
        return logicalOperator != null ? logicalOperator : "";
    } //getter ende
}
