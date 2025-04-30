package com.github.lordyavin.migadu;

import java.util.Base64;

public class BasicAuth {
  private BasicAuth() {}

  public static final String getBasicAuthenticationHeader(String username, String password) {
    String valueToEncode = username + ":" + password;
    return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    // return "Basic " + username + ":" + password;
  }
}
