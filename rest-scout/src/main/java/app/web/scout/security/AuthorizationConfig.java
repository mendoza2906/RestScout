package app.web.scout.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import app.web.scout.model.pojo.security.UserAccount;
import app.web.scout.services.AccountService;
import app.web.scout.services.TokenBlackListService;
import app.web.scout.util.Constant;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${security.oauth2.client.id}")
    private String clientId;

    @Value("${security.oauth2.client.secret}")
    private String clientSecret;

    @Value("${security.oauth2.signingKey}")
    private String signingKey;
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        return new AccountService();
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {

        oauthServer
                // we're allowing access to the token only for clients with 'ROLE_TRUSTED_CLIENT' authority
                .tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()

                .withClient(clientId)
                    .authorizedGrantTypes("client_credentials", "password", "refresh_token")
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .scopes("read", "write")
                    .resourceIds(resourceId)
                    .accessTokenValiditySeconds(Constant.ACCESS_TOKEN_VALIDITY_SECONDS) 
                    .refreshTokenValiditySeconds(Constant.REFRESH_TOKEN_VALIDITY_SECONDS) 
                    .secret(passwordEncoder.encode(clientSecret));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);

    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Autowired
    private TokenBlackListService blackListService;

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        MyTokenService tokenService = new MyTokenService(blackListService);
        tokenService.setTokenStore(tokenStore());
        tokenService.setSupportRefreshToken(true);
        tokenService.setTokenEnhancer(accessTokenConverter());
        return tokenService;
    }


    static class MyTokenService extends DefaultTokenServices {

        private TokenBlackListService blackListService;

        public MyTokenService(TokenBlackListService blackListService) {
            this.blackListService = blackListService;
        }

        @Override
        public OAuth2AccessToken readAccessToken(String accessToken) {
            return super.readAccessToken(accessToken);
        }



        @Override
        public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
            OAuth2AccessToken token = super.createAccessToken(authentication);
            UserAccount account = (UserAccount) authentication.getPrincipal();
            String jti = (String) token.getAdditionalInformation().get("jti");

            blackListService.addToEnabledList(
                    account.getId(),
                    jti,
                    token.getExpiration().getTime() );
            return token;
        }

        @Override
        public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) throws AuthenticationException {

            String jti = tokenRequest.getRequestParameters().get("jti");
            try {
                if ( jti != null )
                        if ( blackListService.isBlackListed(jti) ) return null;


                OAuth2AccessToken token = super.refreshAccessToken(refreshTokenValue, tokenRequest);
                blackListService.addToBlackList(jti);
                return token;
            } catch (TokenBlackListService.TokenNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
