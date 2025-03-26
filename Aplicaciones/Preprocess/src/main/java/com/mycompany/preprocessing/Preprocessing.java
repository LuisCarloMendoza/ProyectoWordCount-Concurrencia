/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.preprocessing;

/**
 *
 * @author luisc
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Preprocessing {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String basePath = Paths.get(".").toAbsolutePath().normalize().toString() + "/WorkingFiles/";
        String datasetFile = basePath + "dataset.txt";
        String dictionaryFile = basePath + "Dictionary.txt";
        String outputFile = basePath + "datasetProcesado.txt";
        
        cleanDataset(datasetFile, dictionaryFile, outputFile);

        long endTime = System.nanoTime();
        long elapsedTimeNano  = endTime - startTime;
        double elapsedTimeMinutes = (double) elapsedTimeNano / 1_000_000_000 / 60;
        System.out.println("Execution time: " + elapsedTimeMinutes + " minutes");
    }

    private static String removePunctuation(String line) {
        return line.replaceAll("&'.*?\\w+;", " ")
                .replaceAll("[^a-zA-Z ]", " ")
                .replaceAll("\\s+", " ");
    }

    private static ArrayList<String> loadDictionary(String dictionaryFile) {
        ArrayList<String> dictionary = new ArrayList<>();
        try (BufferedReader dictReader = new BufferedReader(new FileReader(dictionaryFile))) {
            String word;
            while ((word = dictReader.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return dictionary;
    }

    public static void cleanDataset(String datasetFile, String dictionaryFile, String outputFile) {
        try {
            ArrayList<String> dictionary = loadDictionary(dictionaryFile);

            try (BufferedReader br = new BufferedReader(new FileReader(datasetFile));
                 FileWriter fw = new FileWriter(outputFile, true)) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = removePunctuation(line);
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        String cleanedWord = word.toLowerCase();
                        if (!dictionary.contains(cleanedWord)) {
                            fw.write(cleanedWord + " ");
                        }
                    }
                    fw.write("\n");
                }
            }
            System.out.println("Dataset cleaned successfully and saved to " + outputFile);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

