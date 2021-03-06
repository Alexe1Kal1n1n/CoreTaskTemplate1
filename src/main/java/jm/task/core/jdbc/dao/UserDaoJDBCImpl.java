package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnect();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE `mydbtest`.`tablejm` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastName` VARCHAR(45) NULL,\n" +
                    "  `age` TINYINT(3) NULL,\n" +
                    "  PRIMARY KEY (`id`));");

            System.out.println("Table create");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE tablejm;");

            System.out.println("Table drop");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO tablejm (name,lastName, age) VALUES (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM tablejm WHERE id=?");) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User delete");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT id, name, lastName, age from tablejm");) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User e : allUser){
            System.out.println(e);
        }

        return allUser;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("TRUNCATE TABLE tablejm;");

            System.out.println("Clean table");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}