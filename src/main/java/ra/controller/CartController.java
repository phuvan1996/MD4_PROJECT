package ra.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.DTO.CartDTO;
import ra.model.entity.Cart;
import ra.model.service.CartDetailService;
import ra.model.service.CartService;
import ra.model.service.ProductService;
import ra.model.service.UserService;
import ra.payload.request.CartRequest;
import ra.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    CartDetailService cartDetailService;

    @GetMapping("/getAllCart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCartById() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Cart> listCart = cartService.findAllUserCartById(userDetails.getUserId());
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Cart cart : listCart) {
            CartDTO cartDTO = new CartDTO();
            cartDTO.setCartId(cart.getCartId());
            cartDTO.setQuantity(cart.getQuantity());
            cartDTO.setPrice(cart.getPrice());
            cartDTO.setTotalPrice(cart.getTotalPrice());
            cartDTO.setProductName(cart.getProduct().getProductName());
            cartDTO.setCreateDate(cart.getCreateDate());
            cartDTO.setTotalAmount(cart.getTotalAmount());
            cartDTOList.add(cartDTO);
        }
        return ResponseEntity.ok(cartDTOList);
    }

    @PostMapping("addCart")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean check = true;
        List<Cart> listCart = cartService.findAllUserCartById(customUserDetails.getUserId());
        Cart cart = new Cart();
        boolean checkExit = false;
        try {
            for (Cart cart1 : listCart) {
                if (cart1.getProduct().getProductId() == cartRequest.getProductId()) {
                    cart = cart1;
                    checkExit = true;
                    break;
                }
            }
            if (checkExit) {
                cart.setQuantity(cart.getQuantity() + cartRequest.getQuantity());
                cartService.insertCart(cart);
            } else {
                cart.setQuantity(cartRequest.getQuantity());
                cart.setProduct(productService.findById(cartRequest.getProductId()));
                cart.setPrice(cart.getProduct().getPrice());
                cart.setTotalPrice(cart.getProduct().getPrice() * cart.getQuantity());
                cart.setUsers(userService.findById(customUserDetails.getUserId()));
                cart.setTotalAmount(cart.getTotalAmount());
                cartService.insertCart(cart);
            }
        } catch (Exception e) {
            check = false;
            e.printStackTrace();
        }
        if (check) {
            return ResponseEntity.ok("Sản Phẩm Đã Thêm Vào Giỏ Hàng");
        } else {
            return ResponseEntity.ok("Chưa Có Sản Phẩm Nào Trong Giỏ Hàng");
        }
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@RequestParam("Quantity") int quantity, @PathVariable("cartId") int cartId) {
        try {
            Cart cart = (Cart) cartService.findAllUserCartById(cartId);
            if (quantity > 0) {
                cart.setQuantity(quantity);
                cart.setTotalPrice(cart.getProduct().getPrice() * cart.getQuantity());
                cartService.insertCart(cart);

            } else {
                cartService.delete(cartId);
            }
            return ResponseEntity.ok("Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Cập nhật thất bại");
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") int id) {
        cartService.delete(id);
        return ResponseEntity.ok("Sản Phẩm Đã Xóa");
    }

    @GetMapping("/{cartId}")
    public Cart getAllCartDetail(@PathVariable("cartId") int id){
        return cartService.findById(id);
    }
}

