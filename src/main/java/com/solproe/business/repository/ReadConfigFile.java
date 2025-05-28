package com.solproe.business.repository;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

public interface ReadConfigFile {
    File readFile(Path filePath);
}
