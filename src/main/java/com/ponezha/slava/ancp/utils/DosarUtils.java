package com.ponezha.slava.ancp.utils;

import com.ponezha.slava.ancp.model.Ordin;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DosarUtils {
    private static final String PATH_TO_DIRECTORY = "/Users/sl/Desktop/PDFs/";

    /*
    / Parsing PDF documents that are contained in a folder
    */
    public static List<String> getOrdinsTextFromPDFFiles() {

        // Step one: Get all the files from a directory
        File files = new File(PATH_TO_DIRECTORY);
        System.out.println("Number of files in the directory: " + files.list().length);

        // Step two: Save only PDF files to array
        File[] listOfPdfFiles = files.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".pdf");
            }
        });

        //Sorting files
        System.out.println("Sorting files");

        Arrays.sort(listOfPdfFiles, Comparator.comparingLong(File::lastModified));

        //Step three: Create a list to store passed Ordins(PDF with decisions)
        List<String> listOfOrdins = new ArrayList<String>();

        //Step five: Parse text for each Ordin pdf file and ass it to the list
        for (File pdf : listOfPdfFiles) {

            System.out.println("Parsing file : " + pdf.getName());
            PDDocument document = null;
            try {
                //Loading an existing document
                document = PDDocument.load(pdf);

                //Instantiate PDFTextStripper class
                PDFTextStripper pdfStripper = new PDFTextStripper();

                //Retrieving text from PDF document
                listOfOrdins.add(pdfStripper.getText(document));

                //Closing the document
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
                listOfOrdins.add(" Not a pdf file !!!");
            }
        }
        System.out.println("Parsed all files");
        System.out.println("Deleting files");
        Arrays.stream(new File(PATH_TO_DIRECTORY).listFiles()).forEach(File::delete);

        return listOfOrdins;
    }

    /*
    / Method for scraping ANPC website and creating a list of links with PDF files.
    */
    public static List<String> getListOfURLsFromANPC() {

        // Step one: Parse a HTML documen.
        final String URL = "http://cetatenie.just.ro/index.php/ro/ordine/articol-11";
        final String baseURL = "http://cetatenie.just.ro/";
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            System.out.println(e);
        }

        //Step two: Use Css selector to select all the <a> elements that contain "images" in "href attribute.CSS selector: div a[href*="images"].
        Elements linkElements = doc.select("div a[href*=\"images\"]");

        // List for storing string values of URLs that lead to PDF files of decisions made by ANPC.


        // Step three: concatinating link of PDF resource to base URL.

        // List for storing string values of URLs that lead to PDF files of decisions made by ANPC.
        List<String> listOfURLs = new ArrayList<String>();
        //Adding base URL to the link
        for (Element element : linkElements) listOfURLs.add(baseURL + element.attr("href"));

        return listOfURLs;
    }

    /*
    / Downloading PDF using list of PDF decision URLs.
    */
    public static void downloadPDFfilesFromList(List<String> listOfURLs) {

        //Counter to increment name of saved filed.
        int counter = 1;
        for (String pdfURL : listOfURLs) {
            System.out.println(pdfURL);
            if(counter==3) break;
            try {
                System.out.println("Opening connection");
                URL url = new URL(pdfURL);
                InputStream in = url.openStream();
                FileOutputStream fos = new FileOutputStream(new File(String.format(PATH_TO_DIRECTORY + "/PDF_%d.pdf", counter++)));


                System.out.println("Reading from resource and writing to file...");
                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {

                    fos.write(buffer, 0, length);
                }
                System.out.println("Closing connection");
                fos.close();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("File downloaded");
        }
    }

    /*
    / Converting a list of String Ordins to a list of Ordins objects.
    */
    public static List<Ordin> convertoOrdinsObjects(List<String> listOfStringOrdins, List<String>urls) {
        System.out.println("Conversion started");

        //List<String> urls = DosarUtils.getListOfURLsFromANPC();
        List<Ordin> listOfOrdins = new ArrayList<Ordin>();


        // Regular expressions
        String caseRegex = "[0-9]{1,6}(/)[0-9]{4}";

        // Setting pattern
        Pattern casePattern = Pattern.compile(caseRegex);

        System.out.println("Starting to match Ordin date and case numbers");

        for (int index = 0; index < listOfStringOrdins.size(); index++) {

            List<String> listOfCaseNumbers = new ArrayList<String>();

            Matcher caseMatcher = casePattern.matcher(listOfStringOrdins.get(index));

            //Matching all case numbers in Ordin file
            while (caseMatcher.find()) {
                listOfCaseNumbers.add(caseMatcher.group());
            }

            listOfOrdins.add(new Ordin().setUrl(urls.get(index)).setCaseNumbers(listOfCaseNumbers));
        }
        System.out.println("Conversion finished");
        System.out.println("Number of ordins processed :" + listOfStringOrdins.size());

        return listOfOrdins;
    }
}

