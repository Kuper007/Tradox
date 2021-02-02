package com.nc.tradox.dao.impl;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import com.nc.tradox.model.impl.Reasons;
import com.nc.tradox.service.TradoxService;
import com.nc.tradox.utilities.ExchangeApi;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("oracle")
public class TradoxDataAccessService implements Dao {

    public Connection connection;

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
            //System.out.println(e);
        }
    }

    @Override
    public Map<User, String> auth(String email, String password){
        Map<User,String> result = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM \"USER\"  WHERE email =" + email);
            if(res.next()){
                String real_password = res.getString("password");
                if(real_password.equals(String.valueOf(password.hashCode()))){
                    User user = new UserImpl(res);
                    result.put(user,"true");
                    statement.close();
                    return result;
                } else {
                    statement.close();
                    result.put(null,"password");
                    return result;
                }
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        result.put(null,"email");
        return null;
    }

    @Override
    public Country getCountryById(String id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM country WHERE country_id =" + "'"+id+"'");
            if(res.next()){
                Country country = new CountryImpl(res);
                statement.close();
                return country;
            } else {
                System.out.println("There is no country with such id");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Passport getPassportById(String id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM passport WHERE passport_id =" +"'" +id+"'");
            if(res.next()){
                Passport passport = new PassportImpl(res);
                statement.close();
                return passport;
            } else {
                System.out.println("There is no passport with such id");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Route getRouteById(String id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM route WHERE route_id="+id);
            if(res!=null){
                Route route = new RouteImpl(res);
                statement.close();
                return route;
            } else {
                System.err.println("Incorrect route id");
            }
           statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean saveRoute(Route route, Integer userId) {
        Set<InfoData> infoData = route.getTransit();
        String departureId = infoData.iterator().next().getDepartureCountry().getDepartureCountry().getShortName();
        String destinationId = getLastElement(infoData).getDestinationCountry().getDestinationCountry().getShortName();
        if (isNewRoute(userId,departureId,destinationId)){
            String query = "INSERT INTO route (transport_type,departure_id,destination_id,user_id)"
                    +"values (?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,String.valueOf(route.getTransportType()));
                preparedStatement.setString(2,departureId);
                preparedStatement.setString(3,destinationId);
                preparedStatement.setInt(4,userId);

                boolean result = preparedStatement.execute();
                preparedStatement.close();
                if (result){
                    Integer routeId = findRoute(userId,departureId,destinationId);
                    saveTransit(infoData,routeId);
                } else {
                    System.err.println("Something went wrong");
                }
                return result;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            System.err.println("This route already exists");
        }
        return false;
    }


    @Override
    public Boolean registrate(Map<String, String> info) {
        boolean res = savePassport(info.get("passport_id"), info.get("citizenship"));
        if (res) {
            try {
                String query = "INSERT INTO \"USER\" (first_name,last_name,birth_date,email,password,phone,passport_id,citizenship,country_id)"
                        +"values (?,?,?,?,?,?,?,?,?)";
                java.util.Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(info.get("birth_date"));
                java.sql.Date date2 = new Date(date1.getTime());
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, info.get("first_name"));
                preparedStatement.setString(2, info.get("last_name"));
                preparedStatement.setDate(3, date2);
                preparedStatement.setString(4, info.get("email"));
                preparedStatement.setInt(5, info.get("password").hashCode());
                preparedStatement.setString(6, info.get("phone"));
                preparedStatement.setString(7, info.get("passport_id"));
                preparedStatement.setString(8, info.get("citizenship"));
                preparedStatement.setString(9, info.get("country_id"));

                boolean result = preparedStatement.execute();
                preparedStatement.close();
                return true;
            } catch (SQLException | ParseException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            String query = "UPDATE \"USER\" SET first_name = ?, last_name = ?, birth_date = ?, email = ?, phone = ?, passport_id = ?, citizenship = ?, country_id = ?"
                    + "WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, (java.sql.Date) user.getBirthDate());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getPassport().getPassportId());
            preparedStatement.setString(7, user.getPassport().getCitizenshipCountry().getShortName());
            preparedStatement.setString(8, user.getLocation().getShortName());
            preparedStatement.setInt(9, user.getUserId());
            boolean result = preparedStatement.execute();
            preparedStatement.close();
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
            Statement statement = connection.createStatement();
            res = statement.execute("DELETE FROM \"USER\" WHERE email="+email);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        boolean res = false;
        try {
            Statement statement = connection.createStatement();
            res = statement.execute("DELETE FROM \"USER\" WHERE user_id=" + id);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public CovidImpl getCovidByCountryId(String id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM covid_info WHERE country_id="+"'"+id+"'");
            if(res.next()){
                CovidImpl covid = new CovidImpl(res);
                statement.close();
                return covid;
            } else {
                System.err.println("Incorrect route id");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public InfoData getInfoData(String departureId, String destinationId) {
        Documents documents = getDocumentsByCountriesIds(departureId,destinationId);
        SpeedLimits speedLimits = getSpeedLimitsByCountryId(destinationId);
        Medicines medicines = getMedicinesByCountryId(destinationId);
        Consulates consulates = getConsulatesByCountriesIds(departureId,destinationId);
        News news = getNewsByCountryId(destinationId);
        Status status = getStatusByCountriesIds(departureId,destinationId);
        ExchangeApi exchangeApi = new ExchangeApi();
        Exchange exchange = null;
        try {
            List<String> apiExchanges = exchangeApi.currentExchange(getCountryById(departureId).getCurrency(),getCountryById(destinationId).getCurrency());
            Departure departure =  new DepartureImpl(getCountryById(departureId));
            Destination destination = new DestinationImpl(getCountryById(destinationId));
            FullRouteImpl fullRoute = new FullRouteImpl(departure,destination);
            exchange = new ExchangeImpl(apiExchanges.get(1),apiExchanges.get(0),fullRoute);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return new InfoDataImpl(documents,speedLimits,medicines,consulates,news,exchange,status);
    }

    @Override
    public Map<String, Status.StatusEnum> getCountriesWhereNameLike(String curCountryId, String search) {
        Map<String, Status.StatusEnum> countries = new HashMap<>();
        search = search.toLowerCase();
        try {
            Statement statement = connection.createStatement();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

    @Override
    public Route getRoute(String userId, String destinationId) {
        User currentUser = this.getUserById(Integer.parseInt(userId));
        InfoData infoData = this.getInfoData(currentUser.getLocation().getShortName(),destinationId);
        Set<InfoData> transits = new LinkedHashSet<>();
        transits.add(infoData);

        int routeId = -1;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT max(route_id) as \"max_count\" FROM Route");
            routeId = res.getInt("max_count");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (routeId == -1) {
            return null;
        }
        return new RouteImpl(routeId, Route.TransportType.car,transits);
    }

    @Override
    public boolean deleteRoute(Integer id){
        boolean res = false;
        try {
            Statement statement = connection.createStatement();
            res = statement.execute("DELETE FROM route WHERE route_id=" + id);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
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
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return new Documents(documents);
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
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return documentIds;
    }

    @Override
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
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new SpeedLimits(speedLimits);
    }

    @Override
    public Medicines getMedicinesByCountryId(String id){
        List<Medicine> medicines = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM medicine WHERE country_id="+id;
            ResultSet res = statement.executeQuery(query);
            while (res.next()){
                Medicine medicine = new MedicineImpl(res.getInt("medicine_id"),
                        res.getString("name"),
                        new DestinationImpl(getCountryById(id)));
                medicines.add(medicine);
            }
            if (medicines.isEmpty()){
                System.err.println("No medicines");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Medicines(medicines);
    }

    @Override
    public Consulates getConsulatesByCountriesIds(String departureId, String destinationId){
        List<Consulate> consulates = new ArrayList<>();
        Departure departure =  new DepartureImpl(getCountryById(departureId));
        Destination destination = new DestinationImpl(getCountryById(destinationId));
        FullRouteImpl fullRoute = new FullRouteImpl(departure,destination);
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM consulate WHERE country_id="+destinationId+" AND owner_id="+departureId;
            ResultSet res = statement.executeQuery(query);
            while (res.next()){
                Consulate consulate = new ConsulateImpl(res.getInt("consulate_id"),getCountryById(departureId),
                        res.getString("city"), res.getString("address"),
                        res.getString("phone"), fullRoute);
                consulates.add(consulate);
            }
            if (consulates.isEmpty()){
                System.err.println("No consulates");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Consulates(consulates);
    }

    @Override
    public News getNewsByCountryId(String id){
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM news WHERE country_id="+id;
            ResultSet res = statement.executeQuery(query);
            while (res.next()){
                NewsItem newsItem = new NewsItemImpl(res.getInt("news_id"),res.getString("text"),
                        res.getDate("\"DATE\""), new DestinationImpl(getCountryById(id)));
                newsItems.add(newsItem);
            }
            if (newsItems.isEmpty()){
                System.err.println("No news");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new News(newsItems);
    }

    @Override
    public Status getStatusByCountriesIds(String departureId, String destinationId){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM status WHERE destination_id="+destinationId+" AND country_id="+departureId;
            ResultSet res = statement.executeQuery(query);
            if (res.next()){
                Status.StatusEnum statusEnum;
                if(res.getInt("value")==0){
                    statusEnum = Status.StatusEnum.close;
                } else {
                    statusEnum = Status.StatusEnum.open;
                }
                Reasons reasons = getReasonsByStatusId(res.getInt("status_id"));
                return new StatusImpl(res.getInt("status_id"), statusEnum,reasons,new DestinationImpl(getCountryById(destinationId)));
            } else {
                System.err.println("No status");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public com.nc.tradox.model.impl.Reasons getReasonsByStatusId(Integer id){
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM reasons WHERE status_id="+id;
            ResultSet res = statement.executeQuery(query);
            if (res.next()){
                return new Reasons(res.getInt("reason_id"),
                        res.getBoolean("covid_reason"), res.getBoolean("citizenship_reason"));
            } else {
                System.err.println("No reasons");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private InfoData getLastElement(Set<InfoData> infoData){
        InfoData lastElement = null;

        for (InfoData element : infoData) {
            lastElement = element;
        }

        return lastElement;
    }

    @Override
    public Boolean saveTransit(Set<InfoData> infoData, Integer route_id){
        String query = "INSERT INTO transit (\"ORDER\",country_id,route_id)"
                +"values (?,?,?)";
        InfoData first = infoData.iterator().next();
        InfoData last = getLastElement(infoData);
        int count = 0;
        boolean res = false;
        try {
            for (InfoData i: infoData){
                if (i!=first && i!=last){
                    count++;
                    String countryId = i.getDepartureCountry().getDepartureCountry().getShortName();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,count);
                    preparedStatement.setString(2,countryId);
                    preparedStatement.setInt(3,route_id);

                    res = preparedStatement.execute();
                    if (!res){
                        System.err.println("Something went wrong with inserting transit to this route: "+route_id);
                        break;
                    }
                    preparedStatement.close();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public Country getCountryByFullName(String fullName) {
        fullName = fullName.toLowerCase();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM country WHERE LOWER(full_name) =" + fullName);
            if (res.next()) {
                Country country = new CountryImpl(res);
                statement.close();
                return country;
            } else {
                System.out.println("There is no country with such fullName");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM \"USER\" WHERE user_id =" + id);
            if (res.next()) {
                User user = new UserImpl(res);
                statement.close();
                return user;
            } else {
                System.out.println("There is no user with such id");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ResultSet transitFor(int routeId, InfoData infoData) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM TRANSIT WHERE route_id =" + routeId + " AND country_id = " + infoData.getDestinationCountry().getDestinationCountry().getShortName());
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Integer findRoute(Integer userId, String departureId, String destinationId){
        int routeId = 0;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT route_id FROM route WHERE user_id="+userId+" AND departure_id="+departureId+" AND destination_id="+destinationId;
            ResultSet res = statement.executeQuery(query);
            if (res.next()){
                routeId = res.getInt("route_id");
            } else {
                System.err.println("No such route");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return routeId;
    }

    private Boolean isNewRoute(Integer userId, String departureId, String destinationId){
        return findRoute(userId, departureId, destinationId) == 0;
    }

    @Override
    public ResultSet createNewTransit(int order, String countryId, int routeId) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO TRANSIT(\"order\",country_id,route_id) VALUES("+ order + "," + countryId + "," + routeId + ")";
            ResultSet res = statement.executeQuery(query);
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Route getRoute() {
        return null;
    }

    @Override
    public ResultSet changeTransitOrder(int transitId, int newOrder) {
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE TRANSIT SET order = " + newOrder + "WHERE transit_id = " + transitId;
            ResultSet res = statement.executeQuery(query);
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean savePassport(String id, String citizenship) {
        try {
            String query = "INSERT INTO passport (passport_id,series,num,country_id) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,id.substring(0, 2));
            preparedStatement.setString(3,id.substring(2));
            preparedStatement.setString(4,citizenship);

            boolean res = preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}