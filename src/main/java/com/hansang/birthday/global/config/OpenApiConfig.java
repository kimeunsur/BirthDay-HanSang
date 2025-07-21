package com.hansang.birthday.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("제목")
                .version("1.0")
                .description("설명");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
