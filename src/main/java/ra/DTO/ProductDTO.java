package ra.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ra.model.entity.Catalog;
import ra.model.entity.Image;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private double price;
    private String description;
    private int quantity;
    private String image;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    private boolean status;
    private List<Image> listImage = new ArrayList<>();
    private Catalog catalog;
}
