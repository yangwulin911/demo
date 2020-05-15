package com.ywl.config;

import com.ywl.handler.CustomAuthExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.concurrent.TimeUnit;

/**
 * oauth2配置类
 * @author yangwulin
 */
@Configuration
public class Oauth2Config {

    /**
     * 访问客户端角色名
     */
    public static final String ROLE_ADMIN = "ADMIN";
    /**
     * 访问客户端秘钥
     */
    public static final String CLIENT_SECRET = "123456";
    /**
     *  访问客户端id
     */
    public static final String CLIENT_ID = "client_1";

    /**
     * 鉴权模式
     */
    public static final String[] GRANT_TYPE = {"password", "refresh_token"};


    /**
     * 资源服务器
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private CustomAuthExceptionHandler customAuthExceptionHandler;


        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    // 请求权限配置
                    .authorizeRequests()
                    // 下面路径直接放行，不需要经过认证
                    .antMatchers("/auth/*", "/auth/user/login").permitAll()
                    // options请求不需要鉴权
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // 用户的增删改接口只允许管理员访问
                    .antMatchers(HttpMethod.POST, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                    .antMatchers(HttpMethod.PUT, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                    .antMatchers(HttpMethod.DELETE, "/auth/user").hasAnyAuthority(ROLE_ADMIN)
                    // 角色 权限列表接口只允许系统管理员和高级用户访问
                    .antMatchers(HttpMethod.GET, "/auth/role").hasAnyAuthority(ROLE_ADMIN)
                    .anyRequest()
                    .authenticated();
        }
    }

    /**
     *
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            // 运行表单验证
            security.allowFormAuthenticationForClients().tokenKeyAccess("isAuthenticated")
                    .checkTokenAccess("permitAll()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String finalSecret = "" + new BCryptPasswordEncoder().encode(CLIENT_ID);
            // 配置客户端，使用密码模式验证鉴权
            clients.inMemory()
                    .withClient(CLIENT_ID)
                    // 密码模式及refresh_token模式
                    .authorizedGrantTypes(GRANT_TYPE[0], GRANT_TYPE[1])
                    .scopes("all")
                    .secret(finalSecret);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            // token信息存储到redis
            endpoints.tokenStore(redisTokenStore()).authenticationManager(authenticationManager);
            // 配置TokenServices参数
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(endpoints.getTokenStore());
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
            tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
            // 设置token有效时间
            tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
            // 设置token 刷新时间间隔
            tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
            tokenServices.setReuseRefreshToken(false);
            endpoints.tokenServices(tokenServices);
        }

        @Bean
        public RedisTokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }
    }

}
