package hello.youtubecommentsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_NAME = "Youtube Comment Search API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Youtube Comment Search API 명세서";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드를 표시할 것이면 true
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hello.youtubecommentsearch")) // Controller가 들어있는 패키지. 이 경로의 하위에 있는 api만 표시됨.
                .paths(PathSelectors.any()) // 위 패키지 안의 api 중 지정된 path만 보여줌. (any()로 설정 시 모든 api가 보여짐)
                .build();
    }

    public ApiInfo apiInfo() {  // API의 이름, 현재 버전, API에 대한 정보
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
//    public ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("SpringBoot Rest API Documentation")
//                .description("3rd UMC Server: BAEMIN Clone coding - ?조")
//                .version("0.1")
//                .build();
//    }
}
//public class SwaggerConfig {  // Swagger
//
//    private static final String API_NAME = "Youtube Comment Search API";
//    private static final String API_VERSION = "0.0.1";
//    private static final String API_DESCRIPTION = "Youtube Comment Search API 명세서";
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
////                .apis(RequestHandlerSelectors.basePackage("hello.youtubecommentsearch.controller"))  // Swagger를 적용할 클래스의 package명
////                .paths(PathSelectors.any())  // 해당 package 하위에 있는 모든 url에 적용
//                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
//                .paths(PathSelectors.ant("/search/**")) // 그중 /api/** 인 URL들만 필터링
//                .build();
////                .apiInfo(apiInfo());
//    }
//
//    public ApiInfo apiInfo() {  // API의 이름, 현재 버전, API에 대한 정보
//        return new ApiInfoBuilder()
//                .title(API_NAME)
//                .version(API_VERSION)
//                .description(API_DESCRIPTION)
//                .build();
//    }
//}