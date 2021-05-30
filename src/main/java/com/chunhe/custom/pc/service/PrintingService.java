package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.PrintingMapper;
import com.chunhe.custom.pc.model.Printing;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * Created by white 2018-5-18 11:55:55
 */
@Service
public class PrintingService extends BaseService<Printing> {

    @Autowired
    private PrintingMapper printingMapper;

    //全部列表
    public List<Printing> findPrintingList(Printing printing) {
        List<Printing> printingList = printingMapper.findPrintingList(printing);
        return printingList;
    }

    /**
     * 查询数据
     */
    public List<Printing> printingList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Printing.class);
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
    public Printing getPrinting(Long id) {
        Printing printing = new Printing();
        printing.setId(id);
        Printing p = printingMapper.getPrinting(printing);
        return p;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> PrintingMap) {
        Printing printing = new Printing();
        String code = ConvertUtil.convert(PrintingMap.get("code"), String.class);
        String name = ConvertUtil.convert(PrintingMap.get("name"), String.class);
        String remark = ConvertUtil.convert(PrintingMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(PrintingMap.get("url"), String.class);

        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        printing.setCode(code);
        printing.setName(name);
        printing.setRemark(remark);
        printing.setImgUrl(imgUrl);

        if (insertNotNull(printing) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> PrintingMap) {
        Long id = ConvertUtil.convert(PrintingMap.get("id"), Long.class);
        Printing printing = selectByKey(id);
        String code = ConvertUtil.convert(PrintingMap.get("code"), String.class);
        String name = ConvertUtil.convert(PrintingMap.get("name"), String.class);
        String remark = ConvertUtil.convert(PrintingMap.get("remark"), String.class);
        String imgUrl = ConvertUtil.convert(PrintingMap.get("url"), String.class);

        if (imgUrl == null || imgUrl.equals("")) {
            return ServiceResponse.error("请上传图片");
        }
        printing.setCode(code);
        printing.setName(name);
        printing.setRemark(remark);
        printing.setImgUrl(imgUrl);
        if (updateNotNull(printing) != 1) {
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
        Printing printing = selectByKey(id);
        if (expireNotNull(printing) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

}
