package guru.springframework.springrestclientexamples.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private ApplicationContext appCtx;

    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToApplicationContext(appCtx).build();
    }

    @Test
    public void testEnterUserList() {
        FluxExchangeResult<String> result = webTestClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk().returnResult(String.class);

        log.info("following the Test Result... {}",result.toString());
    }

    @Test
    public void testEnterUserListAlt() {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(String.class)
                .consumeWith(System.out::println);

    }

    @Test
    public void testPostEntity() {
        webTestClient.post().uri(uriBuilder -> uriBuilder.path("/users")
                        .queryParam("results", 13)
                        .build())
                .contentType(MediaType.TEXT_HTML)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(result -> assertThat(result.getResponseBody()).containsSequence("Users from API"))
                .consumeWith(System.out::println);
    }

}