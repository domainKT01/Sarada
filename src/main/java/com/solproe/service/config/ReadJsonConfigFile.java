package com.solproe.service.config;

import com.solproe.business.repository.ReadConfigFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadJsonConfigFile implements ReadConfigFile {

    @Override
    public File readFile(String filePath) {
        File fi = new File(filePath);
        return fi;
    }
}
