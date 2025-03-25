/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.frequencyanalysis2;

/**
 *
 * @author luisc
 */

import java.io.*;
import java.util.*;

public class FrequencyAnalysis2 {

    static class Rank {
        String word1;
        String word2;
        int count;

        Rank(String word1, String word2, int count) {
            this.word1 = word1;
            this.word2 = word2;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        String inputFile = "wordcount-results2.txt";
        String filteredOutput = "freq-results2.txt";
        String sortedOutput = "freq-results-sorted2.txt";

        List<Rank> ranks = new ArrayList<>();

        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writerUnsorted = new BufferedWriter(new FileWriter(filteredOutput))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\t");
                if (parts.length == 2) {
                    String[] words = parts[0].split("\\s+");
                    if (words.length == 2) {
                        String word1 = words[0];
                        String word2 = words[1];
                        try {
                            int count = Integer.parseInt(parts[1]);
                            if (count >= 5000) {
                                Rank r = new Rank(word1, word2, count);
                                ranks.add(r);
                                writerUnsorted.write((word1 + " " + word2).replace("\\", "") + " " + count);
                                writerUnsorted.newLine();
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping invalid count: " + parts[1]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        ranks.sort((a, b) -> Integer.compare(b.count, a.count));

        try (BufferedWriter writerSorted = new BufferedWriter(new FileWriter(sortedOutput))) {
            for (Rank r : ranks) {
                writerSorted.write((r.word1 + " " + r.word2).replace("\\", "") + " " + r.count);
                writerSorted.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Bigram frequency analysis complete.");
    }
}

