package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.pc.mapper.GroupInfoMapper;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.thirdSupplier.rows;
import com.chunhe.custom.utils.DictUtils;
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
public class PartsService extends BaseService<Parts> {

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private UniqueGroupService uniqueGroupService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StyleService styleService;

    @Autowired
    private RelYsService relYsService;


    //全部列表
    public List<Parts> findPartsList(Parts parts) {
        List<Parts> partsList;
        List<Product> productList;
        if (parts.getStyleId() != null) {
            //取样式
            Style style = styleService.getStyle(parts.getStyleId());
            if (style == null) {
                throw new RFException("款式不存在");
            }
            if (style.getIsKt() == Style.IS_KT_FALSE) {
                throw new RFException("该款式无法下单");
            }
            if (style.getIsZs() != true) {
                throw new RFException("无匹配钻石");
            }
            parts.setOrderBy(DictUtils.findValueByTypeAndKey(Parts.DIAMOND_ORDER_BY, parts.getOrderByType()));
            //2018-9-17 12:31:28 全部钻石，不做过滤
            parts.setType(Parts.TYPE_DIAMOND);
            //2019年3月20日12:20:25 查询钻石，展示未锁定
            parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
            partsList = partsMapper.findPartsDiamondList(parts);
        } else {
            if (parts.getType() != null && parts.getType() == Parts.TYPE_DIAMOND) {
                parts.setType(Parts.TYPE_DIAMOND);
                partsList = partsMapper.findPartsList(parts);
            } else {
                //戒臂加花头列表(isShow=true)
                parts.setIsShow(Parts.IS_SHOW_TRUE);
                parts.setOrderBy("level asc");
                partsList = partsMapper.findPartsList(parts);
            }
        }
        return partsList;
    }

    /**
     * 查询钻石列表
     *
     * @param parts
     * @return
     */
    public List<Parts> findDiamondList(Parts parts) {
        parts.setType(Parts.TYPE_DIAMOND);
        parts.setOrderBy(DictUtils.findValueByTypeAndKey(Parts.DIAMOND_ORDER_BY, parts.getOrderByType()));
        //2019年3月20日12:20:25 查询钻石，展示未锁定
        parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
        List<Parts> partsList = partsMapper.findPartsDiamondList(parts);
        Product product = new Product();
        product.setStyleId(parts.getStyleId());
        product.setExSc(parts.getProductExSc());
        product.setExJbKd(parts.getProductJbkd());
        product.setJscz(parts.getProductJscz());
        product.setJsys(parts.getProductJsys());
        List<Product> productList = productMapper.findProductList(product);
        for (int i = 0; i < partsList.size(); i++) {
            Parts diamond = partsList.get(i);
            if (this.checkDiamondAndProductByFs(diamond, productList)) {
                diamond.setHasSku(Boolean.TRUE);
            } else {
                diamond.setHasSku(Boolean.FALSE);
            }
            this.setParam(diamond);
        }
        return partsList;
    }

