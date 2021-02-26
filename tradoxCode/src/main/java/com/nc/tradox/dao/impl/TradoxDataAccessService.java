package com.nc.tradox.dao.impl;

import com.nc.tradox.dao.Dao;
import com.nc.tradox.model.*;
import com.nc.tradox.model.impl.*;
import com.nc.tradox.model.service.CountryView;
import com.nc.tradox.model.service.HaveDocumentView;
import com.nc.tradox.model.service.Response;
import com.nc.tradox.utilities.ExchangeApi;
import org.springframework.stereotype.Repository;

import java.io.IOException;
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
    public Route getRouteById(Integer routeId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROUTE WHERE ROUTE_ID =" + routeId);
            if (resultSet.next()) {
                Country departure = getCountryById(resultSet.getString("departure_id"));
                Country destination = getCountryById(resultSet.getString("destination_id"));
                Route route = new RouteImpl(resultSet);
                route.addTransitCountry(getInfoData(departure, destination));
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
        int rowsCount = 0;
        Set<InfoData> infoData = route.getTransit();
        String departureId = infoData.iterator().next().getDeparture().getShortName();
        String destinationId = getLastElement(infoData).getDestination().getShortName();
        if (!isRoute(userId, departureId, destinationId)) {
            try {
                String query = "INSERT INTO ROUTE (TRANSPORT_TYPE, DEPARTURE_ID, DESTINATION_ID, USER_ID) "
                        + "VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(route.getTransportType()));
                preparedStatement.setString(2, departureId);
                preparedStatement.setString(3, destinationId);
                preparedStatement.setInt(4, userId);
                rowsCount = preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException exception) {
                LOGGER.log(Level.SEVERE, "TradoxDataAccessService.saveRoute " + exception.getMessage());
            }
        }
        return rowsCount > 0;
    }

    @Override
    public Boolean saveRoute(Integer userId, FullRoute fullRoute) {
        int rowsCount = 0;
        try {
            String query = "INSERT INTO ROUTE (TRANSPORT_TYPE, DEPARTURE_ID, DESTINATION_ID, USER_ID) "
                    + "VALUES ('plane', ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fullRoute.getDeparture().getShortName());
            preparedStatement.setString(2, fullRoute.getDestination().getShortName());
            preparedStatement.setInt(3, userId);
            rowsCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.saveRoute " + exception.getMessage());
        }
        return rowsCount > 0;
    }

    @Override
    public InfoData getInfoData(Country departure, Country destination) {
        FullRoute fullRoute = new FullRouteImpl(departure, destination);
        double mediumBill = getMediumBill(destination);
        Documents documents = getDocumentsByCountryIds(fullRoute);
        int tourismCount = getTourismCount(destination);
        Medicines medicines = getMedicinesByCountryId(destination);
        Covid covidInfo = getCovidInfo(destination);
        Consulates consulates = getConsulatesByCountryIds(fullRoute);
        SpeedLimits speedLimits = getSpeedLimitsByCountryId(destination);
        String departureCurrency = getCurrency(fullRoute.getDeparture());
        String destinationCurrency = getCurrency(fullRoute.getDestination());
        Exchange exchange = getExchangeByCountryIds(fullRoute, departureCurrency, destinationCurrency);
        News news = getNewsByCountryId(destination);
        Status status = getStatusByCountryIds(fullRoute);
        return new InfoDataImpl(fullRoute,
                mediumBill,
                documents,
                tourismCount,
                medicines,
                covidInfo,
                consulates,
                speedLimits,
                destinationCurrency,
                exchange,
                news,
                status);
    }

    public Exchange getExchangeByCountryIds(FullRoute fullRoute, String departureCurrency, String destinationCurrency) {
        try {
            List<String> apiExchanges = new ExchangeApi().currentExchange(departureCurrency, destinationCurrency);
            return new ExchangeImpl(apiExchanges.get(1), apiExchanges.get(0), fullRoute);
        } catch (InterruptedException | IOException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getExchangeByCountryId " + exception.getMessage());
        }
        return null;
    }

    public Set<Route> getRoutsByUser(Integer userId) {
        Set<Route> transit = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM route WHERE user_id = '" + userId + "'");
            int count = 0;
            while (res.next()) {
                int routeId = res.getInt("route_id");
                Country departure = getCountryById(res.getString("departure_id"));
                Country destination = getCountryById(res.getString("destination_id"));
                Set<InfoData> infoData = new LinkedHashSet<>();
                infoData.add(getInfoData(departure, destination));
                transit.add(new RouteImpl(routeId, infoData));
                count++;
            }
            if (count == 0) {
                System.out.println("This user hasn't saved any routes yet");
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transit;
    }

    public Set<Route> getFakeRoutesByUserId(Integer userId) {
        Set<Route> transit = new HashSet<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ROUTE WHERE USER_ID = '" + userId + "'");
            while (resultSet.next()) {
                Route route = new RouteImpl();
                route.setRouteId(resultSet.getInt("route_id"));
                InfoData infoData = new InfoDataImpl();
                FullRoute fullRoute = new FullRouteImpl(getCountryById(resultSet.getString("departure_id")),
                        getCountryById(resultSet.getString("destination_id")));
                infoData.setFullRoute(fullRoute);
                route.addTransitCountry(infoData);
                transit.add(route);
            }
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transit;
    }

    public Boolean registrate(User user, String password) {
        try {
            String query = "INSERT INTO \"USER\" (first_name, last_name, birth_date, " +
                    "email, password, phone, passport_id, citizenship, country_id)"
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
    public boolean updateUserData(User user) {
        try {
            String query = "UPDATE \"USER\" SET first_name = ?, last_name = ?, " +
                    "birth_date = ?, email = ?, phone = ?, " +
                    "passport_id = ?, citizenship = ?, country_id = ?"
                    + "WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getBirthDate().getTime()));
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPhone());
            preparedStatement.setString(6, user.getPassport().getPassportId());
            preparedStatement.setString(7, user.getPassport().getCitizenshipCountry().getShortName());
            preparedStatement.setString(8, user.getLocation().getShortName());
            preparedStatement.setInt(9, user.getUserId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result != 0;
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateUserData " + exception.getMessage());
        }
        return false;
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
            ResultSet res = statement.executeQuery("SELECT s.value as value, c.full_name as name " +
                    "FROM status s INNER JOIN country c ON c.country_id=s.destination_id " +
                    "AND s.destination_id!=" + "'" + curCountryId + "'" + " " +
                    "AND s.country_id = " + "'" + curCountryId + "'" + " " +
                    "WHERE LOWER(c.full_name) LIKE '" + search + "%'");
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
    public Route getRoute(Integer userId, String destinationId) {
        User currentUser = getUserById(userId);
        String departureId = currentUser.getLocation().getShortName();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM ROUTE WHERE USER_ID = " + userId + " " +
                    "AND DEPARTURE_ID = '" + departureId + "' AND DESTINATION_ID = '" + destinationId + "'");
            Integer routeId = res.getInt("ROUTE_ID");
            Route.TransportType transportType = Route.TransportType.valueOf(res.getString("TRANSPORT_TYPE"));
            Set<InfoData> transits = new LinkedHashSet<>();
            //InfoData infoData = getInfoData(currentUser.getLocation().getShortName(), destinationId);
            //transits.add(infoData);
            statement.close();
            return new RouteImpl(routeId, transportType, transits);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
        String query = "INSERT INTO transit (\"ORDER\", country_id, route_id) "
                + "values (?, ?, ?)";
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
        User user = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet userData = statement.executeQuery("SELECT * FROM \"USER\" " +
                    "LEFT JOIN COUNTRY ON \"USER\".COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                    "WHERE USER_ID = " + userId);
            if (userData.next()) {
                String passportId = userData.getString("passport_id");
                Statement statement2 = connection.createStatement();
                ResultSet passportData = statement2.executeQuery("SELECT SERIES, NUM, SHORT_NAME, FULL_NAME " +
                        "FROM PASSPORT " +
                        "LEFT JOIN COUNTRY ON PASSPORT.COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                        "WHERE PASSPORT_ID = '" + passportId + "'");
                if (passportData.next()) {
                    user = new UserImpl(userData, passportData);
                }
                user = new UserImpl(userData);
                statement2.close();
            }
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getUserById " + exception.getMessage());
        }
        if (user != null) {
            user.setTransit(getFakeRoutesByUserId(userId));
        }
        return user;
    }

    @Override
    public Country getUserLocationById(int userId) {
        Country country = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT SHORT_NAME, FULL_NAME FROM \"USER\" " +
                    "LEFT JOIN COUNTRY ON \"USER\".COUNTRY_ID = COUNTRY.COUNTRY_ID " +
                    "WHERE USER_ID = " + userId);
            if (resultSet.next()) {
                country = new CountryImpl(resultSet);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.getUserLocationById " + exception.getMessage());
        }
        return country;
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
            ResultSet res = stmt.executeQuery("SELECT * FROM TRANSIT " +
                    "WHERE route_id =" + routeId + " " +
                    "AND country_id = " + "'" + infoData.getDestination().getShortName() + "'");
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isRoute(Integer userId, String departureId, String destinationId) {
        boolean isRoute = false;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT ROUTE_ID FROM ROUTE " +
                    "WHERE USER_ID = " + userId + " AND DEPARTURE_ID = '" + departureId + "' " +
                    "AND DESTINATION_ID = '" + destinationId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                isRoute = true;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.isRoute " + exception.getMessage());
        }
        return isRoute;
    }

    @Override
    public ResultSet createNewTransit(int order, String countryId, int routeId) {
        try {
            Statement statement = connection.createStatement();
            String query = "INSERT INTO TRANSIT(\"order\",country_id,route_id) " +
                    "VALUES (" + order + "," + countryId + "," + routeId + ")";
            ResultSet res = statement.executeQuery(query);
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    @Override
    public boolean isCountry(String fullName) {
        boolean isCountry = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT FULL_NAME FROM COUNTRY " +
                    "WHERE FULL_NAME = '" + fullName + "'");
            if (res.next()) {
                isCountry = true;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.isCountry " + exception.getMessage());
        }
        return isCountry;
    }

    @Override
    public boolean isShortCountry(String countryId) {
        boolean isCountry = false;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT SHORT_NAME FROM COUNTRY " +
                    "WHERE SHORT_NAME = '" + countryId + "'");
            if (res.next()) {
                isCountry = true;
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.isShortCountry " + exception.getMessage());
        }
        return isCountry;
    }

    @Override
    public List<CountryView> getCountryList() {
        List<CountryView> countryList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COUNTRY ORDER BY COUNTRY_ID");
            while (resultSet.next()) {
                CountryView country = new CountryView();
                country.setShortName(resultSet.getString("SHORT_NAME"));
                country.setFullName(resultSet.getString("FULL_NAME"));
                country.setCurrency(resultSet.getString("CURRENCY"));
                country.setMediumBill(resultSet.getDouble("MEDIUM_BILL"));
                country.setTourismCount(resultSet.getInt("TOURISM_COUNT"));
                countryList.add(country);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getCountriesList " + exception.getMessage());
        }
        return countryList;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM \"USER\" " +
                    "LEFT JOIN PASSPORT ON \"USER\".PASSPORT_ID = PASSPORT.PASSPORT_ID ORDER BY USER_ID");
            while (resultSet.next()) {
                User user = new UserImpl(resultSet);
                userList.add(user);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getUserList " + exception.getMessage());
        }
        return userList;
    }

    @Override
    public List<Document> getDocumentList() {
        List<Document> documentList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DOCUMENT ORDER BY DOCUMENT_ID");
            while (resultSet.next()) {
                Document document = new DocumentImpl(resultSet);
                documentList.add(document);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getDocumentList " + exception.getMessage());
        }
        return documentList;
    }

    @Override
    public List<Consulate> getConsulateList() {
        List<Consulate> consulateList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CONSULATE ORDER BY CONSULATE_ID " +
                    "FETCH NEXT 1000 ROWS ONLY");
            while (resultSet.next()) {
                Consulate consulate = new ConsulateImpl(resultSet);
                consulate.setCountry(new CountryImpl(resultSet.getString("COUNTRY_ID"), null));
                consulate.setOwner(new CountryImpl(resultSet.getString("OWNER_ID"), null));
                consulateList.add(consulate);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getConsulateList " + exception.getMessage());
        }
        return consulateList;
    }

    @Override
    public List<HaveDocumentView> getCountryDocumentList() {
        List<HaveDocumentView> haveDocumentViewList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM HAVE_DOCUMENT ORDER BY HAVE_DOCUMENT_ID " +
                    "FETCH NEXT 1000 ROWS ONLY");
            while (resultSet.next()) {
                HaveDocumentView haveDocumentView = new HaveDocumentView();
                haveDocumentView.setId(resultSet.getInt("HAVE_DOCUMENT_ID"));
                haveDocumentView.setDocumentId(resultSet.getInt("DOCUMENT_ID"));
                haveDocumentView.setDeparture(new CountryImpl(resultSet.getString("DEPARTURE_ID"), null));
                haveDocumentView.setDestination(new CountryImpl(resultSet.getString("DESTINATION_ID"), null));
                haveDocumentViewList.add(haveDocumentView);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getDocumentList " + exception.getMessage());
        }
        return haveDocumentViewList;
    }

    @Override
    public List<Medicine> getMedicineList() {
        List<Medicine> medicineList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM MEDICINE ORDER BY MEDICINE_ID");
            while (resultSet.next()) {
                Medicine medicine = new MedicineImpl(resultSet);
                medicine.setCountry(new CountryImpl(resultSet.getString("COUNTRY_ID"), null));
                medicineList.add(medicine);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getMedicineList " + exception.getMessage());
        }
        return medicineList;
    }

    @Override
    public List<Status> getStatusList() {
        List<Status> statusList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM STATUS " +
                    "LEFT JOIN REASONS ON STATUS.STATUS_ID = REASONS.STATUS_ID ORDER BY STATUS.STATUS_ID " +
                    "FETCH NEXT 1000 ROWS ONLY");
            while (resultSet.next()) {
                Status status = new StatusImpl(resultSet);
                status.setCountry(new CountryImpl(resultSet.getString("COUNTRY_ID"), null));
                status.setDestination(new CountryImpl(resultSet.getString("DESTINATION_ID"), null));
                statusList.add(status);
            }
            statement.close();
        } catch (SQLException exception) {
            LOGGER.severe("TradoxDataAccessService.getStatusList " + exception.getMessage());
        }
        return statusList;
    }

    @Override
    public boolean updateCountry(CountryView countryView) {
        int rowCount = 0;
        try {
            String query = "UPDATE COUNTRY " +
                    "SET FULL_NAME     = ?, " +
                    "    SHORT_NAME    = ?, " +
                    "    CURRENCY      = ?, " +
                    "    MEDIUM_BILL   = ?, " +
                    "    TOURISM_COUNT = ? " +
                    "WHERE COUNTRY_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryView.getFullName());
            preparedStatement.setString(2, countryView.getShortName());
            preparedStatement.setString(3, countryView.getCurrency());
            preparedStatement.setDouble(4, countryView.getMediumBill());
            preparedStatement.setInt(5, countryView.getTourismCount());
            preparedStatement.setString(6, countryView.getShortName());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateCountry " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateUser(User user) {
        int rowCount = 0;
        try {
            String query = "UPDATE \"USER\" " +
                    "SET FIRST_NAME  = ?, " +
                    "    LAST_NAME   = ?, " +
                    "    BIRTH_DATE  = ?, " +
                    "    EMAIL       = ?, " +
                    "    VERIFY      = ?, " +
                    "    PHONE       = ?, " +
                    "    PASSPORT_ID = ?, " +
                    "    COUNTRY_ID  = ?, " +
                    "    USER_TYPE   = ?, " +
                    "    CITIZENSHIP = ? " +
                    "WHERE USER_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, (java.sql.Date) user.getBirthDate());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.isVerified() ? 1 : 0);
            preparedStatement.setString(6, user.getPhone());
            preparedStatement.setString(7, user.getPassportId());
            preparedStatement.setString(8, user.getLocation().getShortName());
            preparedStatement.setString(9, user.getUserType().toString());
            preparedStatement.setString(10, user.getCitizenshipCountry().getShortName());
            preparedStatement.setInt(11, user.getUserId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateUser " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateDocument(Document document) {
        int rowCount = 0;
        try {
            String query = "UPDATE DOCUMENT " +
                    "SET NAME        = ?, " +
                    "    DESCRIPTION = ?, " +
                    "    \"FILE\"    = ? " +
                    "WHERE DOCUMENT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getDescription());
            preparedStatement.setString(3, document.getFileLink());
            preparedStatement.setInt(4, document.getDocumentId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateConsulate(Consulate consulate) {
        int rowCount = 0;
        try {
            String query = "UPDATE CONSULATE " +
                    "SET CITY         = ?, " +
                    "    ADDRESS      = ?, " +
                    "    PHONE        = ?, " +
                    "    OWNER_ID     = ?, " +
                    "    COUNTRY_ID   = ? " +
                    "WHERE CONSULATE_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, consulate.getCity());
            preparedStatement.setString(2, consulate.getAddress());
            preparedStatement.setString(3, consulate.getPhoneNumber());
            preparedStatement.setString(4, consulate.getOwner().getShortName());
            preparedStatement.setString(5, consulate.getCountry().getShortName());
            preparedStatement.setInt(6, consulate.getConsulateId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateConsulate " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateCountryDocument(HaveDocumentView haveDocumentView) {
        int rowCount = 0;
        try {
            String query = "UPDATE HAVE_DOCUMENT " +
                    "SET DOCUMENT_ID      = ?, " +
                    "    DESTINATION_ID   = ?, " +
                    "    DEPARTURE_ID     = ? " +
                    "WHERE HAVE_DOCUMENT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, haveDocumentView.getDocumentId());
            preparedStatement.setString(2, haveDocumentView.getDestination().getShortName());
            preparedStatement.setString(3, haveDocumentView.getDeparture().getShortName());
            preparedStatement.setInt(4, haveDocumentView.getId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateCountryDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateMedicine(Medicine medicine) {
        int rowCount = 0;
        try {
            String query = "UPDATE MEDICINE " +
                    "SET NAME        = ?, " +
                    "    COUNTRY_ID  = ? " +
                    "WHERE MEDICINE_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setString(2, medicine.getCountry().getShortName());
            preparedStatement.setInt(3, medicine.getMedicineId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateMedicine " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateStatus(Status status) {
        int rowCount = 0;
        try {
            String query = "UPDATE STATUS " +
                    "SET VALUE          = ?, " +
                    "    DESTINATION_ID = ?, " +
                    "    COUNTRY_ID     = ? " +
                    "WHERE STATUS_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status.getStatus().toString());
            preparedStatement.setString(2, status.getDestination().getShortName());
            preparedStatement.setString(3, status.getCountry().getShortName());
            preparedStatement.setInt(4, status.getStatusId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateStatus " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean updateReason(Status status) {
        Reason reason = status.getReason();
        int rowCount = 0;
        try {
            String query = "UPDATE REASONS " +
                    "SET COVID_REASON       = ?, " +
                    "    CITIZENSHIP_REASON = ?, " +
                    "WHERE STATUS_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reason.getCovidReason() ? 1 : 0);
            preparedStatement.setInt(2, reason.getCitizenshipReason() ? 1 : 0);
            preparedStatement.setInt(3, reason.getReasonId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.updateReason " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addCountry(CountryView countryView) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO COUNTRY " +
                    "(COUNTRY_ID, FULL_NAME, SHORT_NAME, CURRENCY, MEDIUM_BILL, TOURISM_COUNT) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryView.getShortName());
            preparedStatement.setString(2, countryView.getFullName());
            preparedStatement.setString(3, countryView.getShortName());
            preparedStatement.setString(4, countryView.getCurrency());
            preparedStatement.setDouble(5, countryView.getMediumBill());
            preparedStatement.setInt(6, countryView.getTourismCount());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addCountry " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addDocument(Document document) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO DOCUMENT (NAME, DESCRIPTION, \"FILE\") " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, document.getName());
            preparedStatement.setString(2, document.getDescription());
            preparedStatement.setString(3, document.getFileLink());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addConsulate(Consulate consulate) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO CONSULATE (CITY, ADDRESS, PHONE, OWNER_ID, COUNTRY_ID) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, consulate.getCity());
            preparedStatement.setString(2, consulate.getAddress());
            preparedStatement.setString(3, consulate.getPhoneNumber());
            preparedStatement.setString(4, consulate.getOwner().getShortName());
            preparedStatement.setString(5, consulate.getCountry().getShortName());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addConsulate " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addCountryDocument(HaveDocumentView haveDocumentView) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO HAVE_DOCUMENT (DOCUMENT_ID, DESTINATION_ID, DEPARTURE_ID) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, haveDocumentView.getDocumentId());
            preparedStatement.setString(2, haveDocumentView.getDestination().getShortName());
            preparedStatement.setString(3, haveDocumentView.getDeparture().getShortName());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addCountryDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addMedicine(Medicine medicine) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO TRADOX.MEDICINE (NAME, COUNTRY_ID) " +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setString(2, medicine.getCountry().getShortName());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addMedicine " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addStatus(Status status) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO TRADOX.STATUS (VALUE, DESTINATION_ID, COUNTRY_ID) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status.getStatus().toString());
            preparedStatement.setString(2, status.getDestination().getShortName());
            preparedStatement.setString(3, status.getCountry().getShortName());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addStatus " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean addReason(Status status) {
        int rowCount = 0;
        try {
            String query = "INSERT INTO REASONS (COVID_REASON, CITIZENSHIP_REASON, STATUS_ID) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, status.getReason().getCovidReason() ? 1 : 0);
            preparedStatement.setInt(2, status.getReason().getCitizenshipReason() ? 1 : 0);
            preparedStatement.setInt(3, status.getStatusId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.addReason " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteCountry(String countryId) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM COUNTRY " +
                    "WHERE COUNTRY_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryId);
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteCountry " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteUser(User user) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM \"USER\" " +
                    "WHERE USER_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getUserId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteUser " + exception.getMessage());
        }
        return rowCount > 0;
    }


    @Override
    public boolean deleteDocument(Document document) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM DOCUMENT " +
                    "WHERE DOCUMENT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, document.getDocumentId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteConsulate(Consulate consulate) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "    FROM CONSULATE " +
                    "    WHERE CONSULATE_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, consulate.getConsulateId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteConsulate " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteCountryDocument(HaveDocumentView haveDocumentView) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM HAVE_DOCUMENT " +
                    "WHERE HAVE_DOCUMENT_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, haveDocumentView.getDocumentId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteCountryDocument " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteMedicine(Medicine medicine) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM MEDICINE " +
                    "WHERE MEDICINE_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, medicine.getMedicineId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteMedicine " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteStatus(Status status) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM STATUS " +
                    "WHERE STATUS_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, status.getStatusId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteStatus " + exception.getMessage());
        }
        return rowCount > 0;
    }

    @Override
    public boolean deleteReason(Status status) {
        int rowCount = 0;
        try {
            String query = "DELETE " +
                    "FROM REASONS " +
                    "WHERE REASON_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, status.getReason().getReasonId());
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            LOGGER.log(Level.SEVERE, "TradoxDataAccessService.deleteReason " + exception.getMessage());
        }
        return rowCount > 0;
    }

}