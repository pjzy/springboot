package com.zy.service.test1;

import com.zy.entities.Dept;

import java.util.List;

public interface DeptTest1Service {
    public Integer add(Dept dept);
    public Dept get(Integer deptno);
    public List<Dept> list();
}
