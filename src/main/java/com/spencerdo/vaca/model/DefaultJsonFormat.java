package com.spencerdo.vaca.model;

/**
 * Created by doyonghoon on 2015. 9. 2..
 */
public class DefaultJsonFormat {

  int resultCode;
  String result;
  Object data;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
