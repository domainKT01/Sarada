package com.solproe.business.repository;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

public interface ConfigFileGenerator {
    void generate(JsonObject jsonObject, @Nullable String filePath);
}
