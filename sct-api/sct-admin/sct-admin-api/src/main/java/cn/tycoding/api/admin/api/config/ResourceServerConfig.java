package cn.tycoding.api.admin.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author tycoding
 * @date 2019-05-25
 */
@Slf4j
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        log.info("加载了 >> ResourceServerConfig...");
        http
                .authorizeRequests()
                .antMatchers("/user/info/*")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .csrf().disable();
    }
}
