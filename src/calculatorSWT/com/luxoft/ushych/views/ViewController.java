package calculatorSWT.com.luxoft.ushych.views;

import java.util.stream.Stream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import calculatorSWT.com.luxoft.ushych.controllers.CalculateController;
import calculatorSWT.com.luxoft.ushych.models.Operation;

public class ViewController {

    private CalculateController controller;

    private Display display;
    private Shell shell;

    private Text firstNumber;
    private Combo operation;
    private Text secondNumber;

    private Button checkButton;
    private Button calculateButton;

    private Label resultLabel;
    private Text resultText;

    private Text historyText;

    public ViewController() {
        controller = new CalculateController();

        display = new Display();
        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
        shell.setText("SWT calculator");
        shell.setSize(300, 400);
        shell.setLayout(new GridLayout(1, true));
    }

    public void show() {
        TabFolder tabFolder = getCTabFolder();

        TabItem calculatorItem = new TabItem(tabFolder, SWT.NONE, 0);
        calculatorItem.setText("Calculator");
        calculatorItem.setControl(getCalculatorControl(tabFolder));

        TabItem historyItem = new TabItem(tabFolder, SWT.NONE, 1);
        historyItem.setText("History");
        historyItem.setControl(getHistoryControl(tabFolder));

        tabFolder.addSelectionListener(getHistoriItemListener());
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private Control getHistoryControl(TabFolder tabFolder) {
        Composite composite = new Composite(tabFolder, SWT.NONE);
        composite.setLayout(new FillLayout());
        historyText = new Text(composite, SWT.READ_ONLY | SWT.V_SCROLL | SWT.BEGINNING);
        return composite;
    }

    private Control getCalculatorControl(TabFolder tabFolder) {
        Composite mainComposite = new Composite(tabFolder, SWT.NONE);
        mainComposite.setLayout(new GridLayout(1, true));

        GridData gridDate = new GridData(GridData.GRAB_VERTICAL);

        Group expresionComposite = getExpresionComposite(mainComposite);
        Group calculateComposite = getCalculateComposite(mainComposite);
        Group resultComposite = creteResultComposite(mainComposite);
        expresionComposite.setLayoutData(gridDate);
        calculateComposite.setLayoutData(gridDate);
        resultComposite.setLayoutData(gridDate);

        return mainComposite;
    }

    private Group getExpresionComposite(Composite parent) {
        Group expresionComposite = new Group(parent, SWT.NONE);
        expresionComposite.setLayout(new GridLayout(3, true));
        GridData expresionDate = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        firstNumber = getFieldWithFilter(expresionComposite);
        firstNumber.setLayoutData(expresionDate);
        operation = new Combo(expresionComposite, SWT.CENTER);
        operation.setItems(getOperations());
        operation.select(0);
        operation.addListener(SWT.Verify, getListenerForOperation());
        operation.setLayoutData(expresionDate);
        secondNumber = getFieldWithFilter(expresionComposite);
        secondNumber.setLayoutData(expresionDate);
        return expresionComposite;
    }

    private Group getCalculateComposite(Composite parent) {
        Group calculateComposite = new Group(parent, SWT.NONE);
        calculateComposite.setLayout(new GridLayout(2, true));
        GridData gridForCheck = new GridData(GridData.BEGINNING);
        checkButton = getCheckButton(calculateComposite);
        checkButton.setLayoutData(gridForCheck);

        GridData gridForButton = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        calculateButton = getCalculateButton(calculateComposite);
        calculateButton.setLayoutData(gridForButton);
        return calculateComposite;
    }

    private Group creteResultComposite(Composite parent) {
        Group resultComposite = new Group(parent, SWT.NONE);
        resultComposite.setLayout(new GridLayout(2, true));

        GridData resultLabelDate = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        resultLabel = new Label(resultComposite, SWT.NONE);
        resultLabel.setText("Result: ");
        resultLabel.setLayoutData(resultLabelDate);

        GridData resultTextDate = new GridData(GridData.FILL_HORIZONTAL);
        resultText = new Text(resultComposite, SWT.BORDER);
        resultText.setEditable(false);
        resultText.setLayoutData(resultTextDate);
        return resultComposite;
    }

    private TabFolder getCTabFolder() {
        TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
        tabFolder.setVisible(true);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        return tabFolder;
    }

    private String[] getOperations() {
        var size = Operation.values().length;
        return Stream.of(Operation.values()).map(oper -> oper.getOperation()).toArray(text -> new String[size]);
    }

    private Button getCheckButton(Composite composite) {
        checkButton = new Button(composite, SWT.CHECK);
        checkButton.setText("Calculate on the fly");
        ModifyListener listener = getModifyListener();
        checkButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (checkButton.getSelection()) {
                    addModifyListener(listener);
                    calculateButton.setEnabled(false);
                } else {
                    removeModifyListener(listener);
                    calculateButton.setEnabled(true);
                }
            }
        });

        return checkButton;
    }

    private void addModifyListener(ModifyListener listener) {
        firstNumber.addModifyListener(listener);
        operation.addModifyListener(listener);
        secondNumber.addModifyListener(listener);
    }

    private void removeModifyListener(ModifyListener listener) {
        firstNumber.removeModifyListener(listener);
        operation.removeModifyListener(listener);
        secondNumber.removeModifyListener(listener);
    }

    private Button getCalculateButton(Composite composite) {
        Button resultButton = new Button(composite, SWT.PUSH);
        resultButton.setText("Calculate");
        resultButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String result = controller.calculate(firstNumber.getText(), operation.getText(),
                        secondNumber.getText());
                resultText.setText(result);
            }
        });
        return resultButton;
    }

    private Text getFieldWithFilter(Composite composite) {
        Text text = new Text(composite, SWT.LEFT | SWT.BORDER);
        text.addListener(SWT.Verify, new Listener() {
            @Override
            public void handleEvent(Event e) {
                String string = e.text;
                if (string.matches(".*[^(\\d*(^.)\\d*)]")) {
                    e.doit = false;
                    return;
                }
            }
        });
        return text;
    }

    private SelectionAdapter getHistoriItemListener() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                historyText.setText(controller.getHistoryOperationsString());
            }
        };
    }

    private Listener getListenerForOperation() {
        return new Listener() {
            @Override
            public void handleEvent(Event e) {
                String string = e.text;
                if (string.matches(".*")) {
                    e.doit = false;
                    return;
                }
            }
        };
    }

    private ModifyListener getModifyListener() {
        ModifyListener listener = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent arg0) {
                String result = controller.calculate(firstNumber.getText(),
                        operation.getItem(operation.getSelectionIndex()), secondNumber.getText());
                resultText.setText(result);
            }
        };
        return listener;
    }

}
