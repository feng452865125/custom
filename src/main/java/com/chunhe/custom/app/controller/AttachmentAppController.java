package com.chunhe.custom.app.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.BeanUtil;
import com.chunhe.custom.pc.model.Attachment;
import com.chunhe.custom.pc.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by white 2020年4月25日15:55:33
 * 图片附件管理
 */
@RestController
@RequestMapping("/app/attachment")
public class AttachmentAppController extends BaseController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    @ResponseBody
    public String findAttachmentList(HttpServletRequest req) {
        Attachment attachment = BeanUtil.checkContent(req, Attachment.class);
        attachment.setEnabled(Attachment.ENABLED_TRUE);
        List<Attachment> attachmentList = attachmentService.findAttachmentList(attachment);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, attachmentList);
    }

    /**
     * 详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getAttachment(HttpServletRequest req, @PathVariable Long id) {
        Attachment attachment = attachmentService.getAttachment(id);
        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_CHECK_SUCCESS, attachment);
    }


}
