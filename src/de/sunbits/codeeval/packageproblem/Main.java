package de.sunbits.codeeval.packageproblem;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by matthiaskoch on 09.07.15.
 *
 * @author matthiaskoch
 */
public class Main {


    final class Item implements Comparable<Item> {
        int index;
        float weight;
        int cost;


        @Override
        public String toString() {
            return "Item{" +
                    "index=" + index +
                    ", weight=" + weight +
                    ", cost=" + cost +
                    '}';
        }

//        @Override
//        public int compareTo(final Item o) {
//            if (weight == o.weight) {
//                return cost > o.cost ? 1 : -1;
//            } else {
//                return weight > o.weight ? 1 : -1;
//            }
//        }

        @Override
        public int compareTo(final Item o) {
            if (cost == o.cost) {
                return weight > o.weight ? 1 : -1;
            } else {
                return cost < o.cost ? 1 : -1;
            }
        }
    }

    private final void solve(final String path) {

        final String colon = ":";
        final String braces = "\\)\\(";
        final String oBrace = "(";
        final String cBrace = ")";

        final String empty = "";
        final String space = " ";
        final String com = ",";
        final String dollar = "$";
        final String minus = "-";

//    final private static boolean[] emptyBolAr = new boolean[0];
//    final private static byte[] emptyByteAr = new byte[0];
        final int[] emptyIntAr = new int[0];

        try {
            int iSize;
            int packageMaxWeight;

            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            for (String line : lines) {
                if (line.length() == 0) {
                    System.out.println("-");
                    continue;
                }
//                System.out.println(line);

                line = line.replace(space, empty);
                String[] packline = line.split(colon);
                packageMaxWeight = Integer.valueOf(packline[0]);
                if (packageMaxWeight > 100) {
                    continue;
                }
                String[] sItems = packline[1].split(braces);


                int sLength = sItems.length;

                List<Item> items = new ArrayList<>(sLength);

                for (int i = 0; i < sLength; i++) {
                    String sItem = sItems[i].replace(oBrace, empty);
                    sItem = sItem.replace(cBrace, empty);
                    String[] itemData = sItem.split(com);

                    final float weight = Float.valueOf(itemData[1]);

                    if (weight <= packageMaxWeight) {
                        Item item = new Item();
                        item.index = Integer.valueOf(itemData[0]);
                        item.weight = weight;
                        item.cost = Integer.valueOf(itemData[2].replace(dollar, empty));
                        items.add(item);
                    }
                }
                Collections.sort(items);
//                System.out.println("Items: " + items);

                iSize = items.size();
                int maxCost = 0;
                float maxWeight = packageMaxWeight;

//                boolean[] cPack = emptyBolAr;
                int[] cPack = emptyIntAr;
                int cPacked = 0;
                for (int i = 0; i < iSize; i++) {
                    for (int j = i; j < iSize; j++) {

                        int currentCost = 0;
                        float currentWeight = 0;
                        int[] pack = new int[15];
                        int packed = 0;

                        final Item item1 = items.get(j);
//                                System.out.println("----" + (currentWeight + item1.weight) +"--- "+ packageMaxWeight + " ----> " + currentCost);
                        if ((currentWeight + item1.weight) <= packageMaxWeight) {
                            pack[item1.index] = 1;
                            currentCost += item1.cost;
                            currentWeight += item1.weight;
                            packed++;
                        } else {
//                            break;
                        }


                        for (int k = i; k < iSize; k++) {
//                            System.out.println(j + "-" + k);
                            if (k != j) {
                                final Item item = items.get(k);
//                                System.out.println((currentWeight + item.weight) +"--- "+ packageMaxWeight + " ----> " + currentCost);
                                if ((currentWeight + item.weight) <= packageMaxWeight) {
                                    pack[item.index] = 1;
                                    currentCost += item.cost;
                                    currentWeight += item.weight;
                                    packed++;
                                } else {
//                                    break;
                                }
                            }
                        }
//                        System.out.println(aPackage +"  --  " + aPackage.getItemListAsString());
//                        System.out.println(packed +"-- "+ currentCost +" -- "+ maxCost +" :--------: " + currentWeight +" :-: "+ maxWeight);
                        if (packed > 0 && (currentCost > maxCost || (currentCost == maxCost && currentWeight < maxWeight))) {
                            maxCost = currentCost;
                            maxWeight = currentWeight;
                            cPack = pack;
                            cPacked = packed;
                        }
                    }
                }
                if (cPacked > 0) {
                    final StringBuilder sb = new StringBuilder(cPacked * 2);
                    int j = 0;
                    for (int i = 1; i < 15; i++) {
                        if (cPack[i] == 1) {
                            sb.append(i);
                            j++;
                            if (j < cPacked) {
                                sb.append(com);
                            } else {
                                break;
                            }
                        }
                    }
                    System.out.println(sb.toString());
                } else {
                    System.out.println(minus);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Main pp = new Main();
        if (args.length != 1) {
            System.err.println("no file specified!!");
        } else {
            pp.solve(args[0]);
        }
    }
}