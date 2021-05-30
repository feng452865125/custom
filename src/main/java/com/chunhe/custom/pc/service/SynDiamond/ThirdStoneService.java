package com.chunhe.custom.pc.service.SynDiamond;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.MyTool;
import com.chunhe.custom.framework.utils.XSSFUtil;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.mapper.ThirdSupplierMapper;
import com.chunhe.custom.pc.model.Parts;
import com.chunhe.custom.pc.model.PartsBig;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.pc.service.ThirdSupplierCacheService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 第三方同步的石头维护
 * white 2020年9月13日11:57:37
 */
@Service
public class ThirdStoneService extends BaseService<Parts> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private ThirdSupplierMapper thirdSupplierMapper;

    @Autowired
    private ThirdSupplierCacheService thirdSupplierCacheService;

    //公司列表
    public List findThirdStoneCompanyList() {
        List companyList = thirdSupplierMapper.findThirdStoneCompanyList();
        return companyList;
    }

    /**
     * 查询数据
     */
    public List<Parts> thirdStoneList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(Parts.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        Boolean isToday = true;
        //公司
        DataTablesRequest.Column company = dataTablesRequest.getColumn("company");
        if (StringUtils.isNotBlank(company.getSearch().getValue())) {
            criteria.andEqualTo("company", company.getSearch().getValue());
            isToday = false;
        }
        //证书号
        DataTablesRequest.Column exZsBh = dataTablesRequest.getColumn("exZsBh");
        if (StringUtils.isNotBlank(exZsBh.getSearch().getValue())) {
            criteria.andEqualTo("exZsBh", exZsBh.getSearch().getValue());
            isToday = false;
        }
        //克拉数min
        DataTablesRequest.Column thirdStoneZlMin = dataTablesRequest.getColumn("thirdStoneZlMin");
        if (StringUtils.isNotBlank(thirdStoneZlMin.getSearch().getValue())) {
            int minZl = new BigDecimal(thirdStoneZlMin.getSearch().getValue()).multiply(new BigDecimal(1000)).intValue();
            criteria.andGreaterThanOrEqualTo("exZsZl", minZl);
            isToday = false;
        }
        //克拉数max
        DataTablesRequest.Column thirdStoneZlMax = dataTablesRequest.getColumn("thirdStoneZlMax");
        if (StringUtils.isNotBlank(thirdStoneZlMax.getSearch().getValue())) {
            int maxZl = new BigDecimal(thirdStoneZlMax.getSearch().getValue()).multiply(new BigDecimal(1000)).intValue();
            criteria.andLessThanOrEqualTo("exZsZl", maxZl);
            isToday = false;
        }
        //颜色
        DataTablesRequest.Column exZsYs = dataTablesRequest.getColumn("exZsYs");
        if (StringUtils.isNotBlank(exZsYs.getSearch().getValue())) {
            criteria.andEqualTo("exZsYs", exZsYs.getSearch().getValue());
            isToday = false;
        }
        //净度
        DataTablesRequest.Column exZsJd = dataTablesRequest.getColumn("exZsJd");
        if (StringUtils.isNotBlank(exZsJd.getSearch().getValue())) {
            criteria.andEqualTo("exZsJd", exZsJd.getSearch().getValue());
            isToday = false;
        }
        //切工
        DataTablesRequest.Column exZsQg = dataTablesRequest.getColumn("exZsQg");
        if (StringUtils.isNotBlank(exZsQg.getSearch().getValue())) {
            criteria.andEqualTo("exZsQg", exZsQg.getSearch().getValue());
            isToday = false;
        }
        //抛光
        DataTablesRequest.Column exZsPg = dataTablesRequest.getColumn("exZsPg");
        if (StringUtils.isNotBlank(exZsPg.getSearch().getValue())) {
            criteria.andEqualTo("exZsPg", exZsPg.getSearch().getValue());
            isToday = false;
        }
        //对称
        DataTablesRequest.Column exZsDc = dataTablesRequest.getColumn("exZsDc");
        if (StringUtils.isNotBlank(exZsDc.getSearch().getValue())) {
            criteria.andEqualTo("exZsDc", exZsDc.getSearch().getValue());
            isToday = false;
        }
        //荧光
        DataTablesRequest.Column exZsYg = dataTablesRequest.getColumn("exZsYg");
        if (StringUtils.isNotBlank(exZsYg.getSearch().getValue())) {
            criteria.andEqualTo("exZsYg", exZsYg.getSearch().getValue());
            isToday = false;
        }
        //同步开始时间
        DataTablesRequest.Column createDate = dataTablesRequest.getColumn("createDate");
        if (StringUtils.isNotBlank(createDate.getSearch().getValue())) {
            String start = DateUtil.formatDate(DateUtil.getDateStart(DateUtil.parseDate(createDate.getSearch().getValue())), DateUtil.FORMAT_DATE_TIME);
            String end = DateUtil.formatDate(DateUtil.getDateEnd(DateUtil.parseDate(createDate.getSearch().getValue())), DateUtil.FORMAT_DATE_TIME);
            criteria.andBetween("createDate", start, end);
            isToday = false;
        }
        //如果不带参数查询，则查今天
        if (isToday) {
            String start = DateUtil.formatDate(DateUtil.getDateStart(), DateUtil.FORMAT_DATE_TIME);
            String end = DateUtil.formatDate(DateUtil.getDateEnd(), DateUtil.FORMAT_DATE_TIME);
            criteria.andBetween("createDate", start, end);
        }
        //其他
        criteria.andIsNull("expireDate");
        criteria.andEqualTo("lockStatus", 1);
        List<Parts> thirdStoneList = getMapper().selectByExample(example);
        return thirdStoneList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public Parts getThirdStone(Long id) {
        Parts thirdStone = new Parts();
        thirdStone.setId(id);
        Parts stone = partsMapper.getThirdStone(thirdStone);
        return stone;
    }


    /**
     * 上架，下架
     *
     * @param id
     * @return
     */
    public ServiceResponse updateEnableStatus(Long id) {
        Parts thirdStone = selectByKey(id);
        String message = "";
        Date date = DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATE_TIME));
        if (thirdStone != null) {
            if (thirdStone.getEnableStatus().equals(1)) {
                thirdStone.setEnableStatus(0);
                message = "下架";
            } else {
                thirdStone.setEnableStatus(1);
                message = "上架";
            }
            //操作时间
            thirdStone.setEnableDate(date);
            thirdStone.setEnableOverDate(date);
            super.updateNotNull(thirdStone);
        }
        return ServiceResponse.succ(message + "成功");
    }


    /**
     * 批量导入，上架/下架
     *
     * @return
     */
    public ServiceResponse importPartsEnable(MultipartFile multipartFile, Integer enableType) {
        int result = 0;
        String message = "";
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
            String zsbhArr = "";
            //证书号在第一列, 对应的行索引是0
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                //getCell(0)，为序号，无意义
                String zsbh = XSSFUtil.getStringCellValue(row.getCell(0)); // 证书号在第一列
                if (StringUtils.isEmpty(zsbh)) {
                    continue;
                }
                zsbhArr = zsbhArr + "'" + zsbh + "'" + ",";
            }
            //去掉最后一位的","
            zsbhArr = zsbhArr.substring(0, zsbhArr.length() - 1);
            if (StringUtils.isEmpty(zsbhArr)) {
                return ServiceResponse.error("证书号为空，导入失败");
            }
            //1上架, 0下架
            if (enableType.equals(0)) {
                message = "下架";
                result = partsMapper.enablePartsByImport(zsbhArr, 0);
            } else if (enableType.equals(1)) {
                message = "上架";
                result = partsMapper.enablePartsByImport(zsbhArr, 1);
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
        return ServiceResponse.succ("导入成功，批量" + message + result + "颗");
    }


    /**
     * 批量导入，默认上架
     *
     * @return
     */
    @Transactional
    public ServiceResponse importPartsStone(MultipartFile multipartFile) {
        int insertResult = 0;
        int updateResult = 0;
        String message = "";
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
            Date today = new Date();
            Sheet sheet = workbook.getSheetAt(0);
            //简单校验，是不是使用正确的导入模板
            if (sheet.getRow(0) == null
                    || sheet.getRow(0).getCell(0) == null
                    || sheet.getRow(0).getCell(2) == null
                    || !String.valueOf(sheet.getRow(0).getCell(0)).equals("*供应商缩写")
                    || !String.valueOf(sheet.getRow(0).getCell(2)).equals("*GIA证书号")
                    || !String.valueOf(sheet.getRow(0).getCell(10)).equals("*库存地")
                    || !String.valueOf(sheet.getRow(0).getCell(11)).equals("*采购价（人民币）")) {
                return ServiceResponse.error("模板使用错误，请重新下载导入模板");
            }
            // importStone.xls 从第2行开始, 对应的行索引是1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String zsGys = XSSFUtil.getStringCellValue(row.getCell(0)); // 供应商
                String zsBm = XSSFUtil.getStringCellValue(row.getCell(1)); // 石头编码或库存码
                String zsZsh = XSSFUtil.getStringCellValue(row.getCell(2)); // 证书编号
                String zsZl = XSSFUtil.getStringCellValue(row.getCell(3)); // 石重
                String zsYs = XSSFUtil.getStringCellValue(row.getCell(4)); // 颜色
                String zsJd = XSSFUtil.getStringCellValue(row.getCell(5)); // 净度
                String zsQg = XSSFUtil.getStringCellValue(row.getCell(6)); // 切工
                String zsPg = XSSFUtil.getStringCellValue(row.getCell(7)); // 抛光
                String zsDc = XSSFUtil.getStringCellValue(row.getCell(8)); // 对称
                String zsYg = XSSFUtil.getStringCellValue(row.getCell(9)); // 荧光
                String zsKcd = XSSFUtil.getStringCellValue(row.getCell(10)); // 库存地
                String zsCgj = XSSFUtil.getStringCellValue(row.getCell(11)); // 采购价
                Object[] checkArr = {zsGys, zsZsh, zsZl, zsYs, zsJd, zsQg, zsPg, zsDc, zsYg, zsKcd, zsCgj};
                for (int i = 0; i < checkArr.length; i++) {
                    if (checkArr[i] == null || StringUtils.isEmpty(String.valueOf(checkArr[i]))) {
                        String msg = "第" + (rowIndex + 1) + "行，数据格式有误，导入失败";
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error(msg);
                    }
                }
                //供应商如果不存在，新建一个
                ThirdSupplier ts = thirdSupplierCacheService.getCacheThirdSupplierManager(zsGys.toUpperCase());
                if (ts == null || ts.getId() == null) {
                    ts = new ThirdSupplier();
                    ts.setName(zsGys.toUpperCase());
                    ts.setShortName(zsGys.toUpperCase());
                    ts.setScore(100);
                    ts.setStatus(true);
                    ts.setCreateDate(today);
                    thirdSupplierCacheService.setCacheThirdSupplierManager(ts);//存进cache
                }
                Parts localParts = new Parts();
                localParts.setType(Parts.TYPE_DIAMOND);
                localParts.setCompany(zsGys);
                localParts.setExZsBh(zsZsh);
                Parts parts = partsMapper.getParts(localParts);
                if (parts == null) {
                    parts = new Parts();
                }
                parts.setCompany(zsGys);
                if (StringUtils.isEmpty(zsBm)) {
                    //仓库码没有，暂时用证书号代替
                    zsBm = zsZsh;
                }
                parts.setCode(zsBm);
                String kcd = "SZ";
                parts.setLocation(kcd);
                parts.setType(Parts.TYPE_DIAMOND);
                parts.setStatus(1);
                parts.setPrice(0);
                parts.setDollarPrice(new BigDecimal(zsCgj).multiply(new BigDecimal(100)).intValue());
                parts.setExZsBh(zsZsh);
                parts.setExZsZs("GIA");
                parts.setExZsZl(new BigDecimal(zsZl).multiply(new BigDecimal(1000)).intValue());
                parts.setExZsYs(zsYs);
                parts.setExZsJd(zsJd);
                parts.setExZsQg(zsQg);
                parts.setExZsPg(zsPg);
                parts.setExZsDc(zsDc);
                parts.setExZsYg(zsYg);
                parts.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
                parts.setEnableStatus(1);
                parts.setEnableDate(today);
                parts.setEnableOverDate(today);
                if (parts.getId() == null) {
                    parts.setCreateDate(today);
                    partsMapper.insert(parts);
                    insertResult++;
                } else {
                    parts.setUpdateDate(today);
                    partsMapper.updateByPrimaryKey(parts);
                    updateResult++;
                }
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
        return ServiceResponse.succ("导入成功，新增" + insertResult + "颗，更新" + updateResult + "颗");
    }


    /**
     * 结束操作
     *
     * @param enableOverDate
     * @return
     */
    public ServiceResponse overPartsEnable(String enableOverDate) {
        if (StringUtils.isEmpty(enableOverDate)) {
            return ServiceResponse.error("结束日期没有选择");
        }
        Date startDate = DateUtil.getDateStart(DateUtil.parseDate(enableOverDate));
        Date endDate = DateUtil.getDateEnd(DateUtil.parseDate(enableOverDate));
        int result = partsMapper.enablePartsByOver(startDate, endDate);
        return ServiceResponse.succ("结束操作" + enableOverDate + ", " + result + "颗石头");
    }

    /**
     * 导出
     *
     * @param createDate
     * @param response
     * @return
     */
    public ServiceResponse outportPartsEnable(String createDate, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(createDate)) {
            createDate = DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATE);
        }
        Date startDate = DateUtil.getDateStart(DateUtil.parseDate(createDate));
        Date endDate = DateUtil.getDateEnd(DateUtil.parseDate(createDate));
        //导出未操作的
        List<Parts> partsList = partsMapper.findThirdStoneList(startDate, endDate, 1);
        //开始导出
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("钻石");
        // 设置要导出的文件的名字
        String fileName = "未操作的石头列表_" + createDate + "_" + MyTool.createRandom(true, 6) + ".xls";
        //字体
        Font fontTitle = wb.createFont();
        fontTitle.setFontHeightInPoints((short) 14);
        fontTitle.setFontName("宋体");
        Font fontRow = wb.createFont();
        fontRow.setFontHeightInPoints((short) 12);
        fontRow.setFontName("宋体");
        //标准样式，居中
        CellStyle standardStyle = wb.createCellStyle();
        standardStyle.setWrapText(true);
        standardStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        standardStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        standardStyle.setBorderBottom(CellStyle.BORDER_THIN);//边框
        standardStyle.setBorderTop(CellStyle.BORDER_THIN);
        standardStyle.setBorderLeft(CellStyle.BORDER_THIN);
        standardStyle.setBorderRight(CellStyle.BORDER_THIN);
        //标题样式，居中
        CellStyle styleTitle = wb.createCellStyle();
        styleTitle.cloneStyleFrom(standardStyle);
        styleTitle.setFont(fontTitle);
        //内容样式1，居中
        CellStyle styleRow = wb.createCellStyle();
        styleRow.cloneStyleFrom(standardStyle);
        styleRow.setFont(fontRow);
        //内容样式2，居右
        CellStyle styleRow2 = wb.createCellStyle();
        styleRow2.cloneStyleFrom(standardStyle);
        styleRow2.setAlignment(CellStyle.ALIGN_RIGHT);
        styleRow2.setFont(fontRow);
        //第一行标题栏
        ArrayList titleHeaders = new ArrayList();
        titleHeaders.add("证书号");
        titleHeaders.add("证书");
        titleHeaders.add("钻重");
        titleHeaders.add("颜色");
        titleHeaders.add("净度");
        titleHeaders.add("切工");
        titleHeaders.add("抛光");
        titleHeaders.add("对称");
        titleHeaders.add("荧光");
        titleHeaders.add("公司简称");
        titleHeaders.add("库存地");
        titleHeaders.add("采购价");
        titleHeaders.add("单位");
        titleHeaders.add("国际报价");
        titleHeaders.add("退点");
        titleHeaders.add("同步时间");
        titleHeaders.add("上架状态");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < titleHeaders.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titleHeaders.get(i).toString());
            cell.setCellStyle(styleTitle);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i = 0; i < partsList.size(); i++) {
            Parts parts = partsList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(parts.getExZsBh());
            row.createCell(1).setCellValue(parts.getExZsZs());
            row.createCell(2).setCellValue(String.format("%.2f", new BigDecimal(parts.getExZsZl()).divide(new BigDecimal(1000))));
            row.createCell(3).setCellValue(parts.getExZsYs());
            row.createCell(4).setCellValue(parts.getExZsJd());
            row.createCell(5).setCellValue(parts.getExZsQg());
            row.createCell(6).setCellValue(parts.getExZsPg());
            row.createCell(7).setCellValue(parts.getExZsDc());
            row.createCell(8).setCellValue(parts.getExZsYg());
            row.createCell(9).setCellValue(parts.getCompany());
            row.createCell(10).setCellValue(parts.getLocation());
            row.createCell(11).setCellValue(String.format("%.2f", new BigDecimal(parts.getDollarPrice()).divide(new BigDecimal(100))));
            String unit = "人民币";
            if ("CHINASTAR".equals(parts.getCompany().toUpperCase())
                    || "HB".equals(parts.getCompany().toUpperCase())
                    || "JP".equals(parts.getCompany().toUpperCase())
                    || "PG".equals(parts.getCompany().toUpperCase())
                    || "DIAMART".equals(parts.getCompany().toUpperCase())
                    || "OPO".equals(parts.getCompany().toUpperCase())
                    || "DHA".equals(parts.getCompany().toUpperCase())
                    || "KBS".equals(parts.getCompany().toUpperCase())
                    || "HB2".equals(parts.getCompany().toUpperCase())
                    || "KDL".equals(parts.getCompany().toUpperCase())
                    || "HST".equals(parts.getCompany().toUpperCase())) {
                unit = "美元";
            }
            row.createCell(12).setCellValue(unit);
            row.createCell(13).setCellValue(String.format("%.2f", new BigDecimal(parts.getInterPrice()).divide(new BigDecimal(100))));
            row.createCell(14).setCellValue(parts.getSaleBack());
            String createD = "";
            if (parts.getCreateDate() != null) {
                createD = String.valueOf(DateUtil.formatDate(parts.getCreateDate(), DateUtil.FORMAT_DATE_TIME));
            }
            row.createCell(15).setCellValue(createD);
            String enable = "下架";
            if (parts.getEnableStatus().equals(1)) {
                enable = "上架";
            }
            row.createCell(16).setCellValue(enable);

            row.getCell(0).setCellStyle(styleRow);
            row.getCell(1).setCellStyle(styleRow);
            row.getCell(2).setCellStyle(styleRow);
            row.getCell(3).setCellStyle(styleRow);
            row.getCell(4).setCellStyle(styleRow);
            row.getCell(5).setCellStyle(styleRow);
            row.getCell(6).setCellStyle(styleRow);
            row.getCell(7).setCellStyle(styleRow);
            row.getCell(8).setCellStyle(styleRow);
            row.getCell(9).setCellStyle(styleRow);
            row.getCell(10).setCellStyle(styleRow);
            row.getCell(11).setCellStyle(styleRow2);
            row.getCell(12).setCellStyle(styleRow);
            row.getCell(13).setCellStyle(styleRow2);
            row.getCell(14).setCellStyle(styleRow);
            row.getCell(15).setCellStyle(styleRow);
            row.getCell(16).setCellStyle(styleRow);
        }
        //列宽重新整理  .getBytes().length * 2 * 256 / 3 * 2
        for (int i = 0; i < titleHeaders.size(); i++) {
            if (i == 0 || i == 14 || i == 15) {
                //证书号，返点，时间
                sheet.setColumnWidth(i, titleHeaders.get(i).toString().getBytes().length * 2 * 256 / 4 * 5);
            } else {
                sheet.setColumnWidth(i, titleHeaders.get(i).toString().getBytes().length * 2 * 256 / 4 * 3);
            }
        }
        //excel数据完成，生成文件，并下载
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setContentType("text/html");
        fileName = URLEncoder.encode(fileName, "UTF-8");
