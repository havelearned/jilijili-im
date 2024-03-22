package org.dromara.im.controller.web;

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
import org.dromara.im.domain.vo.MsgActionVo;
import org.dromara.im.domain.bo.MsgActionBo;
import org.dromara.im.service.IMsgActionService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 聊天动作
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/msgAction")
public class MsgActionController extends BaseController {

    private final IMsgActionService msgActionService;

    /**
     * 查询聊天动作列表
     */
    @SaCheckPermission("im:msgAction:list")
    @GetMapping("/list")
    public TableDataInfo<MsgActionVo> list(MsgActionBo bo, PageQuery pageQuery) {
        return msgActionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出聊天动作列表
     */
    @SaCheckPermission("im:msgAction:export")
    @Log(title = "聊天动作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MsgActionBo bo, HttpServletResponse response) {
        List<MsgActionVo> list = msgActionService.queryList(bo);
        ExcelUtil.exportExcel(list, "聊天动作", MsgActionVo.class, response);
    }

    /**
     * 获取聊天动作详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:msgAction:query")
    @GetMapping("/{id}")
    public R<MsgActionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(msgActionService.queryById(id));
    }

    /**
     * 新增聊天动作
     */
    @SaCheckPermission("im:msgAction:add")
    @Log(title = "聊天动作", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MsgActionBo bo) {
        return toAjax(msgActionService.insertByBo(bo));
    }

    /**
     * 修改聊天动作
     */
    @SaCheckPermission("im:msgAction:edit")
    @Log(title = "聊天动作", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MsgActionBo bo) {
        return toAjax(msgActionService.updateByBo(bo));
    }

    /**
     * 删除聊天动作
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:msgAction:remove")
    @Log(title = "聊天动作", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(msgActionService.deleteWithValidByIds(List.of(ids), true));
    }
}
