package com.chunhe.custom.pc.service;

import com.chunhe.custom.pc.service.mail.MailService;
import com.chunhe.custom.framework.model.SysConfig;
import com.chunhe.custom.framework.service.BaseService;
import com.chunhe.custom.framework.service.SysConfigService;
import com.chunhe.custom.framework.utils.DateUtil;
import com.chunhe.custom.pc.mapper.TaskMapper;
import com.chunhe.custom.pc.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by white 2018年8月2日14:32:49
 */
@Service
public class TaskService extends BaseService<Task> {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private PartsService partsService;

    @Autowired
    private PartsBigService partsBigService;

    @Autowired
    private MailService mailService;

    @Value("${thirdLockToOrder}")
    private String thirdLockToOrder;

    /**
     * 查找任务
     *
     * @param task
     * @return
     */
    public List<Task> findTaskList(Task task) {
        List<Task> taskList = taskMapper.findTaskList(task);
        return taskList;
    }

    /**
     * 查找指定任务
     *
     * @param task
     * @return
     */
    public Task getTask(Task task) {
        Task tt = taskMapper.getTask(task);
        return tt;
    }

    /**
     * 新建任务
     *
     * @param company
     * @param orderId
     * @param productId
     */
    public void createTask(String company, String orderId, String productId, String thirdOrderId, Integer status) {
        Task task = new Task();
        task.setCompany(company);
        task.setOrderId(orderId);
        task.setProductid(productId);
        task.setThirdOrderId(thirdOrderId);
        String lockToOrder = sysConfigService.getSysConfigByKey(SysConfig.SYS_CONFIG_THIRD_LOCKTOORDER, String.valueOf(thirdLockToOrder));
        task.setStatus(status);
        task.setLastDate(DateUtil.getDate(new Date(), NumberUtils.toInt(lockToOrder) * 60 * 1000));
        super.insertNotNull(task);
    }

    /**
     * 取消任务
     *
     * @param orderId
     */
    public void cancelTask(String orderId) {
        Task task = new Task();
        task.setOrderId(orderId);
        Task tt = taskMapper.getTask(task);
        if (tt != null && DateUtil.compareLess(new Date(), tt.getLastDate())) {
            //在取消有效时间内，可以取消
            super.expireNotNull(tt);
        }
    }

    /**
     * 改变状态
     *
     * @param taskId
     * @param status
     * @param thirdResponse
     */
    public void updateTask(Long taskId, Integer status, String thirdResponse) {
        Task task = super.selectByKey(taskId);
        if (task != null) {
            task.setStatus(status);
            task.setThirdOrderId(task.getThirdOrderId() + "||" + thirdResponse);
            super.updateNotNull(task);
        }
    }

    /**
     * 改变次数
     */
    public void updateTaskCount(Long taskId, String result, String param) {
        Task task = super.selectByKey(taskId);
        String response = "";
        if (task != null) {
            if (task.getNum() < 3) {
                task.setNum(task.getNum() + 1);
                if (!StringUtils.isEmpty(task.getContent())) {
                    response = task.getContent();
                }
                task.setContent(response + "||第" + task.getNum() + "次下单&时间=" + new Date().toString() + "&param=" + param + "&result=" + result);
                super.updateNotNull(task);
                if (task.getNum() >= 3) {
                    //超过3次，发邮件通知业务部门
                    try {
                        mailService.orderFailMail(task);
                        task.setStatus(Task.STATUS_FINISH);
                        updateNotNull(task);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 判断调用次数
     *
     * @param taskId
     */
    public Boolean checkTaskCount(Long taskId) {
        Task task = super.selectByKey(taskId);
        if (task != null) {
            if (task.getNum() < 3) {
                return true;
            }
        }
        return false;
    }
}
