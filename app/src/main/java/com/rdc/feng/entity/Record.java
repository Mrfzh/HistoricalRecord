package com.rdc.feng.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Feng Zhaohao
 * Created on 2018/7/20
 */
@Entity
public class Record {
    @Id(autoincrement = true)
    private Long id;
    private String data;
    @Generated(hash = 1128842769)
    public Record(Long id, String data) {
        this.id = id;
        this.data = data;
    }
    @Generated(hash = 477726293)
    public Record() {
    }
    public Long getId() {
        return this.id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
