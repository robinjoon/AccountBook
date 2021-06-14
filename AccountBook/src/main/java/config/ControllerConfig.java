package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.AccountBookController;
import controller.AccountController;
import controller.AssetController;
import controller.CategoryController;
import controller.ConversionAccountController;


@Configuration
public class ControllerConfig {

	@Bean AccountController accountController() {
		return new AccountController();
	}
	@Bean AccountBookController accountBookController() {
		return new AccountBookController();
	}
	@Bean ConversionAccountController conversionAccountController() {
		return new ConversionAccountController();
	}
	@Bean AssetController assetController() {
		return new AssetController();
	}
	@Bean CategoryController categoryController() {
		return new CategoryController();
	}
}
