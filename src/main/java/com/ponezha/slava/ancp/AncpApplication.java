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

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class AncpApplication {

	public static void main(String[] args) {
		SpringApplication.run(AncpApplication.class, args);
	}
	// TEST
//	@Autowired
//	private OrdinsRepo ordinsRepo;
//
//	@Scheduled(fixedRateString = "PT12H")
//	void updateDB(){
//
//		System.out.println("Task started");
//		//Compare list of urls from DB and url list on ANCP website
//		List<String> currentUrls = DosarUtils.getListOfURLsFromANPC();
//		List<Ordin> ordinsInDB = (List<Ordin>) ordinsRepo.findAll();
//
//		if (currentUrls.size() > ordinsInDB.size()) {
//			System.out.println("Adding missing ordins to DB");
//
//			List<String> missingUrls = currentUrls.stream().limit(currentUrls.size() - ordinsInDB.size()).collect(Collectors.toList());
//
//			missingUrls.stream().forEach(System.out::println);
//			DosarUtils.downloadPDFfilesFromList(missingUrls);
//			List<Ordin> ordins = DosarUtils.convertoOrdinsObjects(DosarUtils.getOrdinsTextFromPDFFiles());
//			ordinsRepo.saveAll(ordins);
//		}
//
//		System.out.println("Task is finished");
//
//	}
//
//	@Configuration
//	@EnableScheduling
//	public class ShadualingConfinuration{
//	}
}
