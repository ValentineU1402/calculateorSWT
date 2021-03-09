package calculatorSWT.com.luxoft.ushych.views;

import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

import calculatorSWT.com.luxoft.ushych.controllers.CalculateController;
import calculatorSWT.com.luxoft.ushych.models.Operation;

public class ViewController {

    private CalculateController controller;

    private Display display;
    private Shell shell;

    private SashForm sashExpresion;
    private Text firstNumber;
    private Combo operation;
    private Text secondNumber;

    private SashForm sashCalculate;
    private Button checkButton;
    private Button calculateButton;

    private SashForm sashResult;
    private Label resultLabel;
    private Text resultText;

    public ViewController() {
        controller = new CalculateController();

        display = new Display();
        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        shell.setText("SWT calculator");
        shell.setSize(300, 400);
        shell.setLayout(new GridLayout(1, true));

        sashExpresion = new SashForm(shell, SWT.HORIZONTAL);
        firstNumber = new Text(sashExpresion, SWT.LEFT | SWT.BORDER);
        operation = new Combo(sashExpresion, SWT.CENTER);
        operation.setItems(createOperations());
        operation.select(0);
        secondNumber = new Text(sashExpresion, SWT.LEFT | SWT.BORDER);
        sashExpresion.setWeights(new int[] { 2, 1, 2 });

        sashCalculate = new SashForm(shell, SWT.HORIZONTAL);
        checkButton = new Button(sashCalculate, SWT.CHECK);
        checkButton.setText("Calculate on the fly");
        calculateButton = new Button(sashCalculate, SWT.PUSH);
        calculateButton.setText("Calculate");
        sashCalculate.setWeights(new int[] { 1, 1 });

        sashResult = new SashForm(shell, SWT.HORIZONTAL);
        resultLabel = new Label(sashResult, SWT.HORIZONTAL);
        resultLabel.setText("Result: ");
        resultText = new Text(sashResult, SWT.RIGHT | SWT.BORDER);
        resultText.setEditable(false);
        sashResult.setWeights(new int[] { 1, 1 });
    }

    public void show() {
        calculateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String result = controller.calculate(firstNumber.getText(), operation.getText(),
                        secondNumber.getText());
               resultText.setText(result);
            }
        });

        // TabFolder tabFolder = createCTabFolder();
        // TabItem calculatorItem = new TabItem(tabFolder, SWT.NONE, 0);
        // calculatorItem.setText("Calculator");
        // TabItem historyItem = new TabItem(tabFolder, SWT.NONE, 1);
        // historyItem.setText("History");

        // shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private TabFolder createCTabFolder() {
        TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
        tabFolder.setVisible(true);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
        return tabFolder;
    }

    private String[] createOperations() {
        var size = Operation.values().length;
        return Stream.of(Operation.values()).map(oper -> oper.getOperation()).toArray(text -> new String[size]);
    }
}
