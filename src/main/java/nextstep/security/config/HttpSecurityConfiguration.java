package nextstep.security.config;

import nextstep.security.access.hierarchicalroles.NullRoleHierarchy;
import nextstep.security.access.hierarchicalroles.RoleHierarchy;
import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.builder.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpSecurityConfiguration {

    @Bean
    @Scope("prototype")
    public HttpSecurity httpSecurity(AuthenticationManager authenticationManager
            , RoleHierarchy roleHierarchy
    ) {
        if (roleHierarchy == null) {
            roleHierarchy = new NullRoleHierarchy();
        }

        HttpSecurity httpSecurity = new HttpSecurity(authenticationManager);
        httpSecurity.setSharedObject(RoleHierarchy.class, roleHierarchy);

        return httpSecurity;
    }
}
