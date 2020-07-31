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
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("****插入结果:  "+result );
        if (result>0){
            return new CommonResult(200,"插入数据成功 serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据失败 serverPort:"+serverPort,result);

        }
    }

    @GetMapping("/payment/get/{id}")
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
    @GetMapping("/payment/discovery")
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
    //演示超时
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try { TimeUnit.SECONDS.sleep(3); }catch (Exception e) {e.printStackTrace();}
        return serverPort;
    }

    //负载均衡
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
    @GetMapping("/payment/zipkin")
    public String paymentZipkin()
    {
        return "hi ,i'am paymentzipkin server fall back，welcome to zx，O(∩_∩)O哈哈~";
    }
}
