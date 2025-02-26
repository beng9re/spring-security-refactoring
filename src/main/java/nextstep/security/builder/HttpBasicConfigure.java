package nextstep.security.builder;

import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.authentication.BasicAuthenticationFilter;

public class HttpBasicConfigure implements SecurityConfigurer<HttpBasicConfigure, HttpSecurity> {
    private AuthenticationManager authenticationManager;

    public HttpBasicConfigure(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        BasicAuthenticationFilter basicAuthenticationFilter = new BasicAuthenticationFilter(authenticationManager);
        builder.addFilter(basicAuthenticationFilter);
    }
}
