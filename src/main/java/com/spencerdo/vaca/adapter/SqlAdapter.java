package com.spencerdo.vaca.adapter;

import com.spencerdo.vaca.model.Vocabulary;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlAdapter {

  public SqlAdapter() {

  }

  public boolean remove() {
    return false;
  }

  public boolean add(Vocabulary voca) {
    getConnection();
    return true;
  }

  public List<Vocabulary> getAllVocabularies() {
    return null;
  }

  public List<Vocabulary> getVocabularies(int frequency) {
    return null;
  }

  public List<Vocabulary> getVocabulariesBySynonym(String synonym) {
    return null;
  }

  private void createVocabularTable() {

  }

  private static Connection mPostgresDatabaseConnection = null;

  public static Connection getConnection() {
    if (mPostgresDatabaseConnection == null) {
      try {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
        mPostgresDatabaseConnection = DriverManager.getConnection(dbUrl, username, password);
        createTable(Table.VOCABULARY);
      } catch (URISyntaxException | SQLException e) {
        e.printStackTrace();
      }
    }
    return mPostgresDatabaseConnection;
  }

  private static void createTable(Table table) {
    try {
      Statement stmt = getConnection().createStatement();
      stmt.executeUpdate(table.getQuery());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private interface QuerySyntax {
    String getQuery();
  }

  private enum Table implements QuerySyntax {
    VOCABULARY {
      @Override
      public String getQuery() {
        return "CREATE TABLE IF NOT EXISTS vocabulary (id INT, word TEXT, synonyms TEXT[], frequency INT)";
      }
    }
  }
}
