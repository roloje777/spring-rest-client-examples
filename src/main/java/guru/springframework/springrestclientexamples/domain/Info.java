package guru.springframework.springrestclientexamples.domain;

import lombok.Data;

@Data
public class Info {
    private String seed;
    private int result;
    private int page;
    private String version;
}
