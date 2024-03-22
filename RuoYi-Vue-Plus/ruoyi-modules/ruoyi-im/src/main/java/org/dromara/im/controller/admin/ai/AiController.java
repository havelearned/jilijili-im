package org.dromara.im.controller.admin.ai;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.ai.config.properties.AiProperties;
import org.dromara.common.core.domain.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 28/01/2024
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/ai")
public class AiController {
    private final AiProperties aiProperties;


    @SaCheckPermission("im:ai:info")
    @GetMapping("/info")
    public R<?> getAiInfo() {
        return R.ok(aiProperties);
    }
}
