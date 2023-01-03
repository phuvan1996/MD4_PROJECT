package ra.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Catalog;
import java.util.List;

public interface CatalogService {
    List<Catalog> findAll();
    Catalog findById(int id);
    Catalog saveOrUpdate(Catalog catalog);
    void delete(int id);
    List<Catalog> searchByNameOrId(String catalogName,int catalogId);
    List<Catalog> sortCatalogByName(String direction);
    List<Catalog> sortByName_Id(String directionName,String directionId);
    Page<Catalog>getPagging(Pageable pageable);
}
