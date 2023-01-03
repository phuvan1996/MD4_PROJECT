//package ra.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Data;
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Data
//@Table(name = "orders")
//public class Order {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "OrderId")
//    private int orderId;
//    @Column(name = "CreateDate")
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date createDate;
//    @Column(name = "OrderStatus")
//    private int orderStatus;
//    @Column(name = "TotalAmount")
//    private float totalAmount;
//    @Column(name = "Price")
//    private float price;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "UserId")
//    @JsonIgnore
//    private Users users;
//    @OneToMany(mappedBy = "order")
//    List<OrderDetail>listOrderDetail = new ArrayList<>();
//}
