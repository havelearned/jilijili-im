package org.dromara.common.ai.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonModel {
    /**
     * <a href="https://maas.aminer.cn/dev/api">智谱清言 接口地址</a>
     */
    GLM_4("zhipu", "GLM-4", "https://open.bigmodel.cn/api/paas/v4/chat/completions"),
    GLM_4V("zhipu", "GLM-4V-4", "https://open.bigmodel.cn/api/paas/v4/chat/completions"),
    GLM_3_Turbo("zhipu", "GLM-3-Turbo", "https://open.bigmodel.cn/api/paas/v4/chat/completions"),
    ;


    private final String type;
    private final String name;
    private final String url;
}
