package nextstep.security.builder;

import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.authentication.UsernamePasswordAuthenticationFilter;

public class FormLoginConfigure implements SecurityConfigurer<UsernamePasswordAuthenticationFilter, HttpSecurity> {
    private final AuthenticationManager authenticationManager;

    public FormLoginConfigure(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter(authenticationManager);

        builder.addFilter(usernamePasswordAuthenticationFilter);
    }
}
