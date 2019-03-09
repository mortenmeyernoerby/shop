package dk.mortenmeyer.shop.customer;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemRepresentation {
    private long id;
    private String name;
    private double price;
}
