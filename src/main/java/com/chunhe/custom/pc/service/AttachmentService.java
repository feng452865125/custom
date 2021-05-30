package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.AttachmentMapper;
import com.chunhe.custom.pc.model.Attachment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年4月25日15:55:33
 * 图片附件管理
 */
@Service
public class AttachmentService extends BaseService<Attachment> {

    @Autowired
    private AttachmentMapper attachmentMapper;

    //全部列表
    public List<Attachment> findAttachmentList(Attachment attachment) {
        List<Attachment> attachmentList = attachmentMapper.findAttachmentList(attachment);
        return attachmentList;
    }

    /**
     * 查询数据
     */
    public List<Attachment> attachmentList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Attachment.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Attachment getAttachment(Long id) {
        Attachment attachment = new Attachment();
        attachment.setId(id);
        Attachment att = attachmentMapper.getAttachment(attachment);
        return att;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> attachmentMap) {
        Attachment attachment = new Attachment();
        String name = ConvertUtil.convert(attachmentMap.get("name"), String.class);
        String imgUrl = ConvertUtil.convert(attachmentMap.get("imgUrl"), String.class);
        String remark = ConvertUtil.convert(attachmentMap.get("remark"), String.class);
        Boolean enable = ConvertUtil.convert(attachmentMap.get("isEnabled"), Boolean.class);
        String title = ConvertUtil.convert(attachmentMap.get("title"), String.class);
        String content = ConvertUtil.convert(attachmentMap.get("content"), String.class);
        Integer level = ConvertUtil.convert(attachmentMap.get("level"), Integer.class);
        attachment.setName(name);
        attachment.setImgUrl(imgUrl);
        attachment.setRemark(remark);
        attachment.setEnabled(enable);
        attachment.setTitle(title);
        attachment.setContent(content);
        attachment.setLevel(level);
        if (insertNotNull(attachment) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Attachment attachment, Map<String, Object> attachmentMap) {
        String name = ConvertUtil.convert(attachmentMap.get("name"), String.class);
        String imgUrl = ConvertUtil.convert(attachmentMap.get("imgUrl"), String.class);
        String remark = ConvertUtil.convert(attachmentMap.get("remark"), String.class);
        Boolean enable = ConvertUtil.convert(attachmentMap.get("isEnabled"), Boolean.class);
        String title = ConvertUtil.convert(attachmentMap.get("title"), String.class);
        String content = ConvertUtil.convert(attachmentMap.get("content"), String.class);
        Integer level = ConvertUtil.convert(attachmentMap.get("level"), Integer.class);
        attachment.setName(name);
        attachment.setImgUrl(imgUrl);
        attachment.setRemark(remark);
        attachment.setEnabled(enable);
        attachment.setTitle(title);
        attachment.setContent(content);
        attachment.setLevel(level);
        if (updateNotNull(attachment) != 1) {
            return ServiceResponse.error("更新失败");
        }
        return ServiceResponse.succ("更新成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        Attachment attachment = selectByKey(id);
        if (expireNotNull(attachment) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 可用/不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional(readOnly = false)
    public Boolean enabled(Long id, Boolean isEnabled) {
        Attachment attachment = selectByKey(id);
        attachment.setEnabled(isEnabled);
        return updateNotNull(attachment) == 1;
    }

}
