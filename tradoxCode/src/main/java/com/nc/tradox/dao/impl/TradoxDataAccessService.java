package com.nc.tradox.dao.impl;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository("oracle")
public class TradoxDataAccessService implements Dao {

    public Connection connection;

    private final Logger LOGGER = Logger.getLogger(TradoxDataAccessService.class.getName());

    public TradoxDataAccessService() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@91.219.60.189:1521/XEPDB1",
                    "kuperman_anton", "Oracle11XE#");
            Statement stmt = con.createStatement();
            stmt.execute("ALTER SESSION SET CURRENT_SCHEMA=tradox");
            stmt.close();
            this.connection = con;
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    @Override
    public Response auth(String email, String password) {
        Response response = new Response();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"USER\" " +
                    "WHERE EMAIL = '" + email + "'");
            if (resultSet.next()) {
                int real_password = resultSet.getInt("password");
                if (real_password == password.hashCode()) {
                    User user = new UserImpl();
                    user.setUserId(resultSet.getInt("user_id"));
                    user.setUserType(User.UserTypeEnum.valueOf(resultSet.getString("user_type")));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    if (resultSet.getBoolean("verify"))
                        user.setVerify();
                    response.setObject(user);
                } else {
                    response.setError("password");
                }
            } else {
                response.setError("email");
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.auth " + exception.getMessage());
        }
        return response;
    }

    @Override
    public Country getCountryById(String countryId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SHORT_NAME, FULL_NAME FROM COUNTRY " +
                    "WHERE COUNTRY_ID = '" + countryId + "'");
            if (resultSet.next()) {
                Country country = new CountryImpl(resultSet);
                statement.close();
                return country;
            } else {
                System.out.println("There is no country with such id");
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getCountryById " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Passport getPassportById(String passportId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PASSPORT " +
                    "WHERE PASSPORT_ID = '" + passportId + "'");
            if (resultSet.next()) {
                Passport passport = new PassportImpl(resultSet);
                statement.close();
                return passport;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getPassportById " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Route getRouteById(String routeId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROUTE " +
                    "WHERE ROUTE_ID = '" + routeId + "'");
            if (resultSet != null) {
                Route route = new RouteImpl(resultSet);
                statement.close();
                return route;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getRouteById " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Boolean saveRoute(Route route, Integer userId) {
        Set<InfoData> infoData = route.getTransit();
        String departureId = infoData.iterator().next().getDeparture().getShortName();
        String destinationId = getLastElement(infoData).getDestination().getShortName();
        if (isNewRoute(userId, departureId, destinationId)) {
            String query = "INSERT INTO route (transport_type,departure_id,destination_id,user_id)"
                    + "values (?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(route.getTransportType()));
                preparedStatement.setString(2, departureId);
                preparedStatement.setString(3, destinationId);
                preparedStatement.setInt(4, userId);

                boolean result = preparedStatement.execute();
                preparedStatement.close();
                if (result) {
                    Integer routeId = findRoute(userId, departureId, destinationId);
                    saveTransit(infoData, routeId);
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

    public Boolean registrate(User user, String password) {
        try {
            String query = "INSERT INTO \"USER\" (first_name, last_name, birth_date, email, password, phone, passport_id, citizenship, country_id)"
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getBirthDate().getTime()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, password.hashCode());
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassport().getPassportId());
            preparedStatement.setString(8, user.getPassport().getCitizenshipCountry().getShortName());
            preparedStatement.setString(9, user.getLocation().getShortName());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result != 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        boolean result = false;
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
            result = preparedStatement.execute();
            preparedStatement.close();
            return result;
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateUser " + exception.getMessage());
        }
        return result;
    }

    @Override
    public Boolean verifyUserById(int userId) {
        boolean result = true;
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE \"USER\" SET verify = 1 " +
                    "WHERE user_id = " + userId);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public Boolean changePassword(int id, String newPassword) {
        boolean res = true;
        int hashPassword = newPassword.hashCode();
        try {
            Statement statement = connection.createStatement();
            statement.execute("UPDATE \"USER\" SET password=" + hashPassword + " WHERE user_id=" + id);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public boolean deleteUser(User user) {
        int userId = user.getUserId();
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            result = statement.execute("DELETE FROM \"USER\" WHERE USER_ID = " + userId);
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteUser " + exception.getMessage());
        }
        return result;
    }

    @Deprecated
    public boolean deleteUser(int userId) {
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            result = statement.execute("DELETE FROM \"USER\" WHERE USER_ID = " + userId);
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteUser " + exception.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Status.StatusEnum> getCountriesWhereNameLike(String curCountryId, String search) {
        Map<String, Status.StatusEnum> countries = new HashMap<>();
        search = search.toLowerCase();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT s.value as value, c.full_name as name FROM status s INNER JOIN country c ON c.country_id=s.destination_id AND s.destination_id!=" + "'" + curCountryId + "'" + " AND s.country_id = " + "'" + curCountryId + "'" + " WHERE LOWER(c.full_name) LIKE '" + search + "%'");
            Status.StatusEnum status;
            int count = 0;
            while (res.next()) {
                if (res.getInt("value") == 0) {
                    status = Status.StatusEnum.close;
                } else {
                    status = Status.StatusEnum.open;
                }
                countries.put(res.getString("name"), status);
                count++;
            }
            if (count == 0) {
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
        User currentUser = getUserById(Integer.parseInt(userId));
        //InfoData infoData = getInfoData(currentUser.getLocation().getShortName(), destinationId);
        Set<InfoData> transits = new LinkedHashSet<>();
        //transits.add(infoData);

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
        return new RouteImpl(routeId, Route.TransportType.car, transits);
    }

    @Override
    public boolean deleteRoute(Integer id) {
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
    public Documents getDocumentsByCountryIds(FullRoute fullRoute) {
        List<Document> documents = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM DOCUMENT " +
                    "WHERE DOCUMENT_ID IN (" +
                    "SELECT HAVE_DOCUMENT.DOCUMENT_ID FROM HAVE_DOCUMENT " +
                    "WHERE departure_id = '" + fullRoute.getDeparture().getShortName() + "' " +
                    "AND destination_id = '" + fullRoute.getDestination().getShortName() + "')";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                documents.add(new DocumentImpl(resultSet));
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getDocumentsByCountriesIds " + exception.getMessage());
        }
        return new Documents(documents);
    }

    @Override
    public SpeedLimits getSpeedLimitsByCountryId(Country destination) {
        List<SpeedLimit> speedLimits = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM SPEED_LIMITS " +
                    "WHERE COUNTRY_ID = '" + destination.getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                speedLimits.add(new SpeedLimitImpl(resultSet));
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getSpeedLimitsByCountryId " + exception.getMessage());
        }
        return new SpeedLimits(speedLimits);
    }

    @Override
    public Medicines getMedicinesByCountryId(Country destination) {
        List<Medicine> medicines = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM MEDICINE " +
                    "WHERE COUNTRY_ID = '" + destination.getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Medicine medicine = new MedicineImpl(resultSet);
                medicine.setCountry(destination);
                medicines.add(medicine);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getMedicinesByCountryId " + exception.getMessage());
        }
        return new Medicines(medicines);
    }

    @Override
    public Consulates getConsulatesByCountryIds(FullRoute fullRoute) {
        List<Consulate> consulates = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM CONSULATE " +
                    "WHERE COUNTRY_ID = '" + fullRoute.getDestination().getShortName() + "' " +
                    "AND OWNER_ID = '" + fullRoute.getDeparture().getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Consulate consulate = new ConsulateImpl(resultSet);
                consulate.setOwner(fullRoute.getDestination());
                consulate.setCountry(fullRoute.getDeparture());
                consulates.add(consulate);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getConsulatesByCountryIds " + exception.getMessage());
        }
        return new Consulates(consulates);
    }

    @Override
    public News getNewsByCountryId(Country destination) {
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM NEWS " +
                    "WHERE COUNTRY_ID = '" + destination.getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                NewsItem newsItem = new NewsItemImpl(resultSet);
                newsItem.setCountry(destination);
                newsItems.add(newsItem);
            }
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getNewsByCountryId " + exception.getMessage());
        }
        return new News(newsItems);
    }

    @Override
    public Status getStatusByCountryIds(FullRoute fullRoute) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM STATUS LEFT JOIN REASONS ON STATUS.STATUS_ID = REASONS.STATUS_ID " +
                    "WHERE DESTINATION_ID = '" + fullRoute.getDestination().getShortName() + "' " +
                    "AND COUNTRY_ID = '" + fullRoute.getDeparture().getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Status status = new StatusImpl(resultSet);
                statement.close();
                return status;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getStatusByCountryIds " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Covid getCovidInfo(Country country) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM COVID_INFO " +
                    "WHERE COUNTRY_ID = '" + country.getShortName() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Covid covidInfo = new CovidImpl(resultSet);
                statement.close();
                return covidInfo;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getCovidInfo " + exception.getMessage());
        }
        return null;
    }

    @Override
    public double getMediumBill(Country country) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MEDIUM_BILL FROM COUNTRY " +
                    "WHERE COUNTRY_ID = '" + country.getShortName() + "'");
            if (resultSet.next()) {
                double mediumBill = resultSet.getDouble("medium_bill");
                statement.close();
                return mediumBill;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getMediumBill " + exception.getMessage());
        }
        return -1;
    }

    @Override
    public int getTourismCount(Country country) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TOURISM_COUNT FROM COUNTRY " +
                    "WHERE COUNTRY_ID = '" + country.getShortName() + "'");
            if (resultSet.next()) {
                int tourismCount = resultSet.getInt("tourism_count");
                statement.close();
                return tourismCount;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getTourismCount " + exception.getMessage());
        }
        return -1;
    }

    @Override
    public String getCurrency(Country country) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRENCY FROM COUNTRY " +
                    "WHERE COUNTRY_ID = '" + country.getShortName() + "'");
            if (resultSet.next()) {
                String currency = resultSet.getString("currency");
                statement.close();
                return currency;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getCurrency " + exception.getMessage());
        }
        return null;
    }

    private InfoData getLastElement(Set<InfoData> infoData) {
        InfoData lastElement = null;

        for (InfoData element : infoData) {
            lastElement = element;
        }

        return lastElement;
    }

    @Override
    public Boolean saveTransit(Set<InfoData> infoData, Integer route_id) {
        String query = "INSERT INTO transit (\"ORDER\",country_id,route_id)"
                + "values (?,?,?)";
        InfoData first = infoData.iterator().next();
        InfoData last = getLastElement(infoData);
        int count = 0;
        boolean res = false;
        try {
            for (InfoData i : infoData) {
                if (i != first && i != last) {
                    count++;
                    String countryId = i.getDeparture().getShortName();
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, count);
                    preparedStatement.setString(2, countryId);
                    preparedStatement.setInt(3, route_id);

                    res = preparedStatement.execute();
                    if (!res) {
                        System.err.println("Something went wrong with inserting transit to this route: " + route_id);
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
    public Country getCountryByFullName(String countryFullName) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SHORT_NAME, FULL_NAME FROM COUNTRY " +
                    "WHERE LOWER(FULL_NAME) = LOWER('" + countryFullName + "')");
            if (resultSet.next()) {
                Country country = new CountryImpl(resultSet);
                statement.close();
                return country;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getCountryByFullName " + exception.getMessage());
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"USER\" LEFT JOIN PASSPORT ON \"USER\".PASSPORT_ID = PASSPORT.PASSPORT_ID " +
                    "LEFT JOIN COUNTRY ON \"USER\".COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                    "WHERE USER_ID = '" + userId + "'");
            if (resultSet.next()) {
                User user = new UserImpl(resultSet);
                statement.close();
                return user;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getUserById " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Integer getUserByEmail(String email) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT USER_ID FROM \"USER\" " +
                    "WHERE EMAIL = '" + email + "'");
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                statement.close();
                return userId;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getUserByEmail " + exception.getMessage());
        }
        return 0;
    }

    @Override
    public boolean isUser(String email) {
        boolean isUser = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM \"USER\" WHERE email = '" + email + "'");
            if (res.next()) {
                isUser = true;
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isUser;
    }

    public ResultSet transitFor(int routeId, InfoData infoData) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM TRANSIT WHERE route_id =" + routeId + " AND country_id = " + "'" + infoData.getDestination().getShortName() + "'");
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private Integer findRoute(Integer userId, String departureId, String destinationId) {
        int routeId = 0;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT route_id FROM route WHERE user_id=" + userId + " AND departure_id=" + "'" + departureId + "'" + " AND destination_id=" + "'" + destinationId + "'";
            ResultSet res = statement.executeQuery(query);
            if (res.next()) {
                routeId = res.getInt("route_id");
            } else {
                System.err.println("No such route");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return routeId;
    }

    private Boolean isNewRoute(Integer userId, String departureId, String destinationId) {
        return findRoute(userId, departureId, destinationId) == 0;
    }

    @Override
    public ResultSet createNewTransit(int order, String countryId, int routeId) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO TRANSIT(\"order\",country_id,route_id) VALUES(" + order + "," + countryId + "," + routeId + ")";
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
    public boolean isPassport(String passportId) {
        boolean isPassport = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM PASSPORT " +
                    "WHERE PASSPORT_ID = '" + passportId + "'");
            if (res.next()) {
                isPassport = true;
            }
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return isPassport;
    }

    @Override
    public boolean addPassport(Passport passport) {
        try {
            String query = "INSERT INTO PASSPORT (PASSPORT_ID, SERIES, NUM, COUNTRY_ID) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, passport.getPassportId());
            preparedStatement.setString(2, passport.getPassportSeries());
            preparedStatement.setString(3, passport.getPassportNumber());
            preparedStatement.setString(4, passport.getCitizenshipCountry().getShortName());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePassport(Passport passport) {
        try {
            String query = "DELETE FROM PASSPORT WHERE PASSPORT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, passport.getPassportId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM country");
            while (resultSet.next()) {
                Country country = new CountryImpl(resultSet);
                countries.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countries;
    }

}