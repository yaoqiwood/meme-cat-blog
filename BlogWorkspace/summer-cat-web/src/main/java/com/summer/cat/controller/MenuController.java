package com.summer.cat.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.summer.cat.annotation.Pass;
import com.summer.cat.base.Constant;
import com.summer.cat.entity.Menu;
import com.summer.cat.service.service.IMenuService;
import com.summer.cat.util.LogUtil;
import com.summer.cat.util.Returns;

import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author summerCat
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/permission")
// 不加入swagger ui里
@ApiIgnore
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @RequiresAuthentication
    @RequestMapping(value = "getMenu.action")
    @ResponseBody
    public Map<String, ? extends Object> getMenu() {
        try {
            List<Menu> menuList = menuService.getAllMenu(Constant.ROOT_MENU);
            return Returns.mapOk(menuList, Constant.ReturnsMessage.SUCCESS_MSG);
        } catch (Exception e) {
            LogUtil.error(Constant.ReturnsMessage.ERROR_MSG, e);
            return Returns.mapError(Constant.ReturnsMessage.ERROR_MSG + e.getMessage());
        }
    }

    @RequestMapping(value = "getById.action")
    @Pass
    @ResponseBody
    public Map<String, ? extends Object> getById(@RequestParam String id) {
        try {
            return Returns.mapOk(menuService.getById(id), "成功");
        } catch (Exception e) {
            LogUtil.error("失败", e);
            return Returns.mapError("失败：" + e.getMessage());
        }
    }
}
