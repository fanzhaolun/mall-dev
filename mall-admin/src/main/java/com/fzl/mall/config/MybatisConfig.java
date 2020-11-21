package com.fzl.mall.config;

import org.apache.ibatis.type.Alias;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis的配置类
 */
@Configuration
@MapperScan({"com.fzl.mall.mapper","com.fzl.mall.dao"})
public class MybatisConfig {

}
