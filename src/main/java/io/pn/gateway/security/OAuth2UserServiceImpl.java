package io.pn.gateway.security;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OAuth2UserServiceImpl extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2AccessToken accessToken = userRequest.getAccessToken();
		
		var tokenValue = accessToken.getTokenValue();
		var tokenType = accessToken.getTokenType();
		
		System.out.println("Token :"+ tokenValue);
		System.out.println("Token Type :"+tokenType.getValue());
		
		
		var userUri = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		System.out.println("URI :"+userUri);
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken.getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(userUri, HttpMethod.GET, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
			User user = mapper.readValue(response.getBody(), User.class);
			System.out.println(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return super.loadUser(userRequest);
	}
}

record User(String id,String name,String email) {}