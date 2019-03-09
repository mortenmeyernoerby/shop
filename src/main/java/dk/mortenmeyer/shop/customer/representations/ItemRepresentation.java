package dk.mortenmeyer.shop.customer.representations;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemRepresentation {
    private long id;
    private String name;
    private double price;
}
