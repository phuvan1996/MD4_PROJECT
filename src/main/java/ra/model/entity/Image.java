package ra.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
@Entity
@Table(name = "Image")
@Data
public class Image {
    @Id
    @Column(name = "ImageId")
    private int imageId;
    @JsonIgnore
    @Column(name = "Url",columnDefinition = "text")
    private String imageURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    @JsonIgnore
    private Product product;
}
