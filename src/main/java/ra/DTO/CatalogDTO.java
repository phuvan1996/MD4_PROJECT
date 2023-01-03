package ra.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.model.entity.Product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatalogDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catalogId;
    private String catalogName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    private boolean status;
    private List<Product> listProducts = new ArrayList<>();

}