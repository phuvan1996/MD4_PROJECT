package ra.payload.reponse;

import lombok.Data;

@Data
public class CartDetailResponse {
    private String productName;
    private float price;
    private int quantity;
    private float totalPrice;
}
