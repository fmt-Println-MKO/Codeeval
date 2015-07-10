package de.sunbits.codeeval.interruptedbubblesort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final Pattern patRounds = Pattern.compile("\\|");
        final Pattern patSpace = Pattern.compile(" ");

        try {
            final BufferedReader inputStream = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = inputStream.readLine()) != null) {
                String[] lData = patRounds.split(line, 0);
                long rounds = Long.valueOf(lData[1].trim());
                String[] data = patSpace.split(lData[0], 0);

                if (rounds > data.length) {
                    rounds = data.length;
                }
                int[] ar = new int[data.length];

                for (int j = 0; j < rounds; j++) {
                    for (int i = 0; i < ar.length - 1; i++) {
                        int v1;
                        int v2;
                        if (j == 0 && i == 0) {
                            v1 = Integer.valueOf(data[i]);
                            v2 = Integer.valueOf(data[i + 1]);
                        } else if (j == 0 && i > 0) {
                            v1 = ar[i];
                            v2 = Integer.valueOf(data[i + 1]);
                        } else {
                            v1 = ar[i];
                            v2 = ar[i + 1];
                        }
                        if (v1 < v2) {
                            ar[i] = v1;
                            ar[i + 1] = v2;
                        } else {
                            ar[i] = v2;
                            ar[i + 1] = v1;
                        }
                    }
                }
                for (int i = 0; i < ar.length; i++) {
                    System.out.print(ar[i] + " ");
                }
                System.out.println();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}