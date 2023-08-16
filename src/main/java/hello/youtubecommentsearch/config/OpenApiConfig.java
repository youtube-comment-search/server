package hello.youtubecommentsearch.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String API_NAME = "Youtube Comment Search API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Youtube Comment Search API 명세서";
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION);
        return new OpenAPI()
                .components(new Components())
                .info(info);
//                .defaultModelsExpandDepth(0);

    }
}