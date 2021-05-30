package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.DiamondKelaMapper;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.model.DiamondKela;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年11月21日14:32:23 克拉展
 */
@Service
public class DiamondKelaService extends BaseService<DiamondKela> {

    @Autowired
    private DiamondKelaMapper diamondKelaMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    //全部列表
    public List<DiamondKela> findDiamondKelaList(DiamondKela diamondKela) {
        if (diamondKela.getStyleId() == null) {
            throw new RFException("请先确认款式");
        }
        //分页功能先查
        List<DiamondKela> diamondKelaList = diamondKelaMapper.findDiamondKelaList(diamondKela);
        Product product = new Product();
        product.setStyleId(diamondKela.getStyleId());
        product.setExSc(diamondKela.getProductExSc());
        product.setExJbKd(diamondKela.getProductJbkd());
        product.setJscz(diamondKela.getProductJscz());
        product.setJsys(diamondKela.getProductJsys());
        List<Product> productList = productMapper.findProductList(product);
        for (int i = 0; i < diamondKelaList.size(); i++) {
            DiamondKela dk = diamondKelaList.get(i);
            if (this.checkDiamondKelaAndProductByFs(dk, productList)) {
                dk.setHasSku(Boolean.TRUE);
            } else {
                dk.setHasSku(Boolean.FALSE);
            }
            this.setParam(dk);
        }
        /** 2018年12月4日20:18:05 需求变更，先确认主数据sku，再选择钻石
         Product product = new Product();
         product.setStyleId(diamondKela.getStyleId());
         Product pro = productMapper.getProductKtCode(product);
         String ktCode = "";
         if (pro != null) {
         ktCode = pro.getKtCode();
         }
         for (int i = 0; i < diamondKelaList.size(); i++) {
         DiamondKela dk = diamondKelaList.get(i);
         dk.setProductKtCode(ktCode);
         this.setParam(dk);
         }
         **/
        return diamondKelaList;
    }

