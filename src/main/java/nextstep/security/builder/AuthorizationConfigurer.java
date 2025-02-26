package nextstep.security.builder;

import nextstep.security.access.RequestMatcherEntry;
import nextstep.security.authorization.AuthorizationFilter;
import nextstep.security.authorization.AuthorizationManager;
import nextstep.security.authorization.RequestMatcherDelegatingAuthorizationManager;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationConfigurer implements SecurityConfigurer<AuthorizationFilter, HttpSecurity> {

    private final List<RequestMatcherEntry<AuthorizationManager>> mappings = new ArrayList<>();

    @Override
    public void init(HttpSecurity builder) {

    }

    @Override
    public void configure(HttpSecurity builder) {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(new RequestMatcherDelegatingAuthorizationManager(mappings));
        builder.addFilter(authorizationFilter);
    }


    public requestMatchers()

}
