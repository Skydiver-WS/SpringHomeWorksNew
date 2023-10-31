package org.app.config;

import org.app.bean.ContactInitializer;
import org.app.bean.Initializer;
import org.springframework.context.annotation.*;

@Configuration
@Profile("init")
public class InitConfig {
    @Bean
    public Initializer initializer(){
        return new ContactInitializer();
    }
}
