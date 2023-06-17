package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.io.IOException;

@SpringBootApplication
public class Pp231SpringMvcHibernateBootApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Pp231SpringMvcHibernateBootApplication.class, args);
        openHomePage();
    }

    private static void openHomePage() throws IOException {
        String url = "http://localhost:8080/";
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
    }


}
