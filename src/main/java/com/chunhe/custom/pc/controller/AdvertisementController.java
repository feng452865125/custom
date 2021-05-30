package com.chunhe.custom.pc.controller;

import com.chunhe.custom.pc.model.Series;
import com.chunhe.custom.pc.service.SeriesService;
import com.github.pagehelper.ISelect;
import com.chunhe.custom.framework.controller.BaseController;
import com.chunhe.custom.framework.datatables.DataTablesRequest;
import com.chunhe.custom.framework.datatables.DataTablesResponse;
import com.chunhe.custom.framework.response.ServiceResponse;
import com.chunhe.custom.pc.model.Advertisement;
import com.chunhe.custom.pc.service.AdvertisementService;
import com.chunhe.custom.pc.service.RelAdvertisementStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by white 2018年7月10日14:26:36
 * 广告栏管理
 */
@Controller
@RequestMapping("/advertisement")
public class AdvertisementController extends BaseController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private RelAdvertisementStyleService relAdvertisementStyleService;

    @Autowired
    private SeriesService seriesService;

    @RequestMapping(value = "/list")
    @PreAuthorize("hasAuthority('advertisement:page')")
    public String advertisementList(Model model) {
        return "pages/advertisement/list";
    }

    /**
     * 列表
     *
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping(value = "/pagination", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('advertisement:list')")
    public DataTablesResponse<Advertisement> pagination(@Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Advertisement> data = advertisementService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        advertisementService.advertisementList(dataTablesRequest);
                    }
                });
        return data;
    }

    @RequestMapping(value = "/add")
    @PreAuthorize("hasAuthority('advertisement:add')")
    public String add(Model model) {
        return "pages/advertisement/add";
    }

    /**
     * 增加
     *
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('advertisement:add')")
    public ResponseEntity save(@RequestBody Map<String, Object> map) {
        ServiceResponse result = advertisementService.save(map);
        return responseDeal(result);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasAuthority('advertisement:delete')")
    public ResponseEntity delete(@PathVariable long id) {
        ServiceResponse result = advertisementService.deleteById(id);
        return responseDeal(result);
    }

    /**
     * 广告维护特殊处理
     * edit，维护自身的数据
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('advertisement:edit')")
    public ResponseEntity update(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = advertisementService.update(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 广告维护特殊处理
     * view，维护详情的数据（标题展示区，内容展示区）
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasAuthority('advertisement:edit')")
    public ResponseEntity detailUpdate(@PathVariable long id, @RequestBody Map<String, Object> map) {
        map.put("id", id);
        ServiceResponse result = advertisementService.detailUpdate(map);
        if (!result.isSucc()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getContent());
        }
        return ResponseEntity.status(HttpStatus.OK).body("更新成功");
    }

    /**
     * 编辑自身数据
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('advertisement:edit')")
    public String edit(@PathVariable Long id, Model model) {
        Advertisement advertisement = advertisementService.getAdvertisement(id);
        model.addAttribute("advertisement", advertisement);
        return "pages/advertisement/edit";
    }

    /**
     * 编辑详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view/{id}")
    @PreAuthorize("hasAuthority('advertisement:view')")
    public String view(@PathVariable Long id, Model model) {
        List<Series> seriesList = seriesService.findSeriesList(new Series());
        model.addAttribute("seriesList", seriesList);
        Advertisement advertisement = advertisementService.getAdvertisement(id);
        model.addAttribute("advertisement", advertisement);
        return "pages/advertisement/view";
    }

    /**
     * 禁用
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/disabled", method = RequestMethod.PATCH)
    public ResponseEntity<String> disabled(@PathVariable long id) {
        if(advertisementService.enabled(id, Advertisement.ENABLED_FALSE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @RequestMapping(value= "/{id}/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> enabled(@PathVariable Long id, Model model) {
        if(advertisementService.enabled(id, Advertisement.ENABLED_TRUE)) {
            return ResponseEntity.status(HttpStatus.OK).body("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("操作失败");
        }
    }


    /**
     * 关联样式时的查询
     * @param dataTablesRequest
     * @return
     */
    @RequestMapping("/style/select/{id}")
    @ResponseBody
    public DataTablesResponse<Advertisement> select(@PathVariable final long id, @Valid @RequestBody final DataTablesRequest dataTablesRequest) {
        DataTablesResponse<Advertisement> data = advertisementService.selectPage(dataTablesRequest,
                new ISelect() {
                    @Override
                    public void doSelect() {
                        relAdvertisementStyleService.findRelAdvertisementStyle(dataTablesRequest, id);
                    }
                });
        return data;
    }

    /**
     * 关联
     * @param map
     * @return
     */
    @RequestMapping(value= "/style/enabled", method = RequestMethod.PATCH)
    @ResponseBody
    public String enabled(@RequestBody Map<String, Object> map) {
        relAdvertisementStyleService.createRelAdvertisementStyle(map);
        return "";
    }

    /**
     * 取消关联
     * @param map
     * @return
     */
    @RequestMapping(value= "/style/disabled", method = RequestMethod.PATCH)
    @ResponseBody
    public String disabled(@RequestBody Map<String, Object> map) {
        relAdvertisementStyleService.deleteRelAdvertisementStyle(map);
        return "";
    }
}
