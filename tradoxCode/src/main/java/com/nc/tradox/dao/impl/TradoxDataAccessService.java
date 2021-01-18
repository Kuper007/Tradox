package com.nc.tradox.dao.impl;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository("oracle")
public class TradoxDataAccessService implements Dao {

    public Connection connection;
    public Statement statement;

    public TradoxDataAccessService() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@91.219.60.189:1521/XEPDB1",
                    "kuperman_anton", "Oracle11XE#");
            Statement stmt = con.createStatement();
            stmt.execute("ALTER SESSION SET CURRENT_SCHEMA=tradox");
            stmt.close();
            this.connection = con;
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public User auth(String email, String password){
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM \"USER\"  WHERE email =" + email);
            if(res.next()){
                String real_password = res.getString("password");
                if(real_password.equals(String.valueOf(password.hashCode()))){
                    User user = new UserImpl(res);
                    statement.close();
                    connection.close();
                    return user;
                } else {
                    System.err.println("Incorrect password");
                    statement.close();
                    connection.close();
                    return null;
                }
            } else {
                System.err.println("There is no user with such email");
            }
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public Country getCountryById(String id) {
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM country WHERE country_id =" + id);
            if(res.next()){
                Country country = new CountryImpl(res);
                statement.close();
                connection.close();
                return country;
            } else {
                System.out.println("There is no country with such id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Passport getPassportById(String id) {
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM passport WHERE passport_id =" + id);
            if(res.next()){
                Passport passport = new PassportImpl(res);
                statement.close();
                connection.close();
                return passport;
            } else {
                System.out.println("There is no passport with such id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Route getRouteById(String id) {
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM route WHERE route_id="+id);
            if(res!=null){
                Route route = new RouteImpl(res);
                connection.close();
                return route;
            } else {
                System.err.println("Incorrect route id");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    @Override
    public Boolean registrate(Map<String, String> info) {
        try {
            String query = "INSERT INTO \"USER\" (first_name,last_name,birth_date,email,password,phone,passport_id,country_id)"
                    +"values (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,info.get("first_name"));
            preparedStatement.setString(2,info.get("last_name"));
            preparedStatement.setDate(3, Date.valueOf(info.get("birth_date")));
            preparedStatement.setString(4,info.get("email"));
            preparedStatement.setInt(5,info.get("password").hashCode());
            preparedStatement.setString(6,info.get("phone"));
            preparedStatement.setString(7,info.get("passport_id"));
            preparedStatement.setString(8,info.get("country_id"));

            boolean result = preparedStatement.execute();
            preparedStatement.close();
            connection.close();
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteUser(User user) {
        String email = user.getEmail();
        boolean res = false;
        try {
            this.statement = connection.createStatement();
            res = statement.execute("DELETE FROM \"USER\" WHERE email="+email);
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public CovidImpl getCovidByCountryId(String id) {
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM covid_info WHERE country_id="+id);
            if(res.next()){
                CovidImpl covid = new CovidImpl(res);
                connection.close();
                return covid;
            } else {
                System.err.println("Incorrect route id");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public InfoData getInfoData(String departureId, String destinationId) {
        try {
            this.statement = connection.createStatement();
            Documents documents = getDocumentsByCountriesIds(departureId,destinationId);
            SpeedLimits speedLimits = getSpeedLimitsByCountryId(destinationId);
            Medicines medicines;
            Consulates consulates;
            News news;
            List<Exchange> exchanges;
            Status status;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Status.StatusEnum> getCountriesWhereNameLike(String curCountryId, String search) {
        Map<String, Status.StatusEnum> countries = new HashMap<>();
        search = search.toLowerCase();
        try {
            this.statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT s.value as value, c.full_name as name FROM status s INNER JOIN country c ON c.country_id=s.destination_id AND s.destination_id!="+curCountryId+" AND s.country_id = "+curCountryId+" WHERE LOWER(c.full_name) LIKE '" + search + "%'");
            Status.StatusEnum status;
            int count = 0;
            while (res.next()){
                if(res.getInt("value")==0){
                    status = Status.StatusEnum.close;
                } else {
                    status = Status.StatusEnum.open;
                }
                countries.put(res.getString("name"),status);
                count++;
            }
            if (count==0){
                System.err.println("There is no such country in this lonely world");
            }
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

    @Override
    public Route getRoute() {
        return new RouteImpl(0, Route.TransportType.car,new HashSet<>());
    }
    // TODO: сделать запрос в бд в таблицу транзит, чтобы достать данные о транзите для Route

    public Documents getDocumentsByCountriesIds(String departureId, String destinationId){
        List<Document> documents = new ArrayList<>();
        List<Integer> documentIds = getDocumentIdsByCountriesIds(departureId,destinationId);
        Departure departure =  new DepartureImpl(getCountryById(departureId));
        Destination destination = new DestinationImpl(getCountryById(destinationId));
        FullRouteImpl fullRoute = new FullRouteImpl(departure,destination);
        for (Integer id: documentIds){
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM document WHERE document_id="+id;
                ResultSet res = statement.executeQuery(query);
                if (res.next()) {
                    Document document = new DocumentImpl(id,res.getString("name"), res.getString("description"), res.getString("\"FILE\""),fullRoute);
                    documents.add(document);
                } else {
                    System.err.println("There is no document with such id: "+id);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        Documents realDocuments = new Documents(documents);
        return realDocuments;
    }

    public List<Integer> getDocumentIdsByCountriesIds(String departureId, String destinationId) {
        List<Integer> documentIds = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT document_id FROM have_document WHERE departure_id="+departureId+" AND destination_id="+destinationId;
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                documentIds.add(res.getInt("document_id"));
            }
            if (documentIds.isEmpty()){
                System.err.println("No documents");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return documentIds;
    }

    public SpeedLimits getSpeedLimitsByCountryId(String id){
        List<SpeedLimit> speedLimits = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM speed_limits WHERE country_id="+id;
            ResultSet res = statement.executeQuery(query);
            while (res.next()){
                SpeedLimit speedLimit = new SpeedLimitImpl(res.getInt("limit_id"),
                        SpeedLimit.TypeOfRoad.valueOf(res.getString("road_type")),
                        new DestinationImpl(getCountryById(id)),res.getInt("speed"));
                speedLimits.add(speedLimit);
            }
            if (speedLimits.isEmpty()){
                System.err.println("No speed limits");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        SpeedLimits realSpeedLimits = new SpeedLimits(speedLimits);
        return realSpeedLimits;
    }
}