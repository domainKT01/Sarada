package com.solproe.ui.viewModels;

import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.dto.ListCodeDTO;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.business.dto.ThresholdInputModel;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.business.usecase.CreateConfigFileUseCase;
import com.solproe.service.config.ConfigFileGeneratorFactory;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.util.ValidateLoad;
import org.jetbrains.annotations.NotNull;


public class ConfigFileViewModel {

    public boolean createConfigFileThreshold(ThresholdInputModel input) {
        ConfigFileThreshold config = getConfigFileThreshold(input);
        ConfigFileGenerator generator = ConfigFileGeneratorFactory.getGenerator("json");
        CreateConfigFileUseCase useCase = new CreateConfigFileUseCase(generator);
        String[] dirName = {
                "Sarada"
        };
        ConfigPropertiesGeneratorInterface configProperties = new ConfigPropertiesGenerator("threshold.json", dirName);
        ValidateLoad validateLoad = new ValidateLoad("threshold.json", "Sarada");
        if (validateLoad.validateFirstRun()) {
            return useCase.createConfigFile(config, configProperties);
        }
        else {
            return useCase.createConfigFile(config, configProperties);
        }
    }

    private static @NotNull ConfigFileThreshold getConfigFileThreshold(ThresholdInputModel input) {
        ConfigFileThreshold config = new ConfigFileThreshold();
        config.setForestFireThresholdOrange(input.getForestFireThresholdOrange());
        config.setForestFireThresholdRed(input.getForestFireThresholdRed());
        config.setPrecipitationThresholdOrange(input.getPrecipitationThresholdOrange());
        config.setPrecipitationThresholdRed(input.getPrecipitationThresholdRed());
        config.setWindThresholdOrange(input.getWindThresholdOrange());
        config.setWindThresholdRed(input.getWindThresholdRed());
        config.setPrecipitationRainPercentOrange(input.getPrecipitationRainPercentOrange());
        config.setPrecipitationRainPercentRed(input.getPrecipitationRainPercentRed());
        config.setCeraunicosThresholdRed(input.getCeraunicosThresholdRed());
        config.setProjectName(input.getProjectName());
        config.setStateName(input.getStateName());
        config.setCityName(input.getCityName());
        config.setIdProject(input.getIdProject());
        config.setSciBoss(input.getSciBoss());
        config.setSciBossContact(input.getSciBossContact());
        config.setAuxiliarSciBoss(input.getAuxiliarSciBoss());
        config.setAuxiliarSciBossContact(input.getAuxiliarSciBossContact());
        return config;
    }


    public boolean createConfigFileMonthly(MonthlyThresholdInputModel model) {
        ConfigFileGenerator configFileGenerator = ConfigFileGeneratorFactory.getGenerator("json");
        CreateConfigFileUseCase createConfigFileUseCase = new CreateConfigFileUseCase(configFileGenerator);
        String[] dirName = {
                "Sarada"
        };
        ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("monthlyThreshold.json", dirName);
        return createConfigFileUseCase.createMonthlyConfigFile(model, config);
    }

    public boolean createConfigCodesFile(ListCodeDTO listCodeDTO) {
        boolean bool;
        try {
            ConfigFileGenerator generator = ConfigFileGeneratorFactory.getGenerator("json");
            CreateConfigFileUseCase useCase = new CreateConfigFileUseCase(generator);
            String[] dirName = {
                    "Sarada"
            };
            ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("listCode.json", dirName);
            bool = useCase.createCodeListConfig(listCodeDTO, config);
            return bool;
        } catch (Exception e) {
            return false;
        }
    }

}
