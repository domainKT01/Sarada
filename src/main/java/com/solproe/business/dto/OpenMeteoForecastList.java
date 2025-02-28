package com.solproe.business.dto;

import com.solproe.business.domain.WeatherNode;

import java.util.ArrayList;
import java.util.List;

public class OpenMeteoForecastList {
    private final List<WeatherNode> nodeList = new ArrayList<>();


    public void addNodeList(WeatherNode weatherNode) {
        this.nodeList.add(weatherNode);
    }

    public List<WeatherNode> getNodeList() {
        return nodeList;
    }
}
