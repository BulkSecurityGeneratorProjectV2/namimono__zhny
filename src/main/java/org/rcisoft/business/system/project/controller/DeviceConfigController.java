package org.rcisoft.business.system.project.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.rcisoft.base.result.Result;
import org.rcisoft.entity.BusDevice;
import org.rcisoft.business.system.project.service.DeviceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Create by 土豆儿
 * Time：2019/3/6 17:14
 **/
@Api(tags = "项目编辑-设备配置")
@RestController
@RequestMapping("deviceConfig")
public class DeviceConfigController {

    @Autowired
    private DeviceConfigService deviceConfigServiceImpl;

    @ApiOperation(value="新增设备配置信息", notes="新增设备配置信息")
    @PostMapping("/addDeviceConfigInfo")
    public Result addDeviceConfigInfo(@RequestBody BusDevice busDevice){
        return Result.result(1, deviceConfigServiceImpl.addDeviceConfigInfo(busDevice));
    }

    @ApiOperation(value="查询设备简要信息", notes="查询设备简要信息")
    @PostMapping("/queryDeviceBriefInfo")
    public Result queryDeviceBriefInfo(@RequestBody BusDevice busDevice){
        return Result.result(deviceConfigServiceImpl.queryDeviceBriefInfo(busDevice));
    }

    @ApiOperation(value="处理一、二级设备类型下拉菜单级联格式", notes="处理一、二级设备类型下拉菜单级联格式")
    @GetMapping("/processTypeFormat")
    public Result processTypeFormat(@RequestParam("systemId") String systemId){
        return Result.result(deviceConfigServiceImpl.processTypeFormat(systemId));
    }

}