    /**
     * 检查钻石颜色，是否在成品的颜色范围内
     *
     * @param diamond
     * @param productList
     * @return
     */
    public boolean checkDiamondAndProductByFs(Parts diamond, List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            //原：首先分数相同
            //fs.equals(product.getExFs())
            //现2019年3月26日21:37:03：产品规格范围内
            if (productService.checkDiamondFs(diamond.getExZsZl(), product)
                    && productService.checkDiamondYs(diamond.getExZsYs(), product.getExZsYs())
                    && productService.checkDiamondJd(diamond.getExZsJd(), product.getExZsJd())) {
                diamond.setProductKtCode(product.getKtCode());
                diamond.setProductPrice(product.getPrice());
                return true;
            }
        }
        return false;
    }

    /**
     * 检查钻石颜色，是否在成品的颜色范围内
     *
     * @param styleId
     * @param partsList
     * @return
     */
    public void checkHasSkuByStyleIdAndDiamond(Long styleId, List<Parts> partsList) {
        Product product = new Product();
        product.setStyleId(styleId);
        List<Product> productList = productMapper.findProductList(product);
        for (int i = 0; i < partsList.size(); i++) {
            Parts zs = partsList.get(i);
            List<Product> copyList = new ArrayList<>();
            copyList.addAll(productList);
            productService.dealDiamondYsAndJdAndFs(copyList, zs);
            if (copyList == null || copyList.size() == 0) {
                zs.setHasSku(Boolean.FALSE);
            } else {
                zs.setHasSku(Boolean.TRUE);
            }
            this.setParam(zs);
        }
    }

    /**
     * 定制时，选择一个组件，查询可以搭配的另一个组件
     *
     * @param parts
     * @return
     */
    public List<RelYs> findRelPartsList(Parts parts) {
        //APP“更多"功能
        List<RelYs> relYsList = relYsService.findRelYsList();
        return relYsList;
    }

    /**
     * 查询数据
     */
    public List<Parts> partsList(DataTablesRequest dataTablesRequest, Integer type) {
        Example example = new Example(Parts.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //编码
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (code != null && StringUtils.isNotBlank(code.getSearch().getValue())) {
            criteria.andLike("code", TableUtil.toFuzzySql(code.getSearch().getValue()));
        }
        //样式
        DataTablesRequest.Column ys = dataTablesRequest.getColumn("ys");
        if (ys != null && StringUtils.isNotBlank(ys.getSearch().getValue())) {
            criteria.andEqualTo("ys", ys.getSearch().getValue());
        }
        //所属组
        DataTablesRequest.Column groupType = dataTablesRequest.getColumn("groupType");
        if (groupType != null && StringUtils.isNotBlank(groupType.getSearch().getValue())) {
            criteria.andEqualTo("groupType", groupType.getSearch().getValue());
        }
        //状态（钻石）
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (status != null && StringUtils.isNotBlank(status.getSearch().getValue())) {
            criteria.andEqualTo("status", status.getSearch().getValue());
        }
        //其他
        criteria.andIsNull("expireDate")
                .andEqualTo("type", type);
        List<Parts> partsList = getMapper().selectByExample(example);
        for (int i = 0; i < partsList.size(); i++) {
            Parts parts = partsList.get(i);
            this.setParam(parts);
        }
        return partsList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Parts getParts(Long id) {
        Parts parts = new Parts();
        parts.setId(id);
        Parts par = partsMapper.getParts(parts);
        this.setParam(par);
        return par;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> partsMap, Integer type) {
        Parts parts = new Parts();
        String code = ConvertUtil.convert(partsMap.get("code"), String.class);
        String name = ConvertUtil.convert(partsMap.get("name"), String.class);
        String threeD30 = ConvertUtil.convert(partsMap.get("threeD30"), String.class);
        String threeD50 = ConvertUtil.convert(partsMap.get("threeD50"), String.class);
        String threeD70 = ConvertUtil.convert(partsMap.get("threeD70"), String.class);
        String threeD100 = ConvertUtil.convert(partsMap.get("threeD100"), String.class);
        Integer groupType = ConvertUtil.convert(partsMap.get("groupType"), Integer.class);
        String jbYsRecommend = ConvertUtil.convert(partsMap.get("jbYsRecommend"), String.class);
        //花头有最佳搭配的戒臂样式
        if (jbYsRecommend != null && !jbYsRecommend.equals("") && !this.checkYsList(jbYsRecommend)) {
            return ServiceResponse.error("样式格式不正确");
        }
        parts.setJbYsRecommend(jbYsRecommend);
        parts.setCode(code);
        parts.setName(name);
        parts.setThreeD30(threeD30);
        parts.setThreeD50(threeD50);
        parts.setThreeD70(threeD70);
        parts.setThreeD100(threeD100);
        parts.setGroupType(groupType);
        if (insertNotNull(parts) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> partsMap, Integer type) {
        Long id = ConvertUtil.convert(partsMap.get("id"), Long.class);
        Parts parts = selectByKey(id);
        String imgUrl = ConvertUtil.convert(partsMap.get("imgUrl"), String.class);
        String sideImgUrl = ConvertUtil.convert(partsMap.get("sideImgUrl"), String.class);
        String remark = ConvertUtil.convert(partsMap.get("remark"), String.class);
        parts.setRemark(remark);
        if (type != Parts.TYPE_DIAMOND) {
            //花头、戒臂imgUrl全部替换
            String level = ConvertUtil.convert(partsMap.get("level"), String.class);
            String threeD50 = ConvertUtil.convert(partsMap.get("threeD50"), String.class);
            String exQgHy = ConvertUtil.convert(partsMap.get("exQghy"), String.class);
            Integer groupType = ConvertUtil.convert(partsMap.get("groupType"), Integer.class);
            Boolean isRecommend = ConvertUtil.convert(partsMap.get("isRecommend"), Boolean.class);
            String jbYsRecommend = ConvertUtil.convert(partsMap.get("jbYsRecommend"), String.class);
            //花头有最佳搭配的戒臂样式
            if (jbYsRecommend != null && !jbYsRecommend.equals("") && !this.checkYsList(jbYsRecommend)) {
                return ServiceResponse.error("样式格式不正确");
            }
            if (level != null && !level.equals("") && !CheckUtil.isIntegerNumber(level)) {
                return ServiceResponse.error("前端展示排序请填写整数");
            }
            parts.setExQghy(exQgHy);
            parts.setIsRecommend(isRecommend);
            //缩略图，有更换的，同样式，同类别
            if (parts.getYs() != null) {
                Parts par = new Parts();
                par.setYs(parts.getYs());
                par.setType(type);
                List<Parts> partsList = partsMapper.findPartsList(par);
                for (int i = 0; i < partsList.size(); i++) {
                    Parts pp = partsList.get(i);
                    if (pp.getId() != parts.getId()) {
                        if (groupType != null && !parts.getGroupType().equals(groupType)) {
                            pp.setGroupType(groupType);
                        }
                        if (threeD50 != null && !threeD50.equals("") && !parts.getThreeD50().equals(threeD50)) {
                            pp.setThreeD50(threeD50);
                        }
                        if (imgUrl != null && !imgUrl.equals("") && !parts.getImgUrl().equals(imgUrl)) {
                            pp.setImgUrl(imgUrl);
                        }
                        if (sideImgUrl != null && !sideImgUrl.equals("") && !parts.getSideImgUrl().equals(sideImgUrl)) {
                            pp.setSideImgUrl(sideImgUrl);
                        }
                        //花头里的同样式，同步更新推荐的戒臂样式
                        if (jbYsRecommend != null && !jbYsRecommend.equals("") && !parts.getJbYsRecommend().equals(jbYsRecommend)) {
                            pp.setJbYsRecommend(jbYsRecommend);
                        }
                        if (level != null && !level.equals("") && !parts.getLevel().equals(level)) {
                            pp.setLevel(new Integer(level));
                        }
                        updateNotNull(pp);
                    }
                }
            }
            parts.setGroupType(groupType);
            parts.setThreeD50(threeD50);
            parts.setImgUrl(imgUrl);
            parts.setSideImgUrl(sideImgUrl);
            parts.setJbYsRecommend(jbYsRecommend);
            if (NumberUtils.isDigits(level)) {
                parts.setLevel(new Integer(level));
            }
        } else {
            //钻石
            if (imgUrl != null && !imgUrl.equals("") && !parts.getImgUrl().equals(imgUrl)) {
                parts.setImgUrl(imgUrl);
            }
            Integer status = ConvertUtil.convert(partsMap.get("status"), Integer.class);
            parts.setStatus(status);
        }
        if (updateNotNull(parts) != 1) {
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
        Parts parts = selectByKey(id);
        if (expireNotNull(parts) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 创建组件
     *
     * @param sapSku
     * @return
     */
    @Transactional
    public void createPartsBySap(SapSku sapSku) {
        Parts part = new Parts();
        part.setCode(sapSku.getCode());
        Parts localParts = partsMapper.getParts(part);
        if (localParts == null) {
            //先拦截数据库没有的，避免无用解析（生产要求备注）
            return;
        }
        int zl = new BigDecimal(Double.toString(NumberUtils.toDouble(sapSku.getJszl(), 0))).multiply(new BigDecimal(1000)).intValue();
        String fskd = "";
        String ys = "";
        String kk = "";
        if (sapSku.getFs() != null && !sapSku.getFs().equals("")) {
            String scyqbz = sapSku.getFs();
            List<String> scyqbzList = Arrays.asList(scyqbz.split("-"));
            if (scyqbzList != null && scyqbzList.size() == 2) {
                kk = scyqbzList.get(0);
                String ysfs = scyqbzList.get(1);
                ys = ysfs.substring(0, 1);
                if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_FLOWER_HEAD))) {
                    //样式分数继续拆分, 第一位是样式，分数100的需要三位
                    if (ysfs.length() >= 4 && ysfs.substring(1, 4).equals("100")) {
                        fskd = ysfs.substring(1, 4);
                    } else {
                        fskd = ysfs.substring(1, 3);
                    }
                } else if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_RING_ARM))) {
                    // 样式分数继续拆分, 第一位是样式, 戒臂宽度需要三位
                    if (ysfs.length() > 1 && ysfs.length() < 4) {
                        //如：A1-H1
                        fskd = ysfs.substring(1, ysfs.length());
                    } else if (ysfs.length() >= 4) {
                        //如：A1-H1.8
                        fskd = ysfs.substring(1, 4);
                    }
                }
            }
        }
        if (!localParts.getYs().equals(ys)) {
            //已存在的（样式不做修改，涉及的太多）
            return;
        }
        Parts sapParts = new Parts(sapSku.getCode(), sapSku.getName(), new Integer(sapSku.getType()), sapSku.getYy(),
                sapSku.getQghy(), sapSku.getJsCz(), sapSku.getJsYs(), zl, kk, ys, fskd, sapSku.getSc());

        BeanUtil.copyObject(sapParts, localParts, true);
        updateNotNull(localParts);

        if (localParts != null) {
            //只更新parts表，不做新增和样式关联修改 2018年10月31日17:08:19
            //更新完，style里(寓意+佩戴类别+金属颜色)修改code, product修改
//            styleService.updateStyleByPartsFromSap(sapSku, localParts);
        } else {
//            //只更新parts表，不做新增和样式关联修改 2018年10月31日17:08:19
//            //本地无,组件表存一条（花头/戒臂）
//            Parts pa = new Parts();
//            pa.setYs(sapSku.getYs());
//            pa.setType(sapParts.getType());
//            Parts checkYs = partsMapper.getParts(pa);
//            if (checkYs != null) {
//                return;
//            }
//            insertNotNull(sapParts);
//            //样式表，花头/戒臂组合，新生成款式
//            styleService.createStyleByPartsFromSap(sapSku);
        }
    }


    /**
     * 检查最佳搭配的戒臂list
     *
     * @param jbYsRecommend
     * @return
     */
    public Boolean checkYsList(String jbYsRecommend) {
        List<String> ysList = Arrays.asList(jbYsRecommend.split("#"));
        if (!uniqueGroupService.checkYs(ysList)) {
            return false;
        }
        return true;
    }

    /**
     * 设置参数（分组的名字）
     *
     * @param parts
     */
    public void setParam(Parts parts) {
        if (parts != null && parts.getGroupType() != null) {
            GroupInfo groupInfo = groupInfoMapper.selectByPrimaryKey(parts.getGroupType().longValue());
            String name = "";
            if (groupInfo != null) {
                name = groupInfo.getName();
            }
            parts.setGroupTypeName(name);
        }
        if (parts != null && parts.getType() == Parts.TYPE_DIAMOND && parts.getStatus() != null) {
            String statusName = DictUtils.findValueByTypeAndKey(Parts.DIAMOND_STATUS, parts.getStatus());
            parts.setStatusName(statusName);
        }
    }


    public List<Parts> partsList(Parts parts) {
        return partsMapper.partsList(parts);
    }


    /**
     * 钻石加锁/解锁，更新状态
     *
     * @param rows
     * @param status
     */
    public void updateDiamondLock(rows rows, Integer status) {
        Parts parts = new Parts();
        parts.setCode(rows.getProductid());
        parts.setCompany(rows.getCompany());
        parts.setType(Parts.TYPE_DIAMOND);
        if (!StringUtils.isEmpty(rows.getStoneZsh())) {
            parts.setExZsBh(rows.getStoneZsh());
        }
        if (!StringUtils.isEmpty(rows.getStoneOrderCharg())) {
            parts.setBatch(rows.getStoneOrderCharg());
        }
        Parts local = partsMapper.getParts(parts);
        if (local != null) {
            local.setLockStatus(status);
            updateNotNull(local);
        }
    }

    /**
     * 订单里，根据证书编号和石头编码查询
     *
     * @param zsbh
     * @return
     */
    public Parts getInfoWithZsbh(String zsbh) {
        Parts parts = new Parts();
        if (!StringUtils.isEmpty(zsbh)) {
            parts.setExZsBh(zsbh);
        }
        Parts p = partsMapper.getParts(parts);
        return p;
    }

    /**
     * 开发者接口，手动改加锁/解锁的状态
     * 开发者接口，手动改上架/下架的状态
     *
     * @param stoneZsh
     * @param stoneLockStatus   手动，加锁/解锁
     * @param stoneEnableStatus 手动，上架/下架
     * @return
     */
    public ServiceResponse updateStoneLockStatusByHand(String stoneZsh, Integer stoneLockStatus, Integer stoneEnableStatus) {
        Parts parts = new Parts();
        parts.setExZsBh(stoneZsh);
        Parts p = partsMapper.getParts(parts);
        if (p == null) {
            return ServiceResponse.error("石头不存在");
        }
        String messageLock = "解锁";
        if (stoneLockStatus.equals(3)) {
            messageLock = "加锁";
        }
        p.setLockStatus(stoneLockStatus);

        String messageEnable = "上架";
        if (stoneEnableStatus.equals(0)) {
            messageEnable = "下架";
        }
        p.setEnableStatus(stoneEnableStatus);

        int result = updateNotNull(p);
        if (result != 1) {
            return ServiceResponse.error("保存失败，请重试");
        }
        return ServiceResponse.succ(messageLock + "成功，lockStatus=" + stoneLockStatus +
                "||====||" + messageEnable + "成功，enableStatus=" + stoneEnableStatus);
    }

    /**
     * 开发者接口，手动添加石头
     *
     * @param stoneZsh
     * @param stoneZs
     * @param stoneZl
     * @param stoneYs
     * @param stoneJd
     * @param stoneQg
     * @param stonePg
     * @param stoneDc
     * @param stoneYg
     * @param stoneCompany
     * @return
     */
    public ServiceResponse createPartsByHand(String stoneZsh, String stoneZs, String stoneZl, String stoneYs, String stoneJd,
                                             String stoneQg, String stonePg, String stoneDc, String stoneYg, String stoneLocation, String stoneCompany) {
        Parts parts = new Parts();
        parts.setType(Parts.TYPE_DIAMOND);
        parts.setExZsBh(stoneZsh);
        Parts p = partsMapper.getParts(parts);
        if (p != null) {
            String message = "上架";
            if (p.getLockStatus().equals(3)) {
                message = "下架";
            }
            return ServiceResponse.error("石头已经存在，所属公司=" + p.getCompany() + ", 库存地=" + p.getLocation()
                    + ", 证书编号=" + p.getExZsBh()
                    + ", 克拉数=" + new BigDecimal(p.getExZsZl()).divide(new BigDecimal(1000)).toString()
                    + ", 颜色=" + p.getExZsYs() + ", 净度=" + p.getExZsJd() + ", 荧光=" + p.getExZsYg()
                    + ", 切工=" + p.getExZsQg() + ", 抛光=" + p.getExZsPg() + ", 对称=" + p.getExZsDc()
                    + ", 当前状态=" + message + ", lockstatus=" + p.getLockStatus());
        }
        Date today = new Date();
        parts.setType(1);
        parts.setPrice(0);
        parts.setDollarPrice(0);
        parts.setCode(stoneZsh);
        parts.setExZsZs(stoneZs.toUpperCase());
        parts.setExZsZl(new BigDecimal(stoneZl).multiply(new BigDecimal(1000)).intValue());
        parts.setExZsYs(stoneYs.toUpperCase());
        parts.setExZsJd(stoneJd.toUpperCase());
        parts.setExZsQg(stoneQg.toUpperCase());
        parts.setExZsPg(stonePg.toUpperCase());
        parts.setExZsDc(stoneDc.toUpperCase());
        parts.setExZsYg(stoneYg.toUpperCase());
        parts.setLocation(stoneLocation);
        parts.setCompany(stoneCompany);
        parts.setLockStatus(1);
        parts.setEnableStatus(1);
        parts.setEnableDate(today);
        parts.setEnableOverDate(today);
        int result = insertNotNull(parts);
        if (result != 1) {
            return ServiceResponse.error("添加失败，请重试");
        }
        return ServiceResponse.succ("添加成功");
    }

}
