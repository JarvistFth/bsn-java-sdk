package com.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Application {
//| id     | String | 申请唯一id |
//| uid    | String | 人才id     |
//| pid    | String | 政策id     |
//| policy | String | 政策名称   |
//| status | String | 申请状态   |
//| ap_id  | String | 审批人id   |
//| extra | String | 扩展字段 |

    //申请唯一id
    @JSONField(name = "id")
    String id;

    //人才id
    @JSONField(name = "uid")
    String uid;
    //政策id
    @JSONField(name = "pid")
    String pid;

    //政策名称
    @JSONField(name = "policy")
    String policy;

//    申请状态
    @JSONField(name = "status")
    String status;

//    审批人id
    @JSONField(name = "ap_id")
    String ap_id;

    public Application() {
    }

    //拓展字段
    @JSONField(name = "extra")
    String extra;

    public Application(String id, String uid, String pid, String policy, String status, String ap_id, String extra) {
        this.id = id;
        this.uid = uid;
        this.pid = pid;
        this.policy = policy;
        this.status = status;
        this.ap_id = ap_id;
        this.extra = extra;
    }

}
