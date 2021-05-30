package com.chunhe.custom.pc.task;

import com.chunhe.custom.pc.model.Task;
import com.chunhe.custom.pc.service.TaskService;
import com.chunhe.custom.pc.thirdSupplier.rows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by white 2019年3月20日19:52:55
 * 定时任务
 */
//@Component
@Service
public class TimedTask {

    protected Logger logger = LogManager.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    /**
     * 查找没处理的线程（每30秒分钟，跑一次查询和下单）
     */
    @Async
    @Scheduled(cron = "30 * * * * ?")
    public void findTaskList() {
        Task task = new Task();
        task.setStatus(Task.STATUS_INIT);
        task.setLastDate(new Date());
        List<Task> list = taskService.findTaskList(task);
        //未跑任务，且当前时间满足取消的最后期限时间，调用下单接口
        for (int i = 0; i < list.size(); i++) {
            final Task tt = list.get(i);
            if (tt.getNum() >= 3) {
                continue;
            }
            try {
                rows rows = new rows();
                rows.setProductid(tt.getProductid());
                rows.setStoneZsh(tt.getStoneZsbh());
                rows.setOrderId(new Long(tt.getOrderId()));
                rows.setTaskId(tt.getId());
                rows.setCompany(tt.getCompany());
                System.out.println("定时任务：" + tt.getCompany());
            } catch (Exception e) {
                logger.info(tt.getCompany() + "{TimedTask}", e);
            }
        }
    }


}
