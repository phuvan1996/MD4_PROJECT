package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.CartDetail;
import ra.model.repository.CartDetailRepository;
import ra.model.service.CartDetailService;

import java.util.List;
@Service
public class CartDetailServiceImp implements CartDetailService {
    @Autowired
    CartDetailRepository cartDetailRepository;
    @Override
    public List<CartDetail> getAllCartDetails(int cartId) {
        return cartDetailRepository.findAll();
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }
}
