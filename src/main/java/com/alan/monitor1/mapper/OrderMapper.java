package com.alan.monitor1.mapper;

import com.alan.monitor1.order.Order;
import com.alan.monitor1.order.SPCShortage;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository("com.alan.monitor1.mapper.OrderMapper")
public interface OrderMapper {

//    public List<Order> orderList() throws Exception;

    public List<Order> orderList(@Param("start_date")String start_date, @Param("end_date") String end_date) throws Exception;


    int batchExecute(SPCShortage spcShortage) throws Exception;

}
