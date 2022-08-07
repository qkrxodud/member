package abley.member;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("abley.member"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("에이블리 회원 조회 서비스")
                .description("앱 서버 설명 문서")
                .version("1.0.0")
                .termsOfServiceUrl("http://cozing.oopy.io/")
                .license("cozing")
                .licenseUrl("http://cozing.oopy.io/")
                .build();
    }

    // 완료가 되었으면 오른쪽 URL 로 접속 => http://localhost:8080/swagger-ui.html
}
