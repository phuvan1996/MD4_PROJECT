package ra.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ra.model.entity.Catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class ProductRequest {
    private int productID;
    private int catalogId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    private String productName;
    private float price;
    private int quantity;
    private String image;
    private String description;
    private List<String> listImageLink = new ArrayList<>();
    private boolean productStatus;
}
