package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Image;
import ra.model.repository.ImageRepository;
import ra.model.service.ImageService;
@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Image saveOrUpdate(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(int id) {
        imageRepository.deleteById(id);
    }
}
