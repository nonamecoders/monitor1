package com.alan.monitor1.order;

import lombok.*;

import java.util.List;

@Data
public class SpcStock {

//    List<Stock> action_args;
//    String action_type;
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Stock {
//
//        List<Menu> menus;
//        String vendor_code;
//        String store_id;
//
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Menu {
//
//        String code;
//        String name;
//        Integer amount;
//        List<Option> options;
//
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static  class  Option {
//
//        String code;
//        String name;
//        Integer amount;
//
//    }
    Action_args action_args;
    String action_type;
    int status_code;
    Result result;
    Error error;
    String store_id;
    boolean success;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Action_args {
        Stock stock;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Stock {
        List<Menu> menus;
        List<Option> options;
        String vendor_code;
        String store_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Menu {
        String code;
        String name;
        int amount;
        int quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Option {
        String code;
        String name;
        int amount;
        int quantity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        List<Menu> menus;
        List<Option> options;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        String simple;
        int code;
    }


}
