package com.solproe.service.config;

import com.solproe.business.repository.ReadConfigFile;

import java.io.File;
import java.nio.file.Path;

public class ReadJsonConfigFile implements ReadConfigFile {

    @Override
    public File readFile(Path filePath) {
        File fi = new File(filePath.toUri());
        return fi;
    }
}
