package calculatorSWT.com.luxoft.ushych.views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ViewController {

    public void show() {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
