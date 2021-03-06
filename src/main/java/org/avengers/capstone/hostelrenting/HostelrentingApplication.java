package org.avengers.capstone.hostelrenting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //to temporary disable spring boot security
public class HostelrentingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HostelrentingApplication.class, args);
    }

}
