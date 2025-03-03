package com.solproe.service.config;

import com.solproe.business.repository.ReadConfigFile;

import java.io.File;

public class ReadJsonConfigFile implements ReadConfigFile {

    @Override
    public File readFile(String filePath) {
        File fi = new File(filePath);
        return fi;
    }
}
