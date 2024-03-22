package org.dromara.im.controller.admin.appbox;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import org.dromara.im.domain.vo.AppboxClassesVo;
import org.dromara.im.domain.bo.AppboxClassesBo;
import org.dromara.im.service.IAppboxClassesService;

/**
 * 应用分类
 * 前端访问路由地址为:/im/classes
 *
 * @author luojiaju
 * @date 2024-01-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/classes")
public class AppboxClassesController extends BaseController {

    private final IAppboxClassesService appboxClassesService;

    /**
     * 查询应用分类列表
     */
//    @SaCheckPermission("im:classes:list")
    @SaCheckLogin
    @GetMapping("/list")
    public R<List<AppboxClassesVo>> list(AppboxClassesBo bo) {
        List<AppboxClassesVo> list = appboxClassesService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出应用分类列表
     */
    @SaCheckPermission("im:classes:export")
    @Log(title = "应用分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppboxClassesBo bo, HttpServletResponse response) {
        List<AppboxClassesVo> list = appboxClassesService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用分类", AppboxClassesVo.class, response);
    }

    /**
     * 获取应用分类详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:classes:query")
    @GetMapping("/{id}")
    public R<AppboxClassesVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(appboxClassesService.queryById(id));
    }

    /**
     * 新增应用分类
     */
    @SaCheckPermission("im:classes:add")
    @Log(title = "应用分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppboxClassesBo bo) {
        return toAjax(appboxClassesService.insertByBo(bo));
    }

    /**
     * 修改应用分类
     */
    @SaCheckPermission("im:classes:edit")
    @Log(title = "应用分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppboxClassesBo bo) {
        return toAjax(appboxClassesService.updateByBo(bo));
    }

    /**
     * 删除应用分类
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:classes:remove")
    @Log(title = "应用分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appboxClassesService.deleteWithValidByIds(List.of(ids), true));
    }
}
