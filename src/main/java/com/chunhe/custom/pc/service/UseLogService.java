package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.mapper.SysUserMapper;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.UseLogMapper;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.model.UseLog;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年8月2日14:32:49
 */
@Service
public class UseLogService extends BaseService<UseLog> {

    @Autowired
    private UseLogMapper useLogMapper;

    @Autowired
    private StyleService styleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    //全部列表
    public List<UseLog> findUseLogList(UseLog UseLog) {
        List<UseLog> useLogList = useLogMapper.findUseLogList(UseLog);
        for (int i = 0; i < useLogList.size(); i++) {
            UseLog useLog = useLogList.get(i);
            useLog.setTypeName(DictUtils.findValueByTypeAndKey(UseLog.ACTION_TYPE, useLog.getType()));
            useLog.setSourceName(DictUtils.findValueByTypeAndKey(UseLog.SOURCE_TYPE, useLog.getSourceType()));
        }
        return useLogList;
    }

    /**
     * 查询数据
     */
    public List<UseLog> useLogList(DataTablesRequest dataTablesRequest) {
        UseLog useLog = new UseLog();
        //排序
        String orderBy = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orderBy)) {
            useLog.setOrderBy(orderBy);
        }
        //操作日志开始时间
        DataTablesRequest.Column startDateColumn = dataTablesRequest.getColumn("startDate");
        if (StringUtils.isNotBlank(startDateColumn.getSearch().getValue())) {
            String startDate = ConvertUtil.convert(startDateColumn.getSearch().getValue(), String.class);
            useLog.setStartDate(DateUtil.getDateStart(DateUtil.parseDate(startDate, "yyyy-MM-dd")));
        }
        //操作日志结束时间
        DataTablesRequest.Column endDateColumn = dataTablesRequest.getColumn("endDate");
        if (StringUtils.isNotBlank(endDateColumn.getSearch().getValue())) {
            String endDate = ConvertUtil.convert(endDateColumn.getSearch().getValue(), String.class);
            useLog.setEndDate(DateUtil.getDateEnd(DateUtil.parseDate(endDate, "yyyy-MM-dd")));
        }
        //样式编码 styleCode
        DataTablesRequest.Column styleCode = dataTablesRequest.getColumn("styleCode");
        if (StringUtils.isNotBlank(styleCode.getSearch().getValue())) {
            useLog.setStyleCode(styleCode.getSearch().getValue());
        }
        //类型搜索 type in (1,2,3)
        DataTablesRequest.Column search = dataTablesRequest.getColumn("search");
        if (StringUtils.isNotBlank(search.getSearch().getValue()) && !search.getSearch().getValue().equals("0")) {
            useLog.setSearch("(" + search.getSearch().getValue() + ")");
        }
        //其他
        List<UseLog> useLogList = this.findUseLogList(useLog);
        return useLogList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public UseLog getUseLog(Long id) {
        UseLog log = new UseLog();
        log.setId(id);
        UseLog useLog = useLogMapper.getUseLog(log);
        useLog.setTypeName(DictUtils.findValueByTypeAndKey(UseLog.ACTION_TYPE, useLog.getType()));
        useLog.setSourceName(DictUtils.findValueByTypeAndKey(UseLog.SOURCE_TYPE, useLog.getSourceType()));
        if(useLog.getUserId() != null){
            SysUser user = sysUserMapper.selectByPrimaryKey(useLog.getUserId().longValue());
            if (user != null) {
                useLog.setUserName(user.getName());
            }
        }
        Style st = styleService.getStyle(useLog.getStyleId());
        styleService.dealWithCoupleStyle(st);
        useLog.setStyle(st);
        return useLog;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> UseLogMap) {
        UseLog UseLog = new UseLog();
        String code = ConvertUtil.convert(UseLogMap.get("code"), String.class);
        String name = ConvertUtil.convert(UseLogMap.get("name"), String.class);
        String remark = ConvertUtil.convert(UseLogMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(UseLogMap.get("url"), String.class);

        if (insertNotNull(UseLog) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> UseLogMap) {
        Long id = ConvertUtil.convert(UseLogMap.get("id"), Long.class);
        UseLog UseLog = selectByKey(id);
        String code = ConvertUtil.convert(UseLogMap.get("code"), String.class);
        String name = ConvertUtil.convert(UseLogMap.get("name"), String.class);
        String remark = ConvertUtil.convert(UseLogMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(UseLogMap.get("url"), String.class);
        if (updateNotNull(UseLog) != 1) {
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
        UseLog UseLog = selectByKey(id);
        if (expireNotNull(UseLog) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 创建日志（转发、试戴、定制）
     *
     * @param useLog
     */
    public void createUseLog(UseLog useLog) {
        super.insertNotNull(useLog);
    }
}
