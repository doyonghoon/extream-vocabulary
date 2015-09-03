package com.spencerdo.vaca.adapter;

import com.spencerdo.vaca.model.Vocabulary;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class SqlAdapter {

  private static Connection mPostgresDatabaseConnection = null;

  public SqlAdapter() {

  }

  public boolean remove() {
    return false;
  }

  public boolean add(Vocabulary voca) {
    System.out.println("voca: " + voca);
    try {
      PreparedStatement p = getConnection().prepareStatement("INSERT INTO vocabulary (word, frequency, synonyms) " +
          "SELECT ?, ?, ? " +
          "WHERE NOT EXISTS " +
          "(SELECT word, frequency, synonyms FROM vocabulary WHERE word=?)");
      p.setString(1, voca.getWord());
      p.setInt(2, voca.getFrequency());
      p.setArray(3, getConnection().createArrayOf("text", convertArrayListToArray(voca.getSynonyms())));
      p.setString(4, voca.getWord());
      return p.executeUpdate() > 0;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public List<Vocabulary> getAllVocabularies() {
    return null;
  }

  public Vocabulary getVocabulary(String word) {
    try {
      PreparedStatement p = getConnection().prepareCall("SELECT * FROM vocabulary WHERE word=?");
      p.setString(1, word);
      ResultSet rs = p.executeQuery();
      if (rs.next()) {
        Vocabulary v = new Vocabulary();
        v.setId(rs.getInt("id"));
        v.setWord(rs.getString("word"));
        v.setFrequency(rs.getInt("frequency"));
        Array a = rs.getArray("synonyms");
        String[] synonyms = (String[]) a.getArray();
        v.setSynonyms(Arrays.asList(synonyms));
        return v;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Vocabulary> getVocabularies(int frequency) {
    return null;
  }

  public List<Vocabulary> getVocabulariesBySynonym(String synonym) {
    return null;
  }

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

  private String[] convertArrayListToArray(List<String> list) {
    return list == null || list.isEmpty() ? new String[0] : list.toArray(new String[list.size()]);
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
