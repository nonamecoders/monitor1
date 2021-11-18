package com.alan.monitor1.service;

import com.alan.monitor1.gis.MinMaxPoint;
import com.alan.monitor1.mapper.BorderMapper;
import com.alan.monitor1.order.Border;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("com.alan.monitor1.service.BorderService")
public class BorderService {
    @Resource(name = "com.alan.monitor1.mapper.BorderMapper")
    BorderMapper mBorderMapper;

    public List<Border> borderListService() throws Exception{

        return mBorderMapper.borderList();
    }

    public List<Border> selectPolyListService() throws Exception{

        return mBorderMapper.selectPolyList();
    }

    public List<Border> selectPolyListService2() throws Exception{

        return mBorderMapper.selectPolyList2();
    }

    public MinMaxPoint selectMinMaxPoint() throws Exception{

        return mBorderMapper.selectMinMaxPoint();
    }

    public Border getGisBorderList(Border border) throws Exception{

        return mBorderMapper.getGisBorderList(border);
    }
}
