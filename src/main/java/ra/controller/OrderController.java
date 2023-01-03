//package ra.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import ra.model.entity.Cart;
//import ra.model.entity.Order;
//import ra.model.entity.OrderDetail;
//import ra.model.service.*;
//import ra.payload.reponse.MessageResponse;
//import ra.payload.reponse.OrderDetailResponse;
//import ra.payload.reponse.OrderResponse;
//import ra.security.CustomUserDetails;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:8080")
//@RequestMapping("api/v1/order")
//public class OrderController {
//    @Autowired
//    UserService userService;
//    @Autowired
//    CartService cartService;
//    @Autowired
//    OrderService orderService;
//    @Autowired
//    OrderDetailService orderDetailService;
//
//    @GetMapping("/getAllOrder")
//    public List<OrderResponse> getAllOrder() {
//        List<OrderResponse> listOrder = new ArrayList<>();
//        List<Order> list = orderService.getAllOrder();
//        for (Order order : list) {
//                OrderResponse orderResponse = new OrderResponse();
//                orderResponse.setOrderId(order.getOrderId());
//                orderResponse.setOrderStatus(order.getOrderStatus());
//                orderResponse.setTotalAmount(order.getTotalAmount());
//                orderResponse.setCreateDate(order.getCreateDate());
//                orderResponse.setUserId(order.getOrderId());
//                orderResponse.setPrice(order.getPrice());
//                listOrder.add(orderResponse);
//            }
//        return listOrder;
//    }
//
//    @PostMapping("/{createOrder}")
//    public ResponseEntity<?> createOrder() {
//        try {
//            Order order = new Order();
//            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            List<Cart> listCart = cartService.findAllUserCartById(userDetails.getUserId());
//            List<OrderDetail> listOrderDetail = new ArrayList<>();
//            float totalAmount = 0f;
//            for (Cart cart : listCart) {
//                totalAmount += cart.getTotalPrice();
//            }
//            order.setOrderStatus(1);
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date dateNow = new Date();
//            String strNow = sdf.format(dateNow);
//            try {
//                order.setCreateDate(sdf.parse(strNow));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            order.setUsers(userService.getUserById(userDetails.getUserId()));
//            order.setTotalAmount(totalAmount);
//            orderService.save(order);
//            for (Cart cart : listCart) {
//                OrderDetail orderDetails = new OrderDetail();
//                orderDetails.setOrder(order);
//                orderDetails.setQuantity(cart.getQuantity());
//                orderDetails.setProduct(cart.getProduct());
//                orderDetails.setPrice(cart.getPrice());
//                orderDetails.setTotalPrice(cart.getProduct().getPrice() * cart.getQuantity());
//                listOrderDetail.add(orderDetails);
//            }
//            return ResponseEntity.ok(new MessageResponse("thêm đơn hàng thành công"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi trong quá trình xử lý vui lòng thử lại!"));
//    }
//    @GetMapping("/{orderId}")
//    public ResponseEntity<?>getAllOrderDetail(@PathVariable("orderId") int id){
//        List<OrderDetail>listOrderDetail = orderDetailService.getAllOrderDetails(id);
//        List<OrderDetailResponse> list= new ArrayList<>();
//        for (OrderDetail orderDetail:listOrderDetail) {
//            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
//            orderDetailResponse.setQuantity(orderDetail.getQuantity());
//            orderDetailResponse.setTotalPrice(orderDetail.getTotalPrice());
//            orderDetailResponse.setPrice(orderDetail.getProduct().getPrice());
//            orderDetailResponse.setProductName(orderDetail.getProduct().getProductName());
//            list.add(orderDetailResponse);
//        }
//        return ResponseEntity.ok(list);
//    }
//    @DeleteMapping("/{orderId}")
//    public void deleteOrder(@PathVariable("orderId") int id){
//        orderService.delete(id);
//    }
//}
