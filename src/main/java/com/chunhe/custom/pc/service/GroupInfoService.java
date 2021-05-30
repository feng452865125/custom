package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.GroupInfoMapper;
import com.chunhe.custom.pc.model.GroupInfo;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月13日11:27:13
 */
@Service
public class GroupInfoService extends BaseService<GroupInfo> {

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private UniqueGroupService uniqueGroupService;

    //全部列表
    public List<GroupInfo> findGroupInfoList(GroupInfo groupInfo) {
        List<GroupInfo> groupInfoList = groupInfoMapper.findGroupInfoList(groupInfo);
        return groupInfoList;
    }

    /**
     * 查询数据
     */
    public List<GroupInfo> groupInfoList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(GroupInfo.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //编码
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            criteria.andLike("code", TableUtil.toFuzzySql(code.getSearch().getValue()));
        }
        //名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        //类型
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (StringUtils.isNotBlank(type.getSearch().getValue())) {
            criteria.andEqualTo("type", type.getSearch().getValue());
        }
        //其他
        criteria.andIsNull("expireDate");
        List<GroupInfo> groupInfoList = getMapper().selectByExample(example);
        this.setParam(groupInfoList);
        return groupInfoList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public GroupInfo getGroupInfo(Long id) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(id);
        GroupInfo gi = groupInfoMapper.getGroupInfo(groupInfo);
        gi.setTypeName(DictUtils.findValueByTypeAndKey("groupInfoType", gi.getType()));
        return gi;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> groupInfoMap) {
        GroupInfo groupInfo = new GroupInfo();
        String code = ConvertUtil.convert(groupInfoMap.get("code"), String.class);
        String name = ConvertUtil.convert(groupInfoMap.get("name"), String.class);
        String remark = ConvertUtil.convert(groupInfoMap.get("remark"), String.class);
        Integer type = ConvertUtil.convert(groupInfoMap.get("type"), Integer.class);
        String ys = ConvertUtil.convert(groupInfoMap.get("ys"), String.class);

        List<String> ysList = Arrays.asList(ys.split("#"));
        if (!uniqueGroupService.checkYs(ysList)) {
            return ServiceResponse.error("样式格式不正确");
        }
        groupInfo.setCode(code);
        groupInfo.setName(name);
        groupInfo.setRemark(remark);
        groupInfo.setType(type);
        groupInfo.setYs(ys);

        if (insertNotNull(groupInfo) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> groupInfoMap) {
        Long id = ConvertUtil.convert(groupInfoMap.get("id"), Long.class);
        GroupInfo groupInfo = selectByKey(id);
        String code = ConvertUtil.convert(groupInfoMap.get("code"), String.class);
        String name = ConvertUtil.convert(groupInfoMap.get("name"), String.class);
        String remark = ConvertUtil.convert(groupInfoMap.get("remark"), String.class);
        Integer type = ConvertUtil.convert(groupInfoMap.get("type"), Integer.class);
        String ys = ConvertUtil.convert(groupInfoMap.get("ys"), String.class);

        List<String> ysList = Arrays.asList(ys.split("#"));
        if (!uniqueGroupService.checkYs(ysList)) {
            return ServiceResponse.error("样式格式不正确");
        }
        groupInfo.setCode(code);
        groupInfo.setName(name);
        groupInfo.setRemark(remark);
        groupInfo.setType(type);
        groupInfo.setYs(ys);
        if (updateNotNull(groupInfo) != 1) {
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
        GroupInfo groupInfo = selectByKey(id);
        if (expireNotNull(groupInfo) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 设置参数（类型）
     * @param list
     */
    public void setParam(List<GroupInfo> list) {
        for(int i = 0; i < list.size(); i++) {
            GroupInfo groupInfo = list.get(i);
            groupInfo.setTypeName(DictUtils.findValueByTypeAndKey("groupInfoType", groupInfo.getType()));
        }
    }
}
