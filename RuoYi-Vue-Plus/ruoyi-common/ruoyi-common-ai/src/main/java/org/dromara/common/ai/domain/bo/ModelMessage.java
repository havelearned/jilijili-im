package org.dromara.common.ai.domain.bo;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 31/01/2024
 */
@Data
@Builder
public class ModelMessage {
    private String role;
    private String content;
}
