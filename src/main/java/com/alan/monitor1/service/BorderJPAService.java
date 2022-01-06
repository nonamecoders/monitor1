package com.alan.monitor1.service;

import com.alan.monitor1.gis.Gis;
import com.alan.monitor1.repository.GisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.alan.monitor1.service.BorderJPAService")
public class BorderJPAService {

    @Autowired
    GisRepository gisRepository;

    public int save(Gis gis) throws  Exception {
        gisRepository.save(gis);
        return 1;
    }
}
