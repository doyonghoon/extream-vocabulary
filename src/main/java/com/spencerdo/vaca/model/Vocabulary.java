package com.spencerdo.vaca.model;

import java.util.ArrayList;
import java.util.List;

public class Vocabulary {

  private int id;
  private int frequency;
  private String word;
  private List<String> synonyms = new ArrayList<>();

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public List<String> getSynonyms() {
    return synonyms;
  }

  public void setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  @Override
  public String toString() {
    return id + ", " + word + "[" + frequency + "]" + ": " + synonyms.toString();
  }

  @Override
  public int hashCode() {
    return word.hashCode() + frequency + synonyms.hashCode();
  }
}
