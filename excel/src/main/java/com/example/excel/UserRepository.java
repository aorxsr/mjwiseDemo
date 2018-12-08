package com.example.excel;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 清朔ael
 * @Date 2018/12/8 16:36
 * @Version 1.0
 **/
public interface UserRepository extends JpaRepository<User,Integer> {
}
