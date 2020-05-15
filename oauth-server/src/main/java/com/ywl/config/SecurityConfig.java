package com.ywl.config;

import com.ywl.domain.CustomUserDetail;
import com.ywl.entity.User;
import com.ywl.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置类
 *
 * @author yangwulin
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserMapper userMapper;

    @Bean
    public RedisTemplate redisTemplate() {
        return new RedisTemplate<>();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return username -> {
            log.info("username:{}", username);
            User user = userMapper.queryById(1);
            if (user != null) {
                CustomUserDetail customUserDetail = new CustomUserDetail();
                customUserDetail.setUsername(user.getAccount());
                customUserDetail.setPassword("{bcrypt}" + bCryptPasswordEncoder.encode(user.getPassword()));
//                    AuthorityUtils.createAuthorityList()
                return customUserDetail;
            } else {
                return null;
            }
        };
    }
}
