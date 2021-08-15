package com.tensquare.base.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {
    @Id
    private String id;
    private String labelname;//标签名称
    private String state;// 状态
    private Integer count;//数量
    private Integer fans;//粉丝数
    private String recommend;//是否推荐
}
