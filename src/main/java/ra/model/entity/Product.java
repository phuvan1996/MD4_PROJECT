package ra.model.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private int productId;
    @Column(name = "ProductName",columnDefinition = "nvarchar(50)")
    private String productName;
    @Column(name = "price")
    private float price;
    @Column(name = "description")
    private String description;
    @Column(name = "CreateDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Status")
    private boolean status;
    @Column(name = "Image")
    private String image;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "catalogId")
    private Catalog catalog;
    @OneToMany(mappedBy = "product")
    private List<Image> listImage = new ArrayList<>();
}
