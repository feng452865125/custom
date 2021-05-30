package com.chunhe.custom.pc.controller;

import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.model.SysUser;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.framework.security.PlatformUser;
import com.chunhe.custom.pc.model.DataSynch;
import com.chunhe.custom.pc.service.DataSynchService;
import com.chunhe.custom.pc.service.SapSkuService;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by white 2018年8月13日14:49:10 数据同步管理
 */
@Controller
@RequestMapping("/dataSynch")
public class DataSynchController extends BaseController {

    @Autowired
    private DataSynchService dataSynchService;

    @Autowired
    private SapSkuService sapSkuService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('dataSynch:page')")
    public String dataSynchList(Model model) {
        return "pages/dataSynch/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dataSynch:list')")
    public DataTablesResponse<DataSynch> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DataSynch> data = dataSynchService.selectPage(dataTablesRequest, new ISelect() {
            @Override
            public void doSelect() {
                dataSynchService.dataSynchList(dataTablesRequest);
            }
        });
        return data;
    }

    /**
     * 数据详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('dataSynch:view')")
    public String view(@PathVariable Long id, Model model) {
        DataSynch dataSynch = dataSynchService.getDataSynch(id);
        model.addAttribute("dataSynch", dataSynch);
        return "pages/dataSynch/view";
    }

    @RequestMapping("/select/{id}")
    @PreAuthorize("hasAuthority('dataSynch:view')")
    @ResponseBody
    public DataTablesResponse<DataSynch> select(@PathVariable final long id, @Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<DataSynch> data = dataSynchService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        dataSynchService.findDataSynchList(dataTablesRequest, id);
                    }
                });
        return data;
    }

    /**
     * 同步数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('dataSynch:synch')")
    public ResponseEntity dataSynch(@PathVariable Long id, Authentication authentication) throws IOException {
        SysUser sysUser = ((PlatformUser) authentication.getPrincipal()).getSysUser();
        ServiceResponse result = dataSynchService.dataSynch(id, sysUser.getUsername());
        return responseDeal(result);
    }

}
