package nextstep.security.config;

import nextstep.security.builder.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpSecurityConfiguration {
    @Bean
    @Scope("prototype")
    public HttpSecurity httpSecurity() {
        return new HttpSecurity();
    }
}
