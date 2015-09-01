package com.spencerdo.vaca;

import com.spencerdo.vaca.adapter.MainService;
import com.spencerdo.vaca.adapter.SqlAdapter;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import org.apache.commons.lang3.math.NumberUtils;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import static spark.Spark.get;

public class Main {
  public static void main(String[] args) {
    SqlAdapter adapter = createSqlAdapter();
    beginServer(NumberUtils.toInt(System.getenv("PORT")), adapter);
  }

  private static SqlAdapter createSqlAdapter() {
    return new SqlAdapter();
  }

  private static void beginServer(int port, SqlAdapter sqlAdapter) {
    Spark.staticFileLocation("/assets");
    FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
    Configuration freeMarkerConfiguration = new Configuration();
    freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(MainService.class, "/"));
    freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
    Spark.port(port);

    get("/", (req, res) -> {
      res.status(200);
      res.type("text/html");
      return freeMarkerEngine.render(new ModelAndView(null, "assets/home.ftl"));
    });
  }
}
