package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 创建应用or频道对象 appbox_application
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("appbox_application")
public class AppboxApplication extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 名称
     */
    private String appName;

    /**
     * 分类id
     */
    private  String classesId;

    /**
     * 图标
     */
    private String iconUrl;

    /**
     * 热度
     */
    private Long popularity;

    /**
     * 封面
     */
    private String cover;

    /**
     * 页面访问量
     */
    private Long pv;

    /**
     * 独立访客数
     */
    private Long uv;

    /**
     * 频道状态:1应用 2频道
     */
    private Long channelStatus;

    /**
     * 应用简介
     */
    private String remark;


}
