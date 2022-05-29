package org.example;

import java.sql.*;
import java.util.*;
public class TrainDAO {
    private static Connection connection;
    public TrainDAO(Connection connection) {
        this.connection = connection;
    }
    static String stat;
    public List<Train> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from train");
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    private List<Train> getTrainList(ResultSet resultSet) throws SQLException {
        List<Train> trains = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String Station = resultSet.getString("station");
            int TrainNumber = resultSet.getInt("trainNumber");
            int Time = resultSet.getInt("time");
            int SeatsCoupe = resultSet.getInt("SeatsCoupe");
            int SeatsReserved = resultSet.getInt("seatsReserved");
            int SeatsLux = resultSet.getInt("seatsLux");
            int rating_type = resultSet.getInt("rating_type");
            trains.add(new Train(id, Station, TrainNumber, Time, SeatsCoupe, SeatsReserved, SeatsLux, rating_type));
        }
        return trains;
    }
    //a
    public List<Train> trainsToStation(String station) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from train join rating c on train.rating_type = c.rating_type where station = ? order by trainNumber");
            preparedStatement.setString(1, station);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    //b

    public List<Train> trainsToStationAfterTime(String station , int time) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from train join rating c on train.rating_type = c.rating_type where station = ? and time > ?");
            preparedStatement.setString(1, station);
            preparedStatement.setInt( 2,time);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    //с

    public List<Train> trainsToStationWithCoupe(String station ) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from train join rating c on train.rating_type = c.rating_type where station = ? and seatsCoupe > ?");
            preparedStatement.setString(1, station);
            preparedStatement.setInt( 2,0);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    //d
    public List<Train> trainsToStationBySeats(String station) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from train join rating c on train.rating_type = c.rating_type where station = ? order by seatsCoupe + seatsReserved + seatsLux");
            preparedStatement.setString(1, station);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    //e
    public List stationsByPopularity() {
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select station, COUNT(*) as num FROM train GROUP BY station ORDER BY COUNT(*) ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stat = resultSet.getString("station");
                list.add(stat);
            }

            return list;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
    //f
    public List<Train> getStationsAndTrains () {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from train join rating c on train.rating_type = c.rating_type order by station, id ");
            ResultSet resultSet = preparedStatement.executeQuery();
            return getTrainList(resultSet);
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

}