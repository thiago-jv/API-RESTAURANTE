package com.algaworks.algafood.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	 clients.inMemory()
	  
	 .withClient("algafood-web")
	  .secret(passwordEncoder.encode("web123"))
	   .authorizedGrantTypes("password")
	    .scopes("write", "read")
	    .accessTokenValiditySeconds(60 * 60 * 6)
	  
	    .and()
	    
	     .withClient("checktoken")
	      .secret(passwordEncoder.encode("check123"))
	       .authorizedGrantTypes("password", "refresh_token")
		    .scopes("write", "read")
		    .accessTokenValiditySeconds(60 * 60 * 6) // 6h
		     .refreshTokenValiditySeconds(60 * 24 * 60 * 60)  // 60 dias
	
		     .and()
     	
		     .withClient("faturamento")
	          .secret(passwordEncoder.encode("faturamento123"))
	           .authorizedGrantTypes("client_credentials")
	           .scopes("write", "read")
	           
	           .and()
	           
	           .withClient("foodsanalytics")
		          .secret(passwordEncoder.encode("food123"))
		           .authorizedGrantTypes("authorization_code")
		           .scopes("write", "read")
		           .redirectUris("http://localhost:8082")
	          // http://localhost:8081/oauth/authorize?response_type=code&client_id=foodsanalytics&state=abc&redirect_uri=http://aplicacao-cliente
	 
		           .and()
		            .withClient("webclient")
		             .authorizedGrantTypes("implicit")
		              .scopes("write", "read")
		              .redirectUris("http://aplicacao-cliente");
	 //http://localhost:8081/oauth/authorize?response_type=token&client_id=webclient&state=abc&redirect_uri=http://aplicacao-cliente
		 
	 
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	  security.checkTokenAccess("isAuthenticated()");
	  //security.checkTokenAccess("permitAll()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	   endpoints
	    .authenticationManager(authenticationManager)
	    .userDetailsService(userDetailsService);
	  //  .reuseRefreshTokens(false);
	   
	}
	
	
}
