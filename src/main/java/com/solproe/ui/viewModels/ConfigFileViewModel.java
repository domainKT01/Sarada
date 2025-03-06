package com.solproe.ui.viewModels;

import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.usecase.CreateConfigFileUseCase;
import com.solproe.service.config.ConfigFileGeneratorFactory;

public class ConfigFileViewModel {


    public void createConfigFileThreshold(
            double forestFireThresholdOrange,
            double forestFireThresholdRed,
            double precipitationThresholdOrange,
            double precipitationThresholdRed,
            double windThresholdOrange,
            double windThresholdRed,
            double precipitationRainPercentOrange,
            double precipitationRainPercentRed,
            double ceraunicosThresholdRed,
            String projectName,
            String stateName,
            String cityName,
            String idProject,
            String sciBoss,
            long sciBossContact,
            String auxiliarSciBoss,
            long auxiliarSciBossContact
    ) {
        System.out.println("ceraunicos: " + ceraunicosThresholdRed);
        ConfigFileThreshold configFileThreshold = new ConfigFileThreshold();
        configFileThreshold.setForestFireThresholdOrange(forestFireThresholdOrange);
        configFileThreshold.setForestFireThresholdRed(forestFireThresholdRed);
        configFileThreshold.setPrecipitationThresholdOrange(precipitationThresholdOrange);
        configFileThreshold.setPrecipitationThresholdRed(precipitationThresholdRed);
        configFileThreshold.setWindThresholdOrange(windThresholdOrange);
        configFileThreshold.setWindThresholdRed(windThresholdRed);
        configFileThreshold.setPrecipitationRainPercentOrange(precipitationRainPercentOrange);
        configFileThreshold.setPrecipitationRainPercentRed(precipitationRainPercentRed);
        configFileThreshold.setCeraunicosThresholdRed(ceraunicosThresholdRed);
        configFileThreshold.setProjectName(projectName);
        configFileThreshold.setStateName(stateName);
        configFileThreshold.setCityName(cityName);
        configFileThreshold.setIdProject(idProject);
        configFileThreshold.setSciBoss(sciBoss);
        configFileThreshold.setSciBossContact(sciBossContact);
        configFileThreshold.setAuxiliarSciBoss(auxiliarSciBoss);
        configFileThreshold.setAuxiliarSciBossContact(auxiliarSciBossContact);

        //business implement
        ConfigFileGenerator configFileGenerator = ConfigFileGeneratorFactory.getGenerator("json");
        CreateConfigFileUseCase createConfigFileUseCase = new CreateConfigFileUseCase("threshold", configFileGenerator);
        System.out.println("view model auxiliar sci boss: " + configFileThreshold.getAuxiliarSciBoss());
        boolean isSuccessful = createConfigFileUseCase.createFileConfig(configFileThreshold);
    }


    public void createFileConfigCode() {
        //code
    }


    public void createReportExcel () {

    }
}
