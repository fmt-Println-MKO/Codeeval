package de.sunbits.codeeval.stringsandarrows;

import java.io.*;

/**
 * Created by matthiaskoch on 09.07.15.
 */
public class Main {

    public static void main(String[] args) {

//        stringArrows1(args[0]);
//        System.out.println("--------------------------------");
        stringArrows2(args[0]);
    }

    private static void stringArrows2(String fileName) {

        InputStream is = null;

        int[] a1b = new int[]{60, 45, 45, 60, 60};
        int[] a2b = new int[]{62, 62, 45, 45, 62};

        int b1b = 10;
        int b2b = 13;
        try {

            is = new FileInputStream(fileName);

            int ca = 0;
            int curByte = is.read();
            int oldByte = 0;
            int j1 = 0;
            int j2 = 0;
            int jb = 0;
            while (curByte != -1) {

                if (curByte == b1b && oldByte == b2b) {
                    //line break only

                } else if (curByte == b1b || curByte == b2b) {
                    //line break count
                    j1 = 0;
                    j2 = 0;
                    jb = 0;
                    System.out.println(ca);
                    ca = 0;
                } else {

                    if (j1 == 1 && a1b[j1] != curByte && a1b[0] == curByte) {
                    } else if (a1b[j1] == curByte) {
                        j1++;
                        j2 = 0;
                        if (j1 == 5) {
                            ca++;
                            j1 = 1;
                        }
                    } else if (j2 == 2 && a2b[j2] != curByte && a2b[1] == curByte) {
                    } else if (a2b[j2] == curByte) {
                        j1 = 0;
                        j2++;
                        if (j2 == 5) {
                            ca++;
                            j2 = 1;
                        }
                    } else {
                        if (j1 > 0) {
                            j1 = 1;
                            if (a1b[j1] == curByte && a1b[0] == oldByte) {
                                j1++;
                            } else {
                                j1 = 0;
                                j2 = 0;
                                if (a1b[j1] == curByte) {
                                    j1++;
                                } else if (a2b[j2] == curByte) {
                                    j2++;
                                }
                            }
                        } else if (j2 > 0) {
                            j1 = 0;
                            j2 = 0;
                            if (a1b[j1] == curByte) {
                                j1++;
                            } else if (a2b[j2] == curByte) {
                                j2++;
                            }
                        }

                    }
                }
                oldByte = curByte;
                curByte = is.read();
            }
            System.out.println(ca);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void stringArrows1(String fileName) {

        char[] a1 = new char[]{'<', '-', '-', '<', '<'};
        char[] a2 = new char[]{'>', '>', '-', '-', '>'};
        try {
            final BufferedReader inputStream = new BufferedReader(new FileReader(fileName));

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