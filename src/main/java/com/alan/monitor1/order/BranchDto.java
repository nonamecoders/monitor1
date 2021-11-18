package com.alan.monitor1.order;

import lombok.Data;

import java.util.List;

@Data
public class BranchDto {

    private String branch;
    private boolean branchOpened;
    private String cookWaitingTime;
    private String coordinate;
    private String[] phoneNumbers;//
    private List<WorkingHours> workingHours;//
    private String workingHoursStatusMessage;
    private String branchName;
    private String streetAddress;
    private List<MenuGroupos> menuGroups; //

    @Data
    public static class WorkingHours {
        private String day;
        private String[] period;
    }

    @Data
    public static class MenuGroupos {
        List<Items> items;
    }

    @Data
    public static class Items {
        private String description;
        private String displayName;
        private String name;
        List<Options> options;
        private boolean outOfStock;
        private Photo photo;
        private int price;
        private boolean recommended;
    }

    @Data
    public static class Options {
        private String displayName;
        private int max;
        private int min;
        private String name;
        private String type;
        private List<Value> values;
    }

    @Data
    public static class Value {
        private int additionalPrice;
        private String displayName;
        private String name;
        private boolean outOfStock;
    }

    @Data
    public static class Photo {
        private String smallUrl;
    }


}

