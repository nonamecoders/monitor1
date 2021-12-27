//package com.alan.monitor1.controller;
//
//import com.alan.monitor1.common.ParamTarget;
//import com.alan.monitor1.order.SPCShortage;
//import com.alan.monitor1.service.OrderService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class OrderConroller {
//
//    @Resource(name="com.alan.monitor1.service.OrderService")
//    OrderService mOrderService;
//
//
////    @RequestMapping("/list") //게시판 리스트 화면 호출(날짜 하드 코딩)
////    private String orderList(Model model /*, HttpServletRequest request*/) throws Exception{
//////        String start_date = request.getParameter("start_date");
//////        String end_date = request.getParameter("end_date");
////        model.addAttribute("list", mOrderService.orderListService());
////
////        return "list";
////    }
//
//    @RequestMapping("/list") //게시판 리스트 화면 호출
//    private String orderList(Model model, HttpServletRequest request) throws Exception{
//
//        String start_date = request.getParameter("start_date");
//        String end_date = request.getParameter("end_date");
//
//
//        System.out.println(start_date);
//        System.out.println(end_date);
//        model.addAttribute("list",mOrderService.orderListService(start_date,end_date));
//        return "list";
//    }
//
////    @RequestMapping("/search") //게시판 리스트 화면 호출
////    private String search(Model model,@PathVariable String start_date,@PathVariable String end_date) throws Exception{
////        model.addAttribute("start_date",start_date);
////        model.addAttribute("end_date",end_date);
////
////        return "search";
////    }
//    @RequestMapping(value = "/brand/branch/shortage/spc", method= RequestMethod.POST) //SPC 결품 API
//    private String spcShortageList(HttpServletRequest request, @RequestBody String params) throws Exception{
//
//        mOrderService.shortageTest(params);
//
//        return "success";
//    }
//}
