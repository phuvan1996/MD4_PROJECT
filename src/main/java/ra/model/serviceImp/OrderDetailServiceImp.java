//package ra.model.serviceImp;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ra.model.entity.OrderDetail;
//import ra.model.repository.OrderDetailRepository;
//import ra.model.service.OrderDetailService;
//
//import java.util.List;
//
//@Service
//public class OrderDetailServiceImp implements OrderDetailService {
//    @Autowired
//    OrderDetailRepository orderDetailRepository;
//
//    @Override
//    public List<OrderDetail> getAllOrderDetails(int orderID) {
//        return orderDetailRepository.findByOrder_OrderId(orderID);
//    }
//
//    @Override
//    public OrderDetail save(OrderDetail orderDetail) {
//        return orderDetailRepository.save(orderDetail);
//    }
//}
