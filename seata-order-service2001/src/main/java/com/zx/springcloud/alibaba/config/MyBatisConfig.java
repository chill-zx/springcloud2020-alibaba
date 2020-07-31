package com.zx.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.zx.springcloud.alibaba.dao"})
public class MyBatisConfig {

}

