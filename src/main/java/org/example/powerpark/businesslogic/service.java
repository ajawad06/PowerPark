package org.example.powerpark.businesslogic;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.powerpark.db.MyJDBC;
import org.example.powerpark.model.*;

import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.List;

public class service{
    private final MyJDBC db = new MyJDBC();
    public int getIdByUsername(String username,String role){
        int id=-1;
        try{
            Connection connectDB = db.getConnection();
            String query = "SELECT "+role+"_id FROM "+role+" WHERE username = ?";
            PreparedStatement statement=connectDB.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(role + "_id");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public boolean Login(String username, String password, String role) {
        boolean success = false;
        try{
            Connection connectDB = db.getConnection();
            String verifyLogin = "Select count(1) from " + role +" where username='" + username + "' and password='" + password + "'";
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if (queryResult.getInt(1)==1){
                    success =  true;
                }
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public boolean SignUp(String username,String pass,String email,String phone,String address){
        boolean success = false;
        try{
            Connection connectDB = db.getConnection();
            String dataStore = "INSERT INTO User (username, password, email, phone_num, address) " + "VALUES ('" + username + "', '" + pass + "', '" + email + "', '" + phone + "', '" + address + "')";
            Statement statement=connectDB.createStatement();
            int rowsAffected = statement.executeUpdate(dataStore);
            if (rowsAffected>0) {
                success = true;
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public int getTotalRows(String tablename){
        int total=0;
        try{
            Connection connectDB = db.getConnection();
            String getTotalRows = "SELECT Count(*) from "+tablename;
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(getTotalRows);
            if (queryResult.next()) {
                total = queryResult.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }
    public ObservableList<RecentBookingsDisplay> getRecentBookings(){
        ObservableList<RecentBookingsDisplay> bookings= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String recentBookingQuery = "SELECT station_name as station,username as user,booking_date " +
                    "from booking " +
                    "inner join station using (station_id) " +
                    "inner join user using (user_id) "+
                    "order by booking_date DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(recentBookingQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                String stationName = queryResult.getString("station");
                String userName = queryResult.getString("user");
                Date date = queryResult.getDate("booking_date");

                bookings.add(new RecentBookingsDisplay(stationName, userName, date));


            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
    public ObservableList<User> getUsers(){
        ObservableList<User> users= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String Query = "SELECT * from user";
            PreparedStatement statement=connectDB.prepareStatement(Query);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int userid = queryResult.getInt("user_id");
                String userName = queryResult.getString("username");
                String password=queryResult.getString("password");
                String email = queryResult.getString("email");
                String phone_num=queryResult.getString("phone_num");
                String address=queryResult.getString("address");
                users.add(new User(userid, userName,password, email,phone_num,address));

            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    public ObservableList<Station> getStations(){
        ObservableList<Station> stations= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String Query = "SELECT * from station";
            PreparedStatement statement=connectDB.prepareStatement(Query);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int stationid = queryResult.getInt("station_id");
                String stationname = queryResult.getString("station_name");
                String location=queryResult.getString("location");
                double latitude = queryResult.getDouble("latitude");
                double longitude = queryResult.getDouble("longitude");
                String charger_type=queryResult.getString("charger_type");
                int total_slots=queryResult.getInt("total_slots");
                int available_slots=queryResult.getInt("available_slots");
                stations.add(new Station(stationid,stationname,location,latitude,longitude,charger_type,total_slots,available_slots));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return stations;
    }
    public ObservableList<Slot> getSlots(int station_id){
        ObservableList<Slot> slots=FXCollections.observableArrayList();
        try{
            Connection connectDB=db.getConnection();
            String Query= "Select slot_id,start_time,end_time from slots where station_id=?";
            PreparedStatement statement=connectDB.prepareStatement(Query);
            statement.setInt(1, station_id);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int id=queryResult.getInt("slot_id");
                LocalTime start = queryResult.getObject("start_time",LocalTime.class);
                LocalTime end=queryResult.getObject("end_time", LocalTime.class);
                slots.add(new Slot(id,start,end));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return slots;
    }
    public boolean isSlotAvailable(int stationId, int slotId, java.sql.Date date) {
        String query = "SELECT * FROM booking WHERE station_id = ? AND slot_id = ? AND booking_date = ?";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, stationId);
            ps.setInt(2, slotId);
            ps.setDate(3, date);
            ResultSet rs = ps.executeQuery();
            return !rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public int bookSlot(int userId, int stationId, int slotId, java.sql.Date date) {
        String insert = "INSERT INTO booking (user_id, station_id, slot_id, booking_date) VALUES (?, ?, ?, ?)";
        int bookingId = -1;

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, userId);
            ps.setInt(2, stationId);
            ps.setInt(3, slotId);
            ps.setDate(4, date);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    bookingId = rs.getInt(1);  // get auto-increment booking_id
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingId; // -1 if booking failed
    }

    public User getUserByUsername(String username) {
        User user = null;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT * FROM user WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("user_id");
                String password=rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone_num");
                String address = rs.getString("address");
                user = new User(id, username,password, email, phone, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public boolean removeUser(String username){
        boolean success=false;
        try {
            Connection conn = db.getConnection();
            String query = "DELETE FROM user WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected>0) {
                success = true;
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public Station getStationByName(String name) {
        Station station = null;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT * FROM station WHERE station_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("station_id");
                String stationName=rs.getString("station_name");
                String location = rs.getString("location");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                String charger_type = rs.getString("charger_type");
                int totalslot=rs.getInt("total_slots");
                int availslot=rs.getInt("available_slots");
                station = new Station(id, stationName,location,latitude,longitude,charger_type,totalslot,availslot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return station;
    }
    public boolean removeStation(String name){
        boolean success=false;
        try {
            Connection conn = db.getConnection();
            String query = "DELETE FROM station WHERE station_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected>0) {
                success = true;
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public boolean addStationinDB(String name, String location, double latitude, double longitude, String charger, int slots) throws Exception {
        boolean success = false;
        Connection conn = null;
        try {
            conn = db.getConnection();
            conn.setAutoCommit(false);  // Start transaction

            String dataStore = "INSERT INTO Station (station_name, location, latitude, longitude, charger_type, total_slots, available_slots) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(dataStore, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, location);
            stmt.setDouble(3, latitude);
            stmt.setDouble(4, longitude);
            stmt.setString(5, charger);
            stmt.setInt(6, slots);
            stmt.setInt(7, slots);
            int rowsAffected1 = stmt.executeUpdate();

            if (rowsAffected1 > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int stationId = generatedKeys.getInt(1);
                    System.out.println("Generated station ID: " + stationId);  // Debugging line

                    // Call separate method to add slots
                    if (addSlotsToStation(conn, stationId, charger)) {
                        conn.commit();  // Commit all changes in the transaction
                        success = true;
                    }
                }
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();  // Rollback in case of an error
            }
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);  // Reset auto-commit
            }
        }
        return success;
    }

    // Method to handle slot addition
    private boolean addSlotsToStation(Connection conn, int stationId, String charger) throws SQLException {
        boolean success = false;
        String slotInsert = "INSERT INTO Slots (station_id, start_time, end_time) VALUES (?, ?, ?)";
        PreparedStatement slotStmt = conn.prepareStatement(slotInsert);

        try {
            if (charger.equalsIgnoreCase("fast")) {
                // 4 slots of 2 hours: 1-3, 3-5, 5-7, 7-9
                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "13:00:00");
                slotStmt.setString(3, "15:00:00");
                slotStmt.addBatch();

                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "15:00:00");
                slotStmt.setString(3, "17:00:00");
                slotStmt.addBatch();

                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "17:00:00");
                slotStmt.setString(3, "19:00:00");
                slotStmt.addBatch();

                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "19:00:00");
                slotStmt.setString(3, "21:00:00");
                slotStmt.addBatch();

            } else if (charger.equalsIgnoreCase("standard")) {
                // 2 slots of 4 hours: 1-5, 5-9
                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "13:00:00");
                slotStmt.setString(3, "17:00:00");
                slotStmt.addBatch();

                slotStmt.setInt(1, stationId);
                slotStmt.setString(2, "17:00:00");
                slotStmt.setString(3, "21:00:00");
                slotStmt.addBatch();
            }

            slotStmt.executeBatch();
            success = true;
        } catch (SQLException e) {
            System.out.println("Error adding slots: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public String getMostFreqUser(){
        String user="";
        try{
            Connection connectDB = db.getConnection();
            String usernameQuery = "SELECT u.username " +
                    "FROM user u " +
                    "INNER JOIN booking b ON u.user_id = b.user_id " +
                    "GROUP BY u.user_id " +
                    "ORDER BY COUNT(b.booking_id) DESC " +
                    "LIMIT 1";
            PreparedStatement statement = connectDB.prepareStatement(usernameQuery);
            ResultSet queryResult = statement.executeQuery();
            if (queryResult.next()) {
                user = queryResult.getString("username");
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
    public String getMostFreqStation(){
        String station="";
        try{
            Connection connectDB = db.getConnection();
            String stationQuery = "SELECT s.station_name " +
                    "FROM station s " +
                    "INNER JOIN booking b ON s.station_id=b.station_id " +
                    "GROUP BY s.station_id " +
                    "ORDER BY COUNT(b.booking_id) DESC " +
                    "LIMIT 1";
            PreparedStatement statement = connectDB.prepareStatement(stationQuery);
            ResultSet queryResult = statement.executeQuery();
            if (queryResult.next()) {
                station = queryResult.getString("station_name");
                System.out.println("Fetched: " + station);

            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return station;
    }
    public String getMostFreqStation(String username) {
        String station = "";
        try {
            Connection connectDB = db.getConnection();
            String stationQuery = "SELECT s.station_name " +
                    "FROM station s " +
                    "INNER JOIN booking b ON s.station_id = b.station_id " +
                    "INNER JOIN user u ON b.user_id = u.user_id " +
                    "WHERE u.username = ? " +  // Use parameterized query
                    "GROUP BY s.station_id " +
                    "ORDER BY COUNT(b.booking_id) DESC " +
                    "LIMIT 1";

            PreparedStatement statement = connectDB.prepareStatement(stationQuery);
            statement.setString(1, username);  // Set the username parameter

            ResultSet queryResult = statement.executeQuery();
            if (queryResult.next()) {
                station = queryResult.getString("station_name");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return station;
    }

    public ObservableList<viewBookingsDisplay> getBookings(){
        ObservableList<viewBookingsDisplay> bookings= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String recentBookingQuery = "SELECT b.booking_id,u.username,s.station_name,sl.start_time,sl.end_time,b.booking_date " +
                    "from booking b " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "inner join slots sl using (slot_id) "+
                    "order by booking_date DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(recentBookingQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int booking_id=queryResult.getInt("booking_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                LocalTime start = queryResult.getObject("start_time",LocalTime.class);
                LocalTime end=queryResult.getObject("end_time", LocalTime.class);
                Date date = queryResult.getDate("booking_date");

                bookings.add(new viewBookingsDisplay(booking_id,username,station,start,end,date));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
    public ObservableList<viewBookingsDisplay> getBookings(int id){
        ObservableList<viewBookingsDisplay> bookings= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String recentBookingQuery = "SELECT b.booking_id,u.username,s.station_name,sl.start_time,sl.end_time,b.booking_date " +
                    "from booking b " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "inner join slots sl using (slot_id) "+
                    "where u.user_id="+id+
                    " order by booking_date DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(recentBookingQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int booking_id=queryResult.getInt("booking_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                LocalTime start = queryResult.getObject("start_time",LocalTime.class);
                LocalTime end=queryResult.getObject("end_time", LocalTime.class);
                Date date = queryResult.getDate("booking_date");

                bookings.add(new viewBookingsDisplay(booking_id,username,station,start,end,date));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }
    public viewBookingsDisplay getBookingById(int id) {
        viewBookingsDisplay booking = null;
        try {
            Connection connectDB = db.getConnection();
            String recentBookingQuery = "SELECT b.booking_id,u.username,s.station_name,sl.start_time,sl.end_time,b.booking_date " +
                    "from booking b " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "inner join slots sl using (slot_id) "+
                    "where b.booking_id= "+id+
                    " order by booking_date DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(recentBookingQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int booking_id=queryResult.getInt("booking_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                LocalTime start = queryResult.getObject("start_time",LocalTime.class);
                LocalTime end=queryResult.getObject("end_time", LocalTime.class);
                Date date = queryResult.getDate("booking_date");

                booking = new viewBookingsDisplay(booking_id,username,station,start,end,date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }
    public boolean cancelBooking(int id){
        boolean success=false;
        try {
            Connection conn = db.getConnection();
            String query = "DELETE FROM booking WHERE booking_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected>0) {
                success = true;
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public ObservableList<viewInvoiceDisplay> getInvoices(){
        ObservableList<viewInvoiceDisplay> invoices= FXCollections.observableArrayList();
        try{
            Connection connectDB = db.getConnection();
            String InvoiceQuery = "SELECT i.invoice_id, u.username, s.station_name,b.booking_id,i.amount,i.status " +
                    "from invoice i "+
                    "inner join booking b using (booking_id) " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "order by b.booking_id DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(InvoiceQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int invoice_id=queryResult.getInt("invoice_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                int booking_id= queryResult.getInt("booking_id");
                int amount =queryResult.getInt("amount");
                String status = queryResult.getString("status");

                invoices.add(new viewInvoiceDisplay(invoice_id,booking_id,amount,status,username,station));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }public ObservableList<viewInvoiceDisplay> getInvoices(int userid){
        ObservableList<viewInvoiceDisplay> invoices= FXCollections.observableArrayList();
        try{
            Connection connectDB =db.getConnection();
            String InvoiceQuery = "SELECT i.invoice_id, u.username, s.station_name,b.booking_id,i.amount,i.status " +
                    "from invoice i "+
                    "inner join booking b using (booking_id) " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "where u.user_id="+userid+
                    " order by b.booking_id DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(InvoiceQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int invoice_id=queryResult.getInt("invoice_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                int booking_id= queryResult.getInt("booking_id");
                int amount =queryResult.getInt("amount");
                String status = queryResult.getString("status");

                invoices.add(new viewInvoiceDisplay(invoice_id,booking_id,amount,status,username,station));
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }
    public int getRevenue(){
        int amount=0;
        try{
            Connection connectDB = db.getConnection();
            String revenueQuery = "SELECT SUM(amount) as revenue " +
                    "from Invoice; ";
            PreparedStatement statement = connectDB.prepareStatement(revenueQuery);
            ResultSet queryResult = statement.executeQuery();
            if (queryResult.next()) {
                amount = queryResult.getInt("revenue");
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return amount;
    }
    public Admin fetchAdminDetails(String username) {
        Admin admin = null;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT username, password,email, phone_num FROM admin WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String user = rs.getString("username");
                String pass=rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone_num");
                admin = new Admin(user, pass,email, phone);
            }
        } catch (Exception e) {
            System.out.println("Error fetching admin details: " + e.getMessage());
            e.printStackTrace();
        }
        return admin;
    }
    public boolean updateAdminProfile(Admin admin, String role) {
        boolean success = false;
        try {
            Connection conn =db.getConnection();
            String query = "UPDATE " + role + " SET username = ?, password = ?, email = ?, phone_num = ? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getPhone_num());
            stmt.setString(5, Session.getUsername());
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
            if (success) {
                Session.setUsername(admin.getUsername());
            }
        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }


    // USER INTERFACE

    public int getTotalBookings(int id){
        int total=0;
        try{
            Connection connectDB = db.getConnection();
            String getTotalRows = "SELECT COUNT(booking_id) "+
                    "from booking "+
                    "where user_id="+id;
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(getTotalRows);
            if (queryResult.next()) {
                total = queryResult.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }

    public int getUnpaidInvoices(int id){
        int total=0;
        try{
            Connection connectDB = db.getConnection();
            String getTotalRows = "SELECT COUNT(invoice_id) "+
                    "from invoice "+
                    "inner join booking using (booking_id) "+
                    "where status<>'paid' and user_id="+id;
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(getTotalRows);
            if (queryResult.next()) {
                total = queryResult.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }
    public int getAmountSpent(int id){
        int total=0;
        try{
            Connection connectDB = db.getConnection();
            String getTotalRows = "SELECT SUM(amount) "+
                    "from invoice "+
                    "inner join booking using (booking_id) "+
                    "where user_id="+id+
                    " and status='Paid'";
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(getTotalRows);
            if (queryResult.next()) {
                total = queryResult.getInt(1);
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }
    public User fetchUserDetails(String username) {
        User u = null;
        try {
            Connection conn = db.getConnection();
            String query = "SELECT username, password,email, phone_num,address FROM user WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String user = rs.getString("username");
                String pass=rs.getString("password");
                String email = rs.getString("email");
                String phone = rs.getString("phone_num");
                String address=rs.getString("address");
                u = new User(user, pass,email, phone,address);
            }
        } catch (Exception e) {
            System.out.println("Error fetching user details: " + e.getMessage());

            e.printStackTrace();
        }
        return u;
    }
    public boolean updateUserProfile(User u, String role) {
        boolean success = false;
        try {
            Connection conn = db.getConnection();
            String query = "UPDATE " + role + " SET username = ?, password = ?, email = ?, phone_num = ?, address=? WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone_num());
            stmt.setString(5,u.getAddress());
            stmt.setString(6, Session.getUsername());
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
            if (success) {
                Session.setUsername(u.getUsername());
            }
        } catch (Exception e) {
            System.out.println("Error updating profile: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public viewInvoiceDisplay getInvoiceById(int id) {
        viewInvoiceDisplay invoice = null;
        try {
            Connection connectDB = db.getConnection();
            String invoiceQuery = "SELECT i.invoice_id, u.username, s.station_name,b.booking_id,i.amount,i.status " +
                    "from invoice i "+
                    "inner join booking b using (booking_id) " +
                    "inner join station s using (station_id) " +
                    "inner join user u using (user_id) "+
                    "where i.invoice_id="+id+
                    " order by i.invoice_id DESC "+
                    "limit 7;";
            PreparedStatement statement=connectDB.prepareStatement(invoiceQuery);
            ResultSet queryResult=statement.executeQuery();
            while (queryResult.next()){
                int invoice_id=queryResult.getInt("invoice_id");
                String username=queryResult.getString("username");
                String station= queryResult.getString("station_name");
                int booking_id= queryResult.getInt("booking_id");
                int amount =queryResult.getInt("amount");
                String status = queryResult.getString("status");
                invoice = new viewInvoiceDisplay(invoice_id,booking_id,amount,status,username,station);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invoice;
    }
    public boolean payInvoice(int bookingId) {
        boolean success = false;
        try {
            Connection conn = db.getConnection();
            // Step 1: Check if invoice is already paid
            String checkQuery = "SELECT status FROM invoice WHERE booking_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if ("Paid".equalsIgnoreCase(status)) {
                    // Already paid
                    return false;
                } else {
                    // Step 2: Update status to 'Paid'
                    String updateQuery = "UPDATE invoice SET status = 'Paid' WHERE booking_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, bookingId);
                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        success = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }
    public boolean addInvoice(int bookingId, double amount, String status) {
        String insert = "INSERT INTO invoice (booking_id, amount, status) VALUES (?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(insert)) {
            ps.setInt(1, bookingId);
            ps.setDouble(2, amount);
            ps.setString(3, status);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
