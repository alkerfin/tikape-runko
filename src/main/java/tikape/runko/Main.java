package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.annosDao;
import tikape.runko.database.raakaaineDao;
import tikape.runko.domain.Annos;

public class Main {
  static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
          return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567;
  }
    public static void main(String[] args) throws Exception {

        port(getHerokuAssignedPort());



        Database database = new Database("jdbc:sqlite:annokset.db");
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
          map.put("aineet",raakaaineD.findAll());

          return new ModelAndView(map,"annokset");
        },new ThymeleafTemplateEngine());

        get("/raaka-aineet",(req,res) -> {
          HashMap map = new HashMap<>();
          map.put("aineet",raakaaineD.findAll());

          return new ModelAndView(map,"raaka-aineet");
        },new ThymeleafTemplateEngine());


        //Hae annos
        get("/annos/:id",(req,res) -> {
          HashMap map = new HashMap<>();
          Annos a = annosD.findOne(Integer.parseInt(req.params("id")));
          map.put("annos",a);
          map.put("aineet",a.getAineet());
          return new ModelAndView(map, "annos");

        },new ThymeleafTemplateEngine());


        //Poista raaka-aine
        get("/delete/raakaaine/:id",(req,res) -> {
           raakaaineD.delete(Integer.parseInt(req.params("id")));
           res.redirect("/raaka-aineet");
           return true;
        });

        //Poista annos
        get("/delete/annos/:id",(req,res) -> {
          annosD.delete(Integer.parseInt(req.params("id")));
          res.redirect("/annokset");
          return true;
        });


        //Lisää raaka-aine
        post("/add/raakaaine",(req,res) -> {
          String nimi = req.queryParams("nimi");
          raakaaineD.add(nimi);
          res.redirect("/raaka-aineet");
          return true;
        });

        //Lisää annos
        post("/add/annos",(req,res) -> {
          String nimi = req.queryParams("nimi");
          annosD.add(nimi);
          res.redirect("/annokset");
          return true;
        });

        post("/add/linkki",(req,res) -> {
          int a = Integer.parseInt(req.queryParams("annos"));
          int r = Integer.parseInt(req.queryParams("raaka-aine"));
          int j = Integer.parseInt(req.queryParams("jarjestys"));
          String maara = req.queryParams("maara");
          String ohje = req.queryParams("ohje");

          annosD.addLink(a,r,j,maara,ohje);

          res.redirect("/annokset");
          return true;
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