    /**
     * 查询数据
     */
    public List<DiamondKela> diamondKelaList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(DiamondKela.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //编码 code
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            criteria.andLike("code", TableUtil.toFuzzySql(code.getSearch().getValue()));
        }
        //状态 status
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (StringUtils.isNotBlank(status.getSearch().getValue())) {
            criteria.andEqualTo("status", status.getSearch().getValue());
        }
        //其他
        criteria.andIsNull("expireDate");
        List<DiamondKela> diamondKelaList = getMapper().selectByExample(example);
        for (int i = 0; i < diamondKelaList.size(); i++) {
            DiamondKela dk = diamondKelaList.get(i);
            this.setParam(dk);
        }
        return diamondKelaList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public DiamondKela getDiamondKela(Long id) {
        DiamondKela diamondKela = new DiamondKela();
        diamondKela.setId(id);
        DiamondKela dk = diamondKelaMapper.getDiamondKela(diamondKela);
        this.setParam(dk);
        return dk;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> diamondKelaMap) {
        DiamondKela diamondKela = new DiamondKela();
        String code = ConvertUtil.convert(diamondKelaMap.get("code"), String.class);
        String name = ConvertUtil.convert(diamondKelaMap.get("name"), String.class);
        Integer status = ConvertUtil.convert(diamondKelaMap.get("status"), Integer.class);
        String price = ConvertUtil.convert(diamondKelaMap.get("price"), String.class);
        String imgUrl = ConvertUtil.convert(diamondKelaMap.get("imgUrl"), String.class);
        String exZsXz = ConvertUtil.convert(diamondKelaMap.get("exZsXz"), String.class);
        String exZsZs = ConvertUtil.convert(diamondKelaMap.get("exZsZs"), String.class);
        String exZsYs = ConvertUtil.convert(diamondKelaMap.get("exZsYs"), String.class);
        String exZsZl = ConvertUtil.convert(diamondKelaMap.get("exZsZl"), String.class);
        String exZsJd = ConvertUtil.convert(diamondKelaMap.get("exZsJd"), String.class);
        String exZsQg = ConvertUtil.convert(diamondKelaMap.get("exZsQg"), String.class);
        String exZsPg = ConvertUtil.convert(diamondKelaMap.get("exZsPg"), String.class);
        String exZsDc = ConvertUtil.convert(diamondKelaMap.get("exZsDc"), String.class);
        String exZsYg = ConvertUtil.convert(diamondKelaMap.get("exZsYg"), String.class);
        String remark = ConvertUtil.convert(diamondKelaMap.get("remark"), String.class);
        int priceInt = new BigDecimal(Double.toString(NumberUtils.toDouble(price, 0))).multiply(new BigDecimal(100)).intValue();
        int exZsZlInt = new BigDecimal(Double.toString(NumberUtils.toDouble(exZsZl, 0))).multiply(new BigDecimal(1000)).intValue();
        diamondKela.setCode(code);
        diamondKela.setName(name);
        diamondKela.setStatus(status);
        diamondKela.setPrice(priceInt);
        diamondKela.setImgUrl(imgUrl);
        diamondKela.setExZsXz(exZsXz);
        diamondKela.setExZsZs(exZsZs);
        diamondKela.setExZsYs(exZsYs);
        diamondKela.setExZsZl(exZsZlInt);
        diamondKela.setExZsJd(exZsJd);
        diamondKela.setExZsQg(exZsQg);
        diamondKela.setExZsPg(exZsPg);
        diamondKela.setExZsDc(exZsDc);
        diamondKela.setExZsYg(exZsYg);
        diamondKela.setRemark(remark);

        if (insertNotNull(diamondKela) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> diamondKelaMap) {
        Long id = ConvertUtil.convert(diamondKelaMap.get("id"), Long.class);
        DiamondKela diamondKela = selectByKey(id);
        String code = ConvertUtil.convert(diamondKelaMap.get("code"), String.class);
        String name = ConvertUtil.convert(diamondKelaMap.get("name"), String.class);
        Integer status = ConvertUtil.convert(diamondKelaMap.get("status"), Integer.class);
        String price = ConvertUtil.convert(diamondKelaMap.get("price"), String.class);
        String imgUrl = ConvertUtil.convert(diamondKelaMap.get("imgUrl"), String.class);
        String exZsXz = ConvertUtil.convert(diamondKelaMap.get("exZsXz"), String.class);
        String exZsZs = ConvertUtil.convert(diamondKelaMap.get("exZsZs"), String.class);
        String exZsYs = ConvertUtil.convert(diamondKelaMap.get("exZsYs"), String.class);
        String exZsZl = ConvertUtil.convert(diamondKelaMap.get("exZsZl"), String.class);
        String exZsJd = ConvertUtil.convert(diamondKelaMap.get("exZsJd"), String.class);
        String exZsQg = ConvertUtil.convert(diamondKelaMap.get("exZsQg"), String.class);
        String exZsPg = ConvertUtil.convert(diamondKelaMap.get("exZsPg"), String.class);
        String exZsDc = ConvertUtil.convert(diamondKelaMap.get("exZsDc"), String.class);
        String exZsYg = ConvertUtil.convert(diamondKelaMap.get("exZsYg"), String.class);
        String remark = ConvertUtil.convert(diamondKelaMap.get("remark"), String.class);
        int priceInt = new BigDecimal(Double.toString(NumberUtils.toDouble(price, 0))).multiply(new BigDecimal(100)).intValue();
        int exZsZlInt = new BigDecimal(Double.toString(NumberUtils.toDouble(exZsZl, 0))).multiply(new BigDecimal(1000)).intValue();
        diamondKela.setCode(code);
        diamondKela.setName(name);
        diamondKela.setStatus(status);
        diamondKela.setPrice(priceInt);
        diamondKela.setImgUrl(imgUrl);
        diamondKela.setExZsXz(exZsXz);
        diamondKela.setExZsZs(exZsZs);
        diamondKela.setExZsYs(exZsYs);
        diamondKela.setExZsZl(exZsZlInt);
        diamondKela.setExZsJd(exZsJd);
        diamondKela.setExZsQg(exZsQg);
        diamondKela.setExZsPg(exZsPg);
        diamondKela.setExZsDc(exZsDc);
        diamondKela.setExZsYg(exZsYg);
        diamondKela.setRemark(remark);

        if (updateNotNull(diamondKela) != 1) {
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
        DiamondKela diamondKela = selectByKey(id);
        if (expireNotNull(diamondKela) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 详情
     *
     * @param diamondKela
     * @return
     */
    public DiamondKela getDiamondKelaDetail(DiamondKela diamondKela) {
        DiamondKela dk = diamondKelaMapper.getDiamondKela(diamondKela);
        this.setParam(dk);
        return dk;
    }

    /**
     * 检查钻石颜色，是否在成品的颜色范围内
     *
     * @param diamondKela
     * @param productList
     * @return
     */
    public boolean checkDiamondKelaAndProductByFs(DiamondKela diamondKela, List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            //原：首先分数相同
            //fs.equals(product.getExFs())
            //现2019年3月26日21:37:03：产品规格范围内
            if (productService.checkDiamondFs(diamondKela.getExZsZl(), product)
                    && productService.checkDiamondYs(diamondKela.getExZsYs(), product.getExZsYs())
                    && productService.checkDiamondJd(diamondKela.getExZsJd(), product.getExZsJd())) {
                diamondKela.setProductKtCode(product.getKtCode());
                diamondKela.setProductPrice(product.getPrice());
                return true;
            }
        }
        return false;
    }

    /**
     * 设置参数
     *
     * @param diamondKela
     */
    public void setParam(DiamondKela diamondKela) {
        if (diamondKela != null && diamondKela.getStatus() != null) {
            String statusName = DictUtils.findValueByTypeAndKey(DiamondKela.DIAMOND_KELA_STATUS, diamondKela.getStatus());
            diamondKela.setStatusName(statusName);
        }
    }

}
