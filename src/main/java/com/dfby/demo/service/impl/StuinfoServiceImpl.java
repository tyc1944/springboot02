package com.dfby.demo.service.impl;

import com.dfby.demo.mapper.StuinfoMapper;
import com.dfby.demo.pojo.Stuinfo;
import com.dfby.demo.service.StuinfoService;
import com.dfby.demo.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StuinfoServiceImpl implements StuinfoService {
    @Autowired
    private StuinfoMapper stuinfoMapper;

    @Override
    public int deleteByPrimaryKey(String stuno) {
        return stuinfoMapper.deleteByPrimaryKey(stuno);
    }

    @Override
    public int insert(Stuinfo record) {
        return stuinfoMapper.insert(record);
    }

    @Override
    public int insertSelective(Stuinfo record) {
        return stuinfoMapper.insertSelective(record);
    }

    @Override
    public Stuinfo selectByPrimaryKey(String stuno) {
        return stuinfoMapper.selectByPrimaryKey(stuno);
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
    public List<Stuinfo> selectAll(){
        return stuinfoMapper.selectAll();
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
