package com.alan.monitor1.mapper;

import com.alan.monitor1.gis.MinMaxPoint;
import com.alan.monitor1.gis.Border;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("com.alan.monitor1.mapper.BorderMapper")
public interface BorderMapper {

    List<Border> borderList() throws Exception;

    List<Border> selectPolyList() throws  Exception;

    MinMaxPoint selectMinMaxPoint() throws  Exception;

    Border getGisBorderList(Border border) throws  Exception;

    List<Border> selectPolyList2() throws  Exception;
}
