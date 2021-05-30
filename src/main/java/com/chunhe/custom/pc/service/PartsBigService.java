package com.chunhe.custom.pc.service;

import com.chunhe.custom.pc.mapper.PartsBigMapper;
import com.chunhe.custom.pc.model.PartsBig;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.XSSFUtil;
import com.chunhe.custom.pc.thirdSupplier.rows;
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
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2020年5月22日00:10:42
 */
@Service
public class PartsBigService extends BaseService<PartsBig> {

    @Autowired
    private PartsBigMapper partsBigMapper;

    //全部列表
    public List<PartsBig> findPartsBigList(PartsBig partsBig) {
        List<PartsBig> partsBigList = partsBigMapper.findPartsBigList(partsBig);
        return partsBigList;
    }

    /**
     * 查询数据
     */
    public List<PartsBig> partsBigList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(PartsBig.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        Example.Criteria criteria = example.createCriteria();
        //款号
        DataTablesRequest.Column zsKh = dataTablesRequest.getColumn("zsKh");
        if (StringUtils.isNotBlank(zsKh.getSearch().getValue())) {
            criteria.andLike("zsKh", zsKh.getSearch().getValue());
        }
        //公司
        DataTablesRequest.Column company = dataTablesRequest.getColumn("company");
        if (StringUtils.isNotBlank(company.getSearch().getValue())) {
            criteria.andEqualTo("company", company.getSearch().getValue());
        }
        //状态
        DataTablesRequest.Column lockStatus = dataTablesRequest.getColumn("lockStatus");
        if (StringUtils.isNotBlank(lockStatus.getSearch().getValue())) {
            criteria.andEqualTo("lockStatus", lockStatus.getSearch().getValue());
        }
        //其他
        criteria.andIsNull("expireDate");
        return getMapper().selectByExample(example);
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Transactional
    public PartsBig getPartsBig(Long id) {
        PartsBig partsBig = new PartsBig();
        partsBig.setId(id);
        PartsBig pb = partsBigMapper.getPartsBig(partsBig);
        return pb;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> PartsBigMap) {
        PartsBig partsBig = new PartsBig();
        String zsKh = ConvertUtil.convert(PartsBigMap.get("zsKh"), String.class);
        String zsZl = ConvertUtil.convert(PartsBigMap.get("zsZl"), String.class);
        String zsZsh = ConvertUtil.convert(PartsBigMap.get("zsZsh"), String.class);
        String zsYs = ConvertUtil.convert(PartsBigMap.get("zsYs"), String.class);
        String zsJd = ConvertUtil.convert(PartsBigMap.get("zsJd"), String.class);
        String zsQg = ConvertUtil.convert(PartsBigMap.get("zsQg"), String.class);
        String zsPg = ConvertUtil.convert(PartsBigMap.get("zsPg"), String.class);
        String zsDc = ConvertUtil.convert(PartsBigMap.get("zsDc"), String.class);
        String zsYg = ConvertUtil.convert(PartsBigMap.get("zsYg"), String.class);
        String zsKd = ConvertUtil.convert(PartsBigMap.get("zsKd"), String.class);
        String zsGjbj = ConvertUtil.convert(PartsBigMap.get("zsGjbj"), String.class);
        String zsCtjj = ConvertUtil.convert(PartsBigMap.get("zsCtjj"), String.class);
        String zsMj = ConvertUtil.convert(PartsBigMap.get("zsMj"), String.class);
        String zsRmb = ConvertUtil.convert(PartsBigMap.get("zsRmb"), String.class);
        String zsPrice = ConvertUtil.convert(PartsBigMap.get("zsPrice"), String.class);
        Integer lockStatus = ConvertUtil.convert(PartsBigMap.get("lockStatus"), Integer.class);
        Boolean enabled = ConvertUtil.convert(PartsBigMap.get("isEnabled"), Boolean.class);
        String company = "";
        if (!StringUtils.isEmpty(zsKh)) {
            company = zsKh.split("-")[0];
        }
        partsBig.setCompany(company);
        partsBig.setZsKh(zsKh);
        partsBig.setZsZl(new BigDecimal(Double.toString(NumberUtils.toDouble(zsZl, 0))).multiply(new BigDecimal(1000)).intValue());
        partsBig.setZsZsh(zsZsh);
        partsBig.setZsYs(zsYs);
        partsBig.setZsJd(zsJd);
        partsBig.setZsQg(zsQg);
        partsBig.setZsPg(zsPg);
        partsBig.setZsDc(zsDc);
        partsBig.setZsYg(zsYg);
        partsBig.setZsKd(zsKd);
        partsBig.setZsGjbj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsGjbj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsCtjj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsCtjj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsMj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsMj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsRmb(new BigDecimal(Double.toString(NumberUtils.toDouble(zsRmb, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsPrice(new BigDecimal(Double.toString(NumberUtils.toDouble(zsPrice, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setLockStatus(lockStatus);
        partsBig.setEnabled(enabled);

        if (insertNotNull(partsBig) != 1) {
            return ServiceResponse.error("添加失败");
        }
        return ServiceResponse.succ("添加成功");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> PartsBigMap) {
        Long id = ConvertUtil.convert(PartsBigMap.get("id"), Long.class);
        PartsBig partsBig = selectByKey(id);
        String zsKh = ConvertUtil.convert(PartsBigMap.get("zsKh"), String.class);
        String zsZl = ConvertUtil.convert(PartsBigMap.get("zsZl"), String.class);
        String zsZsh = ConvertUtil.convert(PartsBigMap.get("zsZsh"), String.class);
        String zsYs = ConvertUtil.convert(PartsBigMap.get("zsYs"), String.class);
        String zsJd = ConvertUtil.convert(PartsBigMap.get("zsJd"), String.class);
        String zsQg = ConvertUtil.convert(PartsBigMap.get("zsQg"), String.class);
        String zsPg = ConvertUtil.convert(PartsBigMap.get("zsPg"), String.class);
        String zsDc = ConvertUtil.convert(PartsBigMap.get("zsDc"), String.class);
        String zsYg = ConvertUtil.convert(PartsBigMap.get("zsYg"), String.class);
        String zsKd = ConvertUtil.convert(PartsBigMap.get("zsKd"), String.class);
        String zsGjbj = ConvertUtil.convert(PartsBigMap.get("zsGjbj"), String.class);
        String zsCtjj = ConvertUtil.convert(PartsBigMap.get("zsCtjj"), String.class);
        String zsMj = ConvertUtil.convert(PartsBigMap.get("zsMj"), String.class);
        String zsRmb = ConvertUtil.convert(PartsBigMap.get("zsRmb"), String.class);
        String zsPrice = ConvertUtil.convert(PartsBigMap.get("zsPrice"), String.class);
        Integer lockStatus = ConvertUtil.convert(PartsBigMap.get("lockStatus"), Integer.class);
        Boolean enabled = ConvertUtil.convert(PartsBigMap.get("isEnabled"), Boolean.class);
        String company = "";
        if (!StringUtils.isEmpty(zsKh)) {
            company = zsKh.split("-")[0];
        }
        partsBig.setCompany(company);
        partsBig.setZsKh(zsKh);
        partsBig.setZsZl(new BigDecimal(Double.toString(NumberUtils.toDouble(zsZl, 0))).multiply(new BigDecimal(1000)).intValue());
        partsBig.setZsZsh(zsZsh);
        partsBig.setZsYs(zsYs);
        partsBig.setZsJd(zsJd);
        partsBig.setZsQg(zsQg);
        partsBig.setZsPg(zsPg);
        partsBig.setZsDc(zsDc);
        partsBig.setZsYg(zsYg);
        partsBig.setZsKd(zsKd);
        partsBig.setZsGjbj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsGjbj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsCtjj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsCtjj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsMj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsMj, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsRmb(new BigDecimal(Double.toString(NumberUtils.toDouble(zsRmb, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setZsPrice(new BigDecimal(Double.toString(NumberUtils.toDouble(zsPrice, 0))).multiply(new BigDecimal(100)).intValue());
        partsBig.setLockStatus(lockStatus);
        partsBig.setEnabled(enabled);
        if (updateNotNull(partsBig) != 1) {
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
        PartsBig partsBig = selectByKey(id);
        if (expireNotNull(partsBig) != 1) {
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
        PartsBig partsBig = selectByKey(id);
        partsBig.setEnabled(isEnabled);
        return updateNotNull(partsBig) == 1;
    }

    /**
     * 公司名select
     *
     * @return
     */
    public List findCompanyList() {
        List companyList = partsBigMapper.findCompanyList();
        return companyList;
    }

    /**
     * 批量导入
     *
     * @return
     */
    @Transactional
    public ServiceResponse importPartsBig(MultipartFile multipartFile) {
        List<PartsBig> partsBigList = new ArrayList<>();
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
//            Sheet sheet = workbook.getSheet("Sheet1");
            Sheet sheet = workbook.getSheetAt(0);
            //简单校验，是不是使用正确的导入模板
            if (sheet.getRow(0) == null
                    || sheet.getRow(0).getCell(1) == null
                    || sheet.getRow(0).getCell(3) == null
                    || !String.valueOf(sheet.getRow(0).getCell(2)).equals("石重")
                    || !String.valueOf(sheet.getRow(0).getCell(3)).equals("GIA证书号")
                    || !String.valueOf(sheet.getRow(0).getCell(14)).equals("人民币价格")
                    || !String.valueOf(sheet.getRow(0).getCell(15)).equals("销售价")) {
                return ServiceResponse.error("模板使用错误，请重新下载导入模板");
            }
            // importPartsBig.xls 从第2行开始, 对应的行索引是1
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                // XSSFRow 代表一行数据
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                //getCell(0)，为序号，无意义
                String zsKh = XSSFUtil.getStringCellValue(row.getCell(1)); // 款号
                String zsZl = XSSFUtil.getStringCellValue(row.getCell(2)); // 石重
                String zsZsh = XSSFUtil.getStringCellValue(row.getCell(3)); // GIA证书号
                String zsYs = XSSFUtil.getStringCellValue(row.getCell(4)); // 颜色
                String zsJd = XSSFUtil.getStringCellValue(row.getCell(5)); // 净度
                String zsQg = XSSFUtil.getStringCellValue(row.getCell(6)); // 切工
                String zsPg = XSSFUtil.getStringCellValue(row.getCell(7)); // 抛光
                String zsDc = XSSFUtil.getStringCellValue(row.getCell(8)); // 对称
                String zsYg = XSSFUtil.getStringCellValue(row.getCell(9)); // 荧光
                String zsKd = XSSFUtil.getStringCellValue(row.getCell(10)); // 扣点
                String zsGjbj = XSSFUtil.getStringCellValue(row.getCell(11)); // rapport国际报价
                String zsCtjj = XSSFUtil.getStringCellValue(row.getCell(12)); // price/ct分数均价
                String zsMj = XSSFUtil.getStringCellValue(row.getCell(13)); // 美金价格
                String zsRmb = XSSFUtil.getStringCellValue(row.getCell(14)); // 人民币价格
                String zsPrice = XSSFUtil.getStringCellValue(row.getCell(15)); // 销售价
                Object[] checkArr = {zsKh, zsZl, zsZsh, zsYs, zsJd, zsQg, zsPg, zsDc, zsYg, zsPrice};
                for (int i = 0; i < checkArr.length; i++) {
                    if (checkArr[i] == null || StringUtils.isEmpty(String.valueOf(checkArr[i]))) {
                        String msg = "第" + (rowIndex + 1) + "行，数据格式有误，导入失败";
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return ServiceResponse.error(msg);
                    }
                }
                PartsBig partsBig = new PartsBig();
                partsBig.setZsKh(zsKh);
                partsBig.setCompany(partsBig.getZsKh().split("-")[0]);
                partsBig.setZsZl(new BigDecimal(Double.toString(NumberUtils.toDouble(zsZl, 0))).multiply(new BigDecimal(1000)).intValue());
                partsBig.setZsZsh(zsZsh);
                partsBig.setZsYs(zsYs);
                partsBig.setZsJd(zsJd);
                partsBig.setZsQg(zsQg);
                partsBig.setZsPg(zsPg);
                partsBig.setZsDc(zsDc);
                partsBig.setZsYg(zsYg);
                partsBig.setZsKd(zsKd);
                partsBig.setZsGjbj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsGjbj, 0))).multiply(new BigDecimal(100)).intValue());
                partsBig.setZsCtjj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsCtjj, 0))).multiply(new BigDecimal(100)).intValue());
                partsBig.setZsMj(new BigDecimal(Double.toString(NumberUtils.toDouble(zsMj, 0))).multiply(new BigDecimal(100)).intValue());
                partsBig.setZsRmb(new BigDecimal(Double.toString(NumberUtils.toDouble(zsRmb, 0))).multiply(new BigDecimal(100)).intValue());
                partsBig.setZsPrice(new BigDecimal(Double.toString(NumberUtils.toDouble(zsPrice, 0))).multiply(new BigDecimal(100)).intValue());
                partsBig.setLockStatus(ThirdSupplier.STONE_STATUS_UNLOCK);
                partsBig.setEnabled(SysConfig.ENABLED_TRUE);
                partsBigList.add(partsBig);
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
        //本地全部失效
        partsBigMapper.setExpire();
        //全部新增
        for (int i = 0; i < partsBigList.size(); i++) {
            PartsBig partsBig = partsBigList.get(i);
            insertNotNull(partsBig);
        }
        return ServiceResponse.succ("导入成功");
    }


    /**
     * 大克拉钻石加锁/解锁，更新状态
     *
     * @param rows
     * @param status
     */
    public void updatePartsBigDiamondLock(rows rows, Integer status) {
        PartsBig partsBig = new PartsBig();
        partsBig.setZsKh(rows.getProductid());
        if (!StringUtils.isEmpty(rows.getStoneZsh())) {
            partsBig.setZsZsh(rows.getStoneZsh());
        }
        partsBig.setEnabled(SysConfig.ENABLED_TRUE);
        PartsBig local = partsBigMapper.getPartsBig(partsBig);
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
    public PartsBig getInfoWithZsbh(String zsbh) {
        PartsBig partsBig = new PartsBig();
        if (!StringUtils.isEmpty(zsbh)) {
            partsBig.setZsZsh(zsbh);
        }
        PartsBig p = partsBigMapper.getPartsBig(partsBig);
        return p;
    }


    public ServiceResponse enableOn() {
        //本地全部上架
        int result = partsBigMapper.setEnable(1);
        return ServiceResponse.succ("上架" + result + "颗");
    }


    public ServiceResponse enableOff() {
        //本地全部下架
        int result = partsBigMapper.setEnable(0);
        return ServiceResponse.succ("下架" + result + "颗");
    }
}
