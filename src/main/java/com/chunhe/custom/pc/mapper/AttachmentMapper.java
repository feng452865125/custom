package com.chunhe.custom.pc.mapper;

import com.chunhe.custom.framework.mybatis.MyMapper;
import com.chunhe.custom.pc.model.Attachment;

import java.util.List;

public interface AttachmentMapper extends MyMapper<Attachment> {

    List<Attachment> findAttachmentList(Attachment attachment);

    Attachment getAttachment(Attachment attachment);
}