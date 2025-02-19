package com.solproe.business.usecase;

import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigFileInterface;

public class CreateConfigFileUseCase implements ConfigFileGenerator {

    private final ConfigFileGenerator configFileGenerator;

    public CreateConfigFileUseCase(ConfigFileGenerator configFileGenerator) {
        this.configFileGenerator = configFileGenerator;
    }

    @Override
    public void generate(ConfigFileInterface configFileInterface, String path) {
        this.configFileGenerator.generate(configFileInterface, path);
    }
}
