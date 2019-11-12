package com.example.demo.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.security.service.UserDetailsServiceImpl;

// jwtの認証をするクラス
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger Logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request); // jwtトークンから認証に必要な部分のみ取得をし、格納
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // jwtトークンのバリデーションが通った場合
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolderに設定
                /*
                 * ex. SecurityContextHolderに設定後のUserDetailsの取得
                 * UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                 */
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    // headerから認証に必要なデータを抜き出して、返す
    private String parseJwt(HttpServletRequest request) {
        final String AUTH_HEADER_NAME = "Authorization";
        final String AUTH_VALUE_PREFIX = "Bearer ";
        String headerAuth = request.getHeader(AUTH_HEADER_NAME);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(AUTH_VALUE_PREFIX)) {
            return headerAuth.substring(AUTH_VALUE_PREFIX.length(), headerAuth.length());
        }

        return null;
    }
}
