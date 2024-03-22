package org.dromara.im.controller.admin.card;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.im.domain.vo.CardVo;
import org.dromara.im.domain.bo.CardBo;
import org.dromara.im.service.ICardService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 社交卡片
 *
 * @author luojiaju
 * @date 2024-02-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/card")
public class CardController extends BaseController {

    private final ICardService cardService;

    /**
     * 查询社交卡片列表
     */
    @SaCheckPermission("im:card:list")
    @GetMapping("/list")
    public TableDataInfo<CardVo> list(CardBo bo, PageQuery pageQuery) {
        return cardService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出社交卡片列表
     */
    @SaCheckPermission("im:card:export")
    @Log(title = "社交卡片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CardBo bo, HttpServletResponse response) {
        List<CardVo> list = cardService.queryList(bo);
        ExcelUtil.exportExcel(list, "社交卡片", CardVo.class, response);
    }

    /**
     * 获取社交卡片详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:card:query")
    @GetMapping("/{id}")
    public R<CardVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(cardService.queryById(id));
    }

    /**
     * 新增社交卡片
     */
    @SaCheckPermission("im:card:add")
    @Log(title = "社交卡片", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CardBo bo) {
        return toAjax(cardService.insertByBo(bo));
    }

    /**
     * 修改社交卡片
     */
    @SaCheckPermission("im:card:edit")
    @Log(title = "社交卡片", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CardBo bo) {
        return toAjax(cardService.updateByBo(bo));
    }

    /**
     * 删除社交卡片
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:card:remove")
    @Log(title = "社交卡片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(cardService.deleteWithValidByIds(List.of(ids), true));
    }
}
