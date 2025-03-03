package com.solproe.business.usecase;

import com.google.gson.JsonObject;
import com.solproe.business.gateway.ApiCommandInterface;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import com.solproe.business.repository.ReadConfigFile;
import com.solproe.service.APIs.ApiCommandInvoker;
import com.solproe.service.APIs.ApiService;
import com.solproe.service.APIs.GetRequestApi;
import com.solproe.service.config.ReadJsonConfigFile;
import com.solproe.service.excel.ExcelService;
import com.solproe.service.excel.ReportExcelGenerator;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateReportUseCaseTest implements RequestInterface {


    @Test
    public void report() {
        ApiCommandInvoker apiCommandInvoker = new ApiCommandInvoker();
        GenerateReportUseCase generateReportUseCase = new GenerateReportUseCase();
        ApiService apiService = new ApiService(apiCommandInvoker, generateReportUseCase);
        apiCommandInvoker.setRequestInterface(apiService);
        ApiCommandInterface apiCommandInterface = new GetRequestApi(apiService);
        apiService.setApiCommandInterface(apiCommandInterface);
        generateReportUseCase.setRequestInterface(apiService);
        ExcelService excelService = new ExcelService();
        ExcelFileGenerator excelFileGenerator = new ReportExcelGenerator(excelService);
        generateReportUseCase.setExcelFileGenerator(excelFileGenerator);
        ReadConfigFile readConfigFile = new ReadJsonConfigFile();
        generateReportUseCase.setReadConfigFile(readConfigFile);
        generateReportUseCase.generateRequestApi();
    }

    @Override
    public void doRequest(String baseUrl) {

    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        System.out.println("success test generate report use case");
    }

    @Override
    public void failedResponse(Response response) {
        System.out.println("failed test generate report use case");
    }
}