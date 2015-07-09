package de.sunbits.codeeval.stringsandarrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by matthiaskoch on 09.07.15.
 */
public class Main {

    public static void main(String[] args) {

        final char[] a1 = new char[]{'<', '-', '-', '<', '<'};
        final char[] a2 = new char[]{'>', '>', '-', '-', '>'};

        try {
            final BufferedReader inputStream = new BufferedReader(new FileReader(args[0]));

            String line;
            while ((line = inputStream.readLine()) != null) {

                int ca = 0;
                char[] chars = line.toCharArray();
                int size = chars.length;
                for (int i = 0; i < size - 4; i++) {

                    int j = 0;
                    for (; j < 5; j++) {
                        if (a1[j] != chars[i + j]) {
                            break;
                        }
                    }
                    if (j == 5) {
                        ca++;
                        i += 3;
                    } else {
                        j = 0;
                        for (; j < 5; j++) {
                            if (a2[j] != chars[i + j]) {
                                break;
                            }
                        }
                        if (j == 5) {
                            ca++;
                            i += 3;
                        }
                    }
                }
                System.out.println(ca);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}