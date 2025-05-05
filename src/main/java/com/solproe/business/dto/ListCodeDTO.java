package com.solproe.business.dto;

import java.lang.reflect.Field;

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
        Class<?> builderClass = builder.getClass();
        Class<?> dtoClass = this.getClass();

        Field[] builderFields = builderClass.getDeclaredFields();

        try {
            for (Field builderField : builderFields) {
                String fieldName = builderField.getName();
                try {
                    if (builderClass.getDeclaredField(fieldName).get(builder) != null) {
                        Field dtoField = dtoClass.getDeclaredField(fieldName);
                        // Asegurarse de que los tipos sean compatibles (o intentar convertirlos)
                        if (dtoField.getType().isAssignableFrom(builderField.getType())) {
                            builderField.setAccessible(true);
                            dtoField.setAccessible(true);
                            Object value = builderField.get(builder);
                            dtoField.set(this, value);
                        }
                    }
                } catch (NoSuchFieldException e) {
                    // El campo no existe en el DTO, se ignora
                } catch (IllegalAccessException e) {
                    // Error al acceder al campo, podrías loggear o lanzar una excepción
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // Manejar otras excepciones que puedan ocurrir durante la reflexión
            e.printStackTrace();
        }
    }

    public Integer getClearSky() {
        return clearSky;
    }

    public Integer getMainlyClear() {
        return mainlyClear;
    }

    public Integer getPartlyCloudy() {
        return partlyCloudy;
    }

    public Integer getOvercast() {
        return overcast;
    }

    public Integer getFog() {
        return fog;
    }

    public Integer getDepositingRimeFog() {
        return depositingRimeFog;
    }

    public Integer getLightDrizzle() {
        return lightDrizzle;
    }

    public Integer getModerateDrizzle() {
        return moderateDrizzle;
    }

    public Integer getDenseIntensityDrizzle() {
        return denseIntensityDrizzle;
    }

    public Integer getLightFreezingDrizzle() {
        return lightFreezingDrizzle;
    }

    public Integer getDenseIntensityFreezingDrizzle() {
        return denseIntensityFreezingDrizzle;
    }

    public Integer getSlightRain() {
        return slightRain;
    }

    public Integer getModerateRain() {
        return moderateRain;
    }

    public Integer getHeavyIntensityRain() {
        return heavyIntensityRain;
    }

    public Integer getLightFreezingRain() {
        return lightFreezingRain;
    }

    public Integer getHeavyIntensityFreezingRain() {
        return heavyIntensityFreezingRain;
    }

    public Integer getSlightSnowFall() {
        return slightSnowFall;
    }

    public Integer getModerateSnowfall() {
        return moderateSnowfall;
    }

    public Integer getHeavyIntensitySnowFall() {
        return heavyIntensitySnowFall;
    }

    public Integer getSnowGrains() {
        return snowGrains;
    }

    public Integer getSlightRainShower() {
        return slightRainShower;
    }

    public Integer getModerateRainShower() {
        return moderateRainShower;
    }

    public Integer getViolentRinShower() {
        return violentRinShower;
    }

    public Integer getSlightSnowShower() {
        return slightSnowShower;
    }

    public Integer getHeavySnowShower() {
        return heavySnowShower;
    }

    public Integer getThunderstorm() {
        return thunderstorm;
    }

    @SuppressWarnings("FieldCanBeLocal")
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
