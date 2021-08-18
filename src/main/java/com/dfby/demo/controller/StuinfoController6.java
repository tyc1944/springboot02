package com.dfby.demo.controller;

import com.dfby.demo.pojo.Stuinfo;
import com.dfby.demo.service.StuinfoService;
import com.dfby.demo.service.StuinfoService2;
import com.dfby.demo.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StuinfoController6 {
    Logger logger = (Logger) LoggerFactory.getLogger(getClass());//日志
    @Autowired
    private StuinfoService2 stuinfoService;
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/list6")
    public String list(Model model){
//        if(redisUtil.hasKey("stuinfolist")){
//            logger.info("走缓存----");
//            model.addAttribute("stuinfoList",redisUtil.range("stuinfolist",0,redisUtil.listLength("stuinfolist")));
//        }else{
//            logger.info("走数据库----");
//            List stuinfoList=stuinfoService.selectAll();
//            model.addAttribute("stuinfoList",stuinfoList);
//            redisUtil.leftPushAll("stuinfolist",stuinfoList);
//            redisUtil.expire("stuinfolist",30);
//
//        }
            model.addAttribute("stuinfoList",stuinfoService.selectAll());
        return "list";
    }

    @RequestMapping("/listadd6")
    public String add(Stuinfo stuinfo,Model model){
        if(stuinfoService.insert(stuinfo)!=null){
            model.addAttribute("msg","添加成功");
        }else{
            model.addAttribute("msg","添加失败");
        }
        return list(model);
    }

    @RequestMapping("/listtoUpdate6")
    public String toUpdate(String stuno,Model model){
        model.addAttribute("stuinfo",stuinfoService.selectByPrimaryKey(stuno));
        return "edit";
    }

    @RequestMapping("/listupdate6")
    public String update(Stuinfo stuinfo,Model model){
        if(stuinfoService.updateByPrimaryKey(stuinfo)>0){
            model.addAttribute("msg","修改成功");
        }else{
            model.addAttribute("msg","修改失败");
        }
        return list(model);
    }

    @RequestMapping("/listdelete6")
    public String delete(String stuno,Model model){
        if(stuinfoService.deleteByPrimaryKey(stuno)>0){
            model.addAttribute("msg","删除成功");
        }else{
            model.addAttribute("msg","删除失败");
        }
        return list(model);
    }
}
