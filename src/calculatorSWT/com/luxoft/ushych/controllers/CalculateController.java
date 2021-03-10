package calculatorSWT.com.luxoft.ushych.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import calculatorSWT.com.luxoft.ushych.models.Operation;
import calculatorSWT.com.luxoft.ushych.services.CalculateService;

public class CalculateController {

    private CalculateService service;
    private List<String> historyOperations;

    public CalculateController() {
        service = new CalculateService();
        historyOperations = new ArrayList<>();
    }

    public String calculate(String firstNumber, String operation, String secondNumber) {
        Operation enumOperation = getOperation(operation);
        String result = "result";
        if (checkInput(firstNumber, enumOperation, secondNumber)) {
            float firstDigit = parseFloat(firstNumber);
            float secondDigit = parseFloat(secondNumber);
            result = String.valueOf(switchOperation(firstDigit, enumOperation, secondDigit));
            historyOperations.add(String.format("%s %s %s = %s", firstNumber, operation, secondNumber, result));
        } else {
            result = "Incorect input type";
            historyOperations.add(result);
        }
        return String.valueOf(result);
    }

    private float switchOperation(float firstNumber, Operation operation, float secondNumber) {
        float result = 0f;
        switch (operation) {
        case PLUS:
            result = service.plus(firstNumber, secondNumber);
            break;
        case SUBTRACT:
            result = service.subtract(firstNumber, secondNumber);
            break;
        case MULTIPLY:
            result = service.multiply(firstNumber, secondNumber);
            break;
        case DIVIDE:
            result = service.divide(firstNumber, secondNumber);
            break;
        default:
            break;
        }
        return result;
    }

    private float parseFloat(String number) {
        return Float.parseFloat(number);
    }

    private Operation getOperation(String inputOperation) {
        return Stream.of(Operation.values()).filter(operation -> operation.getOperation().equals(inputOperation))
                .findFirst().orElseThrow();
    }

    private boolean checkInput(String first, Operation operation, String second) {
        boolean result = true;
        if (operation.equals(Operation.DIVIDE) && second.equals("0")) {
            result = false;
        }
        if ((first.isBlank() || first.isEmpty()) || (second.isBlank() || second.isEmpty())) {
            result = false;
        }
        try {
            parseFloat(second);
            parseFloat(first);
        }catch (Exception ex) {
            result = false;
        }
            return result;
    }

    public String getHistoryOperationsString() {
        return historyOperations.stream().collect(Collectors.joining("\n"));
    }

}
