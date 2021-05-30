package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.pc.mapper.*;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.pc.mapper.*;
import com.chunhe.custom.pc.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月14日15:00:28
 */
@Service
public class StyleService extends BaseService<Style> {

    @Autowired
    private StyleMapper styleMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private JewelryTypeMapper jewelryTypeMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RelYsService relYsService;

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    //全部列表（APP不需要parts信息，单独列个方法）
    public List<Style> findStyleListApp(Style style) {
        List<Style> styleList;
        if (style != null && !StringUtils.isEmpty(style.getHtYs()) && !StringUtils.isEmpty(style.getHtYs())) {
            styleList = this.findStyleRecommendList(style.getHtYs(), style.getJbYs(), style.getSeries());
        } else {
            styleList = styleMapper.findStyleListApp(style);
        }
        return styleList;
    }

    //样式详情里的推荐列表
    public List<Style> findStyleRecommendList(String htYs, String jbYs, String series) {
        GroupInfo ht = new GroupInfo();
        ht.setType(GroupInfo.GROUP_INFO_TYPE_FLOWER_HEAD);
        ht.setYs(htYs);
        GroupInfo infoHt = groupInfoMapper.getGroupInfoYs(ht);

        GroupInfo jb = new GroupInfo();
        jb.setType(GroupInfo.GROUP_INFO_TYPE_RING_ARM);
        jb.setYs(jbYs);
        GroupInfo infoJb = groupInfoMapper.getGroupInfoYs(jb);

        String htGroupYs = htYs;
        if (infoHt != null) {
            htGroupYs = infoHt.getYs();
        }
        String jbGroupYs = jbYs;
        if (infoJb != null) {
            jbGroupYs = infoJb.getYs();
        }
        List<String> htYsList = Arrays.asList(htGroupYs.split("#"));
        List<String> jbYsList = Arrays.asList(jbGroupYs.split("#"));
        String ys = "";
        for (int i = 0; i < htYsList.size(); i++) {
            for (int j = 0; j < jbYsList.size(); j++) {
                if (htYsList.get(i).equals(htYs) && jbYsList.get(j).equals(jbYs)) {
                    continue;
                }
                ys = ys + "'" + htYsList.get(i) + jbYsList.get(j) + "'" + ",";
            }
        }
        if (StringUtils.isEmpty(ys)) {
            return new ArrayList<>();
        }
        ys = "(" + ys.substring(0, ys.length() - 1) + ")";
        Style style = new Style();
        style.setGroupYs(ys);
        style.setSeries(series);
        List<Style> styleList = styleMapper.findStyleRecommendList(style);
        return styleList;
    }


    //全部列表
    public List<Style> findStyleList(Style style) {
        List<Style> styleList = styleMapper.findStyleList(style);
        for (int i = 0; i < styleList.size(); i++) {
            this.setParam(styleList.get(i));
        }
        return styleList;
    }

