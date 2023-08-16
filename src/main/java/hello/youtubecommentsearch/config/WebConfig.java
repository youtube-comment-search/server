package hello.youtubecommentsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("") // 허용할 도메인을 지정. "*"는 모든 도메인 허용.
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드를 지정.
                .allowedHeaders("*"); // 허용할 HTTP 헤더를 지정. "*"는 모든 헤더 허용.
    }
}
