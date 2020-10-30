package com.ponezha.slava.ancp.services;

import com.ponezha.slava.ancp.model.Ordin;
import com.ponezha.slava.ancp.repos.OrdinsRepo;
import com.ponezha.slava.ancp.utils.DosarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdinService {
    @Autowired
    private OrdinsRepo ordinsRepo;

    public void checkForNewOrdins() {

        //Diff from lists of urls from DB and url list on ANCP website
        List<String> urlOnANCP = DosarUtils.getListOfURLsFromANPC();
        List<String> urlsInDb = ((List<Ordin>) ordinsRepo.findAll()).stream().map(Ordin::getUrl).collect(Collectors.toList());

        //Save only missing urls to a list
        List<String> missingUrls = urlOnANCP.stream().filter(url->!urlsInDb.contains(url)).collect(Collectors.toList());
        missingUrls.stream().forEach(System.out::println);

        if (missingUrls.size() > 0) {
            System.out.println("Number of new ordins: " + missingUrls.size());
            System.out.println("Adding missing ordins to DB");
            DosarUtils.downloadPDFfilesFromList(missingUrls);
            List<Ordin> ordins = DosarUtils.convertoOrdinsObjects(DosarUtils.getOrdinsTextFromPDFFiles(), missingUrls);

            ordinsRepo.saveAll(ordins);
        }
    }
}
