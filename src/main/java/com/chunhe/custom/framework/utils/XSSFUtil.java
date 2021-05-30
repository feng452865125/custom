package com.chunhe.custom.framework.utils;

import com.chunhe.custom.framework.exception.RFException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class XSSFUtil {

    /**
     * multipart转File
     *
     * @param multipartFile
     * @param targetPrefix
     * @return
     */
    public static File transMultipartToFile(MultipartFile multipartFile, String targetPrefix) {
        try {
            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            if (!prefix.equals(targetPrefix)) {
                throw new RFException("文件格式错误，请下载模板" + targetPrefix);
            }
            // 用uuid作为文件名，防止生成的临时文件重复
            final File file = File.createTempFile(String.valueOf(UUID.randomUUID()), prefix);
            // multipartFile转file
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RFException("出现异常，请重试");
        }
    }

    /**
     * 获取Excel
     *
     * @param file
     * @return
     */
    public static XSSFWorkbook getWorkbookFromFile(File file) {
        try {
            FileInputStream fs = FileUtils.openInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            // 输入流使用后，关闭
            fs.close();
            return workbook;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RFException("出现异常，请重试");
        }
    }

    /**
     * 获取单元格里字符串类型的值
     *
     * @param cell
     * @return
     */
    public static String getStringCellValue(Cell cell) {// 获取单元格数据内容为字符串类型的数据
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    strCell = sdf.format(cell.getDateCellValue());
                } else {
                    NumberFormat nf = NumberFormat.getInstance();
                    strCell = nf.format(cell.getNumericCellValue());
                    //字符串里有科学计数法
                    if (strCell.indexOf(",") >= 0) {
                        strCell = strCell.replace(",", "");
                    }
                }
                break;
            case XSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
                try {
                    strCell = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    strCell = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }


}
