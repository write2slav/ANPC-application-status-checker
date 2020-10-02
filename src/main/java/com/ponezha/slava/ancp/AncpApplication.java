package com.ponezha.slava.ancp;

import com.ponezha.slava.ancp.model.Ordin;
import com.ponezha.slava.ancp.repos.OrdinsRepo;
import com.ponezha.slava.ancp.services.OrdinService;
import com.ponezha.slava.ancp.utils.DosarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class AncpApplication {

	public static void main(String[] args) {
		SpringApplication.run(AncpApplication.class, args);
	}

	@Autowired
	private OrdinService ordinService;

	@Scheduled(fixedRateString = "PT3H")
	void updateDB(){

		System.out.println("Task started");
		ordinService.checkForNewOrdins();
		System.out.println("Task finished");
	}

	@Configuration
	@EnableScheduling
	public class ShadualingConfinuration {
	}
}
