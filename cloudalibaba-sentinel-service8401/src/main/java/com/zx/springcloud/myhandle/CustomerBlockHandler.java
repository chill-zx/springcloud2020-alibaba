package com.zx.springcloud.myhandle;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zx.springcloud.entities.CommonResult;

public class CustomerBlockHandler {
    public static CommonResult handleException(BlockException exception){
        return new CommonResult(2020, "自定义的限流处理信息...... CustomerBlockHandler");
    }
}
