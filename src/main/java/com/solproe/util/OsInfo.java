package com.solproe.util;

public class OsInfo {

    public String getOsName() {
        return System.getProperty("os.name");
    }

    public String getOsVersion() {
        return System.getProperty("os.version");
    }

    public String getUserName() {
        return System.getProperty("user.name");
    }

    public String getUserHome() {
        return System.getProperty("user.home");
    }
}
