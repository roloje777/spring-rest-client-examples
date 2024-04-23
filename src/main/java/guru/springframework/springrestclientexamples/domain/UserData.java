package guru.springframework.springrestclientexamples.domain;

import lombok.Data;

import java.util.List;

//@Data
public class UserData {
    List<User> results;

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }
}
