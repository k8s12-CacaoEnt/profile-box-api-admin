package com.goorm.profileboxapiadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.goorm")
@SpringBootApplication
public class ProfileBoxApiAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProfileBoxApiAdminApplication.class, args);
	}

}
