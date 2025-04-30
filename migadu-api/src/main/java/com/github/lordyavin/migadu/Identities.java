package com.github.lordyavin.migadu;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.MessageFormat;
import java.util.function.Function;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.lordyavin.migadu.model.Identity;

public class Identities {
  private static final String ENDPOINT = Migadu.API + "/domains/{0}/mailboxes/{1}/identities";

  private final HttpClient client;
  private final JsonMapper mapper = JsonMapper.builder().build();

  private Function<URI, Builder> httpRequestBuilder;

  public Identities(HttpClient client, Function<URI, HttpRequest.Builder> httpRequestBuilder) {
    this.client = client;
    this.httpRequestBuilder = httpRequestBuilder;
  }

  private static record IdentitiesWrapper(Identity[] identities) {}

  public Identity[] index(String domain, String mailbox) throws IOException, InterruptedException {
    URI uri = URI.create(MessageFormat.format(ENDPOINT, domain, mailbox));
    HttpRequest request = httpRequestBuilder.apply(uri).build();

    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    if (response.statusCode() == 200) {
      return mapper.readValue(response.body(), IdentitiesWrapper.class).identities();
    } else {
      throw new IOException(response.body());
    }
  }
}
