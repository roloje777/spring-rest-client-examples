package guru.springframework.springrestclientexamples.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Dob {
    private LocalDateTime localDateTime;
    private int age;
}
