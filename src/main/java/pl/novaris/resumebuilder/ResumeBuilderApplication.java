package pl.novaris.resumebuilder;

import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.novaris.resumebuilder.config.StorageConfig;
import pl.novaris.resumebuilder.service.StorageService;

@SpringBootApplication(scanBasePackages = {"pl.novaris.resumebuilder"} , exclude = JpaRepositoriesAutoConfiguration.class)
@EnableConfigurationProperties(StorageConfig.class)
@EnableTransactionManagement
public class ResumeBuilderApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ResumeBuilderApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ResumeBuilderApplication.class);
	}
}
