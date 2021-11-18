package com.alan.monitor1.mapper1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository("com.alan.monitor1.mapper1.SmsMapper")
public interface SmsMapper {
    public int insertSms(@Param("msg") String msg) throws Exception;
    public int selectSms() throws Exception;
}
