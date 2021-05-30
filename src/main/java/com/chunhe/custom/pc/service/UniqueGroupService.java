package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.StyleMapper;
import com.chunhe.custom.pc.mapper.UniqueGroupMapper;
import com.chunhe.custom.pc.model.Style;
import com.chunhe.custom.pc.model.UniqueGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年8月2日14:32:49
 */
@Service
public class UniqueGroupService extends BaseService<UniqueGroup> {

    @Autowired
    private StyleService styleService;

    @Autowired
    private StyleMapper styleMapper;

    @Autowired
    private UniqueGroupMapper uniqueGroupMapper;

    //全部列表
    public List<UniqueGroup> findUniqueGroupList(UniqueGroup uniqueGroup) {
        uniqueGroup.setEnabled(UniqueGroup.ENABLED_TRUE);
        List<UniqueGroup> uniqueGroupList = uniqueGroupMapper.findUniqueGroupList(uniqueGroup);
        return uniqueGroupList;
    }

    /**
     * 查询数据
     */
    public List<UniqueGroup> uniqueGroupList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(UniqueGroup.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //花头样式
        DataTablesRequest.Column ht = dataTablesRequest.getColumn("ht");
        if (StringUtils.isNotBlank(ht.getSearch().getValue())) {
            criteria.andLike("ht", TableUtil.toFuzzySql(ht.getSearch().getValue()));
        }
        //戒臂样式
        DataTablesRequest.Column jb = dataTablesRequest.getColumn("jb");
        if (StringUtils.isNotBlank(jb.getSearch().getValue())) {
            criteria.andLike("jb", TableUtil.toFuzzySql(jb.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        List<UniqueGroup> uniqueGroupList = getMapper().selectByExample(example);
        return uniqueGroupList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public UniqueGroup getUniqueGroup(Long id) {
        UniqueGroup log = new UniqueGroup();
        log.setId(id);
        UniqueGroup uniqueGroup = uniqueGroupMapper.getUniqueGroup(log);
        return uniqueGroup;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> uniqueGroupMap) {
        UniqueGroup uniqueGroup = new UniqueGroup();
        String name = ConvertUtil.convert(uniqueGroupMap.get("name"), String.class);
        String url = ConvertUtil.convert(uniqueGroupMap.get("url"), String.class);
        String ht = ConvertUtil.convert(uniqueGroupMap.get("ht"), String.class);
        String jb = ConvertUtil.convert(uniqueGroupMap.get("jb"), String.class);
        String remark = ConvertUtil.convert(uniqueGroupMap.get("remark"), String.class);
        Boolean enable = ConvertUtil.convert(uniqueGroupMap.get("isEnabled"), Boolean.class);
        uniqueGroup.setName(name);
        uniqueGroup.setUrl(url);
        uniqueGroup.setHt(ht);
        uniqueGroup.setJb(jb);
        uniqueGroup.setRemark(remark);
        uniqueGroup.setEnabled(enable);
        if (insertNotNull(uniqueGroup) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> uniqueGroupMap) {
        Long id = ConvertUtil.convert(uniqueGroupMap.get("id"), Long.class);
        UniqueGroup uniqueGroup = selectByKey(id);
        String name = ConvertUtil.convert(uniqueGroupMap.get("name"), String.class);
        String url = ConvertUtil.convert(uniqueGroupMap.get("url"), String.class);
        String ht = ConvertUtil.convert(uniqueGroupMap.get("ht"), String.class);
        String jb = ConvertUtil.convert(uniqueGroupMap.get("jb"), String.class);
        String remark = ConvertUtil.convert(uniqueGroupMap.get("remark"), String.class);
        Boolean enable = ConvertUtil.convert(uniqueGroupMap.get("isEnabled"), Boolean.class);
        List<String> htList = new ArrayList<>();
        List<String> jbList = new ArrayList<>();
        if (ht != null && !ht.equals("")) {
            htList = Arrays.asList(ht.split("#"));
            if (!checkYs(htList)) {
                return ServiceResponse.error("花头样式格式不正确");
            }
        }
        if (jb != null && !jb.equals("")) {
            jbList = Arrays.asList(jb.split("#"));
            if (!checkYs(jbList)) {
                return ServiceResponse.error("戒臂样式格式不正确");
            }
        }
        List<String> htLocalList = new ArrayList<>();
        List<String> jbLocalList = new ArrayList<>();
        if (uniqueGroup.getHt() != null && !uniqueGroup.getHt().equals("")) {
            htLocalList = Arrays.asList(uniqueGroup.getHt().split("#"));
        }
        if (uniqueGroup.getJb() != null && !uniqueGroup.getJb().equals("")) {
            jbLocalList = Arrays.asList(uniqueGroup.getJb().split("#"));
        }
        //花头和戒臂任何一个有改动，则进行逻辑处理，被删除的，set(null)，新增的，set(groupId)
        if ((!uniqueGroup.getHt().equals(ht)) || (!uniqueGroup.getJb().equals(jb))) {
            List<String> localList = htJbPlus(htLocalList, jbLocalList);
            List<String> currentList = htJbPlus(htList, jbList);
            checkLocalHave(localList, currentList);
            groupIdSet(localList, null);
            groupIdSet(currentList, id.intValue());
        }

        uniqueGroup.setName(name);
        uniqueGroup.setUrl(url);
        uniqueGroup.setHt(ht);
        uniqueGroup.setJb(jb);
        uniqueGroup.setRemark(remark);
        uniqueGroup.setEnabled(enable);

        if (updateNotNull(uniqueGroup) != 1) {
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
        UniqueGroup uniqueGroup = selectByKey(id);
        if (expireNotNull(uniqueGroup) != 1) {
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
        UniqueGroup uniqueGroup = selectByKey(id);
        uniqueGroup.setEnabled(isEnabled);
        return updateNotNull(uniqueGroup) == 1;
    }

    /**
     * 检查是否符合规则（大写字母，#号隔开）
     *
     * @param list
     * @return
     */
    public Boolean checkYs(List<String> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            String ys = list.get(i);
            if (!CheckUtil.isEnglishLetter(ys)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 花头样式+戒臂样式的组装
     *
     * @param htList
     * @param jbList
     * @return
     */
    public List<String> htJbPlus(List<String> htList, List<String> jbList) {
        List<String> newList = new ArrayList<>();
        for (int i = 0; i < htList.size(); i++) {
            for (int j = 0; j < jbList.size(); j++) {
                newList.add(htList.get(i) + "#" + jbList.get(j));
            }
        }
        return newList;
    }

    /**
     * 检查是否是已经有的样式，不用再查一遍sql
     *
     * @param localList
     * @param currentList
     * @return
     */
    public void checkLocalHave(List<String> localList, List<String> currentList) {
        for (int i = 0; i < localList.size(); i++) {
            for (int j = 0; j < currentList.size(); j++) {
                if (localList.get(i).equals(currentList.get(j))) {
                    localList.remove(i--);
                    currentList.remove(j--);
                    break;
                }
            }
        }
    }


    /**
     * 设置 null/groupId
     *
     * @param list
     * @param groupId
     */
    public void groupIdSet(List<String> list, Integer groupId) {
        for (int i = 0; i < list.size(); i++) {
            List<String> ysList = Arrays.asList(list.get(i).split("#"));
            Style style = new Style();
            style.setHtYs(ysList.get(0));
            style.setJbYs(ysList.get(1));
            Style st = styleMapper.getStyle(style);
            if (st != null) {
                st.setUniqueGroupId(groupId);
                styleService.updateAll(st);
            }
        }
    }

}
