package com.solproe.business.dto;

import java.nio.file.Path;

public class PathDto {
    private static Path path = null;

    private PathDto() {

    }


    public static Path getInstance() {
        if (path == null) {
            throw new NullPointerException("path no initialized");
        }
        return path;
    }


    public static void init(Path inputPath) {
        if (path == null) {
            path = inputPath;
        }
        else {
            throw new NullPointerException("path already initialized");
        }
    }
}
