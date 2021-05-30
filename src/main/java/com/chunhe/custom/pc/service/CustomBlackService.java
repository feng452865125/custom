package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.framework.utils.XSSFUtil;
import com.chunhe.custom.pc.model.CustomBlack;
import com.chunhe.custom.pc.model.PartsBig;
import com.chunhe.custom.pc.service.cache.CustomBlackCacheService;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.CustomBlackMapper;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.xml.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by white 2021年3月22日22:13:39
 */
@Service
public class CustomBlackService extends BaseService<CustomBlack> {

    @Autowired
    private CustomBlackMapper customBlackMapper;

    @Autowired
    private CustomBlackCacheService customBlackCacheService;

    //全部列表
    public List<CustomBlack> findCustomBlackList(CustomBlack customBlack) {
        List<CustomBlack> customBlackList = customBlackMapper.findCustomBlackList(customBlack);
        return customBlackList;
    }

    /**
     * 查询数据
     */
    public List<CustomBlack> customBlackList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(CustomBlack.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //证书号
        DataTablesRequest.Column blackZsh = dataTablesRequest.getColumn("blackZsh");
        if (StringUtils.isNotBlank(blackZsh.getSearch().getValue())) {
            criteria.andLike("blackZsh", TableUtil.toFuzzySql(blackZsh.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        List<CustomBlack> customBlackList = getMapper().selectByExample(example);
        return customBlackList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public CustomBlack getCustomBlack(Long id) {
        CustomBlack customBlack = new CustomBlack();
        customBlack.setId(id);
        CustomBlack task = customBlackMapper.getCustomBlack(customBlack);
        return task;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> customBlackMap) {
        CustomBlack customBlack = new CustomBlack();
        String blackZsh = ConvertUtil.convert(customBlackMap.get("blackZsh"), String.class);
        Boolean blackEnable = ConvertUtil.convert(customBlackMap.get("blackEnable"), Boolean.class);
        String blackCreateUser = ConvertUtil.convert(customBlackMap.get("blackCreateUser"), String.class);
        String blackReason = ConvertUtil.convert(customBlackMap.get("blackReason"), String.class);
        customBlack.setBlackZsh(blackZsh);
        customBlack.setBlackType(0);
        customBlack.setBlackEnable(blackEnable);
        customBlack.setBlackCreateUser(blackCreateUser);
        customBlack.setBlackReason(blackReason);
        if (insertNotNull(customBlack) != 1) {
            return ServiceResponse.error("添加失败");
        }
        customBlackCacheService.delCacheCustomBlackManager(CustomBlack.CUSTOM_BLACK_CACHE_KEY);
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    public ServiceResponse update(Map<String, Object> customBlackMap) {
        Long id = ConvertUtil.convert(customBlackMap.get("id"), Long.class);
        CustomBlack customBlack = selectByKey(id);
        String blackZsh = ConvertUtil.convert(customBlackMap.get("blackZsh"), String.class);
        Boolean blackEnable = ConvertUtil.convert(customBlackMap.get("blackEnable"), Boolean.class);
        String blackCreateUser = ConvertUtil.convert(customBlackMap.get("blackCreateUser"), String.class);
        String blackReason = ConvertUtil.convert(customBlackMap.get("blackReason"), String.class);
        Boolean isChange = false;
        String oldZsh = customBlack.getBlackZsh();
        if (!oldZsh.equals(blackZsh)) {
            isChange = true;
        }
        customBlack.setBlackZsh(blackZsh);
        customBlack.setBlackType(0);
        customBlack.setBlackEnable(blackEnable);
        customBlack.setBlackCreateUser(blackCreateUser);
        customBlack.setBlackReason(blackReason);

        if (updateNotNull(customBlack) != 1) {
            return ServiceResponse.error("更新失败");
        }
        if (isChange) {
            //换了证书号，旧的删除，新的增加
            customBlackCacheService.delCacheCustomBlackManager(CustomBlack.CUSTOM_BLACK_CACHE_KEY);
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
        CustomBlack customBlack = selectByKey(id);
        if (expireNotNull(customBlack) != 1) {
            return ServiceResponse.error("删除失败");
        }
        customBlackCacheService.delCacheCustomBlackManager(CustomBlack.CUSTOM_BLACK_CACHE_KEY);
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 可用/不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional(readOnly = false)
    public Boolean blackEnabled(Long id, Boolean isEnabled) {
        CustomBlack customBlack = selectByKey(id);
        customBlack.setBlackEnable(isEnabled);
        boolean result = updateNotNull(customBlack) == 1;
        customBlackCacheService.delCacheCustomBlackManager(CustomBlack.CUSTOM_BLACK_CACHE_KEY);
        return result;
    }


    /**
     * 导入
     *
     * @return
     */
    public ServiceResponse customBlackImport(MultipartFile multipartFile, String userName) {
        int insertResult = 0;
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
                    || !String.valueOf(sheet.getRow(0).getCell(0)).equals("*GIA证书号")) {
                return ServiceResponse.error("模板使用错误，请重新下载黑名单模板");
            }
            // importBlack.xls 从第2行开始, 对应的行索引是1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String zsh = XSSFUtil.getStringCellValue(row.getCell(0)); // GIA证书号
                String reason = XSSFUtil.getStringCellValue(row.getCell(1)); // 拉黑原因备注
                if (StringUtils.isEmpty(zsh)) {
                    String msg = "第" + (rowIndex + 1) + "行，数据格式有误，导入失败";
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServiceResponse.error(msg);
                }
                CustomBlack customBlack = new CustomBlack();
                customBlack.setBlackZsh(zsh);
                customBlack.setBlackType(1);
                customBlack.setBlackEnable(true);
                customBlack.setBlackCreateUser(userName);
                customBlack.setBlackReason(reason);
                customBlack.setCreateDate(today);
                insertNotNull(customBlack);
                insertResult++;
                customBlackCacheService.delCacheCustomBlackManager(CustomBlack.CUSTOM_BLACK_CACHE_KEY);
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
        return ServiceResponse.succ("导入成功，[黑名单]新增" + insertResult + "颗");
    }


    /**
     * 导出
     */
    public ServiceResponse customBlackExport(HttpServletResponse response) throws IOException {
        CustomBlack customBlack = new CustomBlack();
        customBlack.setBlackEnable(true);
        List<CustomBlack> customBlackList = customBlackMapper.findCustomBlackList(customBlack);
        //开始导出
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("黑名单");
        // 设置要导出的文件的名字
        String fileName = "黑名单_" + DateUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + ".xls";
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
        //第一行标题栏
        ArrayList titleHeaders = new ArrayList();
        titleHeaders.add("序号");
        titleHeaders.add("GIA证书号");
        titleHeaders.add("类型");
        titleHeaders.add("创建人");
        titleHeaders.add("黑名单原因/备注");
        titleHeaders.add("创建时间");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < titleHeaders.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titleHeaders.get(i).toString());
            cell.setCellStyle(styleTitle);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i = 0; i < customBlackList.size(); i++) {
            CustomBlack black = customBlackList.get(i + 1);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i);
            row.createCell(1).setCellValue(black.getBlackZsh());
            String typeName = "手动添加";
            if (black.getBlackType().equals(1)) {
                typeName = "文件导入";
            }
            row.createCell(2).setCellValue(typeName);
            row.createCell(3).setCellValue(black.getBlackCreateUser());
            row.createCell(4).setCellValue(black.getBlackReason());
            row.createCell(5).setCellValue(DateUtil.formatDate(black.getCreateDate(), DateUtil.FORMAT_DATE_TIME));

            row.getCell(0).setCellStyle(styleRow);
            row.getCell(1).setCellStyle(styleRow);
            row.getCell(2).setCellStyle(styleRow);
            row.getCell(3).setCellStyle(styleRow);
            row.getCell(4).setCellStyle(styleRow);
            row.getCell(5).setCellStyle(styleRow);
        }
        //列宽重新整理  .getBytes().length * 2 * 256 / 3 * 2
        for (int i = 0; i < titleHeaders.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        //excel数据完成，生成文件，并下载
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        fileName = URLEncoder.encode(fileName, "UTF-8");
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
        return ServiceResponse.succ("导出成功，准备下载");
    }


}
