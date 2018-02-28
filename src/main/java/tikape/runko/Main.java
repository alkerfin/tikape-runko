package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();

        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "jaa");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        //Listaa raaka-aineet
        get("/raaka-aineet",(req,res) -> {

        },new ThymeleafTemplateEngine());

        //Listaa annokset
        get("/annokset",(req,res) -> {

        },new ThymeleafTemplateEngine());

        //Hae annos
        get("/annos/:id",(req,res) -> {

        },new ThymeleafTemplateEngine());

        //Hae raaka-aine
        get("/raaka-aine/:id",(req,res) -> {

        },new ThymeleafTemplateEngine());

        //Poista raaka-aine
        post("/delete/raaka-aine/:id",(req,res) -> {

        },json());

        //Poista annos
        post("/delete/annos/:id",(req,res) -> {

        },json());

        //Muokkaa raaka-ainetta
        post("/edit/raaka-aine/:id",(req,res) -> {

        },json());

        //Lisää raaka-aine
        post("/edit/annos/:id",(req,res) -> {

        },json());

        //Lisää annos
        post("/edit/annos/:id",(req,res) -> {

        },json());

        //Muokkaa annosta
        post("/edit/annos/:id",(req,res) -> {

        },json());


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
    }
}
