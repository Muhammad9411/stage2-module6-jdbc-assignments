package jdbc;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleJDBCRepository {

    private Connection connection = null;
    private PreparedStatement ps = null;
    private Statement st = null;

    private static final String createUserSQL = "insert into myusers(firstname, lastname, age) values (?, ?, ?)";
    private static final String updateUserSQL = "update myusers set firstname = ?, lastname = ?, age = ? where id = ?";
    private static final String deleteUser = "delete from myusers where id = ?";
    private static final String findUserByIdSQL = "select * from myusers where id = ?";
    private static final String findUserByNameSQL = "select * from myusers where firstname = ?";
    private static final String findAllUserSQL = "select * from myusers";

    public Long createUser() {

        Long result = null;

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(createUserSQL);
            ps.setString(1, "John");
            ps.setString(2, "Doe");
            ps.setInt(3, 25);
            result = (long) ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User findUserById(Long userId) {

        User user = new User();

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findUserByIdSQL);
            ps.setInt(1, 1);

            ResultSet rst = ps.executeQuery(findUserByIdSQL);

            while (rst.next()) {
                user.setFirstName(rst.getString(1));
                user.setLastName(rst.getString(2));
                user.setAge(rst.getInt(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public User findUserByName(String userName) {

        User user = new User();

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findUserByNameSQL);
            ps.setInt(1, 1);

            ResultSet rst = ps.executeQuery(findUserByNameSQL);
            while (rst.next()) {
                user.setFirstName(rst.getString(1));
                user.setLastName(rst.getString(2));
                user.setAge(rst.getInt(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public List<User> findAllUser() {

        List<User> users = new ArrayList<>();

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findUserByNameSQL);

            ResultSet rst = ps.executeQuery(findUserByNameSQL);
            while (rst.next()) {
                User user = new User();
                user.setId(rst.getLong(1));
                user.setFirstName(rst.getString(2));
                user.setLastName(rst.getString(3));
                user.setAge(rst.getInt(4));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public User updateUser() {

        User user = null;

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(updateUserSQL);
            ps.setString(1, "John1");
            ps.setString(2, "Doe");
            ps.setInt(3, 26);
            ps.setInt(4, 1);
            ps.executeUpdate();


            ps = connection.prepareStatement(findUserByNameSQL);
            ps.setInt(1, 1);
            ResultSet rst = ps.executeQuery(findUserByNameSQL);

            while (rst.next()) {
                user.setFirstName(rst.getString(1));
                user.setLastName(rst.getString(2));
                user.setAge(rst.getInt(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    private void deleteUser(Long userId) {

        try {
            connection = CustomDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(deleteUser);
            ps.setLong(1, userId);

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
