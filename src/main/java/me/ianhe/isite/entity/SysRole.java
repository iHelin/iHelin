package me.ianhe.isite.entity;

import java.io.Serializable;

/**
 * @author linhe2
 * @since 2018/4/27 13:11
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = 6917878828370263271L;

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}