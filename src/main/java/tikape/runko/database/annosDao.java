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
import tikape.runko.domain.Annos;
import tikape.runko.domain.Raakaaine;

public class annosDao implements Dao<Annos, Integer> {

    private Database database;

    public annosDao(Database database) {
        this.database = database;
    }

    public void add(String nimi) {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO annos (nimi) value(?)");
      stmt.setObject(1, nimi);

      stmt.executeQuery();

    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dbo.annos WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Annos a = new Annos(id, nimi);
        stmt = connection.prepareStatement("SELECT * FROM dbo.annosraakaaine WHERE annos_id = ?");
        stmt.setObject(1, key);

        rs = stmt.executeQuery();

        List<Raakaaine> aineet = new ArrayList<>();
        while (rs.next()) {
            stmt = connection.prepareStatement("SELECT * FROM raakaaine WHERE id = ?");
            stmt.setObject(1,rs.getInt("raakaaine_id"));
            ResultSet rs2 = stmt.executeQuery();
            if(!rs2.next()) {
              continue;
            }
            Integer r_id = rs2.getInt("id");
            String r_nimi = rs2.getString("nimi");

            aineet.add(new Raakaaine(r_id, r_nimi));
        }

        a.addAineet(aineet);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }


    @Override
    public List<Annos> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dbo.annos");

        ResultSet rs = stmt.executeQuery();
        List<Annos> annokset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            annokset.add(new Annos(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("DELETE FROM dbo.annos WHERE id = ?");
      stmt.setObject(1, key);


    }

}
