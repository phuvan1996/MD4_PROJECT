package ra.model.service;

import ra.model.entity.Image;
import ra.model.entity.Product;

public interface ImageService {
    Image saveOrUpdate(Image image);
    void delete(int id);
}
