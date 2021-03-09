package calculatorSWT.com.luxoft.ushych.controllers;

import java.util.stream.Stream;

import calculatorSWT.com.luxoft.ushych.models.Operation;
import calculatorSWT.com.luxoft.ushych.services.CalculateService;

public class CalculateController {

    private CalculateService service;

    public CalculateController() {
        service = new CalculateService();
    }

    public String calculate(String firstNumber, String operation, String seconString) {
        Operation enumOperation = getOperation(operation);
        float result = 0f;
        switch (enumOperation) {
        case PLUS:
            result = service.plus(parseFloat(firstNumber), parseFloat(seconString));
            break;
        case SUBTRACT:
            result = service.subtract(parseFloat(firstNumber), parseFloat(seconString));
            break;
        case MULTIPLY:
            result = service.multiply(parseFloat(firstNumber), parseFloat(seconString));
            break;
        case DIVIDE:
            result = service.divide(parseFloat(firstNumber), parseFloat(seconString));
            break;
        default:
            break;
        }
        return String.valueOf(result);
    }

    private float parseFloat(String number) {
        return Float.parseFloat(number);
    }

    private Operation getOperation(String inputOperation) {
        return Stream.of(Operation.values()).filter(operation -> operation.getOperation().equals(inputOperation))
                .findFirst().orElseThrow();
    }

}
