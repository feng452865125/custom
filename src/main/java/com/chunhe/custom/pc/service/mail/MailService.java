package com.chunhe.custom.pc.service.mail;

import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.service.SysUserService;
import com.chunhe.custom.framework.utils.MailUtils;
import com.chunhe.custom.pc.mapper.PartsBigMapper;
import com.chunhe.custom.pc.mapper.PartsMapper;
import com.chunhe.custom.pc.model.BasePrice;
import com.chunhe.custom.pc.model.Orders;
import com.chunhe.custom.pc.model.Task;
import com.chunhe.custom.pc.model.*;
import com.chunhe.custom.pc.service.BasePriceService;
import com.chunhe.custom.pc.service.OrdersService;
import com.chunhe.custom.pc.service.ProductService;
import com.chunhe.custom.pc.thirdSupplier.rows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MailService {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BasePriceService basePriceService;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private PartsBigMapper partsBigMapper;


    @Value("${orderTitle}")
    public static String orderTitle;

    @Value("${cancelTitle}")
    private String cancelTitle;

    @Value("${recipientAddress}")
    public String recipientAddress;

    @Value("${mailSmtp}")
    public static String mailSmtp;

    @Value("${senderAddress}")
    public static String senderAddress;

    @Value("${senderAccount}")
    public String senderAccount;

    @Value("${senderPassword}")
    public String senderPassword;


    /**
     * Diamart 、chinastar、pg 系统下单（发邮件）
     *
     * @param rows
     */
    @Transactional
    public void createOrder(rows rows) throws Exception {
        //邮件部分
        String title = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_ORDER_TITLE, String.valueOf(orderTitle));
        String address = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_RECIPIENT_ADDRESS, String.valueOf(recipientAddress));
        String[] addressArray = address.split(",");
        String company = StringUtils.isEmpty(rows.getCompany()) ? "nothing" : rows.getCompany();
        this.sendMailMessage(rows, title, addressArray, company, null);
    }


    //取消订单
    public void cancelOrder(rows rows) throws Exception {
        //邮件部分
        String title = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_CANCEL_TITLE, String.valueOf(cancelTitle));
        String address = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_RECIPIENT_ADDRESS, String.valueOf(recipientAddress));
        String[] addressArray = address.split(",");
        this.sendMailMessage(rows, title, addressArray, rows.getCompany(), "cancelOrder");
    }


    /**
     * 发送邮件，下单/取消
     *
     * @param rows
     * @param title
     * @throws Exception
     */
    public void sendMailMessage(rows rows, String title, String[] addressArray, String company, String type) throws Exception {
        if (rows.getProductid().equals("")) {
            throw new RFException("钻石编码为空");
        }
        Orders orders = ordersService.selectByKey(rows.getOrderId());
        SysUser user = sysUserService.selectByKey(new Long(orders.getUserId()));
        //邮件部分 固定的
        String smtp = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_SMTP, String.valueOf(mailSmtp));
        //发件人邮箱地址
        String sAddress = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ADDRESS, String.valueOf(senderAddress));
        //发件人邮箱账号
        String sAccount = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ACCOUNT, String.valueOf(senderAccount));
        //发件人邮箱账号
        String sPassword = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_PASSWORD, String.valueOf(senderPassword));

        String tip = "";
        if (!"cancelOrder".equals(type)) {
            if ("JP".equals(company.toUpperCase())
                    || "KBS".equals(company.toUpperCase())
                    || "HAO".equals(company.toUpperCase())
                    || "EX3".equals(company.toUpperCase())
                    || "JP2".equals(company.toUpperCase())) {
                tip = ",   注意：系统已经对该订单的裸石自动下采购订单。";
            }
        }
        //取消订单时提示
        String cancelOrder = "";
        if ("cancelOrder".equals(type)) {
            cancelOrder = "注:该订单已经取消，请取消对石头编码:" + rows.getProductid() +
                    "证书号码：" + rows.getStoneZsh() + "的裸石采购";
        }
        //美元或者人民币设置
        String unit = "";
        if ("CHINASTAR".equals(company.toUpperCase())
                || "HB".equals(company.toUpperCase())
                || "JP".equals(company.toUpperCase())
                || "PG".equals(company.toUpperCase())
                || "DIAMART".equals(company.toUpperCase())
                || "OPO".equals(company.toUpperCase())
                || "DHA".equals(company.toUpperCase())
                || "KBS".equals(company.toUpperCase())
                || "HB2".equals(company.toUpperCase())
                || "KDL".equals(company.toUpperCase())
                || "HST".equals(company.toUpperCase())) {
            unit = "元（美元）";
        } else {
            unit = "元（人民币）";
        }
        for (int i = 0; i < addressArray.length; i++) {
            System.out.println("准备发送邮件，收件人：" + addressArray[i]);
            String content = "订单编号:" + orders.getCode() + "，空托码:" + orders.getKtCode() + "，石头编码:" + rows.getProductid()
                    + "，证书号码：" + rows.getStoneZsh() + "，寓意：" + rows.getProductYy()
                    + "，颜色：" + rows.getStoneYs() + "，净度：" + rows.getStoneJd()
                    + "，重量：" + rows.getStoneZl()
                    + "，采购价格：" + rows.getStoneCgj() + unit
                    + "，供应商名称：" + rows.getCompany()
                    + "，门店编码：" + user.getUsername() + ",店铺名：" + user.getName() + "，店铺联系电话：" + user.getPos()
                    + tip + cancelOrder;
            MailUtils.sendMail(smtp, sAddress, sAccount, sPassword, addressArray[i], title + "/" + user.getName(), content);
        }

    }


    /**
     * KEER
     *
     * @param rows
     * @throws Exception
     */
    public void createNoDiamond(rows rows) throws Exception {
        Orders orders = ordersService.selectByKey(rows.getOrderId());
        SysUser user = sysUserService.selectByKey(new Long(orders.getUserId()));
        BasePrice basePrice = basePriceService.selectByKey(new Long(orders.getBaseId()));
        //邮件部分
        String smtp = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_SMTP, String.valueOf(mailSmtp));
        String sAddress = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ADDRESS, String.valueOf(senderAddress));
        String sAccount = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ACCOUNT, String.valueOf(senderAccount));
        String sPassword = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_PASSWORD, String.valueOf(senderPassword));
        String rAddress = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_RECIPIENT_ADDRESS, String.valueOf(recipientAddress));
        String title = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_ORDER_TITLE, String.valueOf(orderTitle));
        System.out.println("准备发送邮件，收件人：" + rAddress);
        String content = "4C标准，基价id:" + basePrice.getId()
                + "，颜色：" + basePrice.getYs() + "，净度：" + basePrice.getJd()
                + "，切工：" + basePrice.getQg() + "，抛光：" + basePrice.getPg()
                + "，荧光：" + basePrice.getYg() + "，对称：" + basePrice.getDc()
                + "，重量：" + Float.toString(basePrice.getKlMin().floatValue() / 1000)
                + "，店铺名：" + user.getName() + "，店铺联系电话：" + user.getPos();

        String[] rAddressArray = rAddress.split(",");
        for (int i = 0; i < rAddressArray.length; i++) {
            MailUtils.sendMail(smtp, sAddress, sAccount, sPassword, rAddressArray[i], title, content);
        }
    }

    /**
     * 调用接口次数上限，发邮件通知业务部门
     *
     * @param task
     * @throws Exception
     */
    public void orderFailMail(Task task) throws Exception {
        //邮件部分
        String smtp = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_SMTP, String.valueOf(mailSmtp));
        String sAddress = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ADDRESS, String.valueOf(senderAddress));
        String sAccount = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_ACCOUNT, String.valueOf(senderAccount));
        String sPassword = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_SENDER_PASSWORD, String.valueOf(senderPassword));
        String rAddress = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_RECIPIENT_ADDRESS, String.valueOf(recipientAddress));
        String title = "[注意！下单连续失败三次！]";
        System.out.println("准备发送邮件，收件人：" + rAddress);
        String content = "定时任务id：" + task.getId() + "（task表）"
                + "，关联订单号：" + task.getOrderId() + "(orders表)"
                + "，所属公司" + task.getCompany()
                + "，钻石编号：" + task.getProductid()
                + "<br>==========================分割线（建议反馈技术员）============================<br>"
                + task.getContent();

        String[] rAddressArray = rAddress.split(",");
        for (int i = 0; i < rAddressArray.length; i++) {
            MailUtils.sendMail(smtp, sAddress, sAccount, sPassword, rAddressArray[i], title, content);
        }
    }
}
