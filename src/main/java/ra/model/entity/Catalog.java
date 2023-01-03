package ra.model.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "catalog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catalogId")
    private int catalogId;
    @Column(name = "catalogName")
    private String catalogName;
    @Column(name = "createDate")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createDate;
    @Column(name = "status")
    private boolean status;
}
