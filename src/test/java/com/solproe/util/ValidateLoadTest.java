package com.solproe.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateLoadTest {

    @Test
    void validateFirstRun() {
        ValidateLoad validateLoad = new ValidateLoad("config.properties", ".Sarada");
        boolean bool = validateLoad.validateFirstRun();
        if (bool) {
            System.out.println("first run is true");
        }
        else {
            System.out.println("first run is false");
        }
    }
}