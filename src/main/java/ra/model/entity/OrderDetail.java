//package ra.model.entity;
//import lombok.Data;
//import javax.persistence.*;
//
//@Entity
//@Data
//public class OrderDetail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int orderDetailId;
//    @ManyToOne
//    @JoinColumn(name = "orderId")
//    private Order order;
//    @ManyToOne
//    @JoinColumn(name = "productId")
//    private Product product;
//    @Column(name = "price")
//    private float price;
//    @Column(name = "Quantity")
//    private int quantity;
//    @Column(name = "TotalPrice")
//    private float totalPrice;
//}
