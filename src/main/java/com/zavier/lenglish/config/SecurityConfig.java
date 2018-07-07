package com.zavier.lenglish.config;

import com.zavier.lenglish.common.EncryptConstants;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public Realm realm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(EncryptConstants.HASH_ALGORITHM_NAME);
        matcher.setHashIterations(EncryptConstants.HASH_ITERATIONS);
        matcher.setStoredCredentialsHexEncoded(true);
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/user/**", "authc");
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }
}
