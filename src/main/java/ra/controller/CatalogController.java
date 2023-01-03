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
import ra.DTO.CatalogDTO;
import ra.model.entity.Catalog;
import ra.model.service.CatalogService;
import ra.model.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private ProductService productService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Catalog> GetAllCatalog(){
        return catalogService.findAll();
    }
    @GetMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public CatalogDTO OneToManyProduct(@PathVariable("catalogId") int id){
        CatalogDTO catalogDTO = new CatalogDTO();
        Catalog catalog = catalogService.findById(id);
        catalogDTO.setCatalogId(catalog.getCatalogId());
        catalogDTO.setCatalogName(catalog.getCatalogName());
        catalogDTO.setCreateDate(catalog.getCreateDate());
        catalogDTO.setStatus(catalog.isStatus());
        catalogDTO.setListProducts(productService.CatalogOneToManyProduct(id));
        return catalogDTO;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Catalog createCatalog(@RequestBody Catalog catalog){
        return catalogService.saveOrUpdate(catalog);
    }
    @PutMapping("/{catalogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Catalog updateCatalog(@PathVariable("catalogId") int id,@RequestBody Catalog catalog){
        Catalog catalogUpdate = catalogService.findById(id);
        catalogUpdate.setCatalogName(catalog.getCatalogName());
        catalogUpdate.setCreateDate(catalog.getCreateDate());
        catalogUpdate.setStatus(catalog.isStatus());
        return catalogService.saveOrUpdate(catalogUpdate);
    }
    @DeleteMapping("/{catalogId}")
    public void deleteCatalog(@PathVariable("catalogId") int id){

        catalogService.delete(id);
    }
    @GetMapping("/search")
    public List<Catalog> searchByNameOrId(@RequestParam("catalogName") String catalogName,@RequestParam("catalogId") int catalogId){
        return catalogService.searchByNameOrId(catalogName,catalogId);
    }
    @GetMapping("/sortByName")
    public ResponseEntity<List<Catalog>> sortCatalogByBookName(@RequestParam("direction") String direction) {
        List<Catalog> listCatalog = catalogService.sortCatalogByName(direction);
        return new ResponseEntity<>(listCatalog, HttpStatus.OK);
    }
    @GetMapping("/sortByName_Id")
    public ResponseEntity<List<Catalog>> sortByNameAndPriceAndId(@RequestParam("directionName") String directionName, @RequestParam("directionId") String directionId) {
        List<Catalog> listCatalog = catalogService.sortByName_Id(directionName,directionId);
        return new ResponseEntity<>(listCatalog, HttpStatus.OK);
    }
    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPagging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Catalog> pageCatalog = catalogService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",pageCatalog.getContent());
        data.put("total",pageCatalog.getSize());
        data.put("totalItems",pageCatalog.getTotalElements());
        data.put("totalPages",pageCatalog.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }
    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam String direction){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"catalogName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"catalogName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> pageCatalog = catalogService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",pageCatalog.getContent());
        data.put("total",pageCatalog.getSize());
        data.put("totalItems",pageCatalog.getTotalElements());
        data.put("totalPages",pageCatalog.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }
}