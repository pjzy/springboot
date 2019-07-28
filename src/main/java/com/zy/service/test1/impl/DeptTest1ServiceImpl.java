package com.zy.service.test1.impl;

import com.zy.entities.Dept;
import com.zy.entities.DeptExample;
import com.zy.mapper.test1.DeptTest1Mapper;
import com.zy.service.test1.DeptTest1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeptTest1ServiceImpl implements DeptTest1Service {
    @Autowired
    private DeptTest1Mapper deptTest1Mapper;
    public Integer add(Dept dept) {
        return deptTest1Mapper.insert(dept);
    }
    public Dept get(Integer deptno) {
        Dept dept = deptTest1Mapper.selectByPrimaryKey(deptno);
        if(dept==null) {
            throw new RuntimeException("Dept:"+deptno+" 不存在");
        }
        return dept;
    }
    public List<Dept> list(){
        return deptTest1Mapper.selectByExample(new DeptExample());
    }
}
