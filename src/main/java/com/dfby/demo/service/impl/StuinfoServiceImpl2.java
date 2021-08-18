package com.dfby.demo.service.impl;

import com.dfby.demo.mapper.StuinfoMapper;
import com.dfby.demo.pojo.Stuinfo;
import com.dfby.demo.service.StuinfoService;
import com.dfby.demo.service.StuinfoService2;
import com.dfby.demo.util.Page;
import com.dfby.demo.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StuinfoServiceImpl2 implements StuinfoService2 {
    Logger logger = (Logger) LoggerFactory.getLogger(getClass());//日志
    @Autowired
    private StuinfoMapper stuinfoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    //将缓存失效
    @CacheEvict(value = "rediscache", key="'user_'+#stuno")
    public int deleteByPrimaryKey(String stuno) {
        return stuinfoMapper.deleteByPrimaryKey(stuno);
    }

    @Override
    //将返回结果放入缓存,value与application.properties中的
    //spring.cache.cache-names=rediscache相同
    @CachePut(value = "rediscache", key = "'user_'+#result.stuno",condition = "#result!='null'")
    public Stuinfo insert(Stuinfo record) {
        return stuinfoMapper.insert(record)>0?record:null;
    }



    @Override
    //先从缓存中查找，如果没有，调用方法查询，将查询结果放入缓存
    @Cacheable(value = "rediscache", key="'user_'+#stuno",condition = "#result!='null'")
    public Stuinfo selectByPrimaryKey(String stuno) {
        return stuinfoMapper.selectByPrimaryKey(stuno);
    }


    //不使用注解模拟使用redis缓存
    public List<Stuinfo> selectAll(){
        List stuinfoList=null;
        if(redisUtil.hasKey("stuinfolist")){
            logger.info("走缓存~~~");
            stuinfoList=redisUtil.range("stuinfolist",0,redisUtil.listLength("stuinfolist"));
        }else{
            logger.info("走数据库~~~");
            stuinfoList=stuinfoMapper.selectAll();
            redisUtil.leftPushAll("stuinfolist",stuinfoList);
            redisUtil.expire("stuinfolist",30);//设置过期时间
            logger.info("走数据库~~~");
        }
        return stuinfoList;
    }


    @Override
    public int insertSelective(Stuinfo record) {
        return stuinfoMapper.insertSelective(record);
    }



    @Override
    public int updateByPrimaryKeySelective(Stuinfo record) {
        return stuinfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Stuinfo record) {
        return stuinfoMapper.updateByPrimaryKey(record);
    }


    @Override
    public int selectCountByCon(Stuinfo record){return stuinfoMapper.selectCountByCon(record);}

    @Override
    public List<Stuinfo> selectListByCon(Stuinfo stuinfo, Page page) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stuinfo", stuinfo);
        paramMap.put("pageStart", (page.getPageNo() - 1) * page.getPageSize());
        paramMap.put("pageSize",page.getPageSize());
        return stuinfoMapper.selectListByCon(paramMap);
    }

    public PageInfo<Stuinfo> selectPageInfo(Stuinfo record, Integer page, Integer limit){
        PageHelper.startPage(page,limit);//设置页码和每页记录数
        return new PageInfo<Stuinfo>(stuinfoMapper.selectByCon(record));
    }
}
