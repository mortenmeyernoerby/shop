package dk.mortenmeyer.shop.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dk.mortenmeyer.shop.customer.ItemRepresentation;
import dk.mortenmeyer.shop.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(nullable = false)
    private double discount;

    @OneToMany
    @JoinColumn(name = "fk_order")
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public double getTotalPrice() {
        double totalPriceWithoutDiscount = items.stream()
            .mapToDouble(Item::getPrice)
            .sum();
        return totalPriceWithoutDiscount - discount;
    }
}
