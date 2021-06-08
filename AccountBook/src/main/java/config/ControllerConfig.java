package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.AccountBookController;
import controller.AccountController;
import controller.ConversionAccountController;


@Configuration
public class ControllerConfig {

	@Bean
	public AccountController accountController() {
		return new AccountController();
	}
	@Bean
	public AccountBookController accountBookController() {
		return new AccountBookController();
	}
	@Bean
	public ConversionAccountController conversionAccountController() {
		return new ConversionAccountController();
	}
}
