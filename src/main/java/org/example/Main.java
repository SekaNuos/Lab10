package org.example;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private TrainDAO trainDAO;

    public static void main(String[] args) {

        new Main().run();
    }

    private void run() {
        String station;
        int time;

        try {
            Connection admin = DriverManager.getConnection("jdbc:mariadb://localhost:3306/TrainsDB", "admin", "123");
            trainDAO = new TrainDAO(admin);
            List<Train> allTrains = trainDAO.findAll();
            Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(System.in);
            mainloop:
            while (true) {
                int m = menu();
                switch (m) {
                    case 0:
                        break mainloop;
                    case 1: {
                        View.showTrain(allTrains);
                        break;
                    }
                    case 2: {
                        System.out.println("\nВведите название станции: ");
                        station=sc.nextLine();
                        View.showTrain(trainDAO.trainsToStation(station));
                        break;
                    }

                    case 3: {
                        System.out.println("\nВведите название станции: ");
                        station = sc.nextLine();
                        time = sc2.nextInt();
                        View.showTrain(trainDAO.trainsToStationAfterTime(station , time));
                        break;
                    }
                    case 4: {
                        System.out.println("\nВведите название станции: ");
                        station=sc.nextLine();
                        View.showTrain(trainDAO.trainsToStationWithCoupe(station));
                        break;
                    }
                    case 5: {
                        System.out.println("\nВведите название станции: ");
                        station=sc.nextLine();
                        View.showTrain(trainDAO.trainsToStationBySeats(station));
                        break;
                    }
                    case 6: {
                        View.showPopularity(trainDAO.stationsByPopularity());
                        break;
                    }
                    case 7: {
                        View.showTrain(trainDAO.getStationsAndTrains());
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int menu() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("1 - Список всех поездов:");
        System.out.println("2 - Список поездов идущих к одной заданой станции по времени отправки:");
        System.out.println("3 - Список поездов идущих к одной заданой станции после заданой времени отправки:");
        System.out.println("4 - Список поездов идущих к одной заданой станции с местами Купе:");
        System.out.println("5 - Список поездов идущих к одной заданой станции по зростанию мест:");
        System.out.println("6 - Список всех станций в порядке возростания запланированных поездов:");
        System.out.println("7 - Список станций и поездов, которые к ним надвигаются:");
        System.out.println("0 - Выход");
        System.out.println("--------------------------------------------------------------");
        return new Scanner(System.in).nextInt();
    }
}