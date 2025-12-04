package com.solproe.ui.viewModels;

import com.google.gson.JsonObject;
import com.solproe.business.domain.ConfigFileThreshold;
import com.solproe.business.dto.DashboardDto;
import com.solproe.business.dto.ListCodeDTO;
import com.solproe.business.dto.MonthlyThresholdInputModel;
import com.solproe.business.dto.ThresholdInputModel;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.repository.ConfigFileGenerator;
import com.solproe.business.repository.ConfigPropertiesGeneratorInterface;
import com.solproe.business.repository.ErrorCallback;
import com.solproe.business.repository.SuccessCallback;
import com.solproe.business.usecase.CreateConfigFileUseCase;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import com.solproe.service.config.ConfigFileGeneratorFactory;
import com.solproe.service.config.ConfigPropertiesGenerator;
import com.solproe.taskmanager.TaskScheduler;
import com.solproe.taskmanager.TaskSchedulerFactory;
import com.solproe.util.ValidateLoad;
import org.jetbrains.annotations.NotNull;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConfigFileViewModel {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public void createConfigFileThresholdAsync(SuccessCallback successCallback, ErrorCallback onFailure, ThresholdInputModel input) {
        ConfigFileThreshold config = getConfigFileThreshold(input);
        ConfigFileGenerator generator = ConfigFileGeneratorFactory.getGenerator("json");
        ApiCommandInvoker commandInvoker = new ApiCommandInvoker();
        CreateConfigFileUseCase useCase = new CreateConfigFileUseCase(generator);

        ApiService apiService = new ApiService(commandInvoker, useCase);
        commandInvoker.setRequestInterface(apiService);
        ApiCommandInterface apiCommandInterface = new GetRequestApi(apiService);
        apiService.setApiCommandInterface(apiCommandInterface);
        useCase.setRequestInterface(apiService);
        String[] dirName = {
                ".Sarada"
        };
        ConfigPropertiesGeneratorInterface configProperties = new ConfigPropertiesGenerator("threshold.json", dirName);
        ValidateLoad validateLoad = new ValidateLoad("app.log", ".Sarada");
        if (validateLoad.validateFirstRun()) {
            //new execution thread
            this.executor.submit(() -> {
                try {
                    useCase.createConfigFile(config, configProperties);
                    successCallback.onSuccess();
                } catch (Throwable e) {
                    onFailure.onError(e);
                }
            });
        }
        else {
            onFailure.onError(new FileNotFoundException("no existe el directorio de configuración: "));
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
                ".Sarada"
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
                    ".Sarada"
            };
            ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("listCode.json", dirName);
            bool = useCase.createCodeListConfig(listCodeDTO, config);
            return bool;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createConfigDash(DashboardDto dto) {
        try {
            ConfigFileGenerator generator = ConfigFileGeneratorFactory.getGenerator("json");
            CreateConfigFileUseCase useCase = new CreateConfigFileUseCase(generator);
            String[] dirName = {
                    ".Sarada"
            };
            ConfigPropertiesGeneratorInterface config = new ConfigPropertiesGenerator("dashboard.json");
            config.setDirName(dirName);
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("timeAutomatizedTask", dto.hour() + ":" + dto.minute());

            if (dto.token() != null && !dto.token().isEmpty()) {
                jsonObject.addProperty("token", dto.token());
            }

            if (dto.directory() != null && !dto.directory().isEmpty()) {
                jsonObject.addProperty("directory", dto.directory());
            }

            if (!System.getProperty("os.name").contains("ux")) {
                TaskScheduler taskScheduler = TaskSchedulerFactory.getScheduler();
                String taskName = "autoGenerateExcelReport";
                System.out.println("generating task...");
                String[] commands = {
                        "Sarada",
                        "--auto"
                };

                String scheduleTime = dto.hour() + ":" + dto.minute(); // HH:MM
                try {
                    taskScheduler.scheduleTask(taskName, "Sarada", "DAILY", scheduleTime, commands);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            return useCase.createConfigDash(jsonObject, config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
