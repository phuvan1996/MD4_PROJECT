package ra.model.service;

import ra.model.entity.Cart;

import java.util.List;

public interface CartService {
   Cart insertCart(Cart cart);
   List<Cart> findAllUserCartById(int id);
   void delete(int id);
   Cart findById(int cartId);
}
