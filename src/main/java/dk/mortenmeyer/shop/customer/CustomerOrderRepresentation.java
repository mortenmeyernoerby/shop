package dk.mortenmeyer.shop.customer;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerOrderRepresentation {
    private long orderId;
    private long customerId;
    private double totalPrice;
    private double discount;
    private List<ItemRepresentation> items;
}
