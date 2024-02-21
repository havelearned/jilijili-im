package org.dromara.common.ai.config.properties;

import cn.hutool.jwt.JWTUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * description:
 * </p>
 *
 * @date 28/01/2024
 */
@Component
@Validated
@ConfigurationProperties(prefix = "ai")
@Data
public class AiProperties {
    private boolean enable;
    private String defModel;
    private List<AiModel> aiModel;

    @Data
    public static class AiModel {
        private boolean def;
        private boolean enable;
        private String name;
        private String apiKey;
        private String secret;
        private String description;
        private String url;
    }

    public  AiProperties.AiModel getConfigModel(String modelName) {
        AiProperties.AiModel mode = null;
        List<AiProperties.AiModel> aiModel = this.getAiModel();
        if (aiModel != null && !aiModel.isEmpty()) {
            for (AiProperties.AiModel model : aiModel) {
                if (model.getName() != null && model.getName().equals(modelName)) {
                    mode = model;
                    if (!model.isEnable()) {
                        throw new IllegalArgumentException(modelName + "这个 AI 模型已被禁用!!!");
                    }
                    break;
                }
            }
        }
        return mode;
    }



}
