package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.annosDao;
import tikape.runko.database.raakaaineDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("dbc:postgresql://url/database?ssl=true&amp;sslfactory=org.postgresql.ssl.NonValidatingFactory");
        database.init();

        annosDao annosD = new annosDao(database);
        raakaaineDao raakaaineD = new raakaaineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "jaa");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
/*
        //Listaa raaka-aineet
        get("/raaka-aineet",(req,res) -> {

        },new ThymeleafTemplateEngine());
*/
        //Listaa annokset
        //Done?
        get("/annokset",(req,res) -> {
          HashMap map = new HashMap<>();
          map.put("annokset",annosD.findAll());
          map.put("aineet",raakaineD.findAll());

          return new ModelAndView(map,"annokset");
        },new ThymeleafTemplateEngine());



        //Hae annos
        get("/annos/:id",(req,res) -> {
          HashMap map = new HashMap<>();
          map.put("annos",annosD.findOne(Integer.parseInt(req.params("id"))));

          return new ModelAndView(map, "annos");

        },new ThymeleafTemplateEngine());


        //Poista raaka-aine
        post("/delete/raaka-aine/:id",(req,res) -> {
           raakaaineD.delete(Integer.parseInt(req.params("id")));
           res.redirect("/raaka-aineet");
        });

        //Poista annos
        post("/delete/annos/:id",(req,res) -> {
          annosD.delete(Integer.parseInt(req.params("id")));
          res.redirect("/annokset");
        });


        //Lisää raaka-aine
        post("/add/raaka-aine/:id",(req,res) -> {
          return false;
        });

        //Lisää annos
        post("/add/annos/:id",(req,res) -> {
          String nimi = req.queryParams("nimi");
          annosDao.add(nimi);
          return false;
        });


/*
        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
*/

    }
}
