package org.dromara.im.controller.admin.appbox;

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
import org.dromara.im.domain.vo.AppboxFixedAppUserConfigVo;
import org.dromara.im.domain.bo.AppboxFixedAppUserConfigBo;
import org.dromara.im.service.IAppboxFixedAppUserConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 应用的固定用户配置
 * 前端访问路由地址为:/im/fixedAppUserConfig
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/fixedAppUserConfig")
public class AppboxFixedAppUserConfigController extends BaseController {

    private final IAppboxFixedAppUserConfigService appboxFixedAppUserConfigService;

    /**
     * 查询应用的固定用户配置列表
     */
    @SaCheckPermission("im:fixedAppUserConfig:list")
    @GetMapping("/list")
    public TableDataInfo<AppboxFixedAppUserConfigVo> list(AppboxFixedAppUserConfigBo bo, PageQuery pageQuery) {
        return appboxFixedAppUserConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出应用的固定用户配置列表
     */
    @SaCheckPermission("im:fixedAppUserConfig:export")
    @Log(title = "应用的固定用户配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppboxFixedAppUserConfigBo bo, HttpServletResponse response) {
        List<AppboxFixedAppUserConfigVo> list = appboxFixedAppUserConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "应用的固定用户配置", AppboxFixedAppUserConfigVo.class, response);
    }

    /**
     * 获取应用的固定用户配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:fixedAppUserConfig:query")
    @GetMapping("/{id}")
    public R<AppboxFixedAppUserConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(appboxFixedAppUserConfigService.queryById(id));
    }

    /**
     * 新增应用的固定用户配置
     */
    @SaCheckPermission("im:fixedAppUserConfig:add")
    @Log(title = "应用的固定用户配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppboxFixedAppUserConfigBo bo) {
        return toAjax(appboxFixedAppUserConfigService.insertByBo(bo));
    }

    /**
     * 修改应用的固定用户配置
     */
    @SaCheckPermission("im:fixedAppUserConfig:edit")
    @Log(title = "应用的固定用户配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppboxFixedAppUserConfigBo bo) {
        return toAjax(appboxFixedAppUserConfigService.updateByBo(bo));
    }

    /**
     * 删除应用的固定用户配置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:fixedAppUserConfig:remove")
    @Log(title = "应用的固定用户配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appboxFixedAppUserConfigService.deleteWithValidByIds(List.of(ids), true));
    }
}
