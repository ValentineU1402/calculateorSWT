package calculatorSWT.com.luxoft.ushych.models;

public enum Operation {
    PLUS("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/");

    private String operation;

    private Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
