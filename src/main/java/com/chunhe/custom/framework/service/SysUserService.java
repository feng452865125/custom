package com.chunhe.custom.framework.service;

import com.chunhe.custom.pc.mapper.SysUserTokenMapper;
import com.chunhe.custom.pc.model.SysUserToken;
import com.chunhe.custom.pc.service.SysUserTokenService;
import com.chunhe.custom.pc.util.DateUtil;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.exception.RFException;
import com.chunhe.custom.framework.mapper.SysUserMapper;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.model.SysUserRole;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.utils.ConvertUtil;
import com.chunhe.custom.framework.utils.TableUtil;
import com.chunhe.custom.framework.validator.SystemConstant;
import com.chunhe.custom.utils.DictUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class SysUserService extends MyService<SysUser> {

    static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysUserTokenMapper sysUserTokenMapper;

    @Autowired
    private SysUserTokenService sysUserTokenService;


    @Value("${nearby.distance}")
    private int nearbyDistance = 20000;

    @Value("${uuidSwitch}")
    private String uuidSwitch = "0";

    @Value("${storePriceMultiple}")
    private String storePriceMultiple = "1";

    @Value("${tokenTime}")
    private String tokenTime = "30";

    @Value("${isLoginSameTime}")
    private String isLoginSameTime = "1";

    /**
     * 同步数据时用到，所有用户信息（门店）
     *
     * @return
     */
    public List<SysUser> findSysUserList() {
        List<SysUser> sysUserList = sysUserMapper.findSysUserList();
        return sysUserList;
    }

    /**
     * 用户编码是否重复，code
     * 登录账号是否重复，username
     */
    public Boolean isExistByParam(Long id, String property, String value) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(property, value)
                .andIsNull("expireDate");
        if (null != id) {
            criteria.andNotEqualTo("id", id);
        }
        return getMapper().selectCountByExample(example) > 0;
    }

    /**
     * 通过username找数据
     */
    public SysUser selectByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setEnabled(true);
        sysUser.setLocked(false);
        return sysUserMapper.selectByUsername(sysUser);
    }

    /**
     * 查询数据
     */
    public List<SysUser> sysUserList(DataTablesRequest dataTablesRequest) {
        Example example = new Example(SysUser.class);
        String orders = dataTablesRequest.orders();
        if (StringUtils.isNotBlank(orders)) {
            example.setOrderByClause(orders);
        }
        //店铺编码（登录账号）
        Example.Criteria criteria = example.createCriteria();
        DataTablesRequest.Column username = dataTablesRequest.getColumn("username");
        if (StringUtils.isNotBlank(username.getSearch().getValue())) {
            criteria.andLike("username", TableUtil.toFuzzySql(username.getSearch().getValue()));
        }
        //店铺名称
        DataTablesRequest.Column name = dataTablesRequest.getColumn("name");
        if (StringUtils.isNotBlank(name.getSearch().getValue())) {
            criteria.andLike("name", TableUtil.toFuzzySql(name.getSearch().getValue()));
        }
        //营业状态
        DataTablesRequest.Column status = dataTablesRequest.getColumn("status");
        if (StringUtils.isNotBlank(status.getSearch().getValue())) {
            criteria.andEqualTo("status", status.getSearch().getValue());
        }
        criteria.andIsNull("expireDate")
                .andNotEqualTo("username", SystemConstant.USER_ADMIN)
                .andNotEqualTo("status", "5");
        List<SysUser> sysUserList = getMapper().selectByExample(example);
        this.setParam(sysUserList);
        return sysUserList;
    }

    /**
     * 是否保存成功
     */
    @Transactional
    public ServiceResponse save(Map<String, Object> sysUserMap) {
        SysUser sysuser = new SysUser();
        String username = ConvertUtil.convert(sysUserMap.get("username"), String.class);
        String password = ConvertUtil.convert(sysUserMap.get("password"), String.class);
        String rePassword = ConvertUtil.convert(sysUserMap.get("rePassword"), String.class);
        String name = ConvertUtil.convert(sysUserMap.get("name"), String.class);
        String uuid = ConvertUtil.convert(sysUserMap.get("uuid"), String.class);
        String priceMultiple = ConvertUtil.convert(sysUserMap.get("priceMultiple"), String.class);

        if (!StringUtil.isEmpty(password) && !password.equals(rePassword)) {
            return ServiceResponse.error("两次密码不一致");
        }

        if (isExistByParam(null, "username", username)) {
            return ServiceResponse.error("登录账号已存在");
        }
        sysuser.setUuid(uuid);
        if (priceMultiple.equals("") || priceMultiple == null) {
            priceMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
        }
        sysuser.setPriceMultiple(priceMultiple);

        sysuser.setUsername(username);
        sysuser.setName(name);
        sysuser.setPassword(bCryptPasswordEncoder.encode(password));
        sysuser.setEnabled(ConvertUtil.convert(sysUserMap.get("isEnabled"), Boolean.class));
        sysuser.setIsSystem(SysUser.IS_SYSTEM_TRUE);
        sysuser.setStatus(1);
        Long userId = sysuser.getId();
        if (insertNotNull(sysuser) != 1) {
            return ServiceResponse.error("保存失败");
        }

        //  增加角色
        if (!this.createSysUserRole(sysUserMap, userId)) {
            return ServiceResponse.error("保存失败-角色保存失败");
        }
        return ServiceResponse.succ("保存成功");
    }

    /**
     * 锁定/解锁
     *
     * @param isLocked 是否锁定
     */
    @Transactional
    public Boolean locked(Long id, Boolean isLocked) {
        SysUser sysUser = selectByKey(id);
        sysUser.setLocked(isLocked);
        return updateNotNull(sysUser) == 1;
    }

    /**
     * 可用/不可用
     *
     * @param isEnabled 是否可用
     */
    @Transactional(readOnly = false)
    public Boolean enabled(Long id, Boolean isEnabled) {
        SysUser sysUser = selectByKey(id);
        sysUser.setEnabled(isEnabled);
        return updateNotNull(sysUser) == 1;
    }

    /**
     * 修改用户密码
     *
     * @param id          用户编号（userId）
     * @param oldPassword 老密码
     * @param password    新密码
     * @param repassword  确认新密码
     * @return 是否操作成功等信息
     */
    @Transactional(readOnly = false)
    public ServiceResponse changePassword(Long id, String oldPassword, String password, String repassword) {
        SysUser sysUser = selectByKey(id);
        // 判断两次密码是否已一直
        if (!password.equals(repassword)) {
            return ServiceResponse.error("两次密码不一致");
        }
        // 如果和原密码不符合
        if (!bCryptPasswordEncoder.matches(oldPassword, sysUser.getPassword())) {
            return ServiceResponse.error("与之前密码不一致");
        }
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        Boolean result = updateNotNull(sysUser) == 1;
        if (!result) {
            return ServiceResponse.error("密码更新失败！");
        }
        return ServiceResponse.succ("密码更新成功！");
    }

    /**
     * 是否更新成功
     */
    @Transactional
    public ServiceResponse update(Map<String, Object> sysUserMap) {
        Long userId = ConvertUtil.convert(sysUserMap.get("id"), Long.class);
        SysUser sysUser = selectByKey(userId);
        String password = ConvertUtil.convert(sysUserMap.get("password"), String.class);
        String rePassword = ConvertUtil.convert(sysUserMap.get("rePassword"), String.class);
        String loLa = ConvertUtil.convert(sysUserMap.get("loLa"), String.class);
        String uuid = ConvertUtil.convert(sysUserMap.get("uuid"), String.class);
        String priceMultiple = ConvertUtil.convert(sysUserMap.get("priceMultiple"), String.class);

        if (!StringUtil.isEmpty(password)) {
            // 判断两次密码是否已一直
            if (!password.equals(rePassword)) {
                return ServiceResponse.error("两次密码不一致");
            }
            sysUser.setPassword(bCryptPasswordEncoder.encode(ConvertUtil.convert(sysUserMap.get("password"), String.class)));
        }
        if (uuid.equals("") || uuid == null) {
            sysUser.setUuid("");
        } else {
            sysUser.setUuid(uuid);
        }

        if (priceMultiple.equals("") || priceMultiple == null) {
            storePriceMultiple = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_STORE_PRICE_MULTIPLE, storePriceMultiple);
            sysUser.setPriceMultiple(storePriceMultiple);
        } else {
            sysUser.setPriceMultiple(priceMultiple);
        }
        sysUser.setEnabled(ConvertUtil.convert(sysUserMap.get("isEnabled"), Boolean.class));
        //经纬度解析
        if (!loLa.equals("") && loLa != null) {
            List<String> loLaList = Arrays.asList(loLa.split(","));
            if (loLaList != null) {
                if (loLaList.size() != 2) {
                    return ServiceResponse.error("经纬度格式错误");
                } else {
                    sysUser.setLongitude(new BigDecimal(loLaList.get(0)));
                    sysUser.setLatitude(new BigDecimal(loLaList.get(1)));
                }
            }
        }
        if (updateNotNull(sysUser) != 1) {
            return ServiceResponse.error("更新失败");
        }

        // 删除角色
        if (sysUserRoleService.deleteByKey(userId) < 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("保存失败-角色保存失败");
        }

        //  增加角色
        if (!this.createSysUserRole(sysUserMap, userId)) {
            return ServiceResponse.error("保存失败-角色保存失败");
        }

        return ServiceResponse.succ("更新成功");
    }

    /**
     * 删除某个
     */
    @Transactional
    public ServiceResponse deleteById(Long id) {
        SysUser user = selectByKey(id);
        Date date = new Date();
        user.setId(id);
        user.setExpireDate(new Date());
        if (updateNotNull(user) != 1) {
            return ServiceResponse.error("删除用户失败");
        }

        // 删除角色
        if (sysUserRoleService.deleteByKey(id) < 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServiceResponse.error("删除用户角色关联失败");
        }

        return ServiceResponse.succ("删除用户成功");
    }

    @Transactional
    public boolean createSysUserRole(Map<String, Object> sysUserMap, Long userId) {
        SysUserRole sysUserRole = new SysUserRole();
        if (null != sysUserMap.get("roles")) {
            String[] roleArray = ConvertUtil.convert(sysUserMap.get("roles"), String[].class);
            sysUserRole.setUserId(userId);
            sysUserRole.setRoles(StringUtils.join(roleArray, ","));
            if (sysUserRoleService.insertNotNull(sysUserRole) != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }
        return true;
    }

    /**
     * 批量修改店铺密码
     *
     * @param password   新密码
     * @param repassword 确认新密码
     * @return 是否操作成功等信息
     */
    @Transactional(readOnly = false)
    public ServiceResponse changeAllPassword(String password, String repassword) {
        // 判断两次密码是否已一致
        if (!password.equals(repassword)) {
            return ServiceResponse.error("两次密码不一致");
        }
//        Example example = new Example(SysUser.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andIsNull("expireDate")
//                .andNotEqualTo("username", SystemConstant.USER_ADMIN);
//        List<SysUser> sysUserList = getMapper().selectByExample(example);
//        for (int i = 0; i < sysUserList.size(); i++) {
//            SysUser sysUser = sysUserList.get(i);
//            sysUser.setPassword(bCryptPasswordEncoder.encode(password));
//            updateNotNull(sysUser);
//        }
        //2019年4月26日21:07:07---直接update
        SysUser sysUser = new SysUser();
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUserMapper.setNewPassword(sysUser);
        return ServiceResponse.succ("密码批量修改成功！");
    }

    /**
     * 设置参数（状态）
     *
     * @param list
     */
    public void setParam(List<SysUser> list) {
        for (int i = 0; i < list.size(); i++) {
            SysUser sysUser = list.get(i);
            sysUser.setStatusName(DictUtils.findValueByTypeAndKey("userStatusName", sysUser.getStatus()));
        }
    }


    /**************************************/

    /**
     * 用户登录，uuid
     *
     * @param sysUser
     * @return
     */
    @Transactional(readOnly = false)
    public SysUser userLoginIn(SysUser sysUser) {
        SysUser user = sysUserMapper.getSysUser(sysUser);
        if (user == null) {
            throw new RFException("账号密码错误");
        }
        if (!bCryptPasswordEncoder.matches(sysUser.getPassword(), user.getPassword())) {
            throw new RFException("账号密码错误");
        }
        if (user.getEnabled() != SysUser.ENABLED_TRUE) {
            throw new RFException("账号未启用，请联系管理员");
        }
        //先查系统关于uuid的认证开关
        uuidSwitch = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_UUID_SWITCH, String.valueOf(uuidSwitch));
        if (BooleanUtils.toBoolean(uuidSwitch, "1", "0")
                || Boolean.parseBoolean(uuidSwitch)) {
            //开
            if (sysUser.getUuid() == null || sysUser.getUuid().equals("")) {
                throw new RFException("设备id异常");
            }
            String uuid = sysUser.getUuid().replace("-", "");
            if (user.getUuid().equals("")) {
                //第一次登录，设置uuid
                user.setUuid(uuid);
                updateNotNull(user);
            } else {
                if (!user.getUuid().equals(uuid)) {
                    throw new RFException("设备绑定异常，请联系管理员");
                }
            }
        }

        UUID uuid = UUID.randomUUID();//生成guid
        String token = uuid.toString().replaceAll("-", "");
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        sysUserToken.setUserId(user.getId());
        String sameLogin = sysConfigService.getSysConfigByKey(SysConfig.IS_LOGIN_SAME_TIME, isLoginSameTime);
        if (sameLogin.equals("1")) {
            //允许同时登录，不做失效处理

        } else {
            //不允许同时登录，设置其它token失效
            sysUserTokenMapper.setTimeOver(sysUserToken);
        }
        //再新建token
        String tk = sysConfigService.getSysConfigByKey(SysConfig.TOKEN_TIME, tokenTime);
        Date date = DateUtil.getAfterMinuteTime(tk);
        sysUserToken.setEffectiveTime(date);
        if (sysUserTokenService.insertNotNull(sysUserToken) <= 0) {
            throw new RFException("登录失败，请重试");
        }
        user.setToken(token);
        return user;
    }


    @Transactional(readOnly = false)
    public SysUser changePasswordApp(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getUsername())
                || StringUtils.isEmpty(sysUser.getPassword())
                || StringUtils.isEmpty(sysUser.getNewPassword())) {
            throw new RFException("参数错误");
        }
        if (sysUser.getPassword().equals(sysUser.getNewPassword())) {
            throw new RFException("新密码不能与原密码一致");
        }
        if (sysUser.getNewPassword().length() != 6) {
            throw new RFException("密码位数需要6位");
        }
        SysUser localUser = sysUserMapper.getSysUser(sysUser);
        if (localUser == null) {
            throw new RFException("参数错误");
        }
        // 如果和原密码不符合
        if (!bCryptPasswordEncoder.matches(sysUser.getPassword(), localUser.getPassword())) {
            throw new RFException("原密码错误");
        }
        localUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getNewPassword()));
        Boolean result = updateNotNull(localUser) == 1;
        if (!result) {
            throw new RFException("密码修改失败");
        }
        return localUser;
    }

    /**
     * 附近的店铺（20公里）
     *
     * @param sysUser
     * @return
     */
    public List<SysUser> findSysUserNearbyList(SysUser sysUser) {
        List<SysUser> sysUserList = new ArrayList<>();
        //计算此次签到是否在合法距离
        BigDecimal lat1 = sysUser.getLatitude();//纬度
        BigDecimal lon1 = sysUser.getLongitude();//经度
        //定位异常，返回空
        if (lon1 == null || lat1 == null) {
            return sysUserList;
        }
        //先拿数据库配置里的默认距离范围
        String distance = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_NEARBY_DISTANCE, String.valueOf(nearbyDistance));
        sysUser.setNearbyDistance(sysUser.getNearbyDistance() != null ? sysUser.getNearbyDistance() : new Integer(distance));
        sysUser.setEnabled(SysUser.ENABLED_TRUE);
        sysUserList = sysUserMapper.findSysUserNearbyList(sysUser);
        return sysUserList;
    }

    /**
     * 根据 id 获取门店系数
     *
     * @param id 用户Id
     * @return
     */
    public String storeCoefficient(Long id) {
        SysUser sysUser = selectByKey(id);
        String priceMultiple = "";
        if (sysUser != null) {
            priceMultiple = sysUser.getPriceMultiple();
        }
        return priceMultiple;
    }

}
