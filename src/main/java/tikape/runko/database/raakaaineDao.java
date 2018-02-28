/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Raakaaine;

public class raakaaineDao implements Dao<Raakaaine, Integer> {

    private Database database;

    public raakaaineDao(Database database) {
        this.database = database;
    }


    @Override
    public void add(String nimi) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO raakaaine (nimi) values (?)");
      stmt.setObject(1, nimi);

      stmt.execute();

    }

    @Override
    public Raakaaine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM raakaaine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Raakaaine r = new Raakaaine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return r;
    }

    @Override
    public List<Raakaaine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM raakaaine");

        ResultSet rs = stmt.executeQuery();
        List<Raakaaine> raakaaineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            raakaaineet.add(new Raakaaine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return raakaaineet;
    }
    @Override
    public void addLink(int a,int r,int j,String maara,String ohje) throws SQLException {
}
    @Override
    public void delete(Integer key) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("DELETE FROM raakaaine WHERE id = ?");
      stmt.setObject(1, key);

      stmt.execute();
    }

}
