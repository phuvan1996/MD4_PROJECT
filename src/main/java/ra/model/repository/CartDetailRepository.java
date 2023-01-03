package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Cart;
import ra.model.entity.CartDetail;

import java.util.List;

    public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {
    List<CartDetail>findByCart_CartId(int cartId);
}
