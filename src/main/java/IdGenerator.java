package main.java;

import java.util.HashSet;
import java.util.Random;


public class IdGenerator {

    private static int uniqueId;
    private static int uniqueGroupId;
    private static int min = 100000;
    private static int max = 999999;
    private static int max3 = 999;
    private static int min3 = 100;
    static HashSet<Integer> IDs = new HashSet<>();
    static HashSet<Integer> GroupIDs = new HashSet<>();

    public static int generateId() {
        Random randomId = new Random();
        return randomId.nextInt(max - min + 1) + min;
    }
    public static int getUniqueId() {
        uniqueId = IdGenerator.generateId();
        while (IDs.contains(uniqueId)) {
            uniqueId = IdGenerator.generateId();
        }
        IDs.add(uniqueId);
        return uniqueId;
    }
    public static int generateGroupId() {
        Random randomId = new Random();
        return randomId.nextInt(max3 - min3 + 1) + min3;
    }
    public static int getUniqueGroupId() {
        uniqueGroupId = IdGenerator.generateGroupId();
        while (IDs.contains(uniqueGroupId)) {
            uniqueGroupId = IdGenerator.generateGroupId();
        }
        GroupIDs.add(uniqueGroupId);
        return uniqueGroupId;
    }



}
