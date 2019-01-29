package com.mokaz.bankaccount.adapter.rest;

import com.mokaz.bankaccount.application.AccountResource;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.extensions.junit5.MicronautJunit5Extension;
import io.reactivex.Flowable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;

import javax.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@MicronautTest
class AccountControllerTest {

  /*  private static EmbeddedServer server;
    @Inject
    @Client("/")
    private static HttpClient client;*/
/*  @Inject
  AccountClient client;
  @Inject
  EmbeddedServer embeddedServer;*/

    @Inject
    @Client("/")
    RxHttpClient client;
/*    @Inject
    AccountClient client;*/

/*    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeAll
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = server .getApplicationContext() .createBean(HttpClient.class, server.getURL());
    }*/

  //  @BeforeAll
  //  public static void setupServer() {
     //   server = ApplicationContext.run(EmbeddedServer.class);
/*        client = server
                .getApplicationContext()
                .createBean(HttpClient.class, server.getURL());*/
   // }

    @Test
    void a(){
        CreateAccountDto createAccountDto = new CreateAccountDto();
        createAccountDto.setAccountName("account1");
        createAccountDto.setOwnerName("john");

        HttpResponse<Object> exchange = client.toBlocking().exchange(HttpRequest.POST("/account", createAccountDto));
      //  HttpResponse<List> retrieve = client.toBlocking().exchange(HttpRequest.GET("/account"), Argument.of(List.class,AccountResource.class));
      //  System.out.println("--" + retrieve);
        List<AccountResource> retriev2e = client.toBlocking().retrieve(HttpRequest.GET("/account"),Argument.of(List.class,AccountResource.class));
        System.out.println("--2" + retriev2e);
        String retrieve3 = client.toBlocking().retrieve(HttpRequest.GET("/account"));
        System.out.println("--3" +retrieve3);
assertThat(retriev2e.get(0).getName()).isEqualTo("A");
/*       // List<AccountResource> all = client.findAll();
        HttpResponse httpResponse = client.create(createAccountDto);
        System.out.println(httpResponse.getStatus());
        HttpResponse httpResponse1 = client.create(createAccountDto);
        System.out.println(httpResponse1.getStatus());
        List<AccountResource> all2 = client.findAll();
        List<AccountResource> all3 = client.findAll();
       // assertThat(all).hasSize(1);*/
    }

}