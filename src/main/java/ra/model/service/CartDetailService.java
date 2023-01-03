package ra.model.service;
import ra.model.entity.CartDetail;
import java.util.List;

public interface CartDetailService {
    List<CartDetail> getAllCartDetails(int cartId);
    CartDetail save(CartDetail cartDetail);
}
