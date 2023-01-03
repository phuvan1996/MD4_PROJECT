package ra.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.DTO.ProductDTO;
import ra.model.entity.Catalog;
import ra.model.entity.Image;
import ra.model.entity.Product;
import ra.model.service.CatalogService;
import ra.model.service.ImageService;
import ra.model.service.ProductService;
import ra.payload.reponse.MessageResponse;
import ra.payload.request.ProductRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    ImageService imageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CatalogService catalogService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductDTO> getAllProduct() {
        List<ProductDTO> listProductDTO = new ArrayList<>();
        List<Product> listProduct = productService.findAll();
        for (Product pro : listProduct) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(pro.getProductId());
            productDTO.setProductName(pro.getProductName());
            productDTO.setImage(pro.getImage());
            productDTO.setPrice(pro.getPrice());
            productDTO.setCreateDate(pro.getCreateDate());
            productDTO.setQuantity(pro.getQuantity());
            productDTO.setDescription(pro.getDescription());
            productDTO.setStatus(pro.isStatus());
            productDTO.setCatalog(pro.getCatalog());
            productDTO.getListImage().addAll(pro.getListImage());
            listProductDTO.add(productDTO);
        }
        return listProductDTO;
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product getProductById(@PathVariable("productId") int id) {
        return productService.findById(id);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody ProductRequest product) {
        try {
            Product proNew = new Product();
            proNew.setProductName(product.getProductName());
            proNew.setPrice(product.getPrice());
            proNew.setQuantity(product.getQuantity());
            proNew.setCreateDate(product.getCreateDate());
            proNew.setDescription(product.getDescription());
            proNew.setImage(product.getImage());
            proNew.setStatus(true);
            proNew.setCatalog (catalogService.findById(product.getCatalogId()));
            productService.saveOrUpdate(proNew);
            for (String str : product.getListImageLink()) {
                Image image =new Image();
                image.setImageURL(str);
                image.setProduct(proNew);
                imageService.saveOrUpdate(image);
            }
            return ResponseEntity.ok(new MessageResponse("thêm sản phẩm thành công!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi trong quá trình xử lý vui lòng thử lại!"));
        }
    }
    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,@RequestBody ProductRequest product){
        try {
            Catalog catalog = catalogService.findById(product.getCatalogId());
            Product productUpdate = productService.findById(productId);
            productUpdate.setProductName(product.getProductName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setCreateDate(product.getCreateDate());
            productUpdate.setQuantity(product.getQuantity());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setStatus(product.isProductStatus());
            productUpdate.setCatalog(catalog);
            for (Image image :productUpdate.getListImage()) {
                imageService.delete(image.getImageId());
            }
            for (String str :product.getListImageLink()) {
                Image image = new Image();
                image.setImageURL(str);
                image.setProduct(productUpdate);
                imageService.saveOrUpdate(image);
            }
            return ResponseEntity.ok(productUpdate);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("Có lỗi trong quá trình xử lý vui lòng thử lại!");
        }
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable("productId") int id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public List<Product> searchByNameOrId(@RequestParam("productName") String productName, @RequestParam("productId") int id) {
        return productService.searchByName(productName, id);
    }
    @GetMapping("/sortByName")
    public ResponseEntity<List<Product>> sortBookByProductName(@RequestParam("direction") String direction) {
        List<Product> listProduct = productService.sortBookByProductName(direction);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/sortByNameAndPrice")
    public ResponseEntity<List<Product>> sortByNameAndPrice(@RequestParam("directionName") String directionName,
                                                              @RequestParam("directionPrice") String directionPrice) {
        List<Product> listProduct = productService.sortByNameAndPrice(directionName, directionPrice);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    @GetMapping("/sortByNameAndPriceAndId")
    public ResponseEntity<List<Product>> sortByName_Price_Id(@RequestParam("directionName") String directionName,
                                                               @RequestParam("directionPrice") String directionPrice,
                                                               @RequestParam("directionId") String directionId) {
        List<Product> listProduct = productService.sortByName_Price_Id(directionName, directionPrice,directionId);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }
    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPagging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> pageProduct= productService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("product",pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam String direction){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"productName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"productName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Product> pageProduct = productService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("product",pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }
}
