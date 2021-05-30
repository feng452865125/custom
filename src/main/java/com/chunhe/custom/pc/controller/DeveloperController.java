package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.APIUtils;
import com.chunhe.custom.framework.utils.CheckUtil;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.pc.mapper.ProductMapper;
import com.chunhe.custom.pc.model.Product;
import com.chunhe.custom.pc.model.ThirdSupplier;
import com.chunhe.custom.pc.service.ActionLogService;
import com.chunhe.custom.pc.service.OrdersService;
import com.chunhe.custom.pc.service.PartsService;
import com.chunhe.custom.pc.thirdSupplier.rows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by white 2019年3月22日10:27:25
 * 开发者测试
 */
@Controller
@RequestMapping("/developer")
public class DeveloperController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private PartsService partsService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ActionLogService actionLogService;

    @RequestMapping(value = "/list")
    public String developerList(Model model) {
        return "pages/developer/list";
    }

    /********
     * 钻石同步接口
     *******************************************************************************/

    @RequestMapping(value = "/thirdSupplier/diamond/scheduled", method = RequestMethod.POST)
    public ResponseEntity scheduledDiamond(@RequestBody Map<String, Object> map,
                                           Authentication authentication, HttpServletRequest request) {
        String company = ConvertUtil.convert(map.get("company"), String.class);
        actionLogService.createActionLog(authentication, request, "开发者-手动同步石头，" + company);
        return responseDeal(ServiceResponse.succ("同步中"));
    }

    /********
     * 钻石锁定接口
     *******************************************************************************/

    @RequestMapping(value = "/thirdSupplier/diamond/lock", method = RequestMethod.POST)
    public ResponseEntity lockDiamond(@RequestBody Map<String, Object> map,
                                      Authentication authentication, HttpServletRequest request) throws Exception {
        String company = ConvertUtil.convert(map.get("company"), String.class);
        String code = ConvertUtil.convert(map.get("code"), String.class);
        String orderCode = ConvertUtil.convert(map.get("orderCode"), String.class);
        if (StringUtils.isEmpty(code)) {
            return responseDeal(ServiceResponse.error("锁定接口，钻石编码必填"));
        }
        rows rows = new rows();
        rows.setProductid(code);
        rows.setStoneZsh(code);
        rows.setCompany(company);
        rows.setStoneOrderCharg(code);
        rows.setStoneOrderCode(orderCode);
        actionLogService.createActionLog(authentication, request, "开发者-手动加锁石头，" + company);
        return responseDeal(ServiceResponse.succ("锁定中"));
    }

    /********
     * 钻石解锁接口
     *******************************************************************************/

    @RequestMapping(value = "/thirdSupplier/diamond/unlock", method = RequestMethod.POST)
    public ResponseEntity unlockDiamond(@RequestBody Map<String, Object> map,
                                        Authentication authentication, HttpServletRequest request) throws Exception {
        String company = ConvertUtil.convert(map.get("company"), String.class);
        String code = ConvertUtil.convert(map.get("code"), String.class);
        String orderCode = ConvertUtil.convert(map.get("orderCode"), String.class);
        if (StringUtils.isEmpty(code)) {
            return responseDeal(ServiceResponse.error("锁定接口，钻石编码必填"));
        }
        rows rows = new rows();
        rows.setProductid(code);
        rows.setStoneZsh(code);
        rows.setCompany(company);
        rows.setStoneOrderCharg(code);
        rows.setStoneOrderCode(orderCode);
        actionLogService.createActionLog(authentication, request, "开发者-手动解锁石头，" + company);
        return responseDeal(ServiceResponse.succ("解锁中"));
    }

    /********
     * 钻石下单接口
     *******************************************************************************/

    @RequestMapping(value = "/thirdSupplier/diamond/order", method = RequestMethod.POST)
    public ResponseEntity orderDiamond(@RequestBody Map<String, Object> map,
                                       Authentication authentication, HttpServletRequest request) throws Exception {
        String company = ConvertUtil.convert(map.get("company"), String.class);
        String code = ConvertUtil.convert(map.get("code"), String.class);
        rows rows = new rows();
        rows.setProductid(code);
        if (company.equals("JP")) {

        } else if (company.equals("CHINASTAR")) {

        } else if (company.equals("DHA")) {

        } else if (company.equals("PG")) {

        } else if (company.equals("HB")) {

        } else if (company.equals("DIAMART")) {

        } else if (company.equals("HB2")) {

        } else if (company.equals("HAO")) {
        } else if (company.equals("EX3")) {
        } else {
            return responseDeal(ServiceResponse.error("请选择公司"));
        }
        actionLogService.createActionLog(authentication, request, "开发者-手动下单石头，" + company);
        return responseDeal(ServiceResponse.succ("下单中"));
    }



}
