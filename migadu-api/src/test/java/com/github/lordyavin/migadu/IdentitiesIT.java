package com.github.lordyavin.migadu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.lordyavin.migadu.model.Identity;

class IdentitiesIT {

  private static final String MIGADU_KEY = System.getProperty("migadu.key");
  private static final String MIGADU_USER = System.getProperty("migadu.user");
  private static final String MIGADU_DOMAIN = System.getProperty("migadu.domain");
  private static final String MIGADU_MAILBOX = System.getProperty("migadu.mailbox");

  private HttpClient httpClient;
  private String authenticationHeader;

  @BeforeEach
  void setup() {
    httpClient = HttpClient.newBuilder().build();
    authenticationHeader = BasicAuth.getBasicAuthenticationHeader(MIGADU_USER, MIGADU_KEY);
  }

  HttpRequest.Builder requestBuilder(URI uri) {
    return HttpRequest.newBuilder()
        .header("Accept", "application/json")
        .header("Authorization", authenticationHeader)
        .uri(uri);
  }

  @Test
  void testIndex() throws IOException, InterruptedException {
    Identity[] identities =
        new Identities(httpClient, this::requestBuilder).index(MIGADU_DOMAIN, MIGADU_MAILBOX);
    for (Identity identity : identities) {
      System.err.println(identity);
    }
  }
}
