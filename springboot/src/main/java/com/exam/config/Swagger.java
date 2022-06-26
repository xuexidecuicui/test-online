package com.exam.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class Swagger {
    @Bean
    public Docket docket(){
        DocumentationType documentationType = new DocumentationType("swagger","2.0");
        Contact DEFAULT_CONTACT = new Contact("崔仕敏", "http//www.cuishimin.com", "514105470@qq.com");
        ApiInfo DEFAULT = new ApiInfo("学生在线考试","学生在线考试是一个前后端分离的项目","1.0","http://www.cuishimin.springboot.com",DEFAULT_CONTACT,"" +
                "Apache 2.0","http://www.apache.org/licenses/LICENSE-2.0",new ArrayList());
        return new Docket(documentationType).apiInfo(DEFAULT);
//        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo());
    }
//    public ApiInfo apiInfo(){
//        return new ApiInfo("员工系统管理说明","员工管理系统是一套适用与多种企业办公的自动化管理系统","1.0","514105470@qq.com","cuishimin",
//            "Apache2.0","http://www.apache.org/licenses/LICENSE-2.0"
//        );
//    }
}
