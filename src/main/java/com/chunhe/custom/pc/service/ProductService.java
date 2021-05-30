package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.BeanUtil;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.pc.mapper.*;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.mapper.*;
import com.chunhe.custom.pc.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by white 2018年7月14日15:00:28
 */
@Service
public class ProductService extends BaseService<Product> {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private StyleService styleService;

    @Autowired
    private StyleMapper styleMapper;

    @Autowired
    private RelProductPartsMapper relProductPartsMapper;

    @Autowired
    private JewelryTypeMapper jewelryTypeMapper;

    //全部列表
    public List<Product> findProductList(Product product) {
        List<Product> productList = productMapper.findProductList(product);
        return productList;
    }

    /**
     * 查询数据
     */
    public List<Product> productList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Product.class);
        //排序
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
        //kt码
        DataTablesRequest.Column ktCode = dataTablesRequest.getColumn("ktCode");
        if (StringUtils.isNotBlank(ktCode.getSearch().getValue())) {
            criteria.andLike("ktCode", TableUtil.toFuzzySql(ktCode.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        List<Product> productList = getMapper().selectByExample(example);
        //一个成品，有3个配件组成
//        for (int i = 0; i < productList.size(); i++) {
//            Product pr = productList.get(i);
//            RelProductParts relProductParts = new RelProductParts();
//            relProductParts.setProductCode(pr.getCode());
//            List<RelProductParts> list = relProductPartsMapper.findRelProductParts(relProductParts);
//            //检查每个配件的类型
//            for (int j = 0; j < list.size(); j++) {
//                RelProductParts rel = list.get(j);
//                Parts parts = new Parts();
//                parts.setCode(rel.getPartsCode());
//                Parts pa = partsMapper.getParts(parts);
//                if (pa != null) {
//                    if (pa.getType() == Parts.TYPE_FLOWER_HEAD) {
//                        pr.setFlowerHeadNum(parts.getCode());
//                    } else if (pa.getType() == Parts.TYPE_RING_ARM) {
//                        pr.setRingArmNum(parts.getCode());
//                    } else if (pa.getType() == Parts.TYPE_DIAMOND) {
//                        pr.setDiamondNum(parts.getCode());
//                    }
//                }
//            }
//        }
        return productList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        Product pr = productMapper.getProduct(product);
        return pr;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> styleMap) {
        Product product = new Product();
        String code = ConvertUtil.convert(styleMap.get("code"), String.class);

        product.setCode(code);
        if (insertNotNull(product) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> styleMap) {
        Long id = ConvertUtil.convert(styleMap.get("id"), Long.class);
        Product product = selectByKey(id);
        String priceStr = ConvertUtil.convert(styleMap.get("price"), String.class);
        int price = new BigDecimal(Double.toString(NumberUtils.toDouble(priceStr, 0))).multiply(new BigDecimal(100)).intValue();
        if (price == 0){
            //价格=0，不做更新
            return ServiceResponse.error("价格为0，请核对");
        }
        product.setPrice(price);
        if (updateNotNull(product) != 1) {
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
        Product product = selectByKey(id);
        if (expireNotNull(product) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 查找成品list
     *
     * @param product
     * @return
     */
    public List<Product> findProductListByPartsCode(Product product) throws CloneNotSupportedException {
        List<Product> productList;
        if (product.getDiamondCode() != null) {
            //有钻石id，是普通款，样式+钻石→成品list
            Parts par = new Parts();
            par.setCode(product.getDiamondCode());
            Parts zs = partsMapper.getParts(par);
            if (zs == null) {
                throw new RFException("钻石不存在");
            }
            //通过styleId先取样式，再拿ht_ys,jb_ys
            Style style = this.getStyle(product.getStyleId());
            if (style.getSeries().equals("UNIQUE")) {
                product.setJbYs(style.getJbYs());
                product.setHtYs(style.getHtYs());
                //确认样式，根据戒臂和花头的code，查出产品list（与钻石code无关）
                productList = productMapper.findProductListByPartsCode(product);
                dealDiamondYsAndJdAndFs(productList, zs);
            } else {
                //非UNIQUE系列可通过styleId查productList
                product.setStyleId(style.getId());
                productList = productMapper.findProductList(product);
                dealDiamondYsAndJdAndFs(productList, zs);
            }
            //数据处理（加上钻石描述，戒臂描述）
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                //颜色和净度都true，即颜色和净度任意一个false，则remove(加个分数判断)
//                String fs = zs.getExZsZl() / 10 + "";
//                if (!this.checkDiamondYs(zs.getExZsYs(), pro.getExZsYs())
//                        || !this.checkDiamondJd(zs.getExZsJd(), pro.getExZsJd())
//                        || !fs.equals(pro.getExFs())) {
//                    productList.remove(i--);
//                    continue;
//                }
                //style数据
                pro.setStyle((Style) style.clone());
                //parts数据（钻石描述）
                pro.setDiamondRemark(zs.getRemark());
                //parts数据（戒臂描述）
                RelProductParts relProductParts = new RelProductParts();
                relProductParts.setProductCode(pro.getCode());
                List<RelProductParts> relProductPartsList = relProductPartsMapper.findRelProductParts(relProductParts);
                for (int j = 0; j < relProductPartsList.size(); j++) {
                    RelProductParts rel = relProductPartsList.get(j);
                    Parts parts = new Parts();
                    parts.setCode(rel.getPartsCode());
                    Parts pa = partsMapper.getParts(parts);
                    if (pa != null) {
                        if (pa.getType() == Parts.TYPE_FLOWER_HEAD) {
                            pro.setFlowerHeadRemark(pa.getRemark());
                        } else if (pa.getType() == Parts.TYPE_RING_ARM) {
                            pro.setRingArmRemark(pa.getRemark());
                        } else if (pa.getType() == Parts.TYPE_DIAMOND) {
                            pro.setDiamondRemark(pa.getRemark());
                        }
                    }
                }
                //拆分手寸(去掉最后一个#号)
                pro.setHandList(this.checkHandList(pro));
            }
        } else {
//            //通过styleId先取样式，再拿ht_ys,jb_ys
//            Style style = this.getStyle(product.getStyleId());
            //无钻石id，样式→成品list
            productList = productMapper.findProductList(product);
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                pro.setHandList(this.checkHandList(pro));
            }
        }
        return productList;
    }

    /**
     * 新逻辑，下单前的选择
     *
     * @param product
     * @return
     */
    public List<Product> findProductListApp(Product product) {
        List<Product> productList;
        Style style = styleService.selectByKey(product.getStyleId());
        if(style != null && style.getSeries().equals("UNIQUE")){
//            Product pro = new Product();
//            pro.setHtYs(style.getHtYs());
//            pro.setStyleId(product.getStyleId());
//            productList = productMapper.findProductListByStyleHt(pro);
            Example example = new Example(Product.class);
            example.createCriteria()
                    .andIsNull("expireDate")
                    .andEqualTo("styleId", product.getStyleId());
            productList = productMapper.selectByExample(example);
        } else {
            productList = productMapper.findProductList(product);
        }
        if (product.getDiamondCode() != null && !product.getDiamondCode().equals("")) {
            Parts par = new Parts();
            par.setCode(product.getDiamondCode());
            Parts zs = partsMapper.getParts(par);
            if (zs == null) {
                throw new RFException("钻石不存在");
            }
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                //处理掉不能匹配的（产品列表中，不满足颜色，不满足净度（非unique不需分数判断））
                if (pro.getExFs() == null || pro.getExFs().equals("")) {
                    if (!this.checkDiamondYs(zs.getExZsYs(), pro.getExZsYs())
                            || !this.checkDiamondJd(zs.getExZsJd(), pro.getExZsJd())) {
                        productList.remove(i--);
                        continue;
                    }
                } else {
                    if (!this.checkDiamondYs(zs.getExZsYs(), pro.getExZsYs())
                            || !this.checkDiamondJd(zs.getExZsJd(), pro.getExZsJd())
                            || !this.checkDiamondFs(zs.getExZsZl(), pro)) {
                        productList.remove(i--);
                        continue;
                    }
                }
                pro.setHandList(this.checkHandList(pro));
            }
        } else {
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                pro.setHandList(this.checkHandList(pro));
            }
        }
//        //过滤完，剩下数据筛选，下拉框的选择数据
//        findProductGroupBy(productList);
        return productList;
    }

    public HashMap<String, Object> findProductGroupBy(List<Product> productList) {
        List jbkdList = new ArrayList();
        List jsczList = new ArrayList();
        List jsysList = new ArrayList();
        List nxbsList = new ArrayList();
        List scList = new ArrayList();
        List maleScList = new ArrayList();
        List femaleScList = new ArrayList();
        List maleHandList = new ArrayList();
        List femaleHandList = new ArrayList();
        List handList = new ArrayList();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            addToList(jbkdList, product.getExJbKd());
            addToList(jsczList, product.getJscz());
            addToList(jsysList, product.getJsys());
            addToList(nxbsList, product.getNxbs());
            if (product.getType() != null) {
                //情侣戒有两种手寸，男戒，女戒
                if (product.getType() == Product.TYPE_MALE) {
                    addToList(maleScList, product.getExSc());
                } else if (product.getType() == Product.TYPE_FEMALE) {
                    addToList(femaleScList, product.getExSc());
                }
            } else {
                addToList(scList, product.getExSc());
            }
        }
        maleHandList = dealList(maleHandList, maleScList);
        femaleHandList = dealList(femaleHandList, femaleScList);
        handList = dealList(handList, scList);
        map.put("jbkdList", jbkdList);
        map.put("jsczList", jsczList);
        map.put("jsysList", jsysList);
        map.put("nxbsList", nxbsList);
        map.put("handList", handList);
        map.put("maleHandList", maleHandList);
        map.put("femaleHandList", femaleHandList);
        return map;
    }

    public List addToList(List tarList, String value) {
        if (value != null && !value.equals("")) {
            if (!checkInList(tarList, value)) {
                tarList.add(value);
            }
        }
        return tarList;
    }

    public Boolean checkInList(List tarList, String value) {
        for (int i = 0; i < tarList.size(); i++) {
            if (tarList.get(i).equals(value)) {
                return true;
            }
        }
        return false;
    }

    public List dealList(List tarList, List reList) {
        for (int i = 0; i < reList.size(); i++) {
            if (reList.size() == 1) {
                //其他系列
                Product pro = new Product();
                pro.setExSc(reList.get(0).toString());
                tarList = checkHandList(pro);
            } else {
                //时光，单手寸，处理#号
                String value = reList.get(i).toString();
                if (value.substring(value.length() - 1, value.length()).equals("#")) {
                    value = value.substring(0, value.length() - 1);
                }
                tarList.add(value);
            }
        }
        return tarList;
    }

    /**
     * 情侣戒手寸处理
     *
     * @param tarList
     * @param reList
     * @return
     */
    public List<Product> addProductToHandList(List tarList, List<Product> reList) {
        for (int i = 0; i < reList.size(); i++) {
            Product product = reList.get(i);
            if (reList.size() == 1) {
                //其他系列
                tarList = checkHandList(product);
            } else {
                //时光，单手寸，处理#号
                String value = product.getExSc();
                if (value.substring(value.length() - 1, value.length()).equals("#")) {
                    value = value.substring(0, value.length() - 1);
                }
                tarList.add(value);
            }
        }
        return tarList;
    }

    /**
     * 创建成品
     *
     * @param sapSku
     * @return
     */
    @Transactional
    public void createProductBySap(SapSku sapSku) {
        Product product = new Product();
        product.setCode(sapSku.getCode());
        Product localProduct = productMapper.getProduct(product);
        if (localProduct == null) {
            //先拦截数据库没有的，避免无用解析（生产要求备注）
            return;
        }
        int sex = 0;
        if (sapSku.getSex() != null && !sapSku.getSex().equals("")) {
            sex = sapSku.getSex().equals("男") ? 1 : 2;
        }
        String fs = "";
        String kd = "";
        if (sapSku.getFs() != null && !sapSku.getFs().equals("")) {
            String scyqbz = sapSku.getFs();
            List<String> scyqbzList = Arrays.asList(scyqbz.split("-"));
            if (scyqbzList != null && scyqbzList.size() == 2) {
                String ysfs = scyqbzList.get(1);
                if (ysfs.length() >= 4 && ysfs.substring(1, 4).equals("100")) {
                    fs = ysfs.substring(1, 4);
                } else {
                    fs = ysfs.substring(1, 3);
                }
            }
            List<String> scyqbzList2 = Arrays.asList(scyqbz.split("\\."));
            if (scyqbzList2 != null && scyqbzList2.size() == 2) {
                kd = scyqbzList2.get(0).substring(scyqbzList2.get(0).length() - 1, scyqbzList2.get(0).length()) + "." + scyqbzList2.get(1).substring(0, 1);
            }
        }
        String yy = "";
        if (sapSku.getYy() != null && !sapSku.getYy().equals("")) {
            String last = sapSku.getYy().substring(sapSku.getYy().length() - 1, sapSku.getYy().length());
            if (last.equals("男")) {
                //情侣戒寓意后带有一个"男"字或者"女"字，过滤掉
                yy = sapSku.getYy().substring(0, sapSku.getYy().length() - 1);
                sex = 1;
            } else if (last.equals("女")) {
                //情侣戒寓意后带有一个"男"字或者"女"字，过滤掉
                yy = sapSku.getYy().substring(0, sapSku.getYy().length() - 1);
                sex = 2;
            } else {
                yy = sapSku.getYy();
                sex = 0;
            }
        }
        int price = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getPrice(), 0))).multiply(new BigDecimal(100)).intValue();
        int fsCount = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getFsCount(), 0))).intValue();
        Product sapProduct = new Product(sapSku.getCode(), sapSku.getKtCode(), sapSku.getName(), kd, price, sapSku.getZsYs(), sapSku.getZsJd(),
                sex == 0 ? null : sex, sapSku.getSc(), fs, yy, fsCount, sapSku.getJsCz(), sapSku.getZsLx(), sapSku.getJsYs());
        //手寸有变化，同款式的sku全部更新，2019年2月12日09:41:27
        if (!sapProduct.getExSc().equals(localProduct.getExSc())) {
            if (localProduct.getStyleId() != null) {
                Product pp = new Product();
                //同款式，同类型（男戒，女戒）
                pp.setStyleId(localProduct.getStyleId());
                pp.setType(sex);
                List<Product> productList = productMapper.findProductList(pp);
                for (int i = 0; i < productList.size(); i++) {
                    Product sameStyleId = productList.get(i);
                    if (sameStyleId.getId() != localProduct.getId()) {
                        sameStyleId.setExSc(sapProduct.getExSc());
                        updateNotNull(sameStyleId);
                    }
                }
            }
        }
        //已存在,product更新
        BeanUtil.copyObject(sapProduct, localProduct, true);
        updateNotNull(localProduct);

        if (localProduct != null) {
//            //只更新product表，不做新增和样式关联修改 2018年10月31日17:08:19
//            styleService.updateStyleByProductFromSap(sapSku, localProduct);
        } else {
//            insertNotNull(sapProduct);
//            //只更新product表，不做新增和样式关联修改 2018年10月31日17:08:19
//            styleService.updateStyleByProductFromSap(sapSku, sapProduct);
//            //添加成品组件关联表
//            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getHtCode());
//            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getJbCode());
//            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getZsCode());
        }
    }

    /**
     * 更新成品，来自组件（花头/戒臂）的变化改动
     *
     * @param localStyle
     * @return
     */
    @Transactional
    public void updateProductByStyleFromSap(SapSku sapSku, Style localStyle, String moral) {
        Product sapProduct = new Product(sapSku.getKd(), sapSku.getSc(), sapSku.getFs(), moral, sapSku.getJsCz(), sapSku.getJsYs());
        Product product = new Product();
        product.setStyleId(localStyle.getId());
        List<Product> productList = productMapper.findProductList(product);
        for (int i = 0; i < productList.size(); i++) {
            Product localProduct = productList.get(i);
            BeanUtil.copyObject(sapProduct, localProduct, true);
            updateNotNull(localProduct);
        }
    }

    /**
     * 创建成品
     *
     * @param sapSku
     * @return
     */
    @Transactional
    public void updateProductBySap(SapSku sapSku) {
        int sex = sapSku.getSex().equals("男") ? 1 : 2;
        int price = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getPrice(), 0))).multiply(new BigDecimal(100)).intValue();
        Product sapProduct = new Product(sapSku.getCode(), sapSku.getKtCode(), sapSku.getName(), sapSku.getKd(), price, sapSku.getZsYs(), sapSku.getZsJd(),
                sex, sapSku.getSc(), sapSku.getFs(), sapSku.getYy(), new Integer(sapSku.getFsCount()), sapSku.getJsCz(), sapSku.getZsLx(), sapSku.getJsYs());

        Product product = new Product();
        product.setCode(sapSku.getCode());
        Product localProduct = productMapper.getProduct(product);
        if (localProduct != null) {
            //已存在,product更新
            BeanUtil.copyObject(sapProduct, localProduct, true);
            updateNotNull(localProduct);
            //佩戴类别，style更新用到
            JewelryType jewelryType = new JewelryType();
            jewelryType.setName(sapSku.getPdLx());
            JewelryType jewelry = jewelryTypeMapper.getJewelryType(jewelryType);

            Boolean isKt = sapSku.getIsKt().equals("是") ? Boolean.TRUE : Boolean.FALSE;
            Boolean isZs = sapSku.getIsZs().equals("是") ? Boolean.TRUE : Boolean.FALSE;
            Style style = new Style(sapSku.getYy() + sapSku.getPdLx() + sapSku.getJsYs(), sapSku.getName(), jewelry.getId().intValue(),
                    sapSku.getYy(), sapSku.getGs(), sapSku.getSeries(), sapSku.getQghy(), sapSku.getYs(),
                    sapSku.getYs(), sapSku.getKstd(), isKt, isZs);
            Style st = new Style();
            st.setId(localProduct.getStyleId());
            Style localStyle = styleMapper.getStyle(st);
            if (localStyle != null) {
                BeanUtil.copyObject(style, localStyle, true);
                styleService.updateNotNull(localStyle);
            }
        } else {
            insertNotNull(sapProduct);
            //添加成品组件关联表
            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getHtCode());
            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getJbCode());
            this.createRelProductPartsBySap(sapSku.getCode(), sapSku.getZsCode());
            //style新增

        }
    }

    /**
     * 添加成品组件关联表
     *
     * @param productCode
     * @param partsCode
     */
    public void createRelProductPartsBySap(String productCode, String partsCode) {
        if (productCode.equals("") || partsCode.equals("")) {
            return;
        }
        RelProductParts relProductParts = new RelProductParts();
        relProductParts.setProductCode(productCode);
        relProductParts.setPartsCode(partsCode);
        RelProductParts rel = relProductPartsMapper.getRelProductParts(relProductParts);
        if (rel == null) {
            relProductPartsMapper.insertSelective(relProductParts);
        }
    }

    /**
     * 检测是否存在SKU
     *
     * @param product
     * @return
     */
    public Product checkKtCode(Product product) {
        if (product.getExSc().equals("")) {
            throw new RFException("请选择手寸");
        }
        Style style = getStyle(product.getStyleId());
        if (style.getIsNxbs() == Boolean.FALSE) {
            //是否内镶宝石，false
            product.setNxbs(null);
        } else {
            if (style.getSeries().equals("UNIQUE")) {
                //这一批数据UNIQUE情侣戒有内镶宝石数据
                if (product.getJscz().equals("")) {
                    throw new RFException("请选择金属材质");
                }
                if (product.getJsys().equals("")) {
                    throw new RFException("请选择金属颜色");
                }
                if (product.getNxbs().equals("")) {
                    throw new RFException("请选择内镶宝石");
                }
            }
        }
        Product pro = productMapper.getProductKtCode(product);
        return pro;
    }

    /**
     * 检查钻石质量，是否在成品分数范围内（产品规格）
     *
     * @param zszl
     * @param product
     * @return
     */
    public Boolean checkDiamondFs(Integer zszl, Product product) {
        //钻石选择  zszl = 320
        //成品  ex_diamond_fs_min = 0.30*1000=300, ex_diamond_fs_max = 0.399 * 1000 = 399
        if (zszl != null && product.getExDiamondFsMin() != null && product.getExDiamondFsMax() != null) {
            if (zszl >= product.getExDiamondFsMin() && zszl <= product.getExDiamondFsMax()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查钻石颜色，是否在成品的颜色范围内
     *
     * @param ys
     * @param productYs
     * @return
     */
    public Boolean checkDiamondYs(String ys, String productYs) {
        //钻石选择  颜色= G
        //成品  D-H, D, K以上，D1
        //完整的 = "D,E,F,G,H,I,J,K,L";


        if (!productYs.equals("")) {
            List<String> list = Arrays.asList(productYs.split("-"));
            if("D-H".equals(productYs)){
                productYs += "-I-J-E-F-G";
                list = Arrays.asList(productYs.split("-"));
            }
            if("D-G".equals(productYs)){
                productYs += "-E-F";
                list = Arrays.asList(productYs.split("-"));
            }
            if (list == null) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).length() > 1) {
                    list.set(i, list.get(i).substring(0, 1));
                }
                if (ys.equals(list.get(i))) {
                    return true;
                }
            }
            if (list.size() == 2) {
                if (ys.compareTo(list.get(0)) >= 0 && ys.compareTo(list.get(1)) <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查钻石净度，是否在成品的净度范围内
     *
     * @param jd
     * @param productJd
     * @return
     */
    public Boolean checkDiamondJd(String jd, String productJd) {
        // 钻石选择    净度 = VS1
        //      成品  IF-VVS-VS
        //        private String initJd = "FL,IF,VVS1,VVS2,VS1,VS2,SI1,SI2";
        if (!productJd.equals("")) {
            if (jd.length() >= 3) {
                jd = jd.substring(0, jd.length() - 1);
            }
            List<String> list = Arrays.asList(productJd.split("-"));
            for (int i = 0; i < list.size(); i++) {
                if (jd.equals(list.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 处理productList，不符合的remove(颜色、净度、分数)
     *
     * @param productList
     * @param zs
     */
    public void dealDiamondYsAndJdAndFs(List<Product> productList, Parts zs) {
        for (int i = 0; i < productList.size(); i++) {
            Product pro = productList.get(i);
            if (pro.getExFs() == null || pro.getExFs().equals("")) {
                if (!this.checkDiamondYs(zs.getExZsYs(), pro.getExZsYs())
                        || !this.checkDiamondJd(zs.getExZsJd(), pro.getExZsJd())) {
                    productList.remove(i--);
                }
            } else {
                if (!this.checkDiamondYs(zs.getExZsYs(), pro.getExZsYs())
                        || !this.checkDiamondJd(zs.getExZsJd(), pro.getExZsJd())
                        || !this.checkDiamondFs(zs.getExZsZl(), pro)) {
                    productList.remove(i--);
                }
            }
        }
    }

    /**
     * 处理手寸，数据分割
     *
     * @param pro
     * @return
     */
    public List checkHandList(Product pro) {
        //11#12#13#14#15#
        List handList = new ArrayList();
        if (pro.getExSc() != null && pro.getExSc().length() > 1) {
            String sc = pro.getExSc().substring(0, pro.getExSc().length() - 1);
            List<String> list = Arrays.asList(sc.split("#"));
            for (int k = 0; k < list.size(); k++) {
                handList.add(list.get(k));
            }
        }
        return handList;
    }

    /**
     * 同时用到，拎出来
     *
     * @param styleId
     * @return
     */
    public Style getStyle(Long styleId) {
        Style style = styleService.getStyle(styleId);
        if (style == null) {
            throw new RFException("款式不存在");
        }
        if (style.getIsKt() == Style.IS_KT_FALSE) {
            throw new RFException("该款式无法下单");
        }
        return style;
    }

    /**
     * 设置product里的详情图及试戴图
     *
     * @param styleMap
     */
    @Transactional
    public void updateProductByStyleMap(Map<String, Object> styleMap) {
        String imgUrlBoy = ConvertUtil.convert(styleMap.get("imgUrlBoy"), String.class);
        String wearUrlBoy30 = ConvertUtil.convert(styleMap.get("wearUrlBoy30"), String.class);
        String wearUrlBoy50 = ConvertUtil.convert(styleMap.get("wearUrlBoy50"), String.class);
        String wearUrlBoy70 = ConvertUtil.convert(styleMap.get("wearUrlBoy70"), String.class);
        String wearUrlBoy100 = ConvertUtil.convert(styleMap.get("wearUrlBoy100"), String.class);
        String imgUrlGirl = ConvertUtil.convert(styleMap.get("imgUrlGirl"), String.class);
        String wearUrlGirl30 = ConvertUtil.convert(styleMap.get("wearUrlGirl30"), String.class);
        String wearUrlGirl50 = ConvertUtil.convert(styleMap.get("wearUrlGirl50"), String.class);
        String wearUrlGirl70 = ConvertUtil.convert(styleMap.get("wearUrlGirl70"), String.class);
        String wearUrlGirl100 = ConvertUtil.convert(styleMap.get("wearUrlGirl100"), String.class);
        Long id = ConvertUtil.convert(styleMap.get("id"), Long.class);
        Product product = new Product();
        product.setStyleId(id);
        List<Product> productList = productMapper.findProductList(product);
        for (int i = 0; i < productList.size(); i++) {
            Product pro = productList.get(i);
            if (pro.getType() == Product.TYPE_MALE) {
                this.setParam(pro, imgUrlBoy, wearUrlBoy30, wearUrlBoy50, wearUrlBoy70, wearUrlBoy100);
            } else if (pro.getType() == Product.TYPE_FEMALE) {
                this.setParam(pro, imgUrlGirl, wearUrlGirl30, wearUrlGirl50, wearUrlGirl70, wearUrlGirl100);
            }
            updateNotNull(pro);
        }
    }

    /**
     * 设置详情图和试戴图
     *
     * @param product
     * @param imgUrl
     * @param wearUrl30
     * @param wearUrl50
     * @param wearUrl70
     * @param wearUrl100
     */
    public void setParam(Product product, String imgUrl, String wearUrl30, String wearUrl50, String wearUrl70, String wearUrl100) {
        product.setImgMaxUrl(imgUrl);
        product.setWearUrl30(wearUrl30);
        product.setWearUrl50(wearUrl50);
        product.setWearUrl70(wearUrl70);
        product.setWearUrl100(wearUrl100);
    }



}
