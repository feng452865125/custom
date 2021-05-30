package com.chunhe.custom.controller.fm;

import com.chunhe.custom.datatables.DataTablesRequest;
import com.chunhe.custom.datatables.DataTablesResponse;
import com.chunhe.custom.entity.SysConfig;
import com.chunhe.custom.exception.ResultBody;
import com.chunhe.custom.mybatis.BaseController;
import com.chunhe.custom.service.fm.FmSysConfigService;
import com.github.pagehelper.ISelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 常用系统配置 freemarker-控制层
 * </p>
 *
 * @author AutoGenerator from white
 * @since 2021-05-30
 */

@Controller
@RequestMapping("/SysConfig")
public class FmSysConfigController extends BaseController {

    @Autowired
    private FmSysConfigService fmSysConfigService;


    @GetMapping(value = "/getList")
    public List<SysConfig> getList() {
        return fmSysConfigService.getList();
    }

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('sysConfig:page')")
    public String sysConfigList(Model model) {
        return "pages/sysConfig/list";
    }

    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('sysConfig:edit')")
    public String edit(@PathVariable Long id, Model model) {
        SysConfig sysConfig = fmSysConfigService.selectByKey(id);
        model.addAttribute("sysConfig", sysConfig);
        return "pages/sysConfig/edit";
    }


    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysConfig:list')")
    public DataTablesResponse<SysConfig> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<SysConfig> data = fmSysConfigService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        fmSysConfigService.sysConfigList(dataTablesRequest);
                    }
                });
        return data;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('sysConfig:edit')")
    public ResultBody update(@PathVariable long id, @RequestBody Map<String, Object> map, Authentication authentication, HttpServletRequest request) {
        map.put("id", id);
        SysConfig sysConfig = fmSysConfigService.selectByKey(id);
        String result = fmSysConfigService.update(sysConfig.getSysConfigKey(), map, sysConfig);
        if (result.equals("error")) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败");
            return ResultBody.error("更新失败");
        }
//        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
        return ResultBody.success();
    }
}