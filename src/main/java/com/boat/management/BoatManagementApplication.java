package com.boat.management;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = "com.boat.management")
public class BoatManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatManagementApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Inspecting beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                if (beanName.contains("corsConfig")) {
                    System.out.println("CorsConfigurer bean found: " + beanName);
                }
            }
        };
    }
	
	@Bean
	 public WebMvcConfigurer corsConfig() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
               	.allowedOrigins("http://localhost:8081", "http://localhost:9090")
               	.allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
               	.allowedHeaders("Authorization","Content-Type")
               	.allowCredentials(true)
               	.maxAge(3600);
				System.out.println("CORS Configuration has been added.");
			}
		};
	}

}
