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

                int a1c = 0;
                int a2c = 0;
                int ca = 0;
                char[] chars = line.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    if (a1c == a2c) {
                        if (chars[i] == a1[a1c]) {
                            a1c++;
                        } else if (chars[i] == a2[a2c]) {
                            a2c++;
                        }
                    } else if (a1c > 0) {
                        if (chars[i] == a1[a1c]) {
                            a1c++;
                            if (a1c == 5) {
                                ca++;
                                a1c = 1;
                            }
                        } else if (a1c == 1 && chars[i] == a1[a1c - 1]) {

                        } else if (chars[i] == a2[a2c]) {
                            a1c = 0;
                            a2c++;
                        } else {
                            a1c = 0;
                        }
                    } else if (a2c > 0) {
                        if (chars[i] == a2[a2c]) {
                            a2c++;
                            if (a2c == 5) {
                                ca++;
                                a2c = 1;
                            }
                        } else if (a2c == 2 && chars[i] == a2[a2c - 1]) {

                        } else if (chars[i] == a1[a1c]) {
                            a2c = 0;
                            a1c++;
                        } else {
                            a2c = 0;
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