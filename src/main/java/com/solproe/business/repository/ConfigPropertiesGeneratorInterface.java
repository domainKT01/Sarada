package com.solproe.business.repository;

import java.nio.file.Path;

public interface ConfigPropertiesGeneratorInterface {
    Path getAppConfigPath();

    // Tus métodos createPropertyFile y addProperties usarían getAppConfigPath()
    // en lugar de getFilePath() para obtener la ruta al archivo.
    // Ejemplo para createPropertyFile:
    boolean createPropertyFile();

    // Similarmente para addProperties...
    boolean addProperties(String[][] properties);
}
