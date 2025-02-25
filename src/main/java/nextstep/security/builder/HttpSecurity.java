
package nextstep.security.builder;


import jakarta.servlet.Filter;
import nextstep.security.config.DefaultSecurityFilterChain;
import nextstep.security.config.SecurityFilterChain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HttpSecurity {
    private final LinkedHashMap<Class<? extends SecurityConfigurer>, SecurityConfigurer> configurers = new LinkedHashMap<>();
    private List<Filter> filters = new ArrayList<>();


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

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }

    private SecurityConfigurer getOrApply(SecurityConfigurer configurer) {
        this.configurers.put(configurer.getClass(), configurer);
        return configurer;
    }
}
