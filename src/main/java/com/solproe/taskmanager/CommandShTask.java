package com.solproe.taskmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandShTask {

    public void execute(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("SCHTASKS OUTPUT: " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("El comando 'schtasks' falló con código de salida: " + exitCode);
        }
    }
}
