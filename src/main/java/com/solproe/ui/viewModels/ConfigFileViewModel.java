package com.solproe.ui.viewModels;

import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.business.dto.ThresholdInputModel;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.usecase.CreateConfigFileUseCase;
import com.solproe.service.config.ConfigFileGeneratorFactory;


public class ConfigFileViewModel {


    public boolean createConfigFileThreshold(ThresholdInputModel input) {
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
        ConfigFileGenerator generator = ConfigFileGeneratorFactory.getGenerator("json");
        CreateConfigFileUseCase useCase = new CreateConfigFileUseCase("threshold", generator);
        return useCase.createFileConfig(config);
    }


    public boolean createConfigFileMonthly(MonthlyThresholdInputModel model) {
        ConfigFileGenerator configFileGenerator = ConfigFileGeneratorFactory.getGenerator("json");
        CreateConfigFileUseCase createConfigFileUseCase = new CreateConfigFileUseCase("monthlyThreshold", configFileGenerator);
        return createConfigFileUseCase.createConfigFileMonthly(model);
    }

    public boolean createConfigCodesFile() {
        return false;
    }

}
