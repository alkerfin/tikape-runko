package tikape.runko.database;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {

    T findOne(K key) throws SQLException;
    void add(String nimi) throws SQLException;
    void addLink(int a,int r,int j,String maara,String ohje) throws SQLException;
    List<T> findAll() throws SQLException;

    void delete(K key) throws SQLException;
}
