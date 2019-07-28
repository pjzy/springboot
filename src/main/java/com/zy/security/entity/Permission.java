package com.zy.security.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Auther: zy
 * @Date: 20190331 19:12
 * @Description:
 */

@Entity
@Table(name = "sys_Permission")
public class Permission {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //权限名称
    @Column(name = "name", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String name;

    //权限描述
    @Column(name = "descritpion", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String descritpion;

    //授权链接
    @Column(name = "url", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String url;

    //父节点id
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentid")
    private Permission parentPermission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Permission getParentPermission() {
        return parentPermission;
    }

    public void setParentPermission(Permission parentPermission) {
        this.parentPermission = parentPermission;
    }
}
