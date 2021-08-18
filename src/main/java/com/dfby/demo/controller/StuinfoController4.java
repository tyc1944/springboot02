package com.dfby.demo.controller;

import com.dfby.demo.pojo.Stuinfo;
import com.dfby.demo.service.impl.StuinfoServiceImpl;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dfby.demo.service.StuinfoService;
import com.dfby.demo.util.Page;
import com.dfby.demo.util.Response;
import com.dfby.demo.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StuinfoController4 {
    Logger logger = (Logger) LoggerFactory.getLogger(getClass());//日志
    @Autowired
    private StuinfoService stuinfoService;

    @GetMapping("/stuinfo4")
    public Map list(Stuinfo stuinfo,Integer page,Integer limit){
        Page mypage=new Page();
        mypage.setPageNo(page);
        mypage.setPageSize(limit);
        mypage.setRowCount(stuinfoService.selectCountByCon(stuinfo));
        List<Stuinfo> stuinfoList=stuinfoService.selectListByCon(stuinfo,mypage);
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","query success");
        map.put("count",mypage.getRowCount());
        map.put("data",stuinfoList);
        logger.trace("trace 级别日志");
        logger.debug("debug 级别日志");
        logger.info("info 级别日志");
        logger.warn("warn 级别日志");
        logger.error("error 级别日志");
        return map;
    }
    @GetMapping("/stuinfo4_2")
    public Result list4(Stuinfo stuinfo,Integer page,Integer limit)
    {
        PageInfo<Stuinfo> pageInfo=stuinfoService.selectPageInfo(stuinfo,page,limit);
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","query success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        logger.info("我的控制器~~"+map);
        return  Response.success(map);
    }
    @PostMapping("/stuinfo4")
    public Result add(Stuinfo stuinfo){
        if(stuinfoService.insert(stuinfo)>0){
            return Response.success();
        }else{
            return Response.error();
        }
    }

    @GetMapping("/stuinfo4/{stuno}")
    public Result toUpdate(@PathVariable("stuno") String stuno){

        return Response.success(stuinfoService.selectByPrimaryKey(stuno));
    }

    @PutMapping("/stuinfo4")
    public Result update(Stuinfo stuinfo){
        if(stuinfoService.updateByPrimaryKey(stuinfo)>0){
            return Response.success();
        }else{
            return Response.error();
        }
    }

    @DeleteMapping("/stuinfo4/{stuno}")
    public Result delete(@PathVariable("stuno") String stuno){
        if(stuinfoService.deleteByPrimaryKey(stuno)>0){
            return Response.success();
        }else{
            return Response.error();
        }
    }
}
