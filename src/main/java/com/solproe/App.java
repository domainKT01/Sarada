package com.solproe;

import com.solproe.util.logging.LogInitializer;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        LogInitializer.init();
        boolean bool = Arrays.stream(args).toList().contains("--auto");
        if (bool) {

        }
        else {
            MainApp mainApp = new MainApp();
            mainApp.exec();
        }
    }
}
