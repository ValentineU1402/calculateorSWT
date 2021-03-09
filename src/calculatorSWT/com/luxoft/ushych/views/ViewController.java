package calculatorSWT.com.luxoft.ushych.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ViewController {

    private Display display;
    private Shell shell;

    public ViewController() {
        display = new Display();
        shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
    }

    public void show() {
        shell.setText("SWT calculator");
        shell.setSize(300, 400);
        shell.setLayout(new GridLayout(1, true));

        CTabFolder tabFolder = createCTabFolder();
        CTabItem calculatorItem = new CTabItem(tabFolder, SWT.NONE, 0);
        calculatorItem.setText("Calculator");

        CTabItem historyItem = new CTabItem(tabFolder, SWT.NONE, 1);
        historyItem.setText("History");

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private CTabFolder createCTabFolder() {
        CTabFolder tabFolder = new CTabFolder(shell, SWT.TOP);
        tabFolder.setBorderVisible(true);
        tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

        tabFolder.setSelectionBackground(new Color[] { display.getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW),
                display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW),
                display.getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW) }, new int[] { 50, 100 });

        return tabFolder;
    }
}