    /**
     * 查询数据
     */
    public List<Style> styleList(DataTablesRequest dataTablesRequest) {
        Style style = new Style();
        //排序
        String orderBy = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orderBy)) {
            style.setOrderBy(orderBy);
        }
        //id
        DataTablesRequest.Column id = dataTablesRequest.getColumn("id");
        if (StringUtils.isNotBlank(id.getSearch().getValue())) {
            style.setId(new Long(id.getSearch().getValue()));
        }
        //名称
        DataTablesRequest.Column code = dataTablesRequest.getColumn("code");
        if (StringUtils.isNotBlank(code.getSearch().getValue())) {
            style.setCode(code.getSearch().getValue());
        }
        //类型
        DataTablesRequest.Column type = dataTablesRequest.getColumn("type");
        if (StringUtils.isNotBlank(type.getSearch().getValue())) {
            style.setType(new Integer(type.getSearch().getValue()));
        }
        //系列
        DataTablesRequest.Column series = dataTablesRequest.getColumn("series");
        if (StringUtils.isNotBlank(series.getSearch().getValue())) {
            style.setSeries(series.getSearch().getValue());
        }
        //花头
        DataTablesRequest.Column htYs = dataTablesRequest.getColumn("htYs");
        if (StringUtils.isNotBlank(htYs.getSearch().getValue())) {
            style.setHtYs(htYs.getSearch().getValue().toUpperCase());
        }
        //戒臂
        DataTablesRequest.Column jbYs = dataTablesRequest.getColumn("jbYs");
        if (StringUtils.isNotBlank(jbYs.getSearch().getValue())) {
            style.setJbYs(jbYs.getSearch().getValue().toUpperCase());
        }
        //多表关联，不用example
        List<Style> styleList = this.findStyleList(style);
        return styleList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public Style getStyle(Long id) {
        Style style = new Style();
        style.setId(id);
        Style st = styleMapper.getStyle(style);
        if (st == null) {
            throw new RFException("款式不存在");
        }
        //详情轮播图转换为list+工艺介绍转换为list
        st.setImgsUrlList(TransformStringListUtil.StringToList(st.getImgsUrl()));
        st.setIntroductionImgsList(TransformStringListUtil.StringToList(st.getIntroductionImgs()));
        this.setParam(st);
        return st;
    }

    public Style getStyleDetail(Style style) {
        Style st = styleMapper.getStyle(style);
        if (st == null) {
            throw new RFException("款式不存在");
        }
        this.setParam(st);
        //详情轮播图转换为list+工艺介绍转换为list
        st.setImgsUrlList(TransformStringListUtil.StringToList(st.getImgsUrl()));
        st.setIntroductionImgsList(TransformStringListUtil.StringToList(st.getIntroductionImgs()));
        //花头的分数范围
        st.setFsRange(this.getFsRange(st));
        //戒臂的宽度范围
        st.setKdRange(this.getKdRange(st));
        //戒臂
        Parts parts = new Parts();
        parts.setType(Parts.TYPE_RING_ARM);
        parts.setYs(st.getJbYs());
        parts.setIsShow(Parts.IS_SHOW_TRUE);
        Parts ringArm = partsMapper.getParts(parts);
        //戒臂宽度有特殊情况，从rel_ys表中取出，并set回去(开口，戒臂宽度)
        if (ringArm != null) {
            RelYs relYs = new RelYs();
            relYs.setHtYs(st.getHtYs());
            relYs.setJbYs(st.getJbYs());
            RelYs rel = relYsService.getRelYs(relYs);
            if (rel != null) {
                ringArm.setFsKd(rel.getKd());
                ringArm.setKk(rel.getKk());
            }
            st.setRingArm(ringArm);
        }
        //手寸，成品中任取一条，同款式手寸一致(情侣戒不一致）
        if (!st.getTypeName().equals(Style.STYLE_TYPE_COUPLE)) {
            Product product = new Product();
            product.setStyleId(st.getId());
            product.setGroupBy("ex_sc");
            product.setOrderBy("ex_sc + 0");
            List<Product> productList = productMapper.findProductList(product);
            List scList = new ArrayList();
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                if (productList.size() == 1) {
                    scList = productService.checkHandList(pro);
                } else {
                    String value = pro.getExSc();
//                    if(value.length() > 2) {
//                        scList = productService.checkHandList(pro);
//
//                    }else{
                    if (StringUtils.isEmpty(value)) {
                        break;
                    }
                    //时光，单手寸，处理#号
                    if (value.substring(value.length() - 1, value.length()).equals("#")) {
                        value = value.substring(0, value.length() - 1);
                    }
//                    }
                    productService.addToList(scList, value);
                }
            }
            st.setHandList(scList);
        }
        return st;
    }

    /**
     * 广告详情，绑定的styleList（后台维护）
     *
     * @param style
     * @return
     */
    public List<Style> findRelAdvertisementStyle(Style style) {
        List<Style> styleList = styleMapper.findRelAdvertisementStyle(style);
        for (int i = 0; i < styleList.size(); i++) {
            this.setParam(styleList.get(i));
        }
        return styleList;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> styleMap, MultipartFile multipartFile) {
        Style style = new Style();
        String code = ConvertUtil.convert(styleMap.get("code"), String.class);
        String name = ConvertUtil.convert(styleMap.get("name"), String.class);
        Integer type = ConvertUtil.convert(styleMap.get("type"), Integer.class);
        String htYs = ConvertUtil.convert(styleMap.get("htYs"), String.class);
        String jbYs = ConvertUtil.convert(styleMap.get("jbYs"), String.class);
//        String series = ConvertUtil.convert(styleMap.get("series"), String.class);

        List imgsUrlList = ConvertUtil.convert(styleMap.get("imgsUrl"), List.class);
        String imgMaxUrl = ConvertUtil.convert(styleMap.get("imgMaxUrl"), String.class);
        String imgUrl = ConvertUtil.convert(styleMap.get("imgUrl"), String.class);
        String rate45imgUrl = ConvertUtil.convert(styleMap.get("rate45imgUrl"), String.class);
        String fanUrl = ConvertUtil.convert(styleMap.get("fanUrl"), String.class);
        String videoUrl = ConvertUtil.convert(styleMap.get("videoUrl"), String.class);
        String wearUrl30 = ConvertUtil.convert(styleMap.get("wearUrl30"), String.class);
        String wearUrl50 = ConvertUtil.convert(styleMap.get("wearUrl50"), String.class);
        String wearUrl70 = ConvertUtil.convert(styleMap.get("wearUrl70"), String.class);
        String wearUrl100 = ConvertUtil.convert(styleMap.get("wearUrl100"), String.class);
        String moral = ConvertUtil.convert(styleMap.get("moral"), String.class);
        String features = ConvertUtil.convert(styleMap.get("features"), String.class);
        String story = ConvertUtil.convert(styleMap.get("story"), String.class);
        String meaning = ConvertUtil.convert(styleMap.get("meaning"), String.class);
        String remark = ConvertUtil.convert(styleMap.get("remark"), String.class);

        Boolean isEnable = ConvertUtil.convert(styleMap.get("enabled"), Boolean.class);
        Boolean isRecommend = ConvertUtil.convert(styleMap.get("isRecommend"), Boolean.class);
        Boolean isPrinting = ConvertUtil.convert(styleMap.get("isPrinting"), Boolean.class);
        Boolean isNxbs = ConvertUtil.convert(styleMap.get("isNxbs"), Boolean.class);
        Boolean isGq = ConvertUtil.convert(styleMap.get("isGq"), Boolean.class);

        //检查本地款式
        Style ss = new Style();
        ss.setCode(code);
        Style localStyle = styleMapper.getStyle(ss);
        if (localStyle != null) {
            return ServiceResponse.error(code + ", 该款式已存在");
        }
        style.setCode(code);
        style.setName(name);
        style.setType(type);
        style.setHtYs(htYs);
        style.setJbYs(jbYs);
        style.setSeries("UNIQUE");
        String imgsUrl = "";
        if (imgsUrlList.size() > 0) {
            imgsUrl = TransformStringListUtil.ListToString(imgsUrlList);
            style.setImgsUrl(imgsUrl);
        }
        style.setImgMaxUrl(imgMaxUrl);
        style.setImgUrl(imgUrl);
        style.setRate45imgUrl(rate45imgUrl);
        style.setFanUrl(fanUrl);
        style.setVideoUrl(videoUrl);
        style.setWearUrl30(wearUrl30);
        style.setWearUrl50(wearUrl50);
        style.setWearUrl70(wearUrl70);
        style.setWearUrl100(wearUrl100);
        style.setMoral(moral);
        style.setFeatures(features);
        style.setStory(story);
        style.setMeaning(meaning);
        style.setRemark(remark);
        style.setEnabled(isEnable);
        style.setIsRecommend(isRecommend);
        style.setIsPrinting(isPrinting);
        style.setIsNxbs(isNxbs);
        style.setIsGq(isGq);
        style.setIsZs(true);
        style.setIsKt(true);
        //绑定主数据product(文件导入)
        List<Product> productList = new ArrayList<>();
        //获取Excel
        try {
            Workbook workbook = null;
            File file = null;
            String fileName = multipartFile.getOriginalFilename();
            if (fileName.endsWith(PartsBig.XLS)) {
                //2003
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xls");
            } else if (fileName.endsWith(PartsBig.XLSX)) {
                //2007
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xlsx");
            } else {
                return ServiceResponse.error("不是Excel文件");
            }
            Sheet sheet = workbook.getSheetAt(0);
            //简单校验，是不是使用正确的导入模板
            if (sheet.getRow(0) == null
                    || !String.valueOf(sheet.getRow(0).getCell(0)).equals("物料编码*")
                    || !String.valueOf(sheet.getRow(0).getCell(1)).equals("客定码*")
                    || !String.valueOf(sheet.getRow(0).getCell(9)).equals("寓意*")
                    || !String.valueOf(sheet.getRow(0).getCell(12)).equals("金属颜色*")) {
                return ServiceResponse.error("模板使用错误，请重新下载导入模板");
            }
            // importProduct.xls 从第2行开始, 对应的行索引是1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String pCode = XSSFUtil.getStringCellValue(row.getCell(0)); // 物料编码*
                String pKtCode = XSSFUtil.getStringCellValue(row.getCell(1)); // 客定码*
                String pName = XSSFUtil.getStringCellValue(row.getCell(2)); // 产品名称*
                String pJbkd = XSSFUtil.getStringCellValue(row.getCell(3)); // 戒臂宽度*
                String pPrice = XSSFUtil.getStringCellValue(row.getCell(4)); // 空托价格*
                String pYs = XSSFUtil.getStringCellValue(row.getCell(5)); // 颜色分级范围*
                String pJd = XSSFUtil.getStringCellValue(row.getCell(6)); // 净度分级范围*
                String pSc = XSSFUtil.getStringCellValue(row.getCell(7)); // 手寸范围*
                String pFs = XSSFUtil.getStringCellValue(row.getCell(8)); // 规格分数范围*
                String pYy = XSSFUtil.getStringCellValue(row.getCell(9)); // 寓意*
                String pFsCount = XSSFUtil.getStringCellValue(row.getCell(10)); // 辅石总数
                String pJscz = XSSFUtil.getStringCellValue(row.getCell(11)); // 金属材质*
                String pJsys = XSSFUtil.getStringCellValue(row.getCell(12)); // 金属颜色*
                Object[] checkArr = {pCode, pKtCode, pName, pJbkd, pPrice, pYs, pJd, pSc, pFs, pYy, pJscz, pJsys};
                for (int i = 0; i < checkArr.length; i++) {
                    if (checkArr[i] == null || StringUtils.isEmpty(String.valueOf(checkArr[i]))) {
                        String msg = "第" + (rowIndex + 1) + "行，数据格式有误，导入失败";
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error(msg);
                    }
                }
                if (style.getId() == null) {
                    if (insertNotNull(style) != 1) {
                        return ServiceResponse.error("添加失败");
                    }
                }
                Product product = new Product();
                product.setCode(pCode);
                product.setKtCode(pKtCode);
                product.setName(pName);
                product.setExJbKd(pJbkd);
                product.setPrice(new BigDecimal(Double.toString(NumberUtils.toDouble(pPrice, 0))).multiply(new BigDecimal(100)).intValue());
                product.setExZsYs(pYs);
                product.setExZsJd(pJd);
                product.setExSc(this.dealWithSc(pSc));
                List<String> fsList = Arrays.asList(pFs.split("-"));
                if (fsList != null && fsList.size() == 2) {
                    product.setExDiamondFsMin(new BigDecimal(Double.toString(NumberUtils.toDouble(fsList.get(0), 0))).multiply(new BigDecimal(1000)).intValue());
                    product.setExDiamondFsMax(new BigDecimal(Double.toString(NumberUtils.toDouble(fsList.get(1), 0))).multiply(new BigDecimal(1000)).intValue());
                    product.setExFs(new BigDecimal(Double.toString(NumberUtils.toDouble(fsList.get(0), 0))).multiply(new BigDecimal(100)).toString());
                }
                product.setExYy(pYy);
                product.setExFsCount(NumberUtils.toInt(pFsCount, 0));
                product.setJscz(pJscz);
                product.setJsys(pJsys);
                product.setStyleId(style.getId());
                productList.add(product);
            }
            // 操作完毕后，XSSFWorkbook关闭
            workbook.close();
            // 操作完毕后，临时文件删除
            file.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RFException("出现异常，请重试");
        }
        //全部新增
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            productService.insertNotNull(product);
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> styleMap) {
        Long id = ConvertUtil.convert(styleMap.get("id"), Long.class);
        Style style = selectByKey(id);
        String name = ConvertUtil.convert(styleMap.get("name"), String.class);
        List imgsUrlList = ConvertUtil.convert(styleMap.get("imgsUrl"), List.class);
        String imgMaxUrl = ConvertUtil.convert(styleMap.get("imgMaxUrl"), String.class);
        String imgUrl = ConvertUtil.convert(styleMap.get("imgUrl"), String.class);
        String rate45imgUrl = ConvertUtil.convert(styleMap.get("rate45imgUrl"), String.class);
        String videoUrl = ConvertUtil.convert(styleMap.get("videoUrl"), String.class);
        String story = ConvertUtil.convert(styleMap.get("story"), String.class);
        String meaning = ConvertUtil.convert(styleMap.get("meaning"), String.class);
        String remark = ConvertUtil.convert(styleMap.get("remark"), String.class);
        String features = ConvertUtil.convert(styleMap.get("features"), String.class);

        String fanUrl = ConvertUtil.convert(styleMap.get("fanUrl"), String.class);
        String imgsUrl = "";
        if (imgsUrlList.size() > 0) {
            imgsUrl = TransformStringListUtil.ListToString(imgsUrlList);
            //List list = TransformStringListUtil.StringToList(imgsUrl);
            style.setImgsUrl(imgsUrl);
        }
        Boolean isEnable = ConvertUtil.convert(styleMap.get("enabled"), Boolean.class);
        Boolean isRecommend = ConvertUtil.convert(styleMap.get("isRecommend"), Boolean.class);
        Boolean isPrinting = ConvertUtil.convert(styleMap.get("isPrinting"), Boolean.class);
        Boolean isNxbs = ConvertUtil.convert(styleMap.get("isNxbs"), Boolean.class);
        Boolean isGq = ConvertUtil.convert(styleMap.get("isGq"), Boolean.class);

        this.setParam(style);
        if (style.getTypeName().equals(Style.STYLE_TYPE_COUPLE)) {
            //情侣戒
            productService.updateProductByStyleMap(styleMap);
        } else if (style.getSeries().equals("大克重黄金手镯")
                || style.getSeries().equals("大分数钻石")) {
            String gdSc = ConvertUtil.convert(styleMap.get("gdSc"), String.class);
            String gdJsys = ConvertUtil.convert(styleMap.get("gdJsys"), String.class);
            String gdYsfw = ConvertUtil.convert(styleMap.get("gdYsfw"), String.class);
            String gdJdfw = ConvertUtil.convert(styleMap.get("gdJdfw"), String.class);
            String gdPriceMin = ConvertUtil.convert(styleMap.get("gdPriceMin"), String.class);
            String gdPriceMax = ConvertUtil.convert(styleMap.get("gdPriceMax"), String.class);
            String gdZlAll = ConvertUtil.convert(styleMap.get("gdZlAll"), String.class);
            String gdZlMin = ConvertUtil.convert(styleMap.get("gdZlMin"), String.class);
            String gdZlMax = ConvertUtil.convert(styleMap.get("gdZlAll"), String.class);
            style.setGdSc(gdSc);
            style.setGdJsys(gdJsys);
            style.setGdYsfw(gdYsfw);
            style.setGdJdfw(gdJdfw);
            style.setGdPriceMin(gdPriceMin);
            style.setGdPriceMax(gdPriceMax);
            style.setGdZlAll(gdZlAll);
            style.setGdZlMin(gdZlMin);
            style.setGdZlMax(gdZlMax);
        } else {
            //不是情侣戒
            String wearUrl30 = ConvertUtil.convert(styleMap.get("wearUrl30"), String.class);
            String wearUrl50 = ConvertUtil.convert(styleMap.get("wearUrl50"), String.class);
            String wearUrl70 = ConvertUtil.convert(styleMap.get("wearUrl70"), String.class);
            String wearUrl100 = ConvertUtil.convert(styleMap.get("wearUrl100"), String.class);
            style.setWearUrl30(wearUrl30);
            style.setWearUrl50(wearUrl50);
            style.setWearUrl70(wearUrl70);
            style.setWearUrl100(wearUrl100);
        }
        style.setName(name);
        style.setImgMaxUrl(imgMaxUrl);
        style.setImgUrl(imgUrl);
        style.setRate45imgUrl(rate45imgUrl);
        style.setVideoUrl(videoUrl);
        style.setRemark(remark);
        style.setStory(story);
        style.setMeaning(meaning);
        style.setFeatures(features);

        style.setFanUrl(fanUrl);
        style.setEnabled(isEnable);
        style.setIsRecommend(isRecommend);
        style.setIsPrinting(isPrinting);
        style.setIsNxbs(isNxbs);
        style.setIsGq(isGq);
        if (updateNotNull(style) != 1) {
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
        Style style = selectByKey(id);
        if (expireNotNull(style) != 1) {
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
        Style style = selectByKey(id);
        style.setEnabled(isEnabled);
        return updateNotNull(style) == 1;
    }

    /**
     * 生成样式
     *
     * @param sapSku
     * @return
     */
    @Transactional
    public void createStyleByPartsFromSap(SapSku sapSku) {
        JewelryType jewelryType = new JewelryType();
        jewelryType.setName(sapSku.getPdLx());
        JewelryType jewelry = jewelryTypeMapper.getJewelryType(jewelryType);

        Parts part = new Parts();
        if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_FLOWER_HEAD))) {
            //该花头样式，与所有戒臂样式，组成新款式
            part.setType(Parts.TYPE_RING_ARM);
        } else if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_RING_ARM))) {
            //该戒臂样式，与所有花头样式，组成新款式
            part.setType(Parts.TYPE_FLOWER_HEAD);
        }
        part.setGroupBy("ys");
        List<Parts> partsList = partsMapper.findPartsListByYsGroup(part);
        for (int i = 0; i < partsList.size(); i++) {
            Parts parts = partsList.get(i);
            Boolean isKt = sapSku.getIsKt().equals("是") ? Boolean.TRUE : Boolean.FALSE;
            Boolean isZs = sapSku.getIsZs().equals("是") ? Boolean.TRUE : Boolean.FALSE;
            Style style = new Style(sapSku.getYy() + sapSku.getPdLx() + sapSku.getJsYs(), sapSku.getName(), jewelry.getId().intValue(),
                    sapSku.getYy(), sapSku.getGs(), sapSku.getSeries(), sapSku.getQghy(), sapSku.getYs(),
                    parts.getYs(), sapSku.getKstd(), isKt, isZs);
            insertNotNull(style);
        }
    }

    /**
     * 更新样式(寓意+佩戴类别+金属颜色)修改code
     *
     * @param sapSku
     * @return
     */
    @Transactional
    public void updateStyleByPartsFromSap(SapSku sapSku, Parts localParts) {
        String htYy = "";
        String jbYy = "";
        String allYy = "";
        Style sapStyle = new Style();
        sapStyle.setSeries(sapSku.getSeries());
        sapStyle.setType(2);
        Style style = new Style();
        if (sapSku.getType().equals(SapSku.TYPE_FLOWER_HEAD)) {
            style.setHtYs(sapSku.getYs());
        } else if (sapSku.getType().equals(SapSku.TYPE_RING_ARM)) {
            style.setJbYs(sapSku.getYs());
        }
        //包含该样式（花头/戒臂）的款式list
        List<Style> styleList = styleMapper.findStyleListApp(style);
        for (int i = 0; i < styleList.size(); i++) {
            Style localStyle = styleList.get(i);
            if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_FLOWER_HEAD))) {
                Parts parts = new Parts();
                parts.setYs(localStyle.getJbYs());
                parts.setType(Parts.TYPE_RING_ARM);
                Parts jb = partsMapper.getParts(parts);//拿戒臂的寓意
                htYy = checkYy(sapSku.getYy());
                jbYy = checkYy(jb.getExYy());
                allYy = sapSku.getYy() + jb.getExYy();
            } else if (sapSku.getType().equals(String.valueOf(SapSku.TYPE_RING_ARM))) {
                Parts parts = new Parts();
                parts.setYs(localStyle.getHtYs());
                parts.setType(Parts.TYPE_FLOWER_HEAD);
                Parts ht = partsMapper.getParts(parts);//拿戒臂的寓意
                htYy = checkYy(ht.getExYy());
                jbYy = checkYy(sapSku.getYy());
                allYy = ht.getExYy() + sapSku.getYy();
            }
            sapStyle.setCode(htYy + jbYy + "戒指" + sapSku.getJsYs());
            sapStyle.setMoral(htYy + jbYy);
            BeanUtil.copyObject(sapStyle, localStyle, true);
            updateNotNull(localStyle);
            //该款式对应的成品
            productService.updateProductByStyleFromSap(sapSku, localStyle, allYy);
        }
    }

    /**
     * product新增sku
     *
     * @param sapSku
     * @param localProduct
     */
    @Transactional
    public void updateStyleByProductFromSap(SapSku sapSku, Product localProduct) {
        if (sapSku.getPdLx().equals("") || sapSku.getJsYs().equals("") || sapSku.getYy().equals("")) {
            return;
        }
        JewelryType jewelryType = new JewelryType();
        jewelryType.setName(sapSku.getPdLx());
        JewelryType jewelry = jewelryTypeMapper.getJewelryType(jewelryType);

        Boolean isKt = sapSku.getIsKt().equals("是") ? Boolean.TRUE : Boolean.FALSE;
        Boolean isZs = sapSku.getIsZs().equals("是") ? Boolean.TRUE : Boolean.FALSE;
        Style sapStyle = new Style("", sapSku.getName(), jewelry.getId().intValue(),
                sapSku.getYy(), sapSku.getGs(), sapSku.getSeries(), sapSku.getQghy(), "",
                "", sapSku.getKstd(), isKt, isZs);
        String yy = checkYy(sapSku.getYy());
        sapStyle.setCode(yy + sapSku.getPdLx() + sapSku.getJsYs());
        sapStyle.setMoral(yy);
        Style localStyle = new Style();
        if (localProduct.getStyleId() == null) {
            //不存在，用code查，寓意+佩戴类别+金属颜色+
            Style stCode = new Style();
            stCode.setCode(sapStyle.getCode());
            localStyle = styleMapper.getStyle(stCode);
        } else {
            //存在，用styleId
            Style stId = new Style();
            stId.setId(localProduct.getStyleId());
            localStyle = styleMapper.getStyle(stId);
        }
        if (localStyle == null) {
            insertNotNull(sapStyle);
            localProduct.setStyleId(sapStyle.getId());
        } else {
            BeanUtil.copyObject(sapStyle, localStyle, true);
            updateNotNull(localStyle);
            localProduct.setStyleId(localStyle.getId());
        }
        productService.updateNotNull(localProduct);
    }

    /**
     * 寓意处理，部分戒臂寓意，带个字母，款式里不需要，情侣戒寓意带有男，女，款式里不需要
     *
     * @param str
     * @return
     */
    public String checkYy(String str) {
        String yy;
        if (str == null || str.equals("")) {
            return "";
        }
        if (CheckUtil.isEnglishLetter(str.substring(str.length() - 1))
                || CheckUtil.isNumber(str.substring(str.length() - 1))
                || str.substring(str.length() - 1).equals("男")
                || str.substring(str.length() - 1).equals("女")) {
            //款式里寓意，带L,R,S,M这种英文字母，去掉最后一位
            //款式里寓意，带数字，带男，带女，去掉最后一位
            yy = str.substring(0, str.length() - 1);
        } else {
            yy = str;
        }
        return yy;
    }

    /**
     * 设置参数（类型的名字）
     *
     * @param style
     */
    public void setParam(Style style) {
        if (style != null && style.getType() != null) {
            JewelryType jewelryType = jewelryTypeMapper.selectByPrimaryKey(style.getType().longValue());
            String name = "";
            if (jewelryType != null) {
                name = jewelryType.getName();
            }
            style.setTypeName(name);
        }
    }

    /**
     * 分数范围
     *
     * @param style
     * @return
     */
    public String getFsRange(Style style) {
        List<Product> list = new ArrayList<>();
        Product product = new Product();
        if (!StringUtils.isEmpty(style.getHtYs()) && !StringUtils.isEmpty(style.getJbYs())) {
            //unique系列，有花头，有戒臂
            product.setStyleId(style.getId());
            product.setGroupBy("p.ex_fs");
            product.setOrderBy("p.ex_fs + 0 asc");
            list = productMapper.findProductListByStyle(product);
        }
        //字符串先转int，再排序
        String range = "";
        for (int i = 0; i < list.size(); i++) {
            range = range + list.get(i).getExFs();
            if (i != list.size() - 1) {
                range = range + "、";
            }
        }
        return range;
    }

    /**
     * 戒臂宽度范围（戒臂中取）
     *
     * @param style
     * @return
     */
    public String getKdRange(Style style) {
        List<Product> list = new ArrayList<>();
        Product product = new Product();
        if (!StringUtils.isEmpty(style.getHtYs()) && !StringUtils.isEmpty(style.getJbYs())) {
            product.setStyleId(style.getId());
            product.setGroupBy("p.ex_jb_kd");
            product.setOrderBy("p.ex_jb_kd + 0 asc");
            list = productMapper.findProductListByStyle(product);
        }
        //字符串先转int，再排序
        String range = "";
        for (int i = 0; i < list.size(); i++) {
            range = range + list.get(i).getExJbKd();
            if (i != list.size() - 1) {
                range = range + "、";
            }
        }
        return range;
    }

    public void dealWithCoupleStyle(Style style) {
        if (style.getTypeName().equals(Style.STYLE_TYPE_COUPLE)) {
            //是情侣戒，查询男戒，查询女戒
            Product product = new Product();
            product.setStyleId(style.getId());
            product.setGroupBy("type");
            List<Product> productList = productService.findProductList(product);
            for (int i = 0; i < productList.size(); i++) {
                Product pro = productList.get(i);
                if (pro.getType() == Product.TYPE_MALE) {
                    style.setImgUrlBoy(pro.getImgMaxUrl());
                    style.setWearUrlBoy30(pro.getWearUrl30());
                    style.setWearUrlBoy50(pro.getWearUrl50());
                    style.setWearUrlBoy70(pro.getWearUrl70());
                    style.setWearUrlBoy100(pro.getWearUrl100());
                } else if (pro.getType() == Product.TYPE_FEMALE) {
                    style.setImgUrlGirl(pro.getImgMaxUrl());
                    style.setWearUrlGirl30(pro.getWearUrl30());
                    style.setWearUrlGirl50(pro.getWearUrl50());
                    style.setWearUrlGirl70(pro.getWearUrl70());
                    style.setWearUrlGirl100(pro.getWearUrl100());
                }
            }
        }
    }

    /**
     * 开启和关闭样式
     *
     * @param series
     * @param isEnabled
     * @return
     */
    public int openOnOff(String series, Boolean isEnabled) {
        Style style = new Style();
        style.setSeries(series);
        style.setEnabled(isEnabled);
        return styleMapper.openOnOff(style);
    }


    /**
     * 获取下拉列表
     *
     * @param name
     * @param series
     * @return
     */
    public List<Style> dropDownBox(String name, String series) {
        return styleMapper.dropDownBox(name, series);
    }

    //手寸处理，10-16#   10#11#12#13#14#15#16#
    public String dealWithSc(String scRange) {
        String sc = "";
        List<String> scList = Arrays.asList(scRange.split("-"));
        if (scList.size() == 1) {
            sc = scRange;
        } else if (scList.size() == 2) {
            int min = 0;
            int max = 0;
            for (int i = 0; i < scList.size(); i++) {
                List<String> list = Arrays.asList(scList.get(i).split("#"));
                String value = scList.get(0);
                if (!CheckUtil.isNumber(value)) {
                    return Boolean.FALSE + "##手寸值sc不正确";
                }
                if (i != scList.size() - 1) {
                    min = new Integer(list.get(0));
                } else {
                    max = new Integer(list.get(0));
                }
            }
            if (min >= max) {
                return sc;
            }
            for (int j = min; j <= max; j++) {
                sc = sc + j + "#";
            }
        }
        return sc;
    }

}
