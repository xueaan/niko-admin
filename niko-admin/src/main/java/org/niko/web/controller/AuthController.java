package org.niko.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.niko.common.core.constant.SystemConstants;
import org.niko.common.core.domain.R;
import org.niko.common.core.domain.model.LoginBody;
import org.niko.common.core.domain.model.RegisterBody;
import org.niko.common.core.utils.*;
import org.niko.common.encrypt.annotation.ApiEncrypt;
import org.niko.common.json.utils.JsonUtils;
import org.niko.common.satoken.utils.LoginHelper;
import org.niko.common.sse.dto.SseMessageDto;
import org.niko.common.sse.utils.SseMessageUtils;
import org.niko.common.tenant.helper.TenantHelper;
import org.niko.system.domain.bo.SysTenantBo;
import org.niko.system.domain.vo.SysClientVo;
import org.niko.system.domain.vo.SysTenantVo;
import org.niko.system.service.ISysClientService;
import org.niko.system.service.ISysConfigService;
import org.niko.system.service.ISysTenantService;
import org.niko.web.domain.vo.LoginTenantVo;
import org.niko.web.domain.vo.LoginVo;
import org.niko.web.domain.vo.TenantListVo;
import org.niko.web.service.IAuthStrategy;
import org.niko.web.service.SysLoginService;
import org.niko.web.service.SysRegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 认证
 *
 * @author Lion Li
 */
@Slf4j
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SysLoginService loginService;
    private final SysRegisterService registerService;
    private final ISysConfigService configService;
    private final ISysTenantService tenantService;
    private final ISysClientService clientService;
    private final ScheduledExecutorService scheduledExecutorService;


    /**
     * 登录方法
     *
     * @param body 登录信息
     * @return 结果
     */
    @ApiEncrypt
    @PostMapping("/login")
    public R<LoginVo> login(@RequestBody String body) {
        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        ValidatorUtils.validate(loginBody);
        // 授权类型和客户端id
        String clientId = loginBody.getClientId();
        String grantType = loginBody.getGrantType();
        SysClientVo client = clientService.queryByClientId(clientId);
        // 查询不到 client 或 client 内不包含 grantType
        if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
            log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
            return R.fail(MessageUtils.message("auth.grant.type.error"));
        } else if (!SystemConstants.NORMAL.equals(client.getStatus())) {
            return R.fail(MessageUtils.message("auth.grant.type.blocked"));
        }
        // 校验租户
        loginService.checkTenant(loginBody.getTenantId());
        // 登录
        LoginVo loginVo = IAuthStrategy.login(body, client, grantType);

        Long userId = LoginHelper.getUserId();
        scheduledExecutorService.schedule(() -> {
            SseMessageDto dto = new SseMessageDto();
            dto.setMessage("欢迎登录Niko-Admin后台管理系统");
            dto.setUserIds(List.of(userId));
            SseMessageUtils.publishMessage(dto);
        }, 5, TimeUnit.SECONDS);
        return R.ok(loginVo);
    }









    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 用户注册
     */
    @ApiEncrypt
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!configService.selectRegisterEnabled(user.getTenantId())) {
            return R.fail("当前系统没有开启注册功能！");
        }
        registerService.register(user);
        return R.ok();
    }

    /**
     * 登录页面租户下拉框
     *
     * @return 租户列表
     */
    @GetMapping("/tenant/list")
    public R<LoginTenantVo> tenantList(HttpServletRequest request) throws Exception {
        // 返回对象
        LoginTenantVo result = new LoginTenantVo();
        boolean enable = TenantHelper.isEnable();
        result.setTenantEnabled(enable);
        // 如果未开启租户这直接返回
        if (!enable) {
            return R.ok(result);
        }

        List<SysTenantVo> tenantList = tenantService.queryList(new SysTenantBo());
        List<TenantListVo> voList = MapstructUtils.convert(tenantList, TenantListVo.class);
        try {
            // 如果只超管返回所有租户
            if (LoginHelper.isSuperAdmin()) {
                result.setVoList(voList);
                return R.ok(result);
            }
        } catch (NotLoginException ignored) {
        }

        // 获取域名
        String host;
        String referer = request.getHeader("referer");
        if (StringUtils.isNotBlank(referer)) {
            // 这里从referer中取值是为了本地使用hosts添加虚拟域名，方便本地环境调试
            host = referer.split("//")[1].split("/")[0];
        } else {
            host = new URL(request.getRequestURL().toString()).getHost();
        }
        // 根据域名进行筛选
        List<TenantListVo> list = StreamUtils.filter(voList, vo ->
            StringUtils.equalsIgnoreCase(vo.getDomain(), host));
        result.setVoList(CollUtil.isNotEmpty(list) ? list : voList);
        return R.ok(result);
    }

}
