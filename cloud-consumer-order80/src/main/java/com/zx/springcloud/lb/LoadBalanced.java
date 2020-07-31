package com.zx.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalanced {
    //获取注册中心的实例
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
