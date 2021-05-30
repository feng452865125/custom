package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.pc.mapper.DataSynchMapper;
import com.chunhe.custom.pc.model.DataSynch;
import com.chunhe.custom.pc.model.SysUserDetail;
import com.chunhe.custom.pc.util.DateUtil;
import com.chunhe.custom.pc.xml.*;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserDetailService;
import com.chunhe.custom.pc.xml.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by white 2018-8-14 09:30:54
 */
@Service
public class DataSynchService extends BaseService<DataSynch> {

    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DataSynchMapper dataSynchMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDetailService sysUserDetailService;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${defaultPassword}")
    private static String defaultPassword = "123";

    @Value("${storePriceMultiple}")
    private static String storePriceMultiple = "1.8";

    @Value("${keerokUrl}")
    private static String keerokUrl = "http://192.168.58.73:9080/TBAPApplicationService/SoapBoxer/";

    // list里展示的最新的，orderBy类型
    @Transactional
    public List<DataSynch> findDataSynchOrderByTypeList(DataSynch dataSynch) {
        List<DataSynch> dataSynchList = dataSynchMapper.findDataSynchOrderByTypeList(dataSynch);
        for (int i = 0; i < dataSynchList.size(); i++) {
            DataSynch data = dataSynchList.get(i);
            if (data.getUserCode() != null) {
                SysUser user = sysUserService.selectByUsername(data.getUserCode());
                data.setUserName(user != null ? user.getName() : "Auto");
            }
        }
        return dataSynchList;
    }

    /**
     * 详情里的所有同步数据的过程
     *
     * @param id
     * @return
     */
    @Transactional
    public DataSynch getDataSynch(Long id) {
        DataSynch dataSynch = new DataSynch();
        dataSynch.setId(id);
        DataSynch data = dataSynchMapper.getDataSynch(dataSynch);
        return data;
    }

