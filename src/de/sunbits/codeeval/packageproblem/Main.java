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

    final public static String colon = ":";
    final public static String braces = "\\)\\(";
    final public static String oBrace = "(";
    final public static String cBrace = ")";

    final public static String empty = "";
    final public static String space = " ";
    final public static String com = ",";
    final public static String dollar = "$";
    final public static String minus = "-";


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

        @Override
        public int compareTo(Item o) {
            if (weight == o.weight) {
                return cost > o.cost ? 1 : -1;
            } else {
                return weight > o.weight ? 1 : -1;
            }
        }
    }

    public static final String arrayToString(final int[] items, final int num) {


        final StringBuilder sb = new StringBuilder(num * 2);
        int j = 0;
        for (int i = 1; i < 15; i++) {
            if (items[i] == 1) {
                sb.append(i);
                j++;
                if (j < num) {
                    sb.append(com);
                } else {
                    break;
                }
            }
        }
        return sb.toString();
    }

    public final void solve(final String path) {
        try {
            int iSize;
            int packageMaxWeight;

            List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            for (String line : lines) {
                if (line.length() == 0) {
                    System.out.println("-");
                    continue;
                }

                line = line.replace(space, empty);
//                System.out.println(line);
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

                iSize = items.size();
                int maxCost = 0;
                float maxWeight = packageMaxWeight;
                String max = minus;
                int[] cPack = new int[0];
                int cPacked = 0;
                for (int i = 0; i < iSize; i++) {
                    for (int j = i; j < iSize; j++) {

                        int currentCost = 0;
                        float currentWeight = 0;
                        int[] pack = new int[15];
                        int packed = 0;
                        for (int k = i; k < iSize; k++) {
                            //System.out.println(j + "-" + k);
                            if (k != j) {
                                final Item item = items.get(k);
                                if ((currentWeight + item.weight) <= packageMaxWeight) {
                                    pack[item.index] = 1;
                                    currentCost += item.cost;
                                    currentWeight += item.weight;
                                    packed++;
                                } else {
                                    break;
                                }
                            }
                        }
//                        System.out.println(aPackage +"  --  " + aPackage.getItemListAsString());
                        if (packed > 0 && (currentCost > maxCost || (currentCost == maxCost && currentWeight < maxWeight))) {
                            maxCost = currentCost;
                            maxWeight = currentWeight;
                            cPack = pack;
                            cPacked = packed;
                        }
                    }
                }
                if (cPacked > 0) {
                    System.out.println(arrayToString(cPack, cPacked));
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