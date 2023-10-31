package org.app.config;


import org.app.bean.Initializer;
import org.app.bean.NotInitContacts;
import org.springframework.context.annotation.*;

@Configuration
@Profile("default")
public class DefInitConfig {
    @Bean
    public Initializer initializer(){
        return new NotInitContacts();
    }
}
