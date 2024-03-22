package org.dromara.im.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.domain.R;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.util.Date;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 13/01/2024
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity {


    private String type;
    private Long senderId;
    private Long targetId;
    private Long groupId;
    private String content;
    private Date sendTime;


    public static String fail(String msg) {
        return JsonUtils.toJsonString(R.fail(msg));
    }

}
