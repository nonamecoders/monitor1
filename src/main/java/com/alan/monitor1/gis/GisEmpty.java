package com.alan.monitor1.gis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GisPk.class)
@Entity(name = "gis_empty")
public class GisEmpty {

    @Id
    String brand_cd;
    @Id
    String idx;

    String reg_id;
    @Id
    String reg_date;
    String reg_time;

}
