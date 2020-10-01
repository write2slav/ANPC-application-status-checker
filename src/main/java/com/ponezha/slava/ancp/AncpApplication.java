package com.ponezha.slava.ancp;

import com.ponezha.slava.ancp.model.Ordin;
import com.ponezha.slava.ancp.repos.OrdinsRepo;
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
	private OrdinsRepo ordinsRepo;

	@Scheduled(fixedRateString = "PT3H")
	void updateDB(){

		System.out.println("Task started");
		//Diff from lists of urls from DB and url list on ANCP website
		List<String> urlOnANCP = DosarUtils.getListOfURLsFromANPC();
		List<String> urlsInDb =((List<Ordin>) ordinsRepo.findAll()).stream().map(ordin -> ordin.getUrl()).collect(Collectors.toList());

		urlOnANCP.removeAll(urlsInDb);
		List<String> missingUrls = urlOnANCP;

		if (missingUrls.size()>0) {
			System.out.println("Adding missing ordins to DB");

			DosarUtils.downloadPDFfilesFromList(missingUrls);
			List<Ordin> ordins = DosarUtils.convertoOrdinsObjects(DosarUtils.getOrdinsTextFromPDFFiles(), missingUrls);
			ordinsRepo.saveAll(ordins);

		}
		System.out.println("Task finished");
	}

	@Configuration
	@EnableScheduling
	public class ShadualingConfinuration {
	}
}
