package inaction.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class ProjectConfig {


    private final CustomAuthenticationProvider authenticationProvider;
    public ProjectConfig(CustomAuthenticationProvider authenticationProvider) {

        this.authenticationProvider = authenticationProvider;
    }

//    @Bean
//    UserDetailsService userDetailsService(){
//
//        UserDetails user = User.withUsername("Saeed")
//                .password("1234")
//                .authorities("read")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//
//    }

    /** NoOpPasswordEncoder treats password as plain text, it doesn't
     * encrypt or hash them only compares the string using
     *  equal method
     *  **/
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    /** to customize handling of authentication and authorization
     * we need to define bean of type SecurityFilterChain
     * **/
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(Customizer.withDefaults());

        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider);

        var user = User.withUsername("said")
                .password("1234")
                .authorities("read").build();

        var userDetailsService = new InMemoryUserDetailsManager(user);
        http.userDetailsService(userDetailsService);

        return http.build();
    }




}
