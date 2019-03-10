package dk.mortenmeyer.shop.order;

import dk.mortenmeyer.shop.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
