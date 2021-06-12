package guru.springframework.springrestclientexamples.domain;

import lombok.Data;

@Data
public class Location {
    private Street street;
    private String city;
    private String state;
    private String country;
    private int postcode;
    private Coordinates coordinates;
    private Timezone timezone;
}
