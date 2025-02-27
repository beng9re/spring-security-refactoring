package nextstep.security.builder;

import nextstep.oauth2.registration.ClientRegistrationRepository;
import nextstep.oauth2.web.OAuth2AuthorizationRequestRedirectFilter;
import nextstep.oauth2.web.OAuth2AuthorizedClientRepository;
import nextstep.oauth2.web.OAuth2LoginAuthenticationFilter;
import nextstep.security.authentication.AuthenticationManager;

public class OAuth2Configurer implements SecurityConfigurer<OAuth2LoginAuthenticationFilter, HttpSecurity> {
	private final ClientRegistrationRepository clientRegistrationRepository;
	private final OAuth2AuthorizedClientRepository authorizedClientRepository;
	private final AuthenticationManager authenticationManager;

	public OAuth2Configurer(ClientRegistrationRepository clientRegistrationRepository, AuthenticationManager authenticationManager) {
		this.clientRegistrationRepository = clientRegistrationRepository;
		this.authorizedClientRepository = new OAuth2AuthorizedClientRepository();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void init(HttpSecurity builder) {}

	@Override
	public void configure(HttpSecurity builder) {
		OAuth2AuthorizationRequestRedirectFilter oAuth2AuthorizationRequestRedirectFilter = new OAuth2AuthorizationRequestRedirectFilter(clientRegistrationRepository);

		OAuth2LoginAuthenticationFilter oAuth2LoginAuthenticationFilter = new OAuth2LoginAuthenticationFilter(
				clientRegistrationRepository,
				authorizedClientRepository,
				authenticationManager
		);

		builder.addFilter(oAuth2AuthorizationRequestRedirectFilter);
		builder.addFilter(oAuth2LoginAuthenticationFilter);
	}
}
