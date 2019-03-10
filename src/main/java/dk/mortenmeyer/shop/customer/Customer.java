package dk.mortenmeyer.shop.customer;

import dk.mortenmeyer.shop.order.PurchaseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany
    private List<PurchaseOrder> orders = new ArrayList<>();

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addOrder(PurchaseOrder order) {
        orders.add(order);
    }
}