//        fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ServiceResponse.error("导出失败，稍后重试");
        }
//        return ServiceResponse.succ("导出成功，准备下载");
        return null;
    }


    /**
     * 导出所有已经上架的石头，lock_status=1，enable_status=1
     *
     * @return
     */
    public ServiceResponse outportAllLockEnableDiamondList(HttpServletResponse response) throws IOException {
        List<Parts> partsList = partsMapper.findAllLockEnableDiamondList();
        String createDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd_HHmmss");
        //开始导出
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("已上架");
        // 设置要导出的文件的名字
        String fileName = "已上架石头列表_" + createDate + ".xls";
        //字体
        Font fontTitle = wb.createFont();
        fontTitle.setFontHeightInPoints((short) 14);
        fontTitle.setFontName("宋体");
        Font fontRow = wb.createFont();
        fontRow.setFontHeightInPoints((short) 12);
        fontRow.setFontName("宋体");
        //标准样式，居中
        CellStyle standardStyle = wb.createCellStyle();
        standardStyle.setWrapText(true);
        standardStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
        standardStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        standardStyle.setBorderBottom(CellStyle.BORDER_THIN);//边框
        standardStyle.setBorderTop(CellStyle.BORDER_THIN);
        standardStyle.setBorderLeft(CellStyle.BORDER_THIN);
        standardStyle.setBorderRight(CellStyle.BORDER_THIN);
        //标题样式，居中
        CellStyle styleTitle = wb.createCellStyle();
        styleTitle.cloneStyleFrom(standardStyle);
        styleTitle.setFont(fontTitle);
        //内容样式1，居中
        CellStyle styleRow = wb.createCellStyle();
        styleRow.cloneStyleFrom(standardStyle);
        styleRow.setFont(fontRow);
        //内容样式2，居右
        CellStyle styleRow2 = wb.createCellStyle();
        styleRow2.cloneStyleFrom(standardStyle);
        styleRow2.setAlignment(CellStyle.ALIGN_RIGHT);
        styleRow2.setFont(fontRow);
        //第一行标题栏
        ArrayList titleHeaders = new ArrayList();
        titleHeaders.add("供应商");
        titleHeaders.add("证书");
        titleHeaders.add("证书编号");
        titleHeaders.add("颜色");
        titleHeaders.add("净度");
        titleHeaders.add("切工");
        titleHeaders.add("抛光");
        titleHeaders.add("对称");
        titleHeaders.add("荧光");
        titleHeaders.add("库存地");
        titleHeaders.add("钻重");
        titleHeaders.add("采购价");
        titleHeaders.add("单位");
        titleHeaders.add("国际报价");
        titleHeaders.add("退点");
        titleHeaders.add("同步时间");
        titleHeaders.add("上架状态");
        titleHeaders.add("上架时间");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < titleHeaders.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titleHeaders.get(i).toString());
            cell.setCellStyle(styleTitle);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i = 0; i < partsList.size(); i++) {
            Parts parts = partsList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(parts.getCompany());
            row.createCell(1).setCellValue(parts.getExZsZs());
            row.createCell(2).setCellValue(parts.getExZsBh());
            row.createCell(3).setCellValue(parts.getExZsYs());
            row.createCell(4).setCellValue(parts.getExZsJd());
            row.createCell(5).setCellValue(parts.getExZsQg());
            row.createCell(6).setCellValue(parts.getExZsPg());
            row.createCell(7).setCellValue(parts.getExZsDc());
            row.createCell(8).setCellValue(parts.getExZsYg());
            row.createCell(9).setCellValue(parts.getLocation());
            row.createCell(10).setCellValue(String.format("%.2f", new BigDecimal(parts.getExZsZl()).divide(new BigDecimal(1000))));
            row.createCell(11).setCellValue(String.format("%.2f", new BigDecimal(parts.getDollarPrice()).divide(new BigDecimal(100))));
            String unit = "人民币";
            if ("CHINASTAR".equals(parts.getCompany().toUpperCase())
                    || "HB".equals(parts.getCompany().toUpperCase())
                    || "JP".equals(parts.getCompany().toUpperCase())
                    || "PG".equals(parts.getCompany().toUpperCase())
                    || "DIAMART".equals(parts.getCompany().toUpperCase())
                    || "OPO".equals(parts.getCompany().toUpperCase())
                    || "DHA".equals(parts.getCompany().toUpperCase())
                    || "KBS".equals(parts.getCompany().toUpperCase())
                    || "HB2".equals(parts.getCompany().toUpperCase())
                    || "KDL".equals(parts.getCompany().toUpperCase())
                    || "HST".equals(parts.getCompany().toUpperCase())) {
                unit = "美元";
            }
            row.createCell(12).setCellValue(unit);
            row.createCell(13).setCellValue(String.format("%.2f", new BigDecimal(parts.getInterPrice()).divide(new BigDecimal(100))));
            row.createCell(14).setCellValue(parts.getSaleBack());
            String createD = "";
            if (parts.getCreateDate() != null) {
                createD = String.valueOf(DateUtil.formatDate(parts.getCreateDate(), DateUtil.FORMAT_DATE_TIME));
            }
            row.createCell(15).setCellValue(createD);
            row.createCell(16).setCellValue("已上架");
            String overD = "";
            if (parts.getEnableDate() != null) {
                overD = String.valueOf(DateUtil.formatDate(parts.getEnableDate(), DateUtil.FORMAT_DATE_TIME));
            }
            row.createCell(17).setCellValue(overD);

            row.getCell(0).setCellStyle(styleRow);
            row.getCell(1).setCellStyle(styleRow);
            row.getCell(2).setCellStyle(styleRow);
            row.getCell(3).setCellStyle(styleRow);
            row.getCell(4).setCellStyle(styleRow);
            row.getCell(5).setCellStyle(styleRow);
            row.getCell(6).setCellStyle(styleRow);
            row.getCell(7).setCellStyle(styleRow);
            row.getCell(8).setCellStyle(styleRow);
            row.getCell(9).setCellStyle(styleRow);
            row.getCell(10).setCellStyle(styleRow);
            row.getCell(11).setCellStyle(styleRow2);
            row.getCell(12).setCellStyle(styleRow);
            row.getCell(13).setCellStyle(styleRow2);
            row.getCell(14).setCellStyle(styleRow);
            row.getCell(15).setCellStyle(styleRow);
            row.getCell(16).setCellStyle(styleRow);
            row.getCell(17).setCellStyle(styleRow);
        }
        //列宽重新整理  .getBytes().length * 2 * 256 / 3 * 2
        for (int i = 0; i < titleHeaders.size(); i++) {
            if (i == 0 || i == 14 || i == 15 || i == 17) {
                //证书号，返点，时间，时间
                sheet.setColumnWidth(i, titleHeaders.get(i).toString().getBytes().length * 2 * 256 / 4 * 5);
            } else {
                sheet.setColumnWidth(i, titleHeaders.get(i).toString().getBytes().length * 2 * 256 / 4 * 3);
            }
        }
        //excel数据完成，生成文件，并下载
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        fileName = URLEncoder.encode(fileName, "UTF-8");
//        fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ServiceResponse.error("导出失败，稍后重试");
        }
//        return ServiceResponse.succ("导出成功，准备下载");
        return null;
    }
}
