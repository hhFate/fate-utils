package cn.reinforce.plugin.util.juhe.entity;

import com.google.gson.annotations.SerializedName;

public class Weather {

    private Sk sk;

    private Today today;

    public Sk getSk() {
        return sk;
    }

    public void setSk(Sk sk) {
        this.sk = sk;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "sk=" + sk +
                ", today=" + today +
                '}';
    }

    public class Sk {
        /**
         * 温度
         */
        private String temp;

        /**
         * 风向
         */
        private String windDirection;

        /**
         * 风力
         */
        private String windStrength;

        /**
         * 湿度
         */
        private String humidity;

        /**
         * 更新时间
         */
        private String time;

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWindDirection() {
            return windDirection;
        }

        public void setWindDirection(String windDirection) {
            this.windDirection = windDirection;
        }

        public String getWindStrength() {
            return windStrength;
        }

        public void setWindStrength(String windStrength) {
            this.windStrength = windStrength;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "Sk{" +
                    "temp='" + temp + '\'' +
                    ", windDirection='" + windDirection + '\'' +
                    ", windStrength='" + windStrength + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    public class Today {
        private String city;

        @SerializedName("date_y")
        private String dateY;

        private String week;

        private String temperature;

        private String weather;

        private String wind;

        @SerializedName("dressing_index")
        private String dressingIndex;

        @SerializedName("dressing_advice")
        private String dressingAdvice;

        @SerializedName("uv_index")
        private String uvIndex;

        @SerializedName("comfort_index")
        private String comfortIndex;

        @SerializedName("wash_index")
        private String washIndex;

        @SerializedName("travel_index")
        private String travelIndex;

        @SerializedName("exercise_index")
        private String exerciseIndex;

        @SerializedName("drying_index")
        private String dryingIndex;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDateY() {
            return dateY;
        }

        public void setDateY(String dateY) {
            this.dateY = dateY;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }

        public String getDressingIndex() {
            return dressingIndex;
        }

        public void setDressingIndex(String dressingIndex) {
            this.dressingIndex = dressingIndex;
        }

        public String getDressingAdvice() {
            return dressingAdvice;
        }

        public void setDressingAdvice(String dressingAdvice) {
            this.dressingAdvice = dressingAdvice;
        }

        public String getUvIndex() {
            return uvIndex;
        }

        public void setUvIndex(String uvIndex) {
            this.uvIndex = uvIndex;
        }

        public String getComfortIndex() {
            return comfortIndex;
        }

        public void setComfortIndex(String comfortIndex) {
            this.comfortIndex = comfortIndex;
        }

        public String getWashIndex() {
            return washIndex;
        }

        public void setWashIndex(String washIndex) {
            this.washIndex = washIndex;
        }

        public String getTravelIndex() {
            return travelIndex;
        }

        public void setTravelIndex(String travelIndex) {
            this.travelIndex = travelIndex;
        }

        public String getExerciseIndex() {
            return exerciseIndex;
        }

        public void setExerciseIndex(String exerciseIndex) {
            this.exerciseIndex = exerciseIndex;
        }

        public String getDryingIndex() {
            return dryingIndex;
        }

        public void setDryingIndex(String dryingIndex) {
            this.dryingIndex = dryingIndex;
        }

        @Override
        public String toString() {
            return "Today{" +
                    "city='" + city + '\'' +
                    ", dateY='" + dateY + '\'' +
                    ", week='" + week + '\'' +
                    ", temperature='" + temperature + '\'' +
                    ", weather='" + weather + '\'' +
                    ", wind='" + wind + '\'' +
                    ", dressingIndex='" + dressingIndex + '\'' +
                    ", dressingAdvice='" + dressingAdvice + '\'' +
                    ", uvIndex='" + uvIndex + '\'' +
                    ", comfortIndex='" + comfortIndex + '\'' +
                    ", washIndex='" + washIndex + '\'' +
                    ", travelIndex='" + travelIndex + '\'' +
                    ", exerciseIndex='" + exerciseIndex + '\'' +
                    ", dryingIndex='" + dryingIndex + '\'' +
                    '}';
        }
    }
}
