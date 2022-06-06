package daoObjects;

import interfaces.DAO;
import tableObjects.Event;
import tableObjects.Payment;
import tableObjects.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PaymentDAO implements DAO<Payment> {

    private final Connection connection;

    public PaymentDAO(Connection connection) {
        this.connection = connection;
    }

    private Payment getPayment(ResultSet rs) throws SQLException {
        return new Payment(rs.getLong("Payment ID"), rs.getDate("Payment Date"), rs.getString("Payment Amount"), rs.getLong("Person ID"), rs.getLong("Event ID"), rs.getLong("Installment Number"));
    }

    @Override
    public Optional<Payment> get(long id) {
        Payment payment = null;
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" where \"Payment ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                payment = getPayment(rs);
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(payment);
    }

    @Override
    public ArrayList<Payment> getAll() {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" ;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                paymentArrayList.add(getPayment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentArrayList;
    }

    @Override
    public void save(Payment payment) {

        PreparedStatement preparedStatement;
        String sql = "INSERT INTO \"Payment\" (\"Payment Date\", \"Payment Amount\", \"Person ID\", \"Event ID\", \"Installment Number\") VALUES  ((SELECT CURRENT_DATE), ?, ?, ?, ?);";

        try {

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setFloat(1, Float.parseFloat(payment.getPayment_Amount()));
            preparedStatement.setLong(2, payment.getPerson_Id());
            preparedStatement.setLong(3, payment.getEvent_Id());
            preparedStatement.setLong(4, payment.getInstallment_Number());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Payment payment, String[] params) {

        PreparedStatement preparedStatement;
        String sql = "update \"Payment\" set \"Payment Date\"=?, \"Payment Amount\"=?, \"Person ID\"=?, \"Event ID\"=?, \"Installment Number\"=? where \"Person ID\" = ?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setString(3, params[2]);
            preparedStatement.setString(4, params[3]);
            preparedStatement.setString(5, params[4]);
            preparedStatement.setLong(6, payment.getEvent_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Payment payment) {

        PreparedStatement preparedStatement;
        String sql = "DELETE FROM \"Payment\" WHERE \"Payment ID\"=?;";

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, payment.getEvent_Id());
            preparedStatement.executeUpdate();

            connection.commit();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Payment> getAllForSpecifiedEvent(Event event) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" where \"Event ID\"=?;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, event.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                paymentArrayList.add(getPayment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentArrayList;
    }

    public ArrayList<Payment> getAllForSpecifiedPerson(Person person, Event event) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        String selectQuery = "Select * from \"Payment\" where \"Person ID\"=? and \"Event ID\"=?;";

        try{

            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, person.getPerson_Id());
            preparedStatement.setLong(2, event.getEvent_Id());
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                paymentArrayList.add(getPayment(rs));
            }

            preparedStatement.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentArrayList;
    }
}
