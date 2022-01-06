package com.alan.monitor1.repository;

import com.alan.monitor1.gis.Gis;
import com.alan.monitor1.gis.GisPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GisRepository extends JpaRepository<Gis,GisPk> {


}
