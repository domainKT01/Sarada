package com.solproe.business.domain;

public class ConfigFileThreshold {
    double forestFireThresholdOrange;
    double forestFireThresholdRed;
    double precipitationThresholdOrange;
    double precipitationThresholdRed;
    double windThresholdOrange;
    double windThresholdRed;
    double precipitationRainPercentOrange;
    double precipitationRainPercentRed;
    double ceraunicosThresholdRed;
    String projectName;
    String stateName;
    String cityName;
    String idProject;
    String sciBoss;
    long sciBossContact;
    String auxiliarSciBoss;
    long auxiliarSciBossContact;
    float[] coordinate;


    public double getForestFireThresholdOrange() {
        return forestFireThresholdOrange;
    }

    public void setForestFireThresholdOrange(double forestFireThresholdOrange) {
        this.forestFireThresholdOrange = forestFireThresholdOrange;
    }

    public double getForestFireThresholdRed() {
        return forestFireThresholdRed;
    }

    public void setForestFireThresholdRed(double forestFireThresholdRed) {
        this.forestFireThresholdRed = forestFireThresholdRed;
    }

    public double getPrecipitationThresholdOrange() {
        return precipitationThresholdOrange;
    }

    public void setPrecipitationThresholdOrange(double precipitationThresholdOrange) {
        this.precipitationThresholdOrange = precipitationThresholdOrange;
    }

    public double getPrecipitationThresholdRed() {
        return precipitationThresholdRed;
    }

    public void setPrecipitationThresholdRed(double precipitationThresholdRed) {
        this.precipitationThresholdRed = precipitationThresholdRed;
    }

    public double getWindThresholdOrange() {
        return windThresholdOrange;
    }

    public void setWindThresholdOrange(double windThresholdOrange) {
        this.windThresholdOrange = windThresholdOrange;
    }

    public double getWindThresholdRed() {
        return windThresholdRed;
    }

    public void setWindThresholdRed(double windThresholdRed) {
        this.windThresholdRed = windThresholdRed;
    }

    public double getPrecipitationRainPercentOrange() {
        return precipitationRainPercentOrange;
    }

    public void setPrecipitationRainPercentOrange(double precipitationRainPercentOrange) {
        this.precipitationRainPercentOrange = precipitationRainPercentOrange;
    }

    public double getPrecipitationRainPercentRed() {
        return precipitationRainPercentRed;
    }

    public void setPrecipitationRainPercentRed(double precipitationRainPercentRed) {
        this.precipitationRainPercentRed = precipitationRainPercentRed;
    }

    public double getCeraunicosThresholdRed() {
        return ceraunicosThresholdRed;
    }

    public void setCeraunicosThresholdRed(double ceraunicosThresholdRed) {
        this.ceraunicosThresholdRed = ceraunicosThresholdRed;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getSciBoss() {
        return sciBoss;
    }

    public void setSciBoss(String sciBoss) {
        this.sciBoss = sciBoss;
    }

    public long getSciBossContact() {
        return sciBossContact;
    }

    public void setSciBossContact(long sciBossContact) {
        this.sciBossContact = sciBossContact;
    }

    public String getAuxiliarSciBoss() {
        return auxiliarSciBoss;
    }

    public void setAuxiliarSciBoss(String auxiliarSciBoss) {
        this.auxiliarSciBoss = auxiliarSciBoss;
    }

    public long getAuxiliarSciBossContact() {
        return auxiliarSciBossContact;
    }

    public void setAuxiliarSciBossContact(long auxiliarSciBossContact) {
        this.auxiliarSciBossContact = auxiliarSciBossContact;
    }

    public void setCoordinate(float[] coordinate) {
        this.coordinate = coordinate;
    }

    public float[] getCoordinate() {
        return this.coordinate;
    }
}

