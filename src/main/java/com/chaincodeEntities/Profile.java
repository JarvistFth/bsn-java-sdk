package com.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;

@Data
public class Profile {
//|      id       |  Long  | 人才id                        |
//|     name      | String | 姓名                          |
//| identity_card | String | 身份证hash值                  |
//|   card_type   |  int   | 证件类型 1：身份证；2：护照； |
//|   passport    | String | 护照hash值                    |
//|    open_id    | String | 微信唯一标识                  |
//|    wx_card    | String | 微信会员卡id                  |
//|  wx_card_num  | String | 会员卡号                      |
//|    action     | String | 用户动作                      |
//|    module     | String | 用户使用模块                  |
//| extra | String | 扩展字段 |


    public Profile(String cardType, String id, String name) {
        this.card_type = cardType;
        this.id = id;
        this.name = name;
    }

    public Profile() {

    }

    public Profile(String id, String name, String identity_card, String card_type, String passport, String openid, String wx_card, String wx_card_num, String action, String module, String extra) {
        this.id = id;
        this.name = name;
        this.identity_card = identity_card;
        this.card_type = card_type;
        this.passport = passport;
        this.openid = openid;
        this.wx_card = wx_card;
        this.wx_card_num = wx_card_num;
        this.action = action;
        this.module = module;
        this.extra = extra;
    }

    @JSONField(name = "id")
    String id;

    @JSONField(name = "name")
    String name;

    @JSONField(name = "identity_card_Hash")
    String identity_card;

    @JSONField(name = "card_type")
    String card_type;

    @JSONField(name = "passport")
    String passport;

    @JSONField(name = "wx_openid")
    String openid;

    @JSONField(name = "wx_card")
    String wx_card;

    @JSONField(name = "wx_card_num")
    String wx_card_num;

    @JSONField(name = "action")
    String action;

    @JSONField(name = "module")
    String module;

    @JSONField(name = "extra")
    String extra;







}
