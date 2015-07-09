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


    final public static PackageCostComparator PACKAGE_COST_COMPARATOR = new PackageCostComparator();
    final public static ItemCostComparator ITEM_COST_COMPARATOR = new ItemCostComparator();
    final public static ItemWeightComparator ITEM_WEIGHT_COMPARATOR = new ItemWeightComparator();

    final public static String colon = ":";
    final public static String braces = "\\)\\(";
    final public static String oBrace = "(";
    final public static String cBrace = ")";

    final public static String empty = "";
    final public static String space = " ";
    final public static String com = ",";
    final public static String dollar = "$";
    final public static String minus = "-";


    final class Item {
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
    }

    final class Package {
        final List<Integer> items = new ArrayList<Integer>(15);
        float weight;
        int cost;

        public final String getItemListAsString() {
            Collections.sort(items);
            return listToString(items);
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//
//            Package aPackage = (Package) o;
//
//            if (Float.compare(aPackage.weight, weight) != 0) return false;
//            if (cost != aPackage.cost) return false;
//            Collections.sort(items);
//            return items.equals(aPackage.items);
//
//        }
//
//        @Override
//        public int hashCode() {
//            Collections.sort(items);
//            int result = items.hashCode();
//            result = 31 * result + (weight != +0.0f ? Float.floatToIntBits(weight) : 0);
//            result = 31 * result + cost;
//            return result;
//        }

        @Override
        public String toString() {
            return "Package{" +
                    "items=" + items +
                    ", weight=" + weight +
                    ", cost=" + cost +
                    '}';
        }
    }

    public static final String listToString(List<Integer> items) {
        final StringBuilder sb = new StringBuilder();
        final int size = items.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(com);
            }
            sb.append(items.get(i));
        }
        return sb.toString();
    }

    final public static class ItemCostComparator implements Comparator<Item> {

        @Override
        public int compare(final Item o1, final Item o2) {
            return o1.cost > o2.cost ? 1 : -1;
        }
    }

    final public static class ItemWeightComparator implements Comparator<Item> {

        @Override
        public int compare(final Item o1, final Item o2) {
            if (o1.weight == o2.weight) {
                return o1.cost > o2.cost ? 1 : -1;
            } else {
                return o1.weight > o2.weight ? 1 : -1;
            }
        }
    }

    final public static class PackageCostComparator implements Comparator<Package> {

        @Override
        public int compare(final Package o1, final Package o2) {
            if (o1.cost == o2.cost) {
                return o1.weight < o2.weight ? 1 : -1;
            } else {
                return o1.cost > o2.cost ? 1 : -1;
            }
        }
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
                String[] sitems = packline[1].split(braces);

                List<Item> items = new ArrayList<Item>(15);

                int sLenght = sitems.length;

                for (int i = 0; i < sLenght; i++) {
                    String sitem = sitems[i].replace(oBrace, empty);
                    sitem = sitem.replace(cBrace, empty);
                    String[] itemData = sitem.split(com);

                    final float weight = Float.valueOf(itemData[1]);
                    if (weight <= packageMaxWeight) {
                        Item item = new Item();
                        item.index = Integer.valueOf(itemData[0]);
                        item.weight = weight;
                        item.cost = Integer.valueOf(itemData[2].replace(dollar, empty));
                        items.add(item);
                    }
                }

//                Map<String, Package> packageMap = new HashMap<>(sLenght);
//                final Set<Package> packageSet = new HashSet<>();

                Collections.sort(items, ITEM_WEIGHT_COMPARATOR);

                iSize = items.size();
                int maxCost = 0;
                float maxWeight = packageMaxWeight;
                String max = minus;
                for (int i = 0; i < iSize; i++) {
                    for (int j = i; j < iSize; j++) {

                        Package aPackage = new Package();
                        for (int k = i; k < iSize; k++) {

                            if (k != j) {
                                final Item item = items.get(k);
                                if ((aPackage.weight + item.weight) <= packageMaxWeight) {
                                    aPackage.items.add(item.index);
                                    aPackage.cost += item.cost;
                                    aPackage.weight += item.weight;
                                } else {
                                    break;
                                }
                            }
                        }
//                        System.out.println(aPackage +"  --  " + aPackage.getItemListAsString());
                        if (aPackage.items.size() > 0 && (aPackage.cost > maxCost || (aPackage.cost == maxCost && aPackage.weight < maxWeight))) {
//                            packageMap.put(aPackage.getItemListAsString(), aPackage);
                            maxCost = aPackage.cost;
                            maxWeight = aPackage.weight;
                            max = aPackage.getItemListAsString();
                        }
//                        packageSet.add(aPackage);
                    }
                }
//                final List<Package> packages = new ArrayList<>(packageMap.size());
//                final List<Package> packages = new ArrayList<>(packageSet.size());

//                packages.addAll(packageMap.values());
//                packages.addAll(packageSet);

//                String max = minus;
//                int maxCost = 0;
//                for (String k : packageMap.keySet()) {
//                    Package p = packageMap.get(k);
//                    if (p.cost > maxCost) {
//                        maxCost = p.cost;
//                        max = k;
//                    }
//                }

                System.out.println(max);
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