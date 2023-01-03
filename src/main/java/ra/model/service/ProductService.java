package ra.model.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.model.entity.Product;

import java.util.List;

public interface ProductService{
    List<Product> findAll();
    Product findById(int id);
    Product saveOrUpdate(Product product);
    void delete(int id);
    List<Product> searchByName(String productName,int id);
    List<Product> CatalogOneToManyProduct(int id);
    List<Product> sortBookByProductName(String direction);

    List<Product> sortByNameAndPrice(String directionName,String directionPrice);
    List<Product> sortByName_Price_Id(String directionName,String directionPrice,String directionId);

    Page<Product> getPagging(Pageable pageable);
}