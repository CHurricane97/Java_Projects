package daoObjects;

import interfaces.DAO;
import tableObjects.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class EventDAO implements DAO<Event> {

    private final Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        return new Event(rs.getLong("Event ID"), rs.getString("Name"), rs.getString("Place"), rs.getDate("Date"));
    }

    @Override
    public Optional<Event> get(long id) {
        Event event = null;
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Event\" where \"Event ID\"=?;";

            try {

                preparedStatement = connection.prepareStatement(selectQuery);
                preparedStatement.setLong(1, id);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    event = getEvent(rs);
                }

                preparedStatement.close();
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return Optional.ofNullable(event);
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> eventArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Event\" ;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                eventArrayList.add(getEvent(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventArrayList;
    }

    @Override
    public void save(Event event) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Event\" (\"Name\", \"Place\", \"Date\") VALUES  (?, ?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getPlace());
            preparedStatement.setDate(3, event.getDate());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event, String[] params) {

        PreparedStatement preparedStatement;
        String sql = "update \"Event\" set \"Name\"=?, \"Place\"=?, \"Date\"=? where \"Event ID\" = ?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setString(3, params[2]);
            preparedStatement.setLong(4, event.getEvent_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Event event) {

        PreparedStatement preparedStatement;
        String sql = "DELETE FROM \"Event\" WHERE \"Event ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, event.getEvent_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
