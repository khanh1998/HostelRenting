package org.avengers.capstone.hostelrenting.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

import static com.google.api.client.http.HttpMethods.POST;

@Configuration
@EnableWebMvc
public class WebConfiguation implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8080",
                        "https://hostel-renting.netlify.app",
                        "https://td-vue-firestore-chat.web.app",
                        "https://nhatro.sac.vn",
                        "https://hotel-renting-develop.netlify.app",
                        "http://127.0.0.1:8080",
                        "https://hostel-renting.azurewebsites.net",
                        "https://blue-stone-032f5e600.azurestaticapps.net "
                ).allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("Content-Type", "Origin", "Authorization");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
