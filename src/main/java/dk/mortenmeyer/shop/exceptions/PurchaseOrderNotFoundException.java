package dk.mortenmeyer.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PurchaseOrderNotFoundException extends RuntimeException {
    public PurchaseOrderNotFoundException(long orderId) {
        super("Order with id:[" + orderId + "] not found");
    }
}
