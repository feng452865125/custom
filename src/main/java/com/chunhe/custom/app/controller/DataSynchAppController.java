package com.chunhe.custom.app.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.pc.sapResponse.XMLSapResponse;
import com.chunhe.custom.pc.sapResponse.XMLSapResult;
import com.chunhe.custom.pc.service.DataSynchService;
import com.chunhe.custom.pc.service.SapSkuService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by white 2018年8月13日14:49:10 数据同步管理
 */
@RestController
@RequestMapping("/app/dataSynch")
public class DataSynchAppController extends BaseController {

    @Autowired
    private DataSynchService dataSynchService;

    @Autowired
    private SapSkuService sapSkuService;

//    /**
//     * 对方调用，新增数据，或修改数据
//     *
//     * @param req
//     * @return
//     */
//    @RequestMapping(value = "/sapSku", method = RequestMethod.POST)
//    @ResponseBody
//    public String create(HttpServletRequest req) {
//        SapSku sapSku = new SapSku();
//        String content = req.getParameter("content");
//        if (!CheckUtil.checkNull(content)) {
//            sapSku = JSON.parseObject(content, SapSku.class);
//        }
//        sapSkuService.dataSynchSapSku(sapSku.getDataList());
//        throw new RFException(APIUtils.STATUS_CODE_SUCCESS, APIUtils.MESSAGE_SAVE_SUCCESS);
//    }


    /**
     * sku
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/sku", method = RequestMethod.POST)
    @ResponseBody
    public String sku(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String ss = "", xml = "";
        while ((ss = br.readLine()) != null) {
            xml += ss;
        }
//        String result = "特殊处理，拦截";
        String result = sapSkuService.toBeanSapSku(xml);
        return this.sapResponse(result);
    }

    /**
     * enum
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/enumCode", method = RequestMethod.POST)
    @ResponseBody
    public String enumCode(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String ss = "", xml = "";
        while ((ss = br.readLine()) != null) {
            xml += ss;
        }
        String result = "特殊处理，拦截";
//        String result = sapSkuService.insertEnumCode(xml);
        return this.sapResponse(result);
    }

    /**
     * posCard
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/posCard", method = RequestMethod.POST)
    @ResponseBody
    public String posCard(HttpServletRequest req) throws IOException {
        BufferedReader br = req.getReader();
        String ss = "", xml = "";
        while ((ss = br.readLine()) != null) {
            xml += ss;
        }
//        String result = "特殊处理，拦截";
        String result = sapSkuService.toBeanPosCard(xml);
        return this.sapResponse(result);
    }



//    /**
//     * 原json格式传唤（弃用）
//     * @param req
//     * @return
//     */
//    public String checkXML(HttpServletRequest req) {
//        String xml = req.getParameter("xml");
//        if (xml == null || xml.equals("")) {
//            throw new RFException("xml is null");
//        }
//        List<String> list = Arrays.asList(xml.split("\\{"));
//        if (list.size() == 2) {
//            xml = list.get(1);
//        }
//        return xml;
//    }

    /**
     * xml格式
     *
     * @param result
     * @return
     */
    public String sapResponse(String result) {
        List<String> list = Arrays.asList(result.split("##"));
        if (list == null || list.size() != 2) {
            return "error";
        }
        String isSuccess = list.get(0);
        String tip = list.get(1);
        XMLSapResponse xmlSapResponse = new XMLSapResponse();
        XMLSapResult xmlSapResult = new XMLSapResult();
        xmlSapResult.setResult(isSuccess);
        xmlSapResult.setTip(tip);
        xmlSapResponse.setXMLSapResult(xmlSapResult);

        String headXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        //xml自身下划线为关键字，要转换
        XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
        xStream.processAnnotations(XMLSapResponse.class);
        String xml = xStream.toXML(xmlSapResponse);
        xml = headXML + xml;
        return xml;
    }
}
