package guru.springframework.springrestclientexamples.services;

import guru.springframework.springrestclientexamples.domain.User;
import guru.springframework.springrestclientexamples.domain.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiUrl = apiUrl;
    }

    @Override
    public List<User> getUsers(int limit) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiUrl);
        uriBuilder.queryParam("results",limit);

        UserData results = restTemplate.getForObject( uriBuilder.toUriString(), UserData.class);
        if (results == null) {
            log.info("########## No Results found #########");
            return new ArrayList<>();
        }
        return results.getResults();

    }
}
