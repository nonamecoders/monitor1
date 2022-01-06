package com.alan.monitor1.gis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GisPk implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brand_cd;
    private String idx;
    private String reg_date;

//    @Override
//    public int hashCode(){
//        return Objects.hash(brand_cd,idx,reg_date);
//    }
}
