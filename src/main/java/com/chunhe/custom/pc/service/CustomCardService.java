package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.XSSFUtil;
import com.chunhe.custom.pc.xml.*;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.CustomCardMapper;
import com.chunhe.custom.pc.model.CustomCard;
import com.chunhe.custom.pc.model.CustomCardTask;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by white 2020年10月28日21:29:17
 */
@Service
public class CustomCardService extends BaseService<CustomCard> {

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private CustomCardMapper customCardMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${cardTaskEnableMonth}")
    private String cardTaskEnableMonth;

    @Value("${cardPostUrl}")
    private String cardPostUrl;


    //全部列表
    public List<CustomCard> findCustomCardList(CustomCard customCard) {
        List<CustomCard> customCardList = customCardMapper.findCustomCardList(customCard);
        return customCardList;
    }

    /**
     * 查询数据
     */
    public List<CustomCard> customCardList(DataTablesRequest dataTablesRequest) {
        String codeStart = "";
        String codeEnd = "";
        CustomCard customCard = new CustomCard();
        //排序
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            customCard.setCardOrderBy(orders);
            if (orders.contains("CARD_STATUS_NAME")) {
                customCard.setCardOrderBy("cc.card_status " + orders.split(" ")[1]);
            }
        }
        //卡号
        DataTablesRequest.Column cardCode = dataTablesRequest.getColumn("cardCode");
        if (StringUtils.isNotBlank(cardCode.getSearch().getValue())) {
            customCard.setCardCode(cardCode.getSearch().getValue());
        }
        //建卡批次（任务名）
        DataTablesRequest.Column cardTaskName = dataTablesRequest.getColumn("cardTaskName");
        if (StringUtils.isNotBlank(cardTaskName.getSearch().getValue())) {
            customCard.setCardTaskName(cardTaskName.getSearch().getValue());
        }
        //合作方
        DataTablesRequest.Column cardCompany = dataTablesRequest.getColumn("cardCompany");
        if (StringUtils.isNotBlank(cardCompany.getSearch().getValue())) {
            customCard.setCardCompany(cardCompany.getSearch().getValue());
        }
        //卡号段开始
        DataTablesRequest.Column cardCodeStart = dataTablesRequest.getColumn("cardCodeStart");
        if (StringUtils.isNotBlank(cardCodeStart.getSearch().getValue())) {
            codeStart = cardCodeStart.getSearch().getValue();
            customCard.setCardCodeBefore(codeStart.substring(0, 6));
            customCard.setCardCodeStart(codeStart.substring(6, codeStart.length()));
        }
        //卡号段结束
        DataTablesRequest.Column cardCodeEnd = dataTablesRequest.getColumn("cardCodeEnd");
        if (StringUtils.isNotBlank(cardCodeEnd.getSearch().getValue())) {
            codeEnd = cardCodeEnd.getSearch().getValue();
            customCard.setCardCodeBefore(codeEnd.substring(0, 6));
            customCard.setCardCodeEnd(codeEnd.substring(6, codeEnd.length()));
        }
        if (!StringUtils.isEmpty(codeStart) && !StringUtils.isEmpty(codeEnd)) {
            if (!codeStart.substring(1, 6).equals(codeEnd.substring(1, 6))) {
                return new ArrayList<>();
            }
        }
        //状态
        DataTablesRequest.Column cardStatus = dataTablesRequest.getColumn("cardStatus");
        if (StringUtils.isNotBlank(cardStatus.getSearch().getValue())) {
            customCard.setCardStatus(Integer.valueOf(cardStatus.getSearch().getValue()));
        }
        List<CustomCard> customCardList = this.findCustomCardList(customCard);
        for (CustomCard card : customCardList) {
            card.setCardStatusName(DictUtils.findValueByTypeAndKey(CustomCard.CUSTOM_CARD_STATUS, card.getCardStatus()));
        }
        return customCardList;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    public CustomCard getCustomCard(Long id) {
        CustomCard customCard = new CustomCard();
        customCard.setId(id);
        CustomCard card = customCardMapper.getCustomCard(customCard);
        if (card != null) {
            card.setCardStatusName(DictUtils.findValueByTypeAndKey(CustomCard.CUSTOM_CARD_STATUS, card.getCardStatus()));
        }
        return card;
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    public ServiceResponse deleteById(Long id) {
        CustomCard customCard = selectByKey(id);
        if (expireNotNull(customCard) != 1) {
            return ServiceResponse.error("删除失败");
        }
        return ServiceResponse.succ("删除成功");
    }


    /**
     * 解冻/冻结
     *
     * @param isEnabled 是否可用
     */
    public Boolean cardEnabled(Long id, Integer isEnabled) {
        CustomCard customCard = selectByKey(id);
        customCard.setCardEnable(isEnabled);
        return updateNotNull(customCard) == 1;
    }


    /**
     * 导入卡片
     *
     * @return
     */
    public ServiceResponse customCardImport(MultipartFile multipartFile, Integer cardStatus) {
        //获取Excel
        try {
            Workbook workbook = null;
            File file = null;
            String fileName = multipartFile.getOriginalFilename();
            if (fileName.endsWith(CustomCard.XLS)) {
                //2003
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xls");
            } else if (fileName.endsWith(CustomCard.XLSX)) {
                //2007
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xlsx");
            } else {
                return ServiceResponse.error("不是Excel文件");
            }
            Sheet sheet = workbook.getSheetAt(0);
            String enableMonth = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_ENABLE_MONTH, cardTaskEnableMonth);
            List<CustomCard> cardList = new ArrayList<>();
            //第一行标题索引0，过滤
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String cardCode = XSSFUtil.getStringCellValue(row.getCell(0)); // 卡号
                String cardPassword = XSSFUtil.getStringCellValue(row.getCell(1)); // 密码
                String cardCompany = XSSFUtil.getStringCellValue(row.getCell(2)); // 合作方
                String cardCreateUser = XSSFUtil.getStringCellValue(row.getCell(3)); // 创建人
                String cardCreateDate = XSSFUtil.getStringCellValue(row.getCell(4)); // 创建时间
                String cardPrice = XSSFUtil.getStringCellValue(row.getCell(5)); // 卡片面额
                String cardExpireDate = XSSFUtil.getStringCellValue(row.getCell(6)); // 过期时间
                Object[] checkArr = {cardCode, cardPassword, cardCompany, cardCreateUser, cardCreateDate, cardPrice};
                for (int i = 0; i < checkArr.length; i++) {
                    if (checkArr[i] == null || StringUtils.isEmpty(String.valueOf(checkArr[i]))) {
                        String msg = "第" + (rowIndex + 1) + "行，数据有误，导入失败";
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error(msg);
                    }
                }
                CustomCard customCard = new CustomCard();
                customCard.setCardCode(cardCode);
                customCard.setCardCodeNum(NumberUtils.toInt(cardCode.substring(6, cardCode.length()), 0));
                customCard.setCardPassword(cardPassword);
                customCard.setCardCompany(cardCompany);
                customCard.setCardCreateUser(cardCreateUser);
                customCard.setCardCreateDate(DateUtil.parseDate(cardCreateDate));
                customCard.setCardPrice(cardPrice);
                customCard.setCardStatus(cardStatus);
                Date now;
                if (!StringUtils.isEmpty(cardExpireDate)) {
                    now = DateUtil.parseDate(cardExpireDate);
                } else {
                    now = DateUtil.getDateEnd(DateUtil.getLastMonth(customCard.getCardCreateDate(), Integer.parseInt(enableMonth)));
                }
                customCard.setCardExpireDate(now);
                cardList.add(customCard);
            }
            //数据导入完成，如果是激活，直接对接POS
            if (cardStatus.equals(1)) {
                XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
                XMLParams xmlParams = new XMLParams();
                XMLMetaData xmlMetaData = new XMLMetaData();
                SQLBuilderItem sqlBuilderItems = new SQLBuilderItem("{95E1C157-C4C0-453E-878C-DA685A934CB6}",
                        "", "", "true");
                Save save = new Save();
                ROWDATA rowdata = new ROWDATA();
                ROW[] row = new ROW[cardList.size()];
                for (int i = 0; i < cardList.size(); i++) {
                    CustomCard card = cardList.get(i);
                    String cardCode = card.getCardCode();
                    String cardPassword = card.getCardPassword();
                    String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String cardStartDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String cardEndDate = DateUtil.formatDate(card.getCardExpireDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    row[i] = new ROW("4", cardCode, cardCode, cardCode, cardPassword, "实体卡券",
                            "STKQ", "1", cardCreateDate, cardCreateDate,
                            "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "QST22",
                            cardStartDate, cardEndDate, "Q[*]", "Q[*]",
                            "16", "", "FALSE", "FALSE", "FALSE", "FALSE", "FALSE", "1",
                            String.valueOf(Integer.parseInt(enableMonth) * 30), "1", "0", "TRUE", "", "ADMIN");
                    rowdata.setROW(row);
                }
                SQLBuilderItem sqlBuilderItems1 = new SQLBuilderItem("{4F9326AD-D23F-48B6-938B-EE91AC333388}",
                        "", "", "true");
                Save save1 = new Save();
                ROWDATA rowdata1 = new ROWDATA();
                ROW[] row1 = new ROW[cardList.size()];
                for (int i = 0; i < cardList.size(); i++) {
                    CustomCard card = cardList.get(i);
                    String cardCode = card.getCardCode();
                    String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String price = "";
                    if (card != null) {
                        price = card.getCardPrice();
                    }
                    row1[i] = new ROW("4", cardCode, cardCode, cardCode, "实体卡券",
                            "STKQ", "1", cardCreateDate, cardCreateDate,
                            "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "", "", price, price, "", "");
                    row1[i].setFOPERATOR("ADMIN");
                    rowdata1.setROW(row1);
                }
                save.setROWDATA(rowdata);
                sqlBuilderItems.setSave(save);
                save1.setROWDATA(rowdata1);
                sqlBuilderItems1.setSave(save1);
                sqlBuilderItems.setSQLBuilderItem(sqlBuilderItems1);
                xmlMetaData.setSQLBuilderItem(sqlBuilderItems);
                xmlRequest.setXMLParams(xmlParams);
                xmlRequest.setXMLMetaData(xmlMetaData);

                XMLUtil xmlUtil = new XMLUtil();
                String posXml = xmlUtil.beanToXML(xmlRequest);
                String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_CARD_POS, cardPostUrl);

                String posResult = "";
                try {
                    posResult = xmlUtil.toPost2(posXml, posUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(posResult)) {
                    return ServiceResponse.error("POS对接（批量激活-导入卡片）失败-无返回");
                }
                System.out.println("POS对接（批量激活-导入卡片）返回：");
                System.out.println(posResult);
                XMLResponse response = xmlUtil.XMLToBean2(posResult);
                if (response == null || response.getXMLResult() != true) {
                    return ServiceResponse.error("POS对接（批量激活-导入卡片）失败: " + response.getXMLMessage().getFMESSAGEINFO());
                }
            }
            for (CustomCard card : cardList) {
                CustomCard cc = new CustomCard();
                cc.setCardCode(card.getCardCode());
                CustomCard localCard = customCardMapper.getCustomCard(cc);
                if (localCard == null) {
                    int result = insertNotNull(card);
                    if (result != 1) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error("存储异常，稍后重试");
                    }
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
        return ServiceResponse.succ("导入成功");
    }


    @Transactional
    public ServiceResponse customCardBatchActionByHand(Integer actionType, String cardCodeStart, String cardCodeEnd, String userName) {
        if (StringUtils.isEmpty(cardCodeStart) || StringUtils.isEmpty(cardCodeEnd)
                || cardCodeStart.length() != 16 || cardCodeEnd.length() != 16) {
            return ServiceResponse.error("卡号位数不足16位");
        }
        String codeStartBefore = cardCodeStart.substring(0, 6);
        String codeEndBefore = cardCodeEnd.substring(0, 6);
        if (!codeStartBefore.equals(codeEndBefore)) {
            return ServiceResponse.error("卡号区间填写异常");
        }
        Integer codeStartAfter = Integer.valueOf(cardCodeStart.substring(6, cardCodeStart.length()));
        Integer codeEndAfter = Integer.valueOf(cardCodeEnd.substring(6, cardCodeEnd.length()));
        String action = "";
        int result = 0;
        String enableMonth = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_ENABLE_MONTH, cardTaskEnableMonth);
        if (actionType.equals(0)) {
            //对接POS激活
            XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
            XMLParams xmlParams = new XMLParams();
            XMLMetaData xmlMetaData = new XMLMetaData();
            SQLBuilderItem sqlBuilderItems = new SQLBuilderItem("{95E1C157-C4C0-453E-878C-DA685A934CB6}",
                    "", "", "true");
            Save save = new Save();
            ROWDATA rowdata = new ROWDATA();
            ROW[] row = new ROW[codeEndAfter - codeStartAfter + 1];
            for (int i = 0; i <= codeEndAfter - codeStartAfter; i++) {
                String cardCode = codeStartBefore + String.valueOf(codeStartAfter + i);
                CustomCard card = this.getCardByCodeAndPassword(cardCode, null);
                String cardPassword = card.getCardPassword();
                String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                String cardStartDate = DateUtil.formatDate(new Date(), "yyyyMMdd'T'HH:mm:ssSSS");
                String cardEndDate = DateUtil.formatDate(DateUtil.getLastMonth(new Date(), Integer.parseInt(enableMonth)), "yyyyMMdd'T'HH:mm:ssSSS");
                row[i] = new ROW("4", cardCode, cardCode, cardCode, cardPassword, "实体卡券",
                        "STKQ", "1", cardCreateDate, cardCreateDate,
                        "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "QST22",
                        cardStartDate, cardEndDate, "Q[*]", "Q[*]",
                        "16", "", "FALSE", "FALSE", "FALSE", "FALSE", "FALSE", "1",
                        String.valueOf(Integer.parseInt(enableMonth) * 30), "1", "0", "TRUE", "", "ADMIN");
                rowdata.setROW(row);
            }
            SQLBuilderItem sqlBuilderItems1 = new SQLBuilderItem("{4F9326AD-D23F-48B6-938B-EE91AC333388}",
                    "", "", "true");
            Save save1 = new Save();
            ROWDATA rowdata1 = new ROWDATA();
            ROW[] row1 = new ROW[codeEndAfter - codeStartAfter + 1];
            for (int i = 0; i <= codeEndAfter - codeStartAfter; i++) {
                String cardCode = codeStartBefore + String.valueOf(codeStartAfter + i);
                CustomCard card = this.getCardByCodeAndPassword(cardCode, null);
                String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                String price = "";
                if (card != null) {
                    price = card.getCardPrice();
                }
                row1[i] = new ROW("4", cardCode, cardCode, cardCode, "实体卡券",
                        "STKQ", "1", cardCreateDate, cardCreateDate,
                        "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "", "", price, price, "", "");
                row1[i].setFOPERATOR("ADMIN");
                rowdata1.setROW(row1);
            }
            save.setROWDATA(rowdata);
            sqlBuilderItems.setSave(save);
            save1.setROWDATA(rowdata1);
            sqlBuilderItems1.setSave(save1);
            sqlBuilderItems.setSQLBuilderItem(sqlBuilderItems1);
            xmlMetaData.setSQLBuilderItem(sqlBuilderItems);
            xmlRequest.setXMLParams(xmlParams);
            xmlRequest.setXMLMetaData(xmlMetaData);

            XMLUtil xmlUtil = new XMLUtil();
            String posXml = xmlUtil.beanToXML(xmlRequest);
            String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_CARD_POS, cardPostUrl);

            String posResult = "";
            try {
                posResult = xmlUtil.toPost2(posXml, posUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(posResult)) {
                return ServiceResponse.error("POS对接（批量激活-手填卡号）失败");
            }
            System.out.println("POS对接（批量激活-手填卡号）返回：");
            System.out.println(posResult);
            XMLResponse response = xmlUtil.XMLToBean2(posResult);
            if (response == null || response.getXMLResult() != true) {
                return ServiceResponse.error("POS对接（批量激活-手填卡号）失败: " + response.getXMLMessage().getFMESSAGEINFO());
            }
            //本地激活
            action = "激活";
            result = customCardMapper.customCardActivateByBatchAction(codeStartBefore, codeStartAfter, codeEndAfter, userName);
        } else if (actionType.equals(1)) {
            action = "冻结";
            result = customCardMapper.customCardEnableByBatchAction(null, 0, codeStartBefore, codeStartAfter, codeEndAfter, userName);
        } else if (actionType.equals(2)) {
            action = "解冻";
            result = customCardMapper.customCardEnableByBatchAction(null, 1, codeStartBefore, codeStartAfter, codeEndAfter, userName);
        } else {
            return ServiceResponse.error("操作类型异常");
        }
        return ServiceResponse.succ("批量" + action + "成功 " + result + " 张");
    }


    /**
     * 导入卡片
     *
     * @return
     */
    public ServiceResponse customCardBatchActionByFile(MultipartFile multipartFile, Integer actionType, String userName) {
        //获取Excel
        String action = "";
        if (actionType.equals(0)) {
            action = "激活";
        } else if (actionType.equals(1)) {
            action = "冻结";
        } else if (actionType.equals(2)) {
            action = "解冻";
        } else {
            return ServiceResponse.error("操作类型异常");
        }
        int result = 0;
        String cardCodeErrorArr = "";
        String cardCodeSuccessArr = "";
        List<CustomCard> cardList = new ArrayList<>();
        try {
            Workbook workbook = null;
            File file = null;
            String fileName = multipartFile.getOriginalFilename();
            if (fileName.endsWith(CustomCard.XLS)) {
                //2003
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xls");
            } else if (fileName.endsWith(CustomCard.XLSX)) {
                //2007
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
                file = XSSFUtil.transMultipartToFile(multipartFile, ".xlsx");
            } else {
                return ServiceResponse.error("不是Excel文件");
            }
            Sheet sheet = workbook.getSheetAt(0);
            //证书号在第一列, 对应的行索引是0
            for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String cardCode = XSSFUtil.getStringCellValue(row.getCell(0)); // 卡号
                CustomCard local = this.getCardByCodeAndPassword(cardCode, null);
                if (local == null) {
                    //本地不存在，记录下来
                    cardCodeErrorArr = cardCodeErrorArr + cardCode + ",";
                } else {
                    cardCodeSuccessArr = cardCodeSuccessArr + "'" + cardCode + "'" + ",";
                    cardList.add(local);
                }
            }
            // 操作完毕后，XSSFWorkbook关闭
            workbook.close();
            // 操作完毕后，临时文件删除
            file.delete();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RFException("导入文件异常，请重试");
        }
        //数据读取完成，其他逻辑处理
        if (!StringUtils.isEmpty(cardCodeSuccessArr)) {
            //有正常数据
            String cardCodeArr = cardCodeSuccessArr.substring(0, cardCodeSuccessArr.length() - 1);
            if (actionType.equals(0)) {
                String enableMonth = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_ENABLE_MONTH, cardTaskEnableMonth);
                //激活---POS对接
                XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
                XMLParams xmlParams = new XMLParams();
                XMLMetaData xmlMetaData = new XMLMetaData();
                SQLBuilderItem sqlBuilderItems = new SQLBuilderItem("{95E1C157-C4C0-453E-878C-DA685A934CB6}",
                        "", "", "true");
                Save save = new Save();
                ROWDATA rowdata = new ROWDATA();
                ROW[] row = new ROW[cardList.size()];
                for (int i = 0; i < cardList.size(); i++) {
                    CustomCard card = cardList.get(i);
                    String cardCode = card.getCardCode();
                    String cardPassword = card.getCardPassword();
                    String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String cardStartDate = DateUtil.formatDate(new Date(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String cardEndDate = DateUtil.formatDate(DateUtil.getLastMonth(new Date(), Integer.parseInt(enableMonth)), "yyyyMMdd'T'HH:mm:ssSSS");
                    row[i] = new ROW("4", cardCode, cardCode, cardCode, cardPassword, "实体卡券",
                            "STKQ", "1", cardCreateDate, cardCreateDate,
                            "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "QST22",
                            cardStartDate, cardEndDate, "Q[*]", "Q[*]",
                            "16", "", "FALSE", "FALSE", "FALSE", "FALSE", "FALSE", "1",
                            String.valueOf(Integer.parseInt(enableMonth) * 30), "1", "0", "TRUE", "", "ADMIN");
                    rowdata.setROW(row);
                }
                SQLBuilderItem sqlBuilderItems1 = new SQLBuilderItem("{4F9326AD-D23F-48B6-938B-EE91AC333388}",
                        "", "", "true");
                Save save1 = new Save();
                ROWDATA rowdata1 = new ROWDATA();
                ROW[] row1 = new ROW[cardList.size()];
                for (int i = 0; i < cardList.size(); i++) {
                    CustomCard card = cardList.get(i);
                    String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
                    String cardCode = card.getCardCode();
                    String price = "";
                    if (card != null) {
                        price = card.getCardPrice();
                    }
                    row1[i] = new ROW("4", cardCode, cardCode, cardCode, "实体卡券",
                            "STKQ", "1", cardCreateDate, cardCreateDate,
                            "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "", "", price, price, "", "");
                    row1[i].setFOPERATOR("ADMIN");
                    rowdata1.setROW(row1);
                }
                save.setROWDATA(rowdata);
                sqlBuilderItems.setSave(save);
                save1.setROWDATA(rowdata1);
                sqlBuilderItems1.setSave(save1);
                sqlBuilderItems.setSQLBuilderItem(sqlBuilderItems1);
                xmlMetaData.setSQLBuilderItem(sqlBuilderItems);
                xmlRequest.setXMLParams(xmlParams);
                xmlRequest.setXMLMetaData(xmlMetaData);

                XMLUtil xmlUtil = new XMLUtil();
                String posXml = xmlUtil.beanToXML(xmlRequest);
                String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_CARD_POS, cardPostUrl);

                String posResult = "";
                try {
                    posResult = xmlUtil.toPost2(posXml, posUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (StringUtils.isEmpty(posResult)) {
                    return ServiceResponse.error("POS对接（批量激活-导入文件）失败");
                }
                System.out.println("POS对接（批量激活-导入文件）返回：");
                System.out.println(posResult);
                XMLResponse response = xmlUtil.XMLToBean2(posResult);
                if (response == null || response.getXMLResult() != true) {
                    return ServiceResponse.error("POS对接（批量激活-导入文件）失败: " + response.getXMLMessage().getFMESSAGEINFO());
                }
                //本地激活
                result = customCardMapper.customCardActivateByBatchActionFile(cardCodeArr, userName);
            } else if (actionType.equals(1)) {
                //冻结
                result = customCardMapper.customCardEnableByBatchActionFile(0, cardCodeArr, userName);
            } else if (actionType.equals(2)) {
                //解冻
                result = customCardMapper.customCardEnableByBatchActionFile(1, cardCodeArr, userName);
            }
        }
        if (StringUtils.isEmpty(cardCodeErrorArr)) {
            return ServiceResponse.succ("导入成功，" + action + result + "张");
        } else {
            return ServiceResponse.error("导入成功，" + action + result + "张" + "##" + cardCodeErrorArr.substring(0, cardCodeErrorArr.length() - 1));
        }

    }


    //激活
    public ServiceResponse customCardActivate(Long id) {
        CustomCard card = customCardMapper.selectByPrimaryKey(id);
        if (card == null) {
            return ServiceResponse.error("数据异常，刷新重试");
        }
        String enableMonth = sysConfigService.getSysConfigByKey(CustomCardTask.CUSTOM_CARD_TASK_ENABLE_MONTH, cardTaskEnableMonth);
        //对接POS激活
        XMLRequest xmlRequest = new XMLRequest("2.0", "{1506DC71-E410-4610-BE04-E7766C2F8E50}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItems = new SQLBuilderItem("{95E1C157-C4C0-453E-878C-DA685A934CB6}",
                "", "", "true");
        Save save = new Save();
        ROWDATA rowdata = new ROWDATA();
        ROW[] row = new ROW[1];
        String cardCode = card.getCardCode();
        String cardPassword = card.getCardPassword();
        String cardCreateDate = DateUtil.formatDate(card.getCardCreateDate(), "yyyyMMdd'T'HH:mm:ssSSS");
        String cardStartDate = DateUtil.formatDate(new Date(), "yyyyMMdd'T'HH:mm:ssSSS");
        String cardEndDate = DateUtil.formatDate(DateUtil.getLastMonth(new Date(), Integer.parseInt(enableMonth)), "yyyyMMdd'T'HH:mm:ssSSS");
        row[0] = new ROW("4", cardCode, cardCode, cardCode, cardPassword, "实体卡券",
                "STKQ", "1", cardCreateDate, cardCreateDate,
                "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "QST22",
                cardStartDate, cardEndDate, "Q[*]", "Q[*]",
                "16", "", "FALSE", "FALSE", "FALSE", "FALSE", "FALSE", "1",
                String.valueOf(Integer.parseInt(enableMonth) * 30), "1", "0", "TRUE", "", "ADMIN");
        rowdata.setROW(row);
        SQLBuilderItem sqlBuilderItems1 = new SQLBuilderItem("{4F9326AD-D23F-48B6-938B-EE91AC333388}",
                "", "", "true");
        Save save1 = new Save();
        ROWDATA rowdata1 = new ROWDATA();
        ROW[] row1 = new ROW[1];
        String price = "";
        if (card != null) {
            price = card.getCardPrice();
        }
        row1[0] = new ROW("4", cardCode, cardCode, cardCode, "实体卡券",
                "STKQ", "1", cardCreateDate, cardCreateDate,
                "C7E4F107-14D7-4E02-BAB4-274B67C17EDC", "", "", "", price, price, "", "");
        row1[0].setFOPERATOR("ADMIN");
        rowdata1.setROW(row1);
        save.setROWDATA(rowdata);
        sqlBuilderItems.setSave(save);
        save1.setROWDATA(rowdata1);
        sqlBuilderItems1.setSave(save1);
        sqlBuilderItems.setSQLBuilderItem(sqlBuilderItems1);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItems);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        XMLUtil xmlUtil = new XMLUtil();
        String posXml = xmlUtil.beanToXML(xmlRequest);
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_CARD_POS, cardPostUrl);

        String posResult = "";
        try {
            posResult = xmlUtil.toPost2(posXml, posUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(posResult)) {
            return ServiceResponse.error("POS对接（单卡激活）失败");
        }
        System.out.println("POS对接（单卡激活）返回：");
        System.out.println(posResult);
        XMLResponse response = xmlUtil.XMLToBean2(posResult);
        if (response == null || response.getXMLResult() != true) {
            return ServiceResponse.error("POS对接（单卡激活）失败: " + response.getXMLMessage().getFMESSAGEINFO());
        }
        card.setCardStatus(1);
        card.setCardActivateDate(new Date());
        int result = updateNotNull(card);
        if (result != 1) {
            return ServiceResponse.error("激活失败");
        }
        return ServiceResponse.succ("激活成功");
    }


    //核销
    public ServiceResponse customCardUse(Map<String, Object> map) {
        Long id = ConvertUtil.convert(map.get("id"), Long.class);
        String cardPlatform = ConvertUtil.convert(map.get("cardPlatform"), String.class);
        String cardPlatformOrder = ConvertUtil.convert(map.get("cardPlatformOrder"), String.class);
        String storeName = ConvertUtil.convert(map.get("storeName"), String.class);
        String cardUser = ConvertUtil.convert(map.get("cardUser"), String.class);
        if (id == null
                || StringUtils.isEmpty(cardPlatform)
                || StringUtils.isEmpty(cardPlatformOrder)) {
            return ServiceResponse.error("信息未填写完整");
        }
        CustomCard customCard = customCardMapper.selectByPrimaryKey(id);
        if (customCard == null) {
            return ServiceResponse.error("数据异常，刷新重试");
        }
        customCard.setCardStatus(2);
        customCard.setCardPlatform(cardPlatform);
        customCard.setCardPlatformOrder(cardPlatformOrder);
        customCard.setCardUseStore(storeName);
        customCard.setCardUseUser(cardUser);
        int result = updateNotNull(customCard);
        if (result != 1) {
            return ServiceResponse.error("核销失败");
        }
        return ServiceResponse.succ("核销成功");
    }


    /**
     * 导出
     */
    public ServiceResponse exportErrorCardCode(String cardCodeArr, HttpServletResponse response) throws IOException {
        if (StringUtils.isEmpty(cardCodeArr)) {
            return ServiceResponse.error("无异常卡号");
        }
        List cardCodeList = Arrays.asList(cardCodeArr.split(","));
        //开始导出
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("卡片");
        // 设置要导出的文件的名字
        String fileName = "异常卡号_" + DateUtil.formatDate(new Date(), "yyyyMMdd_HHmmss") + ".xls";
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
        titleHeaders.add("卡片状态");
        Row rowTitle = sheet.createRow(0);
        for (int i = 0; i < titleHeaders.size(); i++) {
            Cell cell = rowTitle.createCell(i);
            cell.setCellValue(titleHeaders.get(i).toString());
            cell.setCellStyle(styleTitle);
        }
        //在表中存放查询到的数据放入对应的列
        for (int i = 0; i < cardCodeList.size(); i++) {
            String cardCode = cardCodeList.get(i).toString();
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(cardCode);
            row.createCell(1).setCellValue("不存在或已失效");
            row.getCell(0).setCellStyle(styleRow);
            row.getCell(1).setCellStyle(styleRow);
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


    public CustomCard getCardByCodeAndPassword(String cardCode, String cardPassword) {
        CustomCard customCard = new CustomCard();
        if (!StringUtils.isEmpty(cardCode)) {
            customCard.setCardCode(cardCode);
        }
        if (!StringUtils.isEmpty(cardPassword)) {
            customCard.setCardPassword(cardPassword);
        }
        CustomCard card = customCardMapper.getCustomCard(customCard);
        return card;
    }
}
