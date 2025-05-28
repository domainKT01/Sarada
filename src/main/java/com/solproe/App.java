package com.solproe;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        boolean bool = Arrays.stream(args).toList().contains("--auto");
        if (bool) {
            //code
        }
        else {

            MainApp mainApp = new MainApp();
            mainApp.exec();
        }
    }
}
