package org.dromara.im.controller.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.im.domain.bo.MsgRecordBo;
import org.dromara.im.domain.vo.MsgRecordVo;
import org.dromara.im.service.IMsgRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天消息记录
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/im/msgRecord")
public class MsgRecordController extends BaseController {

    private final IMsgRecordService msgRecordService;

    /**
     * 查询聊天消息记录列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<MsgRecordVo> list(MsgRecordBo bo, PageQuery pageQuery) {
        return msgRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出聊天消息记录列表
     */
    @SaCheckLogin
    @Log(title = "聊天消息记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MsgRecordBo bo, HttpServletResponse response) {
        List<MsgRecordVo> list = msgRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "聊天消息记录", MsgRecordVo.class, response);
    }

    /**
     * 获取聊天消息记录详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<MsgRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                  @PathVariable Long id) {

        return R.ok(msgRecordService.queryById(id));
    }

    /**
     * 新增聊天消息记录
     */
    @SaCheckLogin
    @Log(title = "聊天消息记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<MsgRecordVo> add(@Validated(AddGroup.class) @RequestBody MsgRecordBo bo) {
        return R.ok(msgRecordService.insertByBo(bo));
    }

    /**
     * 修改聊天消息记录
     */
    @SaCheckLogin
    @Log(title = "聊天消息记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MsgRecordBo bo) {
        return toAjax(msgRecordService.updateByBo(bo));
    }

    /**
     * 修改消息已读状态
     */
    @SaCheckLogin
    @Log(title = "修改消息为已读", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/read")
    public R<Void> editUnRead(@RequestBody MsgRecordBo bo) {
        return toAjax(msgRecordService.updateByboMsgRead(bo));
    }


    /**
     * 删除聊天消息记录
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "聊天消息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(msgRecordService.deleteWithValidByIds(List.of(ids), true));
    }
}
