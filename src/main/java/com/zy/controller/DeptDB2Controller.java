package com.zy.controller;

import com.zy.entities.Dept;
import com.zy.service.test2.DeptTest2Service;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zy
 * @Date: 2019/7/27 15:05
 * @Description:
 */
@Api("数据库2部门查询")
@RestController
@RequestMapping("/deptdb2")
public class DeptDB2Controller {

    @Autowired
    private DeptTest2Service service;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Integer add(@RequestBody Dept dept) {
        return service.add(dept);
    }

    @RequestMapping(value="/get/{id}",method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @RequestMapping(value="/getaa/{id}",method = RequestMethod.GET)
    public Dept getaa(@PathVariable("id") Integer id) {
    System.out.println("111111111111111");
        return new Dept();
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Dept> list(){
        return service.list();
    }
}
