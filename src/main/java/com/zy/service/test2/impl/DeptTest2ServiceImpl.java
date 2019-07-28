package com.zy.service.test2.impl;

import com.zy.entities.Dept;
import com.zy.entities.DeptExample;
import com.zy.mapper.test2.DeptTest2Mapper;
import com.zy.service.test2.DeptTest2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeptTest2ServiceImpl implements DeptTest2Service {
    @Autowired
    private DeptTest2Mapper deptTest2Mapper;
    public Integer add(Dept dept) {
        return deptTest2Mapper.insert(dept);
    }
    public Dept get(Integer deptno) {
        Dept dept = deptTest2Mapper.selectByPrimaryKey(deptno);
        if(dept==null) {
            throw new RuntimeException("Dept:"+deptno+" 不存在");
        }
        return dept;
    }
    public List<Dept> list(){
        return deptTest2Mapper.selectByExample(new DeptExample());
    }
}
