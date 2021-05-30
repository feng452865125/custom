package com.chunhe.custom.pc.service;

import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.pc.model.ActionLog;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by white 2020年11月2日20:11:39
 */
@Service
public class ActionLogService extends BaseService<ActionLog> {


    /**
     * 新增日志
     *
     * @param authentication
     * @param request
     * @param logContent
     */
    public void createActionLog(Authentication authentication, HttpServletRequest request, String logContent) {
        ActionLog actionLog = new ActionLog();
        PlatformUser user = (PlatformUser) authentication.getPrincipal();
        actionLog.setLogUsername(user.getSysUser().getName());
        actionLog.setLogRequestUrl(String.valueOf(request.getRequestURL()));
        actionLog.setLogContent(logContent);
        insertNotNull(actionLog);
    }


}
