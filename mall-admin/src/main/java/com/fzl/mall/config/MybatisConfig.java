package com.fzl.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Mybatis的配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.fzl.mall.mapper","com.fzl.mall.dao"})
public class MybatisConfig {

}
