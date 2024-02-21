package org.dromara.im.controller.admin.notification;
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
import org.dromara.im.domain.vo.ImNoticeVo;
import org.dromara.im.domain.bo.ImNoticeBo;
import org.dromara.im.service.IImNoticeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户通知
 * 前端访问路由地址为:/im/notice
 *
 * @author luojiaju
 * @date 2024-01-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/notice")
public class ImNoticeController extends BaseController {

    private final IImNoticeService imNoticeService;

    /**
     * 查询用户通知列表
     */
    @SaCheckPermission("im:notice:list")
    @GetMapping("/list")
    public TableDataInfo<ImNoticeVo> list(ImNoticeBo bo, PageQuery pageQuery) {
        return imNoticeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户通知列表
     */
    @SaCheckPermission("im:notice:export")
    @Log(title = "用户通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ImNoticeBo bo, HttpServletResponse response) {
        List<ImNoticeVo> list = imNoticeService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户通知", ImNoticeVo.class, response);
    }

    /**
     * 获取用户通知详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:notice:query")
    @GetMapping("/{id}")
    public R<ImNoticeVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id) {
        return R.ok(imNoticeService.queryById(id));
    }

    /**
     * 新增用户通知
     */
    @SaCheckPermission("im:notice:add")
    @Log(title = "用户通知", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ImNoticeBo bo) {
        return toAjax(imNoticeService.insertByBo(bo));
    }

    /**
     * 修改用户通知
     */
    @SaCheckPermission("im:notice:edit")
    @Log(title = "用户通知", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ImNoticeBo bo) {
        return toAjax(imNoticeService.updateByBo(bo));
    }

    /**
     * 删除用户通知
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:notice:remove")
    @Log(title = "用户通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(imNoticeService.deleteWithValidByIds(List.of(ids), true));
    }
}
