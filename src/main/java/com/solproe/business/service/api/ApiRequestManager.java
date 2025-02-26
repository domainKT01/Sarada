package com.solproe.business.service.api;

import com.google.gson.JsonObject;
import com.solproe.business.adapters.OpenMeteoAdapter;
import com.solproe.business.dto.OpenMeteoForecastList;
import com.solproe.business.gateway.RequestInterface;
import com.solproe.business.repository.ExcelFileGenerator;
import okhttp3.Response;

public class ApiRequestManager implements RequestInterface {

    private RequestInterface requestInterface;
    private ExcelFileGenerator excelInterface;

    @Override
    public void doRequest(String baseUrl) {
        this.requestInterface.doRequest(baseUrl);
    }


    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    public void setExcelInterface(ExcelFileGenerator excelFileGenerator) {
        this.excelInterface = excelFileGenerator;
    }

    @Override
    public void successResponse(JsonObject jsonObject) {
        //generate linked list openMeteoAdapter
        OpenMeteoAdapter openMeteoAdapter = new OpenMeteoAdapter(jsonObject);
        OpenMeteoForecastList openMeteoForecastList = openMeteoAdapter.setWeatherForecastDto();

        //generate excel file
        this.excelInterface.generate("/home/prueba/Documentos/graficos.xlsx", openMeteoForecastList);
    }

    @Override
    public void failedResponse(Response response) {

    }
}
