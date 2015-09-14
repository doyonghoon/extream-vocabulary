package com.spencerdo.vaca;

import com.spencerdo.vaca.adapter.MainService;
import com.spencerdo.vaca.adapter.SqlAdapter;
import com.spencerdo.vaca.service.VocabularyService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

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
    Configuration c = new Configuration();
    c.setTemplateLoader(new ClassTemplateLoader(MainService.class, "/"));
    freeMarkerEngine.setConfiguration(c);
    Spark.setPort(port);
    Spark.get("/", (req, res) -> {
      res.status(200);
      res.type("text/html");
      Map<String, Object> m = new HashMap<>();
      return freeMarkerEngine.render(new ModelAndView(m, "assets/index.ftl"));
    });

    VocabularyService vocabularyService = new VocabularyService(sqlAdapter);
    Spark.post("/vocabulary/create", vocabularyService.getCreateRoute(), vocabularyService.getSimpleJsonSuccessTransformer());
    Spark.get("/vocabulary", vocabularyService.getLoadRoute(), vocabularyService.getSimpleJsonSuccessTransformer());
  }
}
