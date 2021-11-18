package com.alan.monitor1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository("com.alan.monitor1.mapper.OrderItemMapper")
public interface OrderItemMapper {

    public int orderItem() throws Exception;


}
