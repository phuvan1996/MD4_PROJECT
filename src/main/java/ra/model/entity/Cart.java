package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId")
    private int cartId;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "totalPrice")
    private float totalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private Users users;
    @Column(name = "Price")
    private float price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private Product product;
    private boolean cartStatus;
    private float totalAmount;
    @OneToMany(mappedBy = "cart")
    List<CartDetail> listCartDetail = new ArrayList<>();
    @Column(name = "CreateDate")
     @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
}
