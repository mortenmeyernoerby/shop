package dk.mortenmeyer.shop;

import dk.mortenmeyer.shop.customer.Customer;
import dk.mortenmeyer.shop.customer.CustomerRepository;
import dk.mortenmeyer.shop.item.Item;
import dk.mortenmeyer.shop.item.ItemRepository;
import dk.mortenmeyer.shop.order.PurchaseOrder;
import dk.mortenmeyer.shop.order.PurchaseOrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner insertData(CustomerRepository customerRepository, PurchaseOrderRepository purchaseOrderRepository, ItemRepository itemRepository) {
		return args -> {
			Customer customerA = new Customer("Peter Jensen", "Møllevej 10");
            PurchaseOrder orderA1 = new PurchaseOrder();
            Item itemA = new Item("ItemA", 100);
            Item itemB = new Item("ItemB", 300);
            orderA1.addItem(itemA);
            orderA1.addItem(itemB);
            orderA1.setDiscount(50);
            customerA.addOrder(orderA1);

            PurchaseOrder orderA2 = new PurchaseOrder();
			customerA.addOrder(orderA2);

            Customer customerB = new Customer("Maria Poulsen", "Søndergade 42");
            PurchaseOrder orderB = new PurchaseOrder();
            customerB.addOrder(orderB);

            itemRepository.save(itemA);
            itemRepository.save(itemB);

            purchaseOrderRepository.save(orderA1);
            purchaseOrderRepository.save(orderA2);
            purchaseOrderRepository.save(orderB);

            customerRepository.save(customerA);
			customerRepository.save(customerB);
		};
	}
}
