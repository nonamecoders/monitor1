package com.alan.monitor1.service;

import com.alan.monitor1.mapper.OrderItemMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;


@Service("com.alan.monitor1.service.OrderItemService")
public class OrderItemService {
    @Resource(name = "com.alan.monitor1.mapper.OrderItemMapper")
    OrderItemMapper mOrderItemMapper;


    public int orderItemService() throws Exception{

        return mOrderItemMapper.orderItem();
    }
}
