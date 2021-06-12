package guru.springframework.springrestclientexamples.services;

import guru.springframework.springrestclientexamples.domain.User;
import guru.springframework.springrestclientexamples.domain.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String SERV_URL = "https://randomuser.me/api/?results=";

    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<User> getUsers(int limit) {
        UserData results = restTemplate.getForObject(SERV_URL + limit, UserData.class);
        if (results == null) {
            log.info("########## No Results found #########");
            return new ArrayList<>();
        }
        return results.getResults();

    }
}
