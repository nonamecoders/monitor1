package com.alan.monitor1.gis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GisPk.class)
@Entity(name = "gis_border_poly_history")
public class Gis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    String brand_cd;

    @Id
    String idx;

    String poly;
    int is_valid;
    String reg_id;

    @Id
    String reg_date;

    String reg_time;
    String upd_id;
    String upd_date;
    String upd_time;

}