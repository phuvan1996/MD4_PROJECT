package ra.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CartDTO {
    private int cartId;
    private int quantity;
    private float totalPrice;
    private String productName;
    private float price;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    private float totalAmount;
}
