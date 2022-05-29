package org.example;

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;
public class View {
    public static void showTrain(List<Train> trains) {
        trains.forEach(out::println);
    }
    public static void showPopularity(List<String> stations) {
        stations.forEach(out::println);
    }
}
