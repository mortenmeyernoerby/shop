package dk.mortenmeyer.shop.order;

import dk.mortenmeyer.shop.exceptions.PurchaseOrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping("/orders")
    public Iterable<PurchaseOrder> getAllOrders() {
        return purchaseOrderRepository.findAll();
    }

    @GetMapping("/orders/{orderId}")
    public PurchaseOrder getOrder(@PathVariable Long orderId) {
        return purchaseOrderRepository
            .findById(orderId)
            .orElseThrow(() -> new PurchaseOrderNotFoundException(orderId));
    }
}
