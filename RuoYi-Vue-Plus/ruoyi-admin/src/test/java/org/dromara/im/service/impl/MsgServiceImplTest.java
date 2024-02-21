package org.dromara.im.service.impl;

import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.im.domain.bo.MsgRecordBo;
import org.dromara.im.domain.vo.MsgRecordVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 20/02/2024
 */
@SpringBootTest
public class MsgServiceImplTest extends BaseController {

    @Autowired
    MsgRecordServiceImpl msgRecordService;


    @Test
    public void listTest() {
        MsgRecordBo bo = new MsgRecordBo();
        bo.setToType(2L);
        bo.setToGroupId(2L);
        PageQuery page = new PageQuery();
        page.setPageNum(0);
        page.setPageSize(10);
        TableDataInfo<MsgRecordVo> info = msgRecordService.queryPageList(bo, page);
        System.out.println(JsonUtils.toJsonString(info));

//        MsgRecordBo bo = new MsgRecordBo();
//        bo.setSenderId(1L);
//        bo.setToId(2L);
//        bo.setToType(1L);
//        PageQuery page = new PageQuery();
//        page.setPageNum(0);
//        page.setPageSize(10);
//        TableDataInfo<MsgRecordVo> info = msgRecordService.queryPageList(bo, page);
//        System.out.println(JsonUtils.toJsonString(info));

    }


}
