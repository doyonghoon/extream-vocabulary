package com.spencerdo.vaca.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spencerdo.vaca.adapter.SqlAdapter;
import com.spencerdo.vaca.model.Vocabulary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import spark.ResponseTransformer;
import spark.Route;

public class VocabularyService {

  private final SqlAdapter adapter;
  private final SimpleJsonTransformer successTransformer;

  public VocabularyService(SqlAdapter adapter) {
    this.adapter = adapter;
    this.successTransformer = new SimpleJsonTransformer();
  }

  public Route getCreateRoute() {
    adapter.add(new Vocabulary());
    return (req, res) -> {
      res.status(200);
      res.type("application/json");
      Map<String, Object> m = new HashMap<>();
      m.put("foo", "bar");
      m.put("data", createList());
      return m;
    };
  }

  private List<String> createList() {
    List<String> tmp = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      tmp.add("item " + i);
    }
    return tmp;
  }

  public SimpleJsonTransformer getSimpleJsonSuccessTransformer() {
    return successTransformer;
  }

  public static class SimpleJsonTransformer implements ResponseTransformer {

    private Gson gson;

    private SimpleJsonTransformer() {
      gson = new GsonBuilder().create();
    }

    @Override
    public String render(Object o) throws Exception {
      return gson.toJson(o);
    }
  }
}
