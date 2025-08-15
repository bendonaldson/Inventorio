package dev.bendonaldson.inventorio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(exclude = {"order", "product"})
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull(message = "Product is required for an order item")
    private Product product;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive value")
    private int quantity;

    @NotNull(message = "Price at time of purchase is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be a positive value")
    private BigDecimal priceAtTimeOfPurchase;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    public OrderItem() {}

    public OrderItem(Product product, int quantity, BigDecimal priceAtTimeOfPurchase) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }
}
