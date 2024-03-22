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
import org.dromara.im.domain.vo.AppboxApplicationVo;
import org.dromara.im.domain.bo.AppboxApplicationBo;
import org.dromara.im.service.IAppboxApplicationService;

/**
 * 创建应用or频道
 * 前端访问路由地址为:/im/appboxapplication
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/appboxapplication")
public class AppboxApplicationController extends BaseController {

    private final IAppboxApplicationService appboxApplicationService;

    /**
     * 查询创建应用or频道列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public R<List<AppboxApplicationVo>> list(AppboxApplicationBo bo) {
        List<AppboxApplicationVo> list = appboxApplicationService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 导出创建应用or频道列表
     */
    @SaCheckPermission("im:appboxapplication:export")
    @Log(title = "创建应用or频道", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppboxApplicationBo bo, HttpServletResponse response) {
        List<AppboxApplicationVo> list = appboxApplicationService.queryList(bo);
        ExcelUtil.exportExcel(list, "创建应用or频道", AppboxApplicationVo.class, response);
    }

    /**
     * 获取创建应用or频道详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("im:appboxapplication:query")
    @GetMapping("/{id}")
    public R<AppboxApplicationVo> getInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(appboxApplicationService.queryById(id));
    }



    /**
     * 新增创建应用or频道
     */
    @SaCheckPermission("im:appboxapplication:add")
    @Log(title = "创建应用or频道", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppboxApplicationBo bo) {
        return toAjax(appboxApplicationService.insertByBo(bo));
    }

    /**
     * 修改创建应用or频道
     */
    @SaCheckPermission("im:appboxapplication:edit")
    @Log(title = "创建应用or频道", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppboxApplicationBo bo) {
        return toAjax(appboxApplicationService.updateByBo(bo));
    }

    /**
     * 删除创建应用or频道
     *
     * @param ids 主键串
     */
    @SaCheckPermission("im:appboxapplication:remove")
    @Log(title = "创建应用or频道", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appboxApplicationService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 获取当前用户的应用列表
     * @return
     */
    @SaCheckLogin
    @GetMapping("/user")
    public R<List<AppboxApplicationVo>> getAppByUser() {
        return R.ok(appboxApplicationService.queryAppByUser());
    }


    @SaCheckLogin
    @GetMapping("/tree/{id}")
    public R<List<AppboxApplicationVo>> getTreeInfo(@NotNull(message = "主键不能为空")
                                          @PathVariable Long id) {
        return R.ok(appboxApplicationService.getTreeInfo(id));
    }

}
