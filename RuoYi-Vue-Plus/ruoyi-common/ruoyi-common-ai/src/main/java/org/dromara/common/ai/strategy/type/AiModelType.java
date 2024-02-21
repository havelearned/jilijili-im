package org.dromara.common.ai.strategy.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 03/02/2024
 */
@Getter
public enum AiModelType {
    GML_4("GLM_4", "glm-4"),
    GLM_3_Turbo("GLM_3_Turbo", "glm-3-turbo"),

    ;
    private final String model;
    private String param;

    AiModelType(String model, String param) {
        this.model = model;
        this.param = param;
    }

    public static String getModelByParam(String model) {
        for (AiModelType value : AiModelType.values()) {
            if (value.model.equals(model)) {
                return value.param;
            }
        }
        return AiModelType.GLM_3_Turbo.getParam();
    }
}
