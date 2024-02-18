package com.quantcast.app;

import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.Files.probeContentType;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        //Create options
        Option filenameOption = Option.builder("f").longOpt("filename")
                .argName("f")
                .hasArg()
                .required(true)
                .desc("Path to directory containing PDF documents to process.\\n\" +\n" +
                        "                                    \"Examples: \\n\" +\n" +
                        "                                    \"-f /path/to/input\\n\" +\n" +
                        "                                    \"-f /path/to/input\\n\" +\n" +
                        "                                    \"-f \\\"/input with spaces in path/folder\\\"\\n\" +\n" +
                        "                                    \"-f ./relative/path\"")
                .build();

        Option dateOption = Option.builder("d").longOpt("dateString")
                .argName("d")
                .hasArg()
                .required(true)
                .desc("Date format YYYY-MM-DD")
                .build();

        //Add the options
        options.addOption(filenameOption);
        options.addOption(dateOption);

        CommandLine cmd;
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helper = new HelpFormatter();

        String dateString = "";
        String filename = "";

        boolean checkDate = false;
        boolean checkFile = false;

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("f")) {
                filename = cmd.getOptionValue("filename");
                checkFile = isFileCSV(filename);
            }

            if (cmd.hasOption("d")) {
                dateString = cmd.getOptionValue("dateString");
                checkDate = isValidDate(dateString);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Command Option:", options);
            System.exit(0);
        }

        if (checkDate && checkFile){
            searchFile(dateString, filename);
        }
    }

    private static void searchFile(String dateString, String filename) {
        LocalDate targetDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);

        //Create an empty list for the cookie count <cookie, how many times shown in the log>
        Map<String, Integer> cookieCounts = new HashMap<>();

        //While reading the file, separate the columns into two parts (cookie,timestamp )
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String cookie = parts[0];

                //converts the timestamp date to LocalDate time to compare input date
                LocalDate timestampDate = LocalDate.parse(parts[1].substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE);
                if (timestampDate.equals(targetDate)) {
                    cookieCounts.put(cookie, cookieCounts.getOrDefault(cookie, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        //counts the maximum time it is shown in the log file
        int maxCount = cookieCounts.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        System.out.println("Most active cookies for " + targetDate + ":");
        cookieCounts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .forEach(entry -> System.out.println(entry.getKey()));
    }

    private static boolean isValidDate(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format, it should be YYYY-MM-DD");
            System.exit(0);
        }
        return true;
    }

    private static boolean isFileCSV(String filename){
        boolean isCSVFile = false;
        try {
            File file = new File(filename);
            Path filePath = file.toPath();
            String contentType = probeContentType(filePath);
            if("text/csv".equals(contentType)){
                isCSVFile = true;
            }
        }
        catch (Exception e) {
            System.out.println("Invalid file format, it should be .csv file");
            System.exit(0);
        }
        return isCSVFile;
    }



}