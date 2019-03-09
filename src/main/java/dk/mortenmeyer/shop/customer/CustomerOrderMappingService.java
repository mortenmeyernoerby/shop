package dk.mortenmeyer.shop.customer;

import java.util.List;
import java.util.stream.Collectors;

import dk.mortenmeyer.shop.order.PurchaseOrder;
import dk.mortenmeyer.shop.customer.representations.CustomerOrderRepresentation;
import dk.mortenmeyer.shop.customer.representations.ItemRepresentation;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderMappingService {

    public CustomerOrderRepresentation map(Customer customer, PurchaseOrder order) {
        List<ItemRepresentation> items = order.getItems()
            .stream()
            .map(item -> ItemRepresentation.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build())
            .collect(Collectors.toList());

        return CustomerOrderRepresentation.builder()
            .orderId(order.getId())
            .customerId(customer.getId())
            .totalPrice(order.getTotalPrice())
            .discount(order.getDiscount())
            .items(items)
            .build();
    }
}
