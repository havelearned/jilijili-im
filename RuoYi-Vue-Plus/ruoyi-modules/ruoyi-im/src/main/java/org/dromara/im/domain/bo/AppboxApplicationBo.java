package org.dromara.im.domain.bo;

import org.dromara.im.domain.AppboxApplication;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 创建应用or频道业务对象 appbox_application
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppboxApplication.class, reverseConvertGenerate = false)
public class AppboxApplicationBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {EditGroup.class})
    private Long id;

    /**
     * 上级id
     */
    @NotNull(message = "上级id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long parentId;

    private Long classesId;

    /**
     * 配置id
     */
    private Long configId;

    /**
     * 分类id
     */
    private Long ClassesId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String appName;

    /**
     * 图标
     */
    private String iconUrl;

    /**
     * 热度
     */
    private Long popularity;

    /**
     * 页面访问量
     */
    private Long pv;

    /**
     * 独立访客数
     */
    private Long uv;


    /**
     * 封面
     */
    private String cover;

    /**
     * 频道状态:1应用 2频道
     */
    private Long channelStatus;

    /**
     * 应用简介
     */
    private String remark;


}
