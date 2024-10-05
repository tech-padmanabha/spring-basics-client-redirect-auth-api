package io.pn.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	@Autowired
	private ClientRegistrationRepository registrarationRepo;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(req -> 
			req.requestMatchers("/").permitAll()
			
				.requestMatchers("/home").authenticated());
	//	http.oauth2Login();
		
		http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorization")
                )
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(this.userService())
                )
                .tokenEndpoint(token -> token
                        .accessTokenResponseClient(this.accessTokenResponseClient())
                )
        );
		http.logout(t -> 
						t //.logoutSuccessUrl("/") // we can use default
						.logoutSuccessHandler(logOutSuccessHandler()) 
						 .invalidateHttpSession(true)
						 .clearAuthentication(true)
						 .deleteCookies("JSESSIONID")
				);
	
		return http.build();
	}
	
	@Bean
	OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
        // Implement your custom user service
	//	DefaultOAuth2UserService oauthService= new DefaultOAuth2UserService();
		DefaultOAuth2UserService oauthService = new OAuth2UserServiceImpl();
        return oauthService;
    }
	
	LogoutSuccessHandler logOutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(registrarationRepo);
		successHandler.setPostLogoutRedirectUri("http://localhost:4455/");
		return successHandler;
	}
	
	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient tokenClient = new DefaultAuthorizationCodeTokenResponseClient();
	
        return tokenClient;
    }
}
