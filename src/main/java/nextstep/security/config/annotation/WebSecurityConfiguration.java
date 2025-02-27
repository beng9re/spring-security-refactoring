package nextstep.security.config.annotation;

import jakarta.servlet.Filter;
import nextstep.security.builder.Customizer;
import nextstep.security.builder.HttpSecurity;
import nextstep.security.config.FilterChainProxy;
import nextstep.security.config.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class WebSecurityConfiguration {

    private final List<SecurityFilterChain> securityFilterChains;
    private final HttpSecurity http;

    public WebSecurityConfiguration(List<SecurityFilterChain> securityFilterChains, HttpSecurity http) {
        this.http = http;
        this.securityFilterChains = securityFilterChains;
    }

    @Bean
    public Filter springSecurityFilterChain() {
        securityFilterChains.add(http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build());


        FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
        return filterChainProxy;
    }
}

