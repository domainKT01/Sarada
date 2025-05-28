package com.solproe.business.repository;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface ConfigFileGenerator {
    void generate(JsonObject jsonObject, @Nullable Path filePath);
}
