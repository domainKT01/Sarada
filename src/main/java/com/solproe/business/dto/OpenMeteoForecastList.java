package com.solproe.business.dto;

import com.solproe.business.domain.WeatherNode;

import java.util.ArrayList;
import java.util.List;

public class OpenMeteoForecastList {

    private WeatherNode lastNode = null;
    private WeatherNode firstNode = null;
    private WeatherNode node = null;

    private List<WeatherNode> nodeList = new ArrayList<>();


    public WeatherNode getLastNode() {
        return this.lastNode;
    }

    public void setLastNode(WeatherNode lastNode) {
        this.lastNode = lastNode;
    }

    public WeatherNode getFirstNode() {
        return this.firstNode;
    }

    public void setFirstNode(WeatherNode firstNode) {
        this.firstNode = firstNode;
    }

    public void addNode(WeatherNode weatherNode) {
        if (this.firstNode != null) {
            this.firstNode.setNextNode(weatherNode);
            this.node = weatherNode;
            while (node.getNextNode() != null) {
                this.node.setNextNode(weatherNode);
            }
            setLastNode(weatherNode);
        }
        else {
            setFirstNode(weatherNode);
        }
    }


    public void addNodeList(WeatherNode weatherNode) {
        this.nodeList.add(weatherNode);
    }

    public List<WeatherNode> getNodeList() {
        return nodeList;
    }
}
