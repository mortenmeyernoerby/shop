package dk.mortenmeyer.shop.customer;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import java.net.URL;

import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void shouldGetCustomerOrders() {
        ResponseEntity<String> response =
            template.getForEntity("http://localhost:{port}/customers/{customerId}/orders/{orderId}", String.class, port, 1, 1);

        assertThat(response.getStatusCode().value(), is(200));

        JsonPath json = JsonPath.from(response.getBody());

        assertThat(json.get("orderId"), is(1));
        assertThat(json.get("customerId"), is(1));
        assertThat(json.get("totalPrice"), is(350f));
        assertThat(json.get("discount"), is(50f));
        assertThat(json.get("items.size()"), is(2));
        assertThat(json.get("items[0].id"), is(1));
        assertThat(json.get("items[0].name"), is("ItemA"));
        assertThat(json.get("items[0].price"), is(100f));
        assertThat(json.get("items[1].id"), is(2));
        assertThat(json.get("items[1].name"), is("ItemB"));
        assertThat(json.get("items[1].price"), is(300f));
    }

    @Test
    public void shouldReturnNotFound() {
        ResponseEntity<String> response =
            template.getForEntity("http://localhost:{port}/customers/{customerId}/orders/{orderId}", String.class, port, 100, 100);

        assertThat(response.getStatusCode().value(), is(404));
    }

}