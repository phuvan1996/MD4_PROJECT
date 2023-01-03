package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Cart;
import ra.model.repository.CartRepository;
import ra.model.service.CartService;

import java.util.List;
@Service
public class CartServiceImp implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Override
    public Cart insertCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> findAllUserCartById(int id) {
        return cartRepository.findByUsers_UserId(id);
    }

    @Override
    public void delete(int id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart findById(int cartId) {
        return cartRepository.findById(cartId).get();
    }
}
