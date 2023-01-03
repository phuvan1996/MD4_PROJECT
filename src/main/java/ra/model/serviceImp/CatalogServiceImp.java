package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.model.entity.Catalog;
import ra.model.repository.CatalogRepository;
import ra.model.service.CatalogService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(rollbackFor = SQLException.class)
public class CatalogServiceImp implements CatalogService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Override
    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public Catalog findById(int id) {
        return catalogRepository.findById(id).get();
    }

    @Override
    public Catalog saveOrUpdate(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public void delete(int id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public List<Catalog> searchByNameOrId(String catalogName, int catalogId) {
        return catalogRepository.findByCatalogNameOrCatalogId(catalogName, catalogId);
    }

    @Override
    public List<Catalog> sortCatalogByName(String direction) {
       if (direction.equals("asc")){
           return catalogRepository.findAll(Sort.by("catalogName").ascending());
       }else {
           return catalogRepository.findAll(Sort.by("catalogName").descending());
       }
    }

    @Override
    public List<Catalog> sortByName_Id(String directionName, String directionId) {
        List<Sort.Order>listOrder = new ArrayList<>();
        if (directionName.equals("asc")){
            listOrder.add(new Sort.Order(Sort.Direction.ASC,"catalogName"));
        }else {
            listOrder.add(new Sort.Order(Sort.Direction.DESC,"catalogName"));
        }if (directionId.equals("id")){
            listOrder.add(new Sort.Order(Sort.Direction.ASC,"catalogId"));
        }else {
            listOrder.add(new Sort.Order(Sort.Direction.DESC,"catalogId"));
        }
        return catalogRepository.findAll(Sort.by(listOrder));
    }

    @Override
    public Page<Catalog> getPagging(Pageable pageable) {
        return catalogRepository.findAll(pageable);
    }

}
