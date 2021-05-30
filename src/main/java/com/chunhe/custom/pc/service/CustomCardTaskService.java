package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.framework.utils.*;
import com.chunhe.custom.pc.mapper.CustomCardMapper;
import com.chunhe.custom.pc.mapper.CustomCardTaskMapper;
import com.chunhe.custom.pc.model.CustomCard;
import com.chunhe.custom.pc.model.CustomCardTask;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年10月28日21:29:17
 */
@Service
public class CustomCardTaskService extends BaseService<CustomCardTask> {

    @Autowired
    private CustomCardTaskMapper customCardTaskMapper;

    @Autowired
    private CustomCardService customCardService;

    @Autowired
    private CustomCardMapper customCardMapper;

    //全部列表
    public List<CustomCardTask> findCustomCardTaskList(CustomCardTask customCardTask) {
        List<CustomCardTask> customCardTaskList = customCardTaskMapper.findCustomCardTaskList(customCardTask);
        return customCardTaskList;
    }

    /**
     * 查询数据
     */
    public List<CustomCardTask> customCardTaskList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(CustomCardTask.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
            if (orders.contains("CARD_TASK_CODE")) {
                example.setOrderByClause("card_task_code_before " + orders.split(" ")[1]);
            }
            if (orders.contains("CARD_TASK_STATUS_NAME")) {
                example.setOrderByClause("card_task_status " + orders.split(" ")[1]);
            }
        }
        Example.Criteria criteria = example.createCriteria();
        //任务名称
        DataTablesRequest.Column cardTaskName = dataTablesRequest.getColumn("cardTaskName");
        if (StringUtils.isNotBlank(cardTaskName.getSearch().getValue())) {
            criteria.andLike("cardTaskName", TableUtil.toFuzzySql(cardTaskName.getSearch().getValue()));
        }
        //创建人
        DataTablesRequest.Column cardTaskCreateUser = dataTablesRequest.getColumn("cardTaskCreateUser");
        if (StringUtils.isNotBlank(cardTaskCreateUser.getSearch().getValue())) {
            criteria.andLike("cardTaskCreateUser", TableUtil.toFuzzySql(cardTaskCreateUser.getSearch().getValue()));
        }
        //筛选开始时间
        DataTablesRequest.Column startDate = dataTablesRequest.getColumn("startDate");
        if (StringUtils.isNotBlank(startDate.getSearch().getValue())) {
            String start = DateUtil.formatDate(DateUtil.getDateStart(DateUtil.parseDate(startDate.getSearch().getValue())), DateUtil.FORMAT_DATE_TIME);
            criteria.andGreaterThanOrEqualTo("createDate", start);
        }
        //筛选结束时间
        DataTablesRequest.Column endDate = dataTablesRequest.getColumn("endDate");
        if (StringUtils.isNotBlank(endDate.getSearch().getValue())) {
            String end = DateUtil.formatDate(DateUtil.getDateEnd(DateUtil.parseDate(endDate.getSearch().getValue())), DateUtil.FORMAT_DATE_TIME);
            criteria.andLessThanOrEqualTo("createDate", end);
        }
        //状态
        DataTablesRequest.Column cardTaskStatus = dataTablesRequest.getColumn("cardTaskStatus");
        if (StringUtils.isNotBlank(cardTaskStatus.getSearch().getValue())) {
            criteria.andEqualTo("cardTaskStatus", Integer.valueOf(cardTaskStatus.getSearch().getValue()));
        }
        //其他
        criteria.andIsNull("expireDate");
        List<CustomCardTask> customCardTaskList = getMapper().selectByExample(example);
        for (CustomCardTask task : customCardTaskList) {
            String codeStart = task.getCardTaskCodeBefore() + task.getCardTaskCodeMiddle() + task.getCardTaskCodeAfter();
            String codeEnd = task.getCardTaskCodeBefore() + task.getCardTaskCodeMiddle() + StringUtil.addzero(Integer.valueOf(task.getCardTaskCodeAfter()) + task.getCardTaskCount() - 1, 6);
            task.setCardTaskCode(codeStart + "~" + codeEnd);
            task.setCardTaskStatusName(DictUtils.findValueByTypeAndKey(CustomCardTask.CUSTOM_CARD_TASK_STATUS, task.getCardTaskStatus()));
        }
        return customCardTaskList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public CustomCardTask getCustomCardTask(Long id) {
        CustomCardTask customCardTask = new CustomCardTask();
        customCardTask.setId(id);
        CustomCardTask task = customCardTaskMapper.getCustomCardTask(customCardTask);
        return task;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> customCardTaskMap) {
        CustomCardTask customCardTask = new CustomCardTask();
        String cardTaskName = ConvertUtil.convert(customCardTaskMap.get("cardTaskName"), String.class);
        String cardTaskCompany = ConvertUtil.convert(customCardTaskMap.get("cardTaskCompany"), String.class);
        Integer cardTaskCount = ConvertUtil.convert(customCardTaskMap.get("cardTaskCount"), Integer.class);
        String cardTaskPrice = ConvertUtil.convert(customCardTaskMap.get("cardTaskPrice"), String.class);
        String cardTaskLastDate = ConvertUtil.convert(customCardTaskMap.get("cardTaskLastDate"), String.class);
        String cardTaskCodeBefore = ConvertUtil.convert(customCardTaskMap.get("cardTaskCodeBefore"), String.class);
        String cardTaskCodeMiddle = ConvertUtil.convert(customCardTaskMap.get("cardTaskCodeMiddle"), String.class);
        String cardTaskCreateUser = ConvertUtil.convert(customCardTaskMap.get("cardTaskCreateUser"), String.class);
        if (cardTaskCount > 999999) {
            return ServiceResponse.error("卡片数量应 ≤ 999999");
        }
        customCardTask.setCardTaskName(cardTaskName);
        customCardTask.setCardTaskCompany(cardTaskCompany);
        customCardTask.setCardTaskCount(cardTaskCount);
        customCardTask.setCardTaskPrice(cardTaskPrice);
        customCardTask.setCardTaskLastDate(DateUtil.getDateEnd(DateUtil.parseDate(cardTaskLastDate, DateUtil.FORMAT_DATE)));
        customCardTask.setCardTaskCodeBefore(cardTaskCodeBefore);
        customCardTask.setCardTaskCodeMiddle(cardTaskCodeMiddle);
        customCardTask.setCardTaskCodeAfter(StringUtil.addzero(1, 6));
        customCardTask.setCardTaskCreateUser(cardTaskCreateUser);
        customCardTask.setCardTaskStatus(CustomCardTask.CUSTOM_CARD_TASK_STATUS_INIT);
        //检查本地，同个前缀（前6+中4），是否存在，号码连续累加
        CustomCard card = new CustomCard();
        card.setCardCodeBefore(cardTaskCodeBefore + cardTaskCodeMiddle);
        CustomCard local = customCardMapper.getCustomCard(card);
        if (local != null) {
            String codeNum = String.valueOf(local.getCardCodeNum());
            int localNum = Integer.valueOf(codeNum.substring(4, codeNum.length()));
            if (localNum + cardTaskCount > 999999) {
                return ServiceResponse.error("自定义前6位'" + cardTaskCodeBefore + "'已存在，卡号累加，所以此次卡片数量应 ≤ " + (999999 - localNum));
            }
            customCardTask.setCardTaskCodeAfter(StringUtil.addzero(Integer.valueOf(codeNum.substring(4, codeNum.length())) + 1, 6));
        }

        if (insertNotNull(customCardTask) != 1) {
            return ServiceResponse.error("添加失败");
        }
        //自动生成对应数量的卡片
        for (int i = 0; i < cardTaskCount; i++) {
            CustomCard customCard = new CustomCard();
            customCard.setCardTaskId(customCardTask.getId().intValue());
            customCard.setCardCode(cardTaskCodeBefore + cardTaskCodeMiddle + StringUtil.addzero(Integer.valueOf(customCardTask.getCardTaskCodeAfter()) + i, 6));
            customCard.setCardCompany(cardTaskCompany);
            customCard.setCardPrice(cardTaskPrice);
            customCard.setCardCodeNum(Integer.valueOf(cardTaskCodeMiddle + StringUtil.addzero(Integer.valueOf(customCardTask.getCardTaskCodeAfter()) + i, 6)));
            customCard.setCardPassword(this.createRandomPassword(8));
            customCard.setCardCreateUser(cardTaskCreateUser);
            customCard.setCardCreateDate(customCardTask.getCreateDate());
            customCard.setCardExpireDate(customCardTask.getCardTaskLastDate());
            int result = customCardService.insertNotNull(customCard);
            if (result != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServiceResponse.error("添加失败");
            }
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    public ServiceResponse update(Map<String, Object> customCardTaskMap) {
        Long id = ConvertUtil.convert(customCardTaskMap.get("id"), Long.class);
        CustomCardTask customCardTask = selectByKey(id);
        String cardTaskName = ConvertUtil.convert(customCardTaskMap.get("cardTaskName"), String.class);
        String cardTaskCompany = ConvertUtil.convert(customCardTaskMap.get("cardTaskCompany"), String.class);
        Integer cardTaskCount = ConvertUtil.convert(customCardTaskMap.get("cardTaskCount"), Integer.class);
        String cardTaskPrice = ConvertUtil.convert(customCardTaskMap.get("cardTaskPrice"), String.class);
        String cardTaskLastDate = ConvertUtil.convert(customCardTaskMap.get("cardTaskLastDate"), String.class);
        String cardTaskCodeBefore = ConvertUtil.convert(customCardTaskMap.get("cardTaskCodeBefore"), String.class);
        String cardTaskCodeMiddle = ConvertUtil.convert(customCardTaskMap.get("cardTaskCodeMiddle"), String.class);
        String cardTaskCreateUser = ConvertUtil.convert(customCardTaskMap.get("cardTaskCreateUser"), String.class);

        customCardTask.setCardTaskName(cardTaskName);
        customCardTask.setCardTaskCompany(cardTaskCompany);
        customCardTask.setCardTaskCount(cardTaskCount);
        customCardTask.setCardTaskPrice(cardTaskPrice);
        customCardTask.setCardTaskLastDate(DateUtil.getDateEnd(DateUtil.parseDate(cardTaskLastDate)));
        customCardTask.setCardTaskCodeBefore(cardTaskCodeBefore);
        customCardTask.setCardTaskCodeMiddle(cardTaskCodeMiddle);
        //是否本地已经存在000001，考虑累加
        customCardTask.setCardTaskCodeAfter(StringUtil.addzero(1, 6));
        customCardTask.setCardTaskCreateUser(cardTaskCreateUser);

        if (updateNotNull(customCardTask) != 1) {
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
        CustomCardTask customCardTask = selectByKey(id);
        if (expireNotNull(customCardTask) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 1可用/0不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional
    public Boolean cardTaskEnabled(Long id, Integer isEnabled, String userName) {
        CustomCardTask customCardTask = selectByKey(id);
        customCardTask.setCardTaskEnable(isEnabled);
        //该任务下的卡片，批量解冻/批量冻结
        customCardMapper.customCardEnableByBatchAction(id.intValue(), isEnabled, null, null, null, userName);
        int result = updateNotNull(customCardTask);
        if (result != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }


    /**
     * 导出
     */
    public ServiceResponse exportCard(Long id, HttpServletResponse response) throws IOException {
        CustomCard customCard = new CustomCard();
        customCard.setCardTaskId(id.intValue());
        List<CustomCard> customCardList = customCardService.findCustomCardList(customCard);
        //开始导出
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("卡片");
        // 设置要导出的文件的名字
        String fileName = "卡片_" + DateUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + ".xls";
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
        titleHeaders.add("卡号");
        titleHeaders.add("密码");
        titleHeaders.add("合作方");
        titleHeaders.add("创建人");
        titleHeaders.add("创建时间");
        titleHeaders.add("卡片面额");
        titleHeaders.add("卡片状态");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < titleHeaders.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titleHeaders.get(i).toString());
            cell.setCellStyle(styleTitle);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i = 0; i < customCardList.size(); i++) {
            CustomCard card = customCardList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(card.getCardCode());
            row.createCell(1).setCellValue(card.getCardPassword());
            row.createCell(2).setCellValue(card.getCardCompany());
            row.createCell(3).setCellValue(card.getCardCreateUser());
            String createD = "";
            if (card.getCardCreateDate() != null) {
                createD = String.valueOf(DateUtil.formatDate(card.getCardCreateDate(), DateUtil.FORMAT_DATE_TIME));
            }
            row.createCell(4).setCellValue(createD);
            row.createCell(5).setCellValue(card.getCardPrice());
            row.createCell(6).setCellValue(DictUtils.findValueByTypeAndKey(CustomCard.CUSTOM_CARD_STATUS, card.getCardStatus()));

            row.getCell(0).setCellStyle(styleRow);
            row.getCell(1).setCellStyle(styleRow);
            row.getCell(2).setCellStyle(styleRow);
            row.getCell(3).setCellStyle(styleRow);
            row.getCell(4).setCellStyle(styleRow);
            row.getCell(5).setCellStyle(styleRow);
            row.getCell(6).setCellStyle(styleRow);
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

    /**
     * 完成制卡
     */
    public ServiceResponse finishCardTask(Long id) {
        CustomCardTask customCardTask = selectByKey(id);
        customCardTask.setCardTaskStatus(CustomCardTask.CUSTOM_CARD_TASK_STATUS_FINISH);
        if (updateNotNull(customCardTask) != 1) {
            return ServiceResponse.error("操作失败");
        }
        return ServiceResponse.succ("操作成功");
    }


    /**
     * 指定位数的唯一密码
     *
     * @param length
     * @return
     */
    public String createRandomPassword(int length) {
        String strTable = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int len = strTable.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            double dblR = Math.random() * len;
            int intR = (int) Math.floor(dblR);
            sb.append(strTable.charAt(intR));
        }
        CustomCard customCard = customCardMapper.getCustomCardByPassword(sb.toString());
        if (customCard != null) {
            return createRandomPassword(length);
        }
        return sb.toString();
    }
}
