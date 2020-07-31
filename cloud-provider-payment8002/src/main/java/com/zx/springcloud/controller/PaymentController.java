package com.zx.springcloud.controller;

import com.zx.springcloud.entities.CommonResult;
import com.zx.springcloud.entities.Payment;
import com.zx.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("payment")
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("****插入结果:  "+result );
        if (result>0){
            return new CommonResult(200,"插入数据成功 serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据失败 serverPort:"+serverPort,result);

        }
    }

    @GetMapping("get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment!=null){
            log.info("****查询成功:  "+payment );
            return new CommonResult(200,"查询数据成功 serverPort:"+serverPort,payment);
        }else {
            log.info("****查询失败:  "+payment );
            return new CommonResult(444,"查询数据失败 serverPort:"+serverPort,payment);

        }
    }
    @GetMapping("discovery")
    public Object discoveryClient(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("***** element:"+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;

    }
    @GetMapping(value = "lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
