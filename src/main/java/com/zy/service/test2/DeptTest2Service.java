package com.zy.service.test2;

import com.zy.entities.Dept;

import java.util.List;

public interface DeptTest2Service {
    public Integer add(Dept dept);
    public Dept get(Integer deptno);
    public List<Dept> list();
}
