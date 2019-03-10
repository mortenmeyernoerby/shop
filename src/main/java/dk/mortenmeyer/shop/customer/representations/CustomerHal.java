package dk.mortenmeyer.shop.customer.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

@Builder
@Getter
public class CustomerHal extends ResourceSupport {

    @JsonProperty("id")
    private long customerId;
    private String name;
    private String address;
}
