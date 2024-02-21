package org.dromara.im.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.redisson.api.RKeys;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 02/02/2024
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/test/aiApp")
@RequiredArgsConstructor
public class WebAiController {



}
