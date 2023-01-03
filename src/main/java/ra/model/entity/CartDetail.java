package ra.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartDetailId;
    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
    @Column(name = "price")
    private float price;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "TotalPrice")
    private float totalPrice;
}