    /**
     * 查询数据
     */
    @Transactional
    public List<DataSynch> dataSynchList(DataTablesRequest dataTablesRequest) {
        DataSynch dataSynch = new DataSynch();
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            dataSynch.setOrderBy(orders);
        }
        List<DataSynch> dataSynchList = this.findDataSynchOrderByTypeList(dataSynch);
        return dataSynchList;
    }

    /**
     * 详情里，某同步的所有日志
     *
     * @param dataTablesRequest
     * @param id
     * @return
     */
    @Transactional
    public List<DataSynch> findDataSynchList(DataTablesRequest dataTablesRequest, long id) {
        DataSynch dataSynch = new DataSynch();
        dataSynch.setId(id);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            dataSynch.setOrderBy(orders);
        }
        //同type，同resource
        List<DataSynch> dataSynchList = dataSynchMapper.findDataSynchList(dataSynch);
        return dataSynchList;
    }

    /**
     * create by xiaobai
     */
    @Transactional
    public ServiceResponse dataSynch(Long id, String username) throws IOException {
        DataSynch dataSynch = dataSynchMapper.selectByPrimaryKey(id);
        if (dataSynch.getType() == DataSynch.DATA_SYNCH_STORE) {
            return this.store(username);
        } else if (dataSynch.getType() == DataSynch.DATA_SYNCH_STORE_USER) {
            return this.storeUser(username);
        }
        return ServiceResponse.error("同步异常");
    }

    /**
     * 定时同步店铺信息
     */
    @Async
    @Scheduled(cron = "0 3 0 1/1 * ? ")
    public ServiceResponse scheduledDataSynch() throws IOException {
        ServiceResponse result = this.store("Auto");
        return result;
    }

    /**
     * 同步店铺信息
     *
     * @return
     */
    @Transactional
    public ServiceResponse store(String username) throws IOException {
        System.out.println("店铺同步，开始，" + DateUtil.dateToStr(new Date()));
        long startTime = System.currentTimeMillis() / 1000;
        XMLRequest xmlRequest = new XMLRequest("2.0", "{BDC156DA-D525-4247-B32C-2E0431E3585D}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{673AA083-ED24-401A-A76E-37DFB56D9391}", "", "3", "true");
        Select select = new Select();
        SQLParameters sqlParameters = new SQLParameters();

        select.setSQLParameters(sqlParameters);
        sqlBuilderItem.setSelect(select);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String xml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);
        String result = xmlUtil.toPost(xml, posUrl);
        // String result = testXML1;
        XMLResponse response = xmlUtil.XMLToBean(result);
        if (response == null || response.getXMLResult() != true) {
            DataSynch data = new DataSynch();
            data.setName(DataSynch.DATA_SYNCH_STORE_NAME);
            data.setType(DataSynch.DATA_SYNCH_STORE);
            data.setResource(DataSynch.DATA_SYNCH_RESOURCE_POS);
            data.setUserCode("无法获取数据，同步失败");
            insertNotNull(data);
            return ServiceResponse.error("无法获取数据，同步失败");
        }
        //先拿数据库配置里的默认密码，门店的店铺默认销售系数
        String password = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_DEFAULT_PASSWORD, defaultPassword);
        String priceMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        // 请求到的店铺数据，本地的店铺数据
        ROW[] rowList = response.getXMLMetaData().getXMLDataSet().getXMLDataTable().getROWDATA().getROW();
        List<SysUser> userList = sysUserService.findSysUserList();
        for (int i = 0; i < rowList.length; i++) {
            ROW rr = rowList[i];
            if (this.checkServerInLocalStore(userList, rr)) {
//                System.out.println("店铺信息，更新" + rr.getFCODE());
            } else {
//                System.out.println("店铺信息，新增" + rr.getFCODE());
                // 新插入数据
                SysUser newUser = new SysUser();
                newUser.setUsername(rr.getFCODE());
                newUser.setPassword(bCryptPasswordEncoder.encode(password));
                newUser.setName(rr.getFNAME());
                newUser.setIsSystem(SysUser.IS_SYSTEM_FALSE);
                newUser.setLocked(SysUser.LOCKED_FALSE);
                newUser.setEnabled(SysUser.ENABLED_FALSE);
                newUser.setAddress(rr.getFDZ());
                newUser.setStatus(rr.getFSTATE().equals(SysUser.USER_STATUS_OPEN) ? 1 : 5);
                newUser.setPriceMultiple(priceMultiple);
                sysUserService.insertNotNull(newUser);
            }
        }
        for (int i = 0; i < userList.size(); i++) {
            SysUser user = userList.get(i);
            if (!this.checkLocalInServerStore(rowList, user)) {
//                System.out.println("店铺信息，失效" + user.getUsername());
                // 失效
                if(user.getIsSystem() != SysUser.IS_SYSTEM_TRUE){
                    sysUserService.expireNotNull(user);
                }
            }
        }
        System.out.println("店铺同步，结束，持续" + (System.currentTimeMillis() / 1000 - startTime));
        DataSynch dataSynch = new DataSynch();
        dataSynch.setName(DataSynch.DATA_SYNCH_STORE_NAME);
        dataSynch.setType(DataSynch.DATA_SYNCH_STORE);
        dataSynch.setResource(DataSynch.DATA_SYNCH_RESOURCE_POS);
        dataSynch.setUserCode(username == null || "".equals(username) ? "Auto" : username);
        if (insertNotNull(dataSynch) != 1) {
            return ServiceResponse.error("同步失败");
        }
        return ServiceResponse.succ("同步完成");
    }

    /**
     * 定时同步店员信息
     */
    @Async
    @Scheduled(cron = "0 6 0 1/1 * ? ")
    public ServiceResponse scheduledDataSynch1() throws IOException {
        System.out.println("-----------------------");
        ServiceResponse result = this.storeUser("Auto");
        return result;
    }

    /**
     * 同步店员信息
     *
     * @return
     */
    @Transactional
    public ServiceResponse storeUser(String username) throws IOException {
        System.out.println("店铺同步，开始，" + DateUtil.dateToStr(new Date()));
        long startTime = System.currentTimeMillis() / 1000;
        XMLRequest xmlRequest = new XMLRequest("2.0", "{BDC156DA-D525-4247-B32C-2E0431E3585D}");
        XMLParams xmlParams = new XMLParams();
        XMLMetaData xmlMetaData = new XMLMetaData();
        SQLBuilderItem sqlBuilderItem = new SQLBuilderItem("{94DAA7CE-D1B2-4788-9A2F-79C26A0918A8}", "", "3", "true");
        Select select = new Select();
        SQLParameters sqlParameters = new SQLParameters();
        ROW row = new ROW();
        row.setFORG_CODE("");// 门店编码
        sqlParameters.setROW(row);
        select.setSQLParameters(sqlParameters);
        sqlBuilderItem.setSelect(select);
        xmlMetaData.setSQLBuilderItem(sqlBuilderItem);
        xmlRequest.setXMLParams(xmlParams);
        xmlRequest.setXMLMetaData(xmlMetaData);

        System.out.println(xmlRequest);
        XMLUtil xmlUtil = new XMLUtil();
        String xml = xmlUtil.beanToXML(xmlRequest);
        //先拿数据库配置里的pos下单地址
        String posUrl = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_ORDER_POS, keerokUrl);
        String result = xmlUtil.toPost(xml, posUrl);
        // String result = testXML2;
        XMLResponse response = xmlUtil.XMLToBean(result);
        if (response == null || response.getXMLResult() != true) {
            DataSynch data = new DataSynch();
            data.setName(DataSynch.DATA_SYNCH_STORE_USER_NAME);
            data.setType(DataSynch.DATA_SYNCH_STORE_USER);
            data.setResource(DataSynch.DATA_SYNCH_RESOURCE_POS);
            data.setUserCode("无法获取数据，同步失败");
            insertNotNull(data);
            return ServiceResponse.error("无法获取数据，同步失败");
        }
        // 请求到的员工数据，本地的员工数据
        ROW[] rowList = response.getXMLMetaData().getXMLDataSet().getXMLDataTable().getROWDATA().getROW();
        List<SysUserDetail> userDetailList = sysUserDetailService.findSysUserDetailList(new SysUserDetail());
        for (int i = 0; i < rowList.length; i++) {
            ROW rr = rowList[i];
            if (this.checkServerInLocalStoreDetail(userDetailList, rr)) {
//                System.out.println("店员信息，更新" + rr.getFCODE());
            } else {
//                System.out.println("店员信息，新增" + rr.getFCODE());
                // 新插入数据
                SysUserDetail sysUserDetail = new SysUserDetail();
                sysUserDetail.setStoreId(rr.getFORG_CODE());
                sysUserDetail.setCode(rr.getFCODE());
                sysUserDetail.setName(rr.getFNAME());
                // sysUserDetail.setJob();
                sysUserDetailService.insertNotNull(sysUserDetail);
            }

        }
        for (int i = 0; i < userDetailList.size(); i++) {
            SysUserDetail userDetail = userDetailList.get(i);
            if (!this.checkLocalInServerStoreDetail(rowList, userDetail)) {
//                System.out.println("店员信息，失效" + userDetail.getCode());
                // 失效
                sysUserDetailService.expireNotNull(userDetail);
            }
        }
        System.out.println("店员同步，结束，持续" + (System.currentTimeMillis() / 1000 - startTime));
        DataSynch dataSynch = new DataSynch();
        dataSynch.setName(DataSynch.DATA_SYNCH_STORE_USER_NAME);
        dataSynch.setType(DataSynch.DATA_SYNCH_STORE_USER);
        dataSynch.setResource(DataSynch.DATA_SYNCH_RESOURCE_POS);
        dataSynch.setUserCode(username == null || "".equals(username) ? "Auto" : username);
        if (insertNotNull(dataSynch) != 1) {
            return ServiceResponse.error("同步失败");
        }
        return ServiceResponse.succ("同步完成");
    }

    /**
     * 对方数据在本地（店铺信息） 1、对方有，本地有（更新数据） 2、对方有，本地无（新插入数据）
     *
     * @param userList
     * @param row
     * @return
     */
    public Boolean checkServerInLocalStore(List<SysUser> userList, ROW row) {
        for (int i = 0; i < userList.size(); i++) {
            SysUser user = userList.get(i);
            if (row.getFCODE().equals(user.getUsername())) {
                // 更新本地
                user.setName(row.getFNAME());
                user.setAddress(row.getFDZ());
                user.setStatus(row.getFSTATE().equals(SysUser.USER_STATUS_OPEN) ? 1 : 5);
                sysUserService.updateNotNull(user);
                return true;
            }
        }
        return false;
    }

    /**
     * 本地数据在对方（店铺信息） 3、本地有，对方无（失效处理）
     *
     * @param rowList
     * @param user
     * @return
     */
    public Boolean checkLocalInServerStore(ROW[] rowList, SysUser user) {
        for (int i = 0; i < rowList.length; i++) {
            ROW row = rowList[i];
            if (row.getFCODE().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对方数据在本地（员工信息） 1、对方有，本地有（更新数据） 2、对方有，本地无（新插入数据）
     *
     * @param userDetailList
     * @param row
     * @return
     */
    public Boolean checkServerInLocalStoreDetail(List<SysUserDetail> userDetailList, ROW row) {
        for (int i = 0; i < userDetailList.size(); i++) {
            SysUserDetail userDetail = userDetailList.get(i);
            if (row.getFCODE().equals(userDetail.getCode())) {
                // 更新本地
                userDetail.setStoreId(row.getFORG_CODE());
                userDetail.setCode(row.getFCODE());
                userDetail.setName(row.getFNAME());
                // userDetail.setJob();
                sysUserDetailService.updateNotNull(userDetail);
                return true;
            }
        }
        return false;
    }

    /**
     * 本地数据在对方（员工信息） 3、本地有，对方无（失效处理）
     *
     * @param rowList
     * @param userDetail
     * @return
     */
    public Boolean checkLocalInServerStoreDetail(ROW[] rowList, SysUserDetail userDetail) {
        for (int i = 0; i < rowList.length; i++) {
            ROW row = rowList[i];
            if (row.getFCODE().equals(userDetail.getCode())) {
                return true;
            }
        }
        return false;
    }

    public static String testXML1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<XMLResponse>\n" + "  <XMLResult>True</XMLResult>\n"
            + "  <XMLMessage FMESSAGEID=\"\" FMESSAGEINDEX=\"0\" FREMARK=\"接收请求，并成功返回结果集！\" FMESSAGEINFO=\"脚本 {673AA083-ED24-401A-A76E-37DFB56D9391}：\n"
            + "SELECT A.FCODE,A.FNAME,B.FCODE AS FORG_STORE_CODE,B.FNAME AS FORG_STORE_NAME\n" + "FROM BN_SYS_ORG A\n"
            + "LEFT JOIN BN_SYS_ORG B ON A.FNCODE=B.FPCODE AND B.FINDEX='33'\n" + "WHERE A.FINDEX='32'\n"
            + "ORDER BY A.FNCODE,B.FCODE\n" + "\n" + "\" />\n" + "  <XMLMetaData>\n"
            + "    <XMLDataSet SQLBuilderID=\"{673AA083-ED24-401A-A76E-37DFB56D9391}\">\n" + "      <XMLDataTable>\n"
            + "        <METADATA>\n" + "          <FIELDS>\n"
            + "            <FIELD attrname=\"FCODE\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FNAME\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FORG_STORE_CODE\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FORG_STORE_NAME\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "          </FIELDS>\n" + "          <PARAMS />\n" + "        </METADATA>\n" + "        <ROWDATA>\n"
            + "          <ROW FCODE=\"5547\" FNAME=\"合肥包河万达百货\" FORG_STORE_CODE=\"55470001\" FORG_STORE_NAME=\"合肥包河万达百货商品柜\" />\n"
            + "          <ROW FCODE=\"5547\" FNAME=\"合肥包河万达百货\" FORG_STORE_CODE=\"55470003\" FORG_STORE_NAME=\"合肥包河万达百货巡展柜\" />\n"
            + "          <ROW FCODE=\"5065\" FNAME=\"北京汉光百货店\" FORG_STORE_CODE=\"50650001\" FORG_STORE_NAME=\"北京汉光百货店商品柜\" />\n"
            + "          <ROW FCODE=\"5315\" FNAME=\"武汉新世界中心店\" FORG_STORE_CODE=\"53150001\" FORG_STORE_NAME=\"武汉新世界中心店商品柜\" />\n"
            + "          <ROW FCODE=\"5315\" FNAME=\"武汉新世界中心店\" FORG_STORE_CODE=\"53150003\" FORG_STORE_NAME=\"武汉新世界中心店巡展柜\" />\n"
            + "          <ROW FCODE=\"5322\" FNAME=\"合肥百大CBD\" FORG_STORE_CODE=\"53220001\" FORG_STORE_NAME=\"合肥百大CBD商品柜\" />\n"
            + "          <ROW FCODE=\"5322\" FNAME=\"合肥百大CBD\" FORG_STORE_CODE=\"53220007\" FORG_STORE_NAME=\"合肥百大CBD维修柜\" />\n"
            + "          <ROW FCODE=\"5301\" FNAME=\"无锡八佰伴商茂中心店\" FORG_STORE_CODE=\"53010003\" FORG_STORE_NAME=\"无锡八佰伴商茂中心店巡展柜\" />\n"
            + "          <ROW FCODE=\"5030\" FNAME=\"武汉光谷步行街旗舰店\" FORG_STORE_CODE=\"50300002\" FORG_STORE_NAME=\"武汉光谷步行街旗舰店商品柜\" />\n"
            + "        </ROWDATA>\n" + "      </XMLDataTable>\n" + "    </XMLDataSet>\n" + "  </XMLMetaData>\n"
            + "</XMLResponse>";

    public static String testXML2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<XMLResponse>\n" + "  <XMLResult>True</XMLResult>\n"
            + "  <XMLMessage FMESSAGEID=\"\" FMESSAGEINDEX=\"0\" FREMARK=\"接收请求，并成功返回结果集！\" FMESSAGEINFO=\"脚本 {673AA083-ED24-401A-A76E-37DFB56D9391}：\n"
            + "SELECT A.FCODE,A.FNAME,B.FCODE AS FORG_STORE_CODE,B.FNAME AS FORG_STORE_NAME\n" + "FROM BN_SYS_ORG A\n"
            + "LEFT JOIN BN_SYS_ORG B ON A.FNCODE=B.FPCODE AND B.FINDEX='33'\n" + "WHERE A.FINDEX='32'\n"
            + "ORDER BY A.FNCODE,B.FCODE\n" + "\n" + "\" />\n" + "  <XMLMetaData>\n"
            + "    <XMLDataSet SQLBuilderID=\"{94DAA7CE-D1B2-4788-9A2F-79C26A0918A8}\">\n" + "      <XMLDataTable>\n"
            + "        <METADATA>\n" + "          <FIELDS>\n"
            + "            <FIELD attrname=\"FCODE\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FNAME\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FORG_CODE\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "            <FIELD attrname=\"FORG_NAME\" fieldtype=\"String\" WIDTH=\"50\" />\n"
            + "          </FIELDS>\n" + "          <PARAMS />\n" + "        </METADATA>\n" + "        <ROWDATA>\n"
            + "          <ROW FCODE=\"10003\" FNAME=\"测试账号3\" FORG_CODE=\"5311\" FORG_NAME=\"上海太平洋徐家汇店\" />\n"
            + "          <ROW FCODE=\"10010\" FNAME=\"营业员1\" FORG_CODE=\"5322\" FORG_NAME=\"合肥百大CBD\" />\n"
            + "          <ROW FCODE=\"10011\" FNAME=\"营业员2\" FORG_CODE=\"5322\" FORG_NAME=\"合肥百大CBD\" />\n"
            + "          <ROW FCODE=\"10012\" FNAME=\"营业员3\" FORG_CODE=\"5322\" FORG_NAME=\"合肥百大CBD\" />\n"
            + "          <ROW FCODE=\"13600060006\" FNAME=\"麦凯乐店长\" FORG_CODE=\"5728\" FORG_NAME=\"大商哈尔滨麦凯乐店\" />\n"
            + "          <ROW FCODE=\"13600070007\" FNAME=\"中央商城店长\" FORG_CODE=\"5729\" FORG_NAME=\"中央红哈尔滨中央商城店\" />\n"
            + "          <ROW FCODE=\"13810575960\" FNAME=\"赵明\" FORG_CODE=\"5002\" FORG_NAME=\"北京新华百货店\" />\n"
            + "          <ROW FCODE=\"13910861260\" FNAME=\"董松\" FORG_CODE=\"5528\" FORG_NAME=\"北京昌平鼓楼南大街店\" />\n"
            + "          <ROW FCODE=\"15800521478\" FNAME=\"柳鹏\" FORG_CODE=\"5090\" FORG_NAME=\"襄阳华洋堂店\" />\n"
            + "          <ROW FCODE=\"302060\" FNAME=\"彭淑绢JM\" FORG_CODE=\"5643\" FORG_NAME=\"河南三门峡万达店\" />\n"
            + "          <ROW FCODE=\"302062\" FNAME=\"刘雅娇WHJM\" FORG_CODE=\"5609\" FORG_NAME=\"武汉经开万达广场店\" />\n"
            + "          <ROW FCODE=\"302064\" FNAME=\"姜胜云JM\" FORG_CODE=\"5714\" FORG_NAME=\"鄂州南浦国际广场店-新\" />\n"
            + "          <ROW FCODE=\"302065\" FNAME=\"张曼娇JM\" FORG_CODE=\"5299\" FORG_NAME=\"北京龙湖长楹天街店\" />\n"
            + "          <ROW FCODE=\"302066\" FNAME=\"李英JM\" FORG_CODE=\"5288\" FORG_NAME=\"南充王府井店\" />\n"
            + "          <ROW FCODE=\"302067\" FNAME=\"刘娅婧JM\" FORG_CODE=\"5612\" FORG_NAME=\"邢台清河城家乐园店\" />\n"
            + "          <ROW FCODE=\"302068\" FNAME=\"陈红BJJM\" FORG_CODE=\"5633\" FORG_NAME=\"北京西铁营万达广场店\" />\n"
            + "          <ROW FCODE=\"302070\" FNAME=\"王丽芳FJJM\" FORG_CODE=\"5635\" FORG_NAME=\"福建晋江SM百货店\" />\n"
            + "          <ROW FCODE=\"5002\" FNAME=\"直营测试2\" FORG_CODE=\"5322\" FORG_NAME=\"合肥百大CBD\" />\n"
            + "        </ROWDATA>\n" + "      </XMLDataTable>\n" + "    </XMLDataSet>\n" + "  </XMLMetaData>\n"
            + "</XMLResponse>";


}
