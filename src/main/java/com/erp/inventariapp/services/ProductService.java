package com.erp.inventariapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.erp.inventariapp.dto.ProductDTO;
import com.erp.inventariapp.entities.Product;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.repositories.CategoryRepository;
import com.erp.inventariapp.repositories.MeasurementRepository;
import com.erp.inventariapp.repositories.ProductRepository;
import com.erp.inventariapp.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productrepository;

    @Autowired
    CategoryRepository categoryrepository;
    
    @Autowired
    MeasurementRepository measurementrepository;
    
    @Override
    public List<ProductDTO> findAll() {
        //Sort sort = Sort.by("name").ascending();
        //List<Product> products = (List<Product>) productrepository.findAll(sort);
        List<Product> products = (List<Product>) productrepository.findAll();
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(p -> productsDTO.add(this.convertProductToDTO(p)));       
        return productsDTO;
    }

    @Override
    public ProductDTO findById(Long idproduct) {
        Product p = productrepository.findById(idproduct).get();
        System.out.println("****** IDCATEGORY: "+p.getCategory().getIdcategory());
        System.out.println("****** NAMECATEGORY: "+p.getCategory().getName());

        return (this.convertProductToDTO(p)); 
    }

    @Override
    public List<ProductDTO> findByCodeLike(String code) {
        List<Product> products = (List<Product>) productrepository.findByCodeLike(code);
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(p -> productsDTO.add(this.convertProductToDTO(p)));
        return productsDTO;
    }

    @Override
    public List<ProductDTO> findByNameLike(String name) {
        List<Product> products = (List<Product>) productrepository.findByNameLike(name);
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(p -> productsDTO.add(this.convertProductToDTO(p)));
        return productsDTO;
    }

    @Override
    public List<ProductDTO> findByCategoryId(Long idcategory) {
        List<Product> products = (List<Product>) productrepository.findByCategoryId(idcategory);
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(p -> productsDTO.add(this.convertProductToDTO(p)));
        return productsDTO;
    }

    @Override
    public List<ProductDTO> findByCategoryName(String categoryname) {
        List<Product> products = (List<Product>) productrepository.findByCategoryName(categoryname);
        List<ProductDTO> productsDTO = new ArrayList<>();
        products.forEach(p -> productsDTO.add(this.convertProductToDTO(p)));
        return productsDTO;
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        Product p = new Product();
        return saveOrUpdate(p, dto);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product p = productrepository.findById(id).get();
        return saveOrUpdate(p, dto);
    }

    @Override
    public void delete(Long idproduct) {
        if (!productrepository.existsById(idproduct))
            throw new ResourceNotFoundException("Producto no encontrado");
        productrepository.deleteById(idproduct);
    }

    private ProductDTO saveOrUpdate(Product p, ProductDTO dto) {
        p.setCode(dto.getCode());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());
        p.setStockmin(dto.getStockmin());
        p.setStockmax(dto.getStockmax());
        p.setState(dto.getState());
        p.setCategory(categoryrepository.findById(dto.getIdcategory()).get());
        p.setMeasurement(measurementrepository.findById(dto.getIdmeasurement()).get());

        return convertProductToDTO(productrepository.save(p));
    }

    private ProductDTO convertProductToDTO(Product p) {
        ProductDTO dto = new ProductDTO();
        dto.setIdproduct(p.getIdproduct());
        dto.setCode(p.getCode());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setStockmin(p.getStockmin());
        dto.setStockmax(p.getStockmax());
        dto.setState(p.getState());
        dto.setIdcategory(p.getCategory().getIdcategory());
        dto.setNamecategory(p.getCategory().getName());
        dto.setIdmeasurement(p.getMeasurement().getIdmeasurement());
        dto.setNamemeasurement(p.getMeasurement().getName());

        return dto;
    }    

    /*
    private ProductDTO convertObjectToDTO(Object[] p) {
        ProductDTO dto = new ProductDTO();

        Product product = (Product) p[0];
        Category category = (Category) p[1];
        Measurement measurement = (Measurement) p[2];

        dto.setIdproduct(product.getIdproduct());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        dto.setStockmin(product.getStockmin());
        dto.setStockmax(product.getStockmax());
        dto.setPrice(product.getPrice());
        dto.setState(product.getState());
        dto.setIdcategory(category.getIdcategory());
        dto.setNamecategory(category.getName());
        dto.setIdmeasurement(measurement.getIdmeasurement());
        dto.setNamemeasurement(measurement.getName());
        return dto;
    }
    */
}
