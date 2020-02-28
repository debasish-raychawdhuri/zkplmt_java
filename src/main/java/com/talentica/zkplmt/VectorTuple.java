package com.talentica.zkplmt;

import java.io.Serializable;

public class VectorTuple implements Serializable {
    CurveVector[] members;

    public VectorTuple(CurveVector[] members) {
        this.members = members;
    }

    public int length(){
        return members.length;
    }
}
