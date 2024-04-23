package Service;

import java.sql.SQLException;
import java.util.List;

public interface CRUDInterface <E>{
    public void insert(E e) throws SQLException;
    public void update(int id ,E e) throws SQLException;
    public void delete(int id) throws SQLException;
    public List<E> getAll()throws SQLException;
}
