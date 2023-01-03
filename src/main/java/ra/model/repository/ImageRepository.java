package ra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Integer> {

}
