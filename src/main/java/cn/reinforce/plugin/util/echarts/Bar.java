package cn.reinforce.plugin.util.echarts;

import java.util.ArrayList;
import java.util.List;


public class Bar {

    private String title;

    private List<String> legend = new ArrayList<>();

    private List<String> xAxis = new ArrayList<>();

    private List<Series> series = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public class Series {

        private String name;

        private String type;

        private String stack;

        private List<Integer> data = new ArrayList<>();

        private Label label;

        private ItemStyle itemStyle;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }

        public List<Integer> getData() {
            return data;
        }

        public void setData(List<Integer> data) {
            this.data = data;
        }

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

        public ItemStyle getItemStyle() {
            return itemStyle;
        }

        public void setItemStyle(ItemStyle itemStyle) {
            this.itemStyle = itemStyle;
        }
    }


}
