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

    @Override
    public void add(String nimi) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO annos (nimi) values (?)");
      stmt.setObject(1, nimi);

      stmt.execute();
    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM annos WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Annos a = new Annos(id, nimi);
        stmt = connection.prepareStatement("SELECT * FROM annosraakaaine WHERE annos_id = ? ORDER BY jarjestys");
        stmt.setObject(1, key);

        rs = stmt.executeQuery();

        List<Raakaaine> aineet = new ArrayList<>();
        while (rs.next()) {
            stmt = connection.prepareStatement("SELECT * FROM raakaaine WHERE id = ?");
            stmt.setObject(1,rs.getInt("raaka_aine_id"));
            ResultSet rs2 = stmt.executeQuery();
            if(!rs2.next()) {
              continue;
            }
            Integer r_id = rs2.getInt("id");
            String r_nimi = rs2.getString("nimi");
            Raakaaine r = new Raakaaine(r_id,r_nimi);
            r.setMaara(rs.getString("maara"));
            r.setOhje(rs.getString("ohje"));
            aineet.add(r);
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM annos");

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
    public void addLink(int a,int r,int j,String maara,String ohje) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("INSERT INTO annosraakaaine (raaka_aine_id,annos_id,jarjestys,maara,ohje) values (?,?,?,?,?)");
      stmt.setObject(1, r);
      stmt.setObject(2, a);
      stmt.setObject(3, j);
      stmt.setObject(4, maara);
      stmt.setObject(5, ohje);


      stmt.execute();
    }

    @Override
    public void delete(Integer key) throws SQLException {
      Connection connection = database.getConnection();
      PreparedStatement stmt = connection.prepareStatement("DELETE FROM annos WHERE id = ?");
      stmt.setObject(1, key);

      stmt.execute();
      stmt = connection.prepareStatement("DELETE FROM annosraakaaine WHERE annos_id = ?");
      stmt.setObject(1, key);

      stmt.execute();

    }

}
