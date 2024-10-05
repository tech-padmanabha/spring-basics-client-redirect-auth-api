//package io.pn.gateway;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
//import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//public class OAuth2AccessTokenResponseClientImpl implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
//
//	@Autowired
//	private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2Service;
//	
//	@Override
//	public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
//		// Get the access token response
//		OAuth2AccessTokenResponse tokenResponse = getTokenResponse(authorizationGrantRequest);
//		OAuth2UserRequest userRequest = new OAuth2UserRequest(authorizationGrantRequest.getClientRegistration(), tokenResponse.getAccessToken());
//		
//		OAuth2User user = oauth2Service.loadUser(userRequest);
//		System.out.println("User:"+user);
//		return tokenResponse;
//	}
//
//}


