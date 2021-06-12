package guru.springframework.springrestclientexamples.domain;

import lombok.Data;

@Data
public class Timezone {
    private String offset;
    private String description;
}
