package com.solproe.business.domain;

public class WeatherNodeList {

    private WeatherNode firstNode = null;
    private WeatherNode lastNode = null;


    public WeatherNode getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(WeatherNode firstNode) {

        this.firstNode = firstNode;
    }

    public void addNode(WeatherNode node) {

        if (this.firstNode == null) {
            setFirstNode(node);
        }
        else if (this.lastNode == null) {
            getFirstNode().setNextNode(node);
            this.lastNode = node;
        }
        else {
            this.lastNode.setNextNode(node);
            this.lastNode = node;
        }
    }

    public WeatherNode getLastNode() {
        return lastNode;
    }

    public void setLastNode(WeatherNode lastNode) {
        this.lastNode = lastNode;
    }
}
