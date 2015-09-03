package com.spencerdo.vaca.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.spencerdo.vaca.adapter.SqlAdapter;
import com.spencerdo.vaca.model.DefaultJsonFormat;
import com.spencerdo.vaca.model.Vocabulary;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import spark.QueryParamsMap;
import spark.Request;
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
    return (req, res) -> {
      res.type("application/json");
      DefaultJsonFormat f = new DefaultJsonFormat();
      f.setResult("fail");
      f.setResultCode(501);
      res.status(501);
      Vocabulary v = parseVocabulary(req);
      if (v != null && adapter.add(v)) {
        res.status(200);
        f.setResult("success");
        f.setResultCode(200);
        f.setData(v);
      }
      return f;
    };
  }

  public Route getLoadRoute() {
    return (req, res) -> {
      res.type("application/json");
      res.status(501);
      DefaultJsonFormat f = new DefaultJsonFormat();
      f.setResult("fail");
      f.setResultCode(501);
      QueryParamsMap m = req.queryMap();
      if (m.hasKeys()) {
        if (m.get("word") != null) {
          Vocabulary v = adapter.getVocabulary(m.get("word").value());
          if (v != null && v.getId() > 0) {
            res.status(200);
            f.setData(v);
            f.setResultCode(200);
            f.setResult("success");
          }
        }
      }
      return f;
    };
  }

  private Vocabulary parseVocabulary(Request req) {
    Vocabulary v = new Vocabulary();
    QueryParamsMap m = req.queryMap();
    if (m == null) {
      return null;
    }
    QueryParamsMap wordMap = m.get("word");
    QueryParamsMap synonymsMap = m.get("synonyms");
    if (hasKeyAndValue(wordMap) || hasKeyAndValue(synonymsMap)) {
      return null;
    }
    String word = wordMap.value();
    String rawSynonyms = synonymsMap.value();
    int frequency = 0;
    if (m.get("frequency") != null) {
      frequency = m.get("frequency").integerValue();
    }
    if (StringUtils.isEmpty(word) || StringUtils.isEmpty(rawSynonyms)) {
      return null;
    }
    v.setWord(word);
    v.setSynonyms(parseSynonyms(rawSynonyms));
    v.setFrequency(frequency);
    return v;
  }

  private boolean hasKeyAndValue(QueryParamsMap map) {
    return map != null && map.hasKeys() && map.hasValue();
  }

  private List<String> parseSynonyms(String src) {
    List<String> result = new ArrayList<>();
    String[] tmp = org.apache.commons.lang3.StringUtils.splitByWholeSeparator(src, ",");
    if (tmp != null && tmp.length > 0) {
      result = new ArrayList<>(Arrays.asList(tmp));
    }
    return result;
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
