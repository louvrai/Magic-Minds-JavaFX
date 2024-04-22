package Service;

import Entity.User;
import Utili.MyDB;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class UserService implements ServiceInterface<User> {
    Connection connection;

    public UserService() {
        connection = MyDB.getInstance().getConnection();
    }


    @Override
    public void insert(User user) throws SQLException {
        PreparedStatement ste = null;
        String sql = "INSERT INTO user "
                + "( `first_name`, `last_name`, `age`, `gender`, `password`, `tel`, `email`, `picture`,`roles`, `is_verified`, `active`) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        ste = connection.prepareStatement(sql);
        ste.setString(1,user.getFirstName());
        ste.setString(2,user.getLastName());
        ste.setInt(3,user.getAge());
        ste.setString(4,user.getGender());
        ste.setString(5,user.getPassword());
        ste.setString(6,user.getTel());
        ste.setString(7,user.getEmail());
        ste.setString(8,user.getPicture());

        ste.setString(9,user.getRoles());
        ste.setBoolean(10,user.isVerified());
        ste.setBoolean(11,user.isActive());

        ste.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }
}
