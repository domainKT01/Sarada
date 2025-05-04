package com.solproe.business.dto;

public class ListCodeDTO {
    private Integer clearSky;
    private Integer mainlyClear, partlyCloudy, overcast;
    private Integer fog, depositingRimeFog;
    private Integer lightDrizzle, moderateDrizzle, denseIntensityDrizzle;
    private Integer lightFreezingDrizzle, denseIntensityFreezingDrizzle;
    private Integer slightRain, moderateRain, heavyIntensityRain;
    private Integer lightFreezingRain, heavyIntensityFreezingRain;
    private Integer slightSnowFall, moderateSnowfall, heavyIntensitySnowFall;
    private Integer snowGrains;
    private Integer slightRainShower, moderateRainShower,violentRinShower;
    private Integer slightSnowShower, heavySnowShower;
    private Integer thunderstorm;

    public ListCodeDTO(Builder builder) {
        this.clearSky = builder.clearSky;
        this.mainlyClear = builder.mainlyClear;
        this.partlyCloudy = builder.partlyCloudy;
        this.fog = builder.fog;
        this.depositingRimeFog = builder.depositingRimeFog;
        this.lightDrizzle = builder.lightDrizzle;
        this.moderateDrizzle = builder.moderateDrizzle;
    }


    public static class Builder {
        private Integer clearSky;
        private Integer mainlyClear, partlyCloudy, overcast;
        private Integer fog, depositingRimeFog;
        private Integer lightDrizzle, moderateDrizzle, denseIntensityDrizzle;
        private Integer lightFreezingDrizzle, denseIntensityFreezingDrizzle;
        private Integer slightRain, moderateRain, heavyIntensityRain;
        private Integer lightFreezingRain, heavyIntensityFreezingRain;
        private Integer slightSnowFall, moderateSnowfall, heavyIntensitySnowFall;
        private Integer snowGrains;
        private Integer slightRainShower, moderateRainShower,violentRainShower;
        private Integer slightSnowShower, heavySnowShower;
        private Integer thunderstorm;

        public Builder clearSky(int clearSky) {
            this.clearSky = clearSky;
            return this;
        }

        public Builder mainlyClear(int mainlyClear) {
            this.mainlyClear = mainlyClear;
            return this;
        }

        public Builder partlyCloudy(int partlyCloudy) {
            this.partlyCloudy = partlyCloudy;
            return this;
        }

        public Builder fog(int fog) {
            this.fog = fog;
            return this;
        }

        public Builder depositingRimeFog(int depositingRimeFog) {
            this.depositingRimeFog = depositingRimeFog;
            return this;
        }

        public Builder lightDrizzle(int lightDrizzle) {
            this.lightDrizzle = lightDrizzle;
            return this;
        }

        public Builder moderateDrizzle(int moderateDrizzle) {
            this.moderateDrizzle = moderateDrizzle;
            return this;
        }

        public Builder denseIntensityDrizzle(int denseIntensityDrizzle) {
            this.denseIntensityDrizzle = denseIntensityDrizzle;
            return this;
        }

        public Builder lightFreezingDrizzle(int lightFreezingDrizzle) {
            this.lightFreezingDrizzle = lightFreezingDrizzle;
            return this;
        }

        public Builder denseIntensityFreezingDrizzle(int denseIntensityFreezingDrizzle) {
            this.denseIntensityFreezingDrizzle = denseIntensityFreezingDrizzle;
            return this;
        }

        public Builder slightRain(int slightRain) {
            this.slightRain = slightRain;
            return this;
        }

        public Builder moderateRain(int moderateRain) {
            this.moderateRain = moderateRain;
            return this;
        }

        public Builder heavyIntensityRain(int heavyIntensityRain) {
            this.heavyIntensityRain = heavyIntensityRain;
            return this;
        }

        public Builder lightFreezingRain(int lightFreezingRain) {
            this.lightFreezingRain = lightFreezingRain;
            return this;
        }

        public Builder heavyIntensityFreezingRain(int heavyIntensityFreezingRain) {
            this.heavyIntensityFreezingRain = heavyIntensityFreezingRain;
            return this;
        }

        public Builder slightSnowFall(int slightSnowFall) {
            this.slightSnowFall = slightSnowFall;
            return this;
        }

        public Builder moderateSnowfall(int moderateSnowfall) {
            this.moderateSnowfall = moderateSnowfall;
            return this;
        }

        public Builder heavyIntensitySnowFall(int heavyIntensitySnowFall) {
            this.heavyIntensitySnowFall = heavyIntensitySnowFall;
            return this;
        }

        public Builder snowGrains(int snowGrains) {
            this.snowGrains = snowGrains;
            return this;
        }

        public Builder slightRainShower(int slightRainShower) {
            this.slightRainShower = slightRainShower;
            return this;
        }

        public Builder moderateRainShower(int moderateRainShower) {
            this.moderateRainShower = moderateRainShower;
            return this;
        }

        public Builder violentRainShower(int violentRainShower) {
            this.violentRainShower = violentRainShower;
            return this;
        }

        public Builder slightSnowShower(int slightSnowShower) {
            this.slightSnowShower = slightSnowShower;
            return this;
        }

        public Builder heavySnowShower(int heavySnowShower) {
            this.heavySnowShower = heavySnowShower;
            return this;
        }

        public Builder thunderstorm(int thunderstorm) {
            this.thunderstorm = thunderstorm;
            return this;
        }

        public ListCodeDTO build() {
            return new ListCodeDTO(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
