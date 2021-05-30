package com.chunhe.custom.framework.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by qiumy on 2017/5/18.
 */
public class RequestUtils {

    public static void startPageHelp(HttpServletRequest req) {
        String pcStr = req.getParameter("pageCurrent");
        String psStr = req.getParameter("pageSize");
        int pageCurrent = NumberUtils.toInt(pcStr, 0);
        int pageSize = NumberUtils.toInt(psStr, 10);
        PageHelper.startPage(pageCurrent, pageSize);
    }

    /**
     * 逻辑分页
     * 适用情况: 用一条sql查询太复杂或无法实现，先查出来所有，后做一些判断进行删除操作，物理分页失效
     *
     * @param list 要分页的全部数据
     */
    public static <E> PageInfo<E> logicalPage(List<E> list) {
        HttpServletRequest req = SpringUtil.getRequest();
        String pcStr = req.getParameter("pageCurrent");
        String psStr = req.getParameter("pageSize");
        int pageCurrent = NumberUtils.toInt(pcStr, 1);
        int pageSize = NumberUtils.toInt(psStr, 10);
        Page<E> page = new Page<>(pageCurrent, pageSize);
        for (int i = 0; i < list.size(); i++) {
            if (i >= page.getStartRow() && i < page.getEndRow())
                page.add(list.get(i));
        }
        page.setTotal(list.size());
        page.setPages(list.size() % pageSize == 0 ? list.size() / pageSize : list.size() / pageSize + 1);
        return new PageInfo<>(page);
    }

}
