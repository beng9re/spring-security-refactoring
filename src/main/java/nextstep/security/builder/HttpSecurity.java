
package nextstep.security.builder;


import jakarta.servlet.Filter;
import nextstep.oauth2.registration.ClientRegistrationRepository;
import nextstep.security.access.hierarchicalroles.RoleHierarchy;
import nextstep.security.authentication.AuthenticationManager;
import nextstep.security.config.DefaultSecurityFilterChain;
import nextstep.security.config.SecurityFilterChain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpSecurity {
    private final LinkedHashMap<Class<? extends SecurityConfigurer>, SecurityConfigurer> configurers = new LinkedHashMap<>();
    private List<Filter> filters = new ArrayList<>();
    private final Map<Class<?>, Object> sharedObjects = new HashMap<>();


    public HttpSecurity(AuthenticationManager authenticationManager) {
        setSharedObject(AuthenticationManager.class, authenticationManager);
    }

    public void setSharedObject(Class<?> key, Object value) {
        sharedObjects.put(key, value);
    }


    public SecurityFilterChain build() {
        init();
        configure();
        return new DefaultSecurityFilterChain(filters);
    }

    private void init() {
        for (SecurityConfigurer configurer : this.configurers.values()) {
            configurer.init(this);
        }
    }

    private void configure() {
        for (SecurityConfigurer configurer : this.configurers.values()) {
            configurer.configure(this);
        }
    }

    public HttpSecurity csrf(Customizer<CsrfConfigurer> csrfCustomizer) {
        csrfCustomizer.customize((CsrfConfigurer) getOrApply(new CsrfConfigurer()));
        return HttpSecurity.this;
    }

    public HttpSecurity httpBasic(Customizer<HttpBasicConfigurer> httpBasicConfigureCustomizer) {
        AuthenticationManager authenticationManager = (AuthenticationManager) sharedObjects.get(AuthenticationManager.class);

        httpBasicConfigureCustomizer.customize((HttpBasicConfigurer) getOrApply(new HttpBasicConfigurer(authenticationManager)));

        return HttpSecurity.this;
    }

    public HttpSecurity formLogin(Customizer<FormLoginConfigurer> formLoginConfigureCustomizer) {
        AuthenticationManager authenticationManager = (AuthenticationManager) sharedObjects.get(AuthenticationManager.class);

        formLoginConfigureCustomizer.customize((FormLoginConfigurer) getOrApply(new FormLoginConfigurer(authenticationManager)));

        return HttpSecurity.this;
    }

    public HttpSecurity authorizeHttpRequests(Customizer<AuthorizationConfigurer> authenticationManager) {
        RoleHierarchy roleHierarchy = (RoleHierarchy) sharedObjects.get(RoleHierarchy.class);

        authenticationManager.customize((AuthorizationConfigurer) getOrApply(new AuthorizationConfigurer(roleHierarchy)));

        return HttpSecurity.this;
    }

    public HttpSecurity securityContext(Customizer<SecurityContextConfigurer> securityContextCustomizer) {
        securityContextCustomizer.customize((SecurityContextConfigurer) getOrApply(new SecurityContextConfigurer()));

        return HttpSecurity.this;
    }

    public HttpSecurity oauth2Login(Customizer<OAuth2Configurer> oauth2ConfigurerCustomizer) {
        AuthenticationManager authenticationManager = (AuthenticationManager) sharedObjects.get(AuthenticationManager.class);
        ClientRegistrationRepository clientRegistrationRepository = (ClientRegistrationRepository) sharedObjects.get(ClientRegistrationRepository.class);

        OAuth2Configurer oAuth2Configurer = new OAuth2Configurer(clientRegistrationRepository, authenticationManager);

        oauth2ConfigurerCustomizer.customize((OAuth2Configurer) getOrApply(oAuth2Configurer));

        return HttpSecurity.this;
    }

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    private SecurityConfigurer getOrApply(SecurityConfigurer configurer) {
        this.configurers.put(configurer.getClass(), configurer);
        return configurer;
    }
}
