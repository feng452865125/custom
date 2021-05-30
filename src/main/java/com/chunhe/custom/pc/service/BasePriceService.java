package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.XSSFUtil;
import com.chunhe.custom.pc.mapper.BasePriceMapper;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.PartsBig;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by white 2019年4月18日17:04:37
 * 4C标准基价
 */
@Service
public class BasePriceService extends BaseService<BasePrice> {

    @Autowired
    private BasePriceMapper basePriceMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private PartsBigService partsBigService;

    @Value("${locationInHk}")
    private String locationInHk;

    @Value("${locationInSz}")
    private String locationInSz;

    private static final String INDIA = "INDIA";

    /**
     * 查询数据
     */
    public List<BasePrice> basePriceList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(BasePrice.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //克拉数
        DataTablesRequest.Column klMinStr = dataTablesRequest.getColumn("klMin");
        if (StringUtils.isNotBlank(klMinStr.getSearch().getValue())) {
            int klMin = new BigDecimal(Double.toString(NumberUtils.toDouble(klMinStr.getSearch().getValue(), 0)))
                    .multiply(new BigDecimal(1000))
                    .intValue();
            criteria.andLessThanOrEqualTo("klMin", klMin);
            criteria.andGreaterThanOrEqualTo("klMax", klMin);
        }
        //颜色
        DataTablesRequest.Column ys = dataTablesRequest.getColumn("ys");
        if (StringUtils.isNotBlank(ys.getSearch().getValue())) {
            criteria.andEqualTo("ys", ys.getSearch().getValue());
        }
        //净度
        DataTablesRequest.Column jd = dataTablesRequest.getColumn("jd");
        if (StringUtils.isNotBlank(jd.getSearch().getValue())) {
            criteria.andEqualTo("jd", jd.getSearch().getValue());
        }
        //切工
        DataTablesRequest.Column qg = dataTablesRequest.getColumn("qg");
        if (StringUtils.isNotBlank(qg.getSearch().getValue())) {
            criteria.andEqualTo("qg", qg.getSearch().getValue());
        }
        //抛光
        DataTablesRequest.Column pg = dataTablesRequest.getColumn("pg");
        if (StringUtils.isNotBlank(pg.getSearch().getValue())) {
            criteria.andEqualTo("pg", pg.getSearch().getValue());
        }
        //对称
        DataTablesRequest.Column dc = dataTablesRequest.getColumn("dc");
        if (StringUtils.isNotBlank(dc.getSearch().getValue())) {
            criteria.andEqualTo("dc", dc.getSearch().getValue());
        }
        //荧光
        DataTablesRequest.Column yg = dataTablesRequest.getColumn("yg");
        if (StringUtils.isNotBlank(yg.getSearch().getValue())) {
            criteria.andEqualTo("yg", yg.getSearch().getValue());
        }
        //其他
        criteria.andIsNull("expireDate");
        List<BasePrice> basePriceList = getMapper().selectByExample(example);
        return basePriceList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public BasePrice getBasePrice(Long id) {
        BasePrice basePrice = new BasePrice();
        basePrice.setId(id);
        BasePrice bp = basePriceMapper.getBasePrice(basePrice);
        return bp;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> basePriceMap) {
        BasePrice basePrice = new BasePrice();
        String name = ConvertUtil.convert(basePriceMap.get("name"), String.class);
        String klMinStr = ConvertUtil.convert(basePriceMap.get("klMin"), String.class);
        String klMaxStr = ConvertUtil.convert(basePriceMap.get("klMax"), String.class);
        String ys = ConvertUtil.convert(basePriceMap.get("ys"), String.class);
        String jd = ConvertUtil.convert(basePriceMap.get("jd"), String.class);
        String qg = ConvertUtil.convert(basePriceMap.get("qg"), String.class);
        String pg = ConvertUtil.convert(basePriceMap.get("pg"), String.class);
        String dc = ConvertUtil.convert(basePriceMap.get("dc"), String.class);
        String yg = ConvertUtil.convert(basePriceMap.get("yg"), String.class);
        String zs = ConvertUtil.convert(basePriceMap.get("zs"), String.class);
        String remark = ConvertUtil.convert(basePriceMap.get("remark"), String.class);
        String priceStr = ConvertUtil.convert(basePriceMap.get("price"), String.class);
        int klMin = new BigDecimal(Double.toString(NumberUtils.toDouble(klMinStr, 0)))
                .multiply(new BigDecimal(1000))
                .intValue();
        int klMax = new BigDecimal(Double.toString(NumberUtils.toDouble(klMaxStr, 0)))
                .multiply(new BigDecimal(1000))
                .intValue();
        if (klMin >= klMax) {
            throw new RFException("克拉数不合理");
        }
        int price = new BigDecimal(Double.toString(NumberUtils.toDouble(priceStr, 0)))
                .multiply(new BigDecimal(100))
                .intValue();
        Boolean status = ConvertUtil.convert(basePriceMap.get("isEnabled"), Boolean.class);
        basePrice.setName(name);
        basePrice.setKlMin(klMin);
        basePrice.setKlMax(klMax);
        basePrice.setYs(ys);
        basePrice.setJd(jd);
        basePrice.setQg(qg);
        basePrice.setPg(pg);
        basePrice.setDc(dc);
        basePrice.setYg(yg);
        basePrice.setZs(zs);
        basePrice.setPrice(price);
        basePrice.setRemark(remark);
        basePrice.setStatus(status);

        if (insertNotNull(basePrice) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> basePriceMap) {
        Long id = ConvertUtil.convert(basePriceMap.get("id"), Long.class);
        BasePrice basePrice = selectByKey(id);
        String name = ConvertUtil.convert(basePriceMap.get("name"), String.class);
        String remark = ConvertUtil.convert(basePriceMap.get("remark"), String.class);
        String priceStr = ConvertUtil.convert(basePriceMap.get("price"), String.class);
        int price = new BigDecimal(Double.toString(NumberUtils.toDouble(priceStr, 0)))
                .multiply(new BigDecimal(100))
                .intValue();
        Boolean status = ConvertUtil.convert(basePriceMap.get("isEnabled"), Boolean.class);
        basePrice.setName(name);
        basePrice.setPrice(price);
        basePrice.setRemark(remark);
        basePrice.setStatus(status);

        if (updateNotNull(basePrice) != 1) {
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
        BasePrice basePrice = selectByKey(id);
        if (expireNotNull(basePrice) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }

    /**
     * 展示/不展示
     *
     * @param isEnabled 是否展示
     */
    @Transactional(readOnly = false)
    public Boolean enabled(Long id, Boolean isEnabled) {
        BasePrice basePrice = selectByKey(id);
        basePrice.setStatus(isEnabled);
        return updateNotNull(basePrice) == 1;
    }

    /**
     * 查询钻石列表（按4C标准，一个标准只展示一条，group by）
     * 除了3EX，就是3VG（剩余7种情况） 2020年8月15日19:46:50
     *
     * @param parts
     * @return
     */
    public List<BasePrice> findDiamondGroupList(Parts parts) {
        parts.setVg3(false);
        String type = "";
        String qg = parts.getExZsQg();
        String pg = parts.getExZsPg();
        String dc = parts.getExZsDc();
        if ((!StringUtils.isEmpty(qg) && qg.equals("EX"))
                && (!StringUtils.isEmpty(pg) && pg.equals("EX"))
                && (!StringUtils.isEmpty(dc) && dc.equals("EX"))) {
            //三个都是EX
            type = "3EX";
        } else if ((!StringUtils.isEmpty(qg) && qg.equals("VG"))
                || (!StringUtils.isEmpty(pg) && pg.equals("VG"))
                || (!StringUtils.isEmpty(dc) && dc.equals("VG"))) {
            //任意一个VG，处于3VG
            parts.setVg3(true);
            type = "3VG";
        } else {
            //其他查全部
            parts.setExZsQg(null);
            parts.setExZsPg(null);
            parts.setExZsDc(null);
            type = "all";
        }
        System.out.println("切工=" + qg + "，抛光=" + pg + "，对称=" + dc + "||APP=" + type);
        //精确搜索钻石克拉
        if (parts.getExZsZlMin() > 0) {
            Integer min = parts.getExZsZlMin();
            parts.setZsZlMin(min);
            String exMin = min.toString();
            parts.setExZsZlMin(Integer.valueOf(exMin.substring(0, exMin.length() - 2) + "00"));
        }
        if (parts.getExZsZlMax() > 0) {
            Integer max = parts.getExZsZlMax();
            parts.setZsZlMax(max);
            String exMax = max.toString();
            parts.setExZsZlMax(Integer.valueOf(exMax.substring(0, exMax.length() - 2) + "99"));
        }
        long startTime = System.currentTimeMillis() / 1000;
        List<BasePrice> basePriceList = basePriceMapper.findDiamondGroupList(parts);
        //成品表里，匹配是否存在主数据
        Product product = new Product();
        product.setStyleId(parts.getStyleId());
        product.setExJbKd(parts.getProductJbkd());
        product.setJscz(parts.getProductJscz());
        product.setJsys(parts.getProductJsys());
        product.setExSc(parts.getProductExSc());
        List<Product> productList = productMapper.findProductList(product);
        //回货时间
        String locationInHkDay = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_LOCATION_IN_HK, String.valueOf(locationInHk));
        String locationInSzDay = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_LOCATION_IN_SZ, String.valueOf(locationInSz));

        for (int i = 0; i < basePriceList.size(); i++) {
            BasePrice basePrice = basePriceList.get(i);
            //计算出的价格除100,获取最终的价格，然后根据最终的价格进行四舍五入,显示在页面;
            BigDecimal p1 = new BigDecimal(basePrice.getPrice());
            BigDecimal p2 = new BigDecimal(100);
            int price = p1.divide(p2, 2, BigDecimal.ROUND_HALF_UP).intValue();
            basePrice.setPrice(price);
            if (parts.getVg3()) {
                basePrice.setQg("VG");
                basePrice.setPg("VG");
                basePrice.setDc("VG");
            }
            basePrice.setProductPrice(0);
            //最有推荐SAP
            if (!StringUtils.isEmpty(basePrice.getStoneCompany())
                    && basePrice.getStoneCompany().toUpperCase().equals("KEERSAP")) {
                basePrice.setStoneRecommend(true);
            }
            //匹配石头
            if (!StringUtils.isEmpty(basePrice.getDiamondId().toString())) {
                basePrice.setHasDiamond(Boolean.TRUE);
            } else {
                basePrice.setHasDiamond(Boolean.FALSE);
            }
            if (!StringUtils.isEmpty(basePrice.getStoneLocation())
                    && basePrice.getStoneLocation().toUpperCase().equals("SZ")) {
                basePrice.setStoneLocationDay(locationInSzDay);
            } else {
                basePrice.setStoneLocationDay(locationInHkDay);
            }
            if (!basePrice.getStoneType().equals("big")) {
                //原逻辑匹配主数据（分数、颜色、净度）
                if (this.checkDiamondHasSkuPrice(basePrice, productList)) {
                    basePrice.setHasSku(Boolean.TRUE);
                } else {
                    basePrice.setHasSku(Boolean.FALSE);
                }
            } else {
                //大克拉匹配主数据（颜色、净度）
                if (this.checkDiamondHasSkuPricePartsBig(basePrice, productList)) {
                    basePrice.setHasSku(Boolean.TRUE);
                } else {
                    basePrice.setHasSku(Boolean.FALSE);
                }
            }
        }
        long endTime = System.currentTimeMillis() / 1000;
        System.out.println("钻石查询APP：" + (endTime - startTime));
        return basePriceList;
    }


    /**
     * 检查（分数，颜色，净度）匹配主数据
     *
     * @param basePrice
     * @param productList
     * @return
     */
    public boolean checkDiamondHasSkuPrice(BasePrice basePrice, List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);

            if (productService.checkDiamondFs(basePrice.getKlMin(), product)
                    && productService.checkDiamondYs(basePrice.getYs(), product.getExZsYs())
                    && productService.checkDiamondJd(basePrice.getJd(), product.getExZsJd())) {
                basePrice.setProductKtCode(product.getKtCode());
                basePrice.setProductCode(product.getCode());
                basePrice.setProductId(Integer.valueOf(product.getId().toString()));
                return true;
            }
            //2019-4-26 03:15:06
            if (basePrice.getKlMin() < 1000
                    && productService.checkDiamondFs(basePrice.getKlMin(), product)
                    && productService.checkDiamondYs(basePrice.getYs(), product.getExZsYs())
                    && product.getExZsJd().equals("SI")) {
                if (basePrice.getJd().equals("SI1") || basePrice.getJd().equals("SI2")
                        || basePrice.getJd().equals("FL") || basePrice.getJd().equals("IF")
                        || basePrice.getJd().equals("VVS1") || basePrice.getJd().equals("VVS2")
                        || basePrice.getJd().equals("VS1") || basePrice.getJd().equals("VS2")) {
                    basePrice.setProductKtCode(product.getKtCode());
                    basePrice.setProductCode(product.getCode());
                    basePrice.setProductId(Integer.valueOf(product.getId().toString()));
                    return true;
                }
            }
            if (basePrice.getKlMin() >= 1000
                    && productService.checkDiamondFs(basePrice.getKlMin(), product)
                    && productService.checkDiamondYs(basePrice.getYs(), product.getExZsYs())
                    && product.getExZsJd().equals("SI")) {
                if (basePrice.getJd().equals("SI1") || basePrice.getJd().equals("SI2")) {
                    basePrice.setProductKtCode(product.getKtCode());
                    basePrice.setProductCode(product.getCode());
                    basePrice.setProductId(Integer.valueOf(product.getId().toString()));
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 检查（颜色，净度）大克拉匹配主数据
     *
     * @param basePrice
     * @param productList
     * @return
     */
    public boolean checkDiamondHasSkuPricePartsBig(BasePrice basePrice, List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            if (productService.checkDiamondYs(basePrice.getYs(), product.getExZsYs())
                    && productService.checkDiamondJd(basePrice.getJd(), product.getExZsJd())) {
                basePrice.setProductKtCode(product.getKtCode());
                basePrice.setProductCode(product.getCode());
                basePrice.setProductId(Integer.valueOf(product.getId().toString()));
                return true;
            }
        }
        return false;
    }


    /**
     * 批量导入
     *
     * @return
     */
    @Transactional
    public ServiceResponse importBasePrice(MultipartFile multipartFile) {
        List<BasePrice> basePriceList = new ArrayList<>();
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
                    || !String.valueOf(sheet.getRow(0).getCell(0)).equals("*分数段")
                    || !String.valueOf(sheet.getRow(0).getCell(1)).equals("*颜色")
                    || !String.valueOf(sheet.getRow(0).getCell(2)).equals("*净度")
                    || !String.valueOf(sheet.getRow(0).getCell(7)).equals("*新系统克拉基价（人民币）")) {
                return ServiceResponse.error("模板使用错误，请重新下载导入模板");
            }
            // importBasePrice.xls 从第2行开始, 对应的行索引是1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String bpFs = XSSFUtil.getStringCellValue(row.getCell(0)); // *分数段
                String bpYs = XSSFUtil.getStringCellValue(row.getCell(1)); // *颜色
                String bpJd = XSSFUtil.getStringCellValue(row.getCell(2)); // *净度
                String bpQg = XSSFUtil.getStringCellValue(row.getCell(3)); // *切工
                String bpPg = XSSFUtil.getStringCellValue(row.getCell(4)); // *抛光
                String bpDc = XSSFUtil.getStringCellValue(row.getCell(5)); // *对称
                String bpYg = XSSFUtil.getStringCellValue(row.getCell(6)); // *荧光
                String bpJg = XSSFUtil.getStringCellValue(row.getCell(7)); // *价格（人民币）
                Object[] checkArr = {bpFs, bpYs, bpJd, bpQg, bpPg, bpDc, bpYg, bpJg};
                for (int i = 0; i < checkArr.length; i++) {
                    if (checkArr[i] == null || StringUtils.isEmpty(String.valueOf(checkArr[i]))) {
                        String msg = "第" + (rowIndex + 1) + "行，数据格式有误，导入失败";
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error(msg);
                    }
                }
                BasePrice basePrice = new BasePrice();
                basePrice.setYs(bpYs.toUpperCase());
                basePrice.setJd(bpJd.toUpperCase());
                basePrice.setQg(bpQg.toUpperCase());
                basePrice.setPg(bpPg.toUpperCase());
                basePrice.setDc(bpDc.toUpperCase());
                basePrice.setYg(bpYg.toUpperCase());
                basePrice.setKlMin(new BigDecimal(Double.toString(NumberUtils.toDouble(bpFs, 0))).multiply(new BigDecimal(1000)).intValue());
                basePrice.setPrice(new BigDecimal(Double.toString(Math.round(NumberUtils.toDouble(bpJg, 0)))).multiply(new BigDecimal(100)).intValue());
                basePriceList.add(basePrice);
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
        //与本地判断
        Date now = new Date();
        for (int i = 0; i < basePriceList.size(); i++) {
            BasePrice bp = basePriceList.get(i);
            BasePrice local = basePriceMapper.getBasePriceByProperty(bp);
            if (local == null) {
                bp.setKlMax(this.getKlMax(bp.getKlMin()));
                insertNotNull(bp);
            } else {
                local.setPrice(bp.getPrice());
                updateNotNull(local);
            }
        }
        return ServiceResponse.succ("导入成功");
    }


    public Integer getKlMax(Integer klMin) {
        int klMax = 0;
        switch (klMin) {
            case 300:
                klMax = 399;
                break;
            case 400:
                klMax = 499;
                break;
            case 500:
                klMax = 599;
                break;
            case 600:
                klMax = 699;
                break;
            case 700:
                klMax = 799;
                break;
            case 800:
                klMax = 899;
                break;
            case 900:
                klMax = 999;
                break;
            case 1000:
                klMax = 1499;
                break;
            case 1500:
                klMax = 1999;
                break;
            case 2000:
                klMax = 2999;
                break;
        }
        return klMax;
    }


}
