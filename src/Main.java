import controller.MaoConverterController;
import view.MaoConverter;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        MaoConverterController converterController = new MaoConverterController();
        converterController.showWindows();
    }
}
