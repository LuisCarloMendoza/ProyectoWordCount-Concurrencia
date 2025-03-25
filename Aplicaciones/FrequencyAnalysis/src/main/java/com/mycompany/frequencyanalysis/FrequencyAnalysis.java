/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.frequencyanalysis;

/**
 *
 * @author luisc
 */

import java.io.*;
import java.util.*;

public class FrequencyAnalysis {

    static class Rank {
        String word;
        int count;

        Rank(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        String inputFile = "wordcount-results.txt";
        String filteredOutput = "freq-results.txt";
        String sortedOutput = "freq-results-sorted.txt";

        List<Rank> ranks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writerUnsorted = new BufferedWriter(new FileWriter(filteredOutput))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    String word = parts[0];
                    try {
                        int count = Integer.parseInt(parts[1]);
                        if (count >= 5000) {
                            Rank r = new Rank(word, count);
                            ranks.add(r);
                            writerUnsorted.write(word + " " + count);
                            writerUnsorted.newLine();
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid count: " + parts[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ranks.sort((a, b) -> Integer.compare(b.count, a.count));

        try (BufferedWriter writerSorted = new BufferedWriter(new FileWriter(sortedOutput))) {
            for (Rank r : ranks) {
                writerSorted.write(r.word + " " + r.count);
                writerSorted.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Analysis complete.");
    }
}
