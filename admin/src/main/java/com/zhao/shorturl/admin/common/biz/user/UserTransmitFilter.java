

package com.zhao.shorturl.admin.common.biz.user;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * 用户信息传输过滤器
 * 小赵
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String username = httpServletRequest.getHeader("username");
        if (StrUtil.isNotBlank(username)) {
            // 对username进行URL解码，确保中文正确处理
            try {
                username = java.net.URLDecoder.decode(username, "UTF-8");
            } catch (Exception e) {
                // 解码失败时保留原始值
                System.err.println("解码username失败: " + e.getMessage());
            }
            
            String userId = httpServletRequest.getHeader("userId");
            String realName = httpServletRequest.getHeader("realName");
            
            // 对realName进行URL解码，确保中文正确处理
            if (StrUtil.isNotBlank(realName)) {
                try {
                    realName = java.net.URLDecoder.decode(realName, "UTF-8");
                } catch (Exception e) {
                    // 解码失败时保留原始值
                    System.err.println("解码realName失败: " + e.getMessage());
                }
            }
            
            UserInfoDTO userInfoDTO = new UserInfoDTO(userId, username, realName);
            UserContext.setUser(userInfoDTO);
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}
