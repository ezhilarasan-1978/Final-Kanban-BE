package com.kanban.kanban;

import com.kanban.kanban.filter.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class KanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApplication.class, args); }

		@Bean
		public FilterRegistrationBean filterUrl(){
			FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
			filterRegistrationBean.setFilter(new UserFilter());

			filterRegistrationBean.addUrlPatterns("");
			return filterRegistrationBean;
	}

}
