package com.example.excel;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author 清朔ael
 * @Date 2018/12/8 16:31
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "area")
public class Area {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column
        private String city;

        @Column
        private String district;

        @Column
        private String province;

        @Column
        private String postCode;


        public static void a (){
                System.out.println(1);
        }

}
