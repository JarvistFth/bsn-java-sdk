package com.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Selector {
    public Selector() {
    }

    @JSONField(name = "selector")
    public String selector;

}
