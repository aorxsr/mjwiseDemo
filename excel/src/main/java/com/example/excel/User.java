package com.example.excel;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author 清朔ael
 * @Date 2018/12/8 16:32
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

}
