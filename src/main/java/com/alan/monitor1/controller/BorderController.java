package com.alan.monitor1.controller;

import com.alan.monitor1.gis.*;
import com.alan.monitor1.gis.Border;
import com.alan.monitor1.service.BorderJPAService;
import com.alan.monitor1.service.BorderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class BorderController {

    @Resource(name="com.alan.monitor1.service.BorderService")
    BorderService mBorderService;


    /**
     * Simply selects the home view to render by returning its name.
     *
     * @throws Exception
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws Exception {

        return "uploadActionExcel";
    }

    @RequestMapping("/borderList") //게시판 리스트 화면 호출
    private String borderList(Model model) throws Exception{

        model.addAttribute("list",mBorderService.borderListService());
        return "borderList";

    }


    @RequestMapping("/polyList") //게시판 리스트 화면 호출
    private String polyList(Model model) throws Exception{

        //등록한 상권(kateck)
        List<Border> kateckBorderList = mBorderService.kateckBorderList();

        //좌표리스트(wgs84)
        List<Point> polyListNew = mBorderService.wgs84New();
        List<Point> polyListOld = mBorderService.wgs84Old();
        //중심좌표
        MinMaxPoint.CenterPoint centerPoint = mBorderService.createCenterPoint();


        model.addAttribute("polyListNew",polyListNew);
        model.addAttribute("polyListOld",polyListOld);
        model.addAttribute("center",centerPoint);
        model.addAttribute("list",kateckBorderList);
        return "polyList";
    }

}
