package br.com.grupo63.techchallenge.payment;

import br.com.grupo63.techchallenge.common.config.auth.jwt.JwtService;
import br.com.grupo63.techchallenge.common.config.aws.ecs.ECSTaskIdInfoContributor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(title = "${info.name}", description = "${info.description}", version = "${info.version}"),

		servers ={
				@Server(url = "${server.servlet.context-path:}", description = "Current URL"),
				@Server(url = "localhost:${server.port:8080}${server.servlet.context-path:}", description = "Localhost"),
				@Server(url = "${app.docs-api-url:(no value)}${server.servlet.context-path:}", description = "Custom URL from env")
		})
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
@SpringBootApplication(scanBasePackageClasses = {
		ServicePaymentApplication.class,
		JwtService.class,
		ECSTaskIdInfoContributor.class
})
public class ServicePaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePaymentApplication.class, args);
	}

}
