package dev.bendonaldson.inventorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(ProductRepository productRepository, CategoryRepository categoryRepository, OrderRepository orderRepository) {
//        return args -> {
//            // Create a category
//            Category electronics = categoryRepository.save(new Category("Electronics"));
//            Category books = categoryRepository.save(new Category("Books"));
//
//            // Save products with categories
//            Product laptop = productRepository.save(new Product("Laptop", "Powerful laptop for developers", 1500.00, 10, electronics));
//            Product keyboard = productRepository.save(new Product("Keyboard", "Mechanical keyboard with RGB lighting", 120.00, 50, electronics));
//            Product mouse = productRepository.save(new Product("Mouse", "Ergonomic wireless mouse", 75.50, 100, electronics));
//            Product springBook = productRepository.save(new Product("Spring Boot in Action", "Comprehensive guide to Spring Boot", 45.00, 25, books));
//
//            // Create an order
//            Order order1 = new Order();
//            order1.addOrderItem(new OrderItem(laptop, 1, laptop.getPrice()));
//            order1.addOrderItem(new OrderItem(keyboard, 2, keyboard.getPrice()));
//            order1.addOrderItem(new OrderItem(mouse, 3, mouse.getPrice()));
//            orderRepository.save(order1);
//
//            // Create another order
//            Order order2 = new Order("DELIVERED");
//            order2.addOrderItem(new OrderItem(springBook, 1, springBook.getPrice()));
//            orderRepository.save(order2);
//        };
//    }
}
