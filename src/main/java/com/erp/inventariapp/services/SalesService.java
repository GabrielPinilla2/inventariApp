package com.erp.inventariapp.services;

import com.erp.inventariapp.dto.SaleDTO;
import com.erp.inventariapp.dto.SaleProductDTO;
import com.erp.inventariapp.entities.Product;
import com.erp.inventariapp.entities.Sale;
import com.erp.inventariapp.entities.SaleProduct;
import com.erp.inventariapp.exceptions.ResourceNotFoundException;
import com.erp.inventariapp.mappers.SalesMapper;
import com.erp.inventariapp.repositories.CustomerRepository;
import com.erp.inventariapp.repositories.ProductRepository;
import com.erp.inventariapp.repositories.SalesRepository;
import com.erp.inventariapp.repositories.SellerRepository;
import com.erp.inventariapp.services.interfaces.ISalesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalesService implements ISalesService {

    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final SalesMapper salesMapper;

    public SalesService(SalesRepository salesRepository, CustomerRepository customerRepository,
                        SellerRepository sellerRepository, ProductRepository productRepository,
                        SalesMapper salesMapper) {
        this.salesRepository = salesRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.salesMapper = salesMapper;
    }

    @Override
    public List<SaleDTO> findAll() {
        List<Sale> sales = salesRepository.findAll();
        return sales.stream()
                .map(salesMapper::toDTO)
                .toList();
    }

    @Override
    public SaleDTO findById(Long idSale) {
        Sale sale = salesRepository.findById(idSale).orElseThrow(
                        () -> new ResourceNotFoundException("No se ha encontrado la venta asociada al ID " + idSale));
        return salesMapper.toDTO(sale);
    }

    @Override
    public List<SaleDTO> findByCustomerId(Long idCustomer) {
        return salesRepository.findByCustomerId(idCustomer).stream()
                .map(salesMapper::toDTO)
                .toList();
    }

    @Override
    public List<SaleDTO> findBySellerId(Long idSeller) {
        return salesRepository.findBySellerId(idSeller).stream()
                .map(salesMapper::toDTO)
                .toList();
    }

    @Override
    public List<SaleDTO> findByProductId(Long idProduct) {
        return salesRepository.findByProductId(idProduct).stream()
                .map(salesMapper::toDTO)
                .toList();
    }

    @Override
    public SaleDTO create(SaleDTO dto) {
        Sale sale = mapSaleDtoToEntity(dto);
        return salesMapper.toDTO(salesRepository.save(sale));
    }

    @Override
    public SaleDTO update(Long idSale, SaleDTO dto) {
        Optional<Sale> optionalSale = salesRepository.findById(idSale);
        if (optionalSale.isPresent()) {
            Sale sale = mapSaleDtoToEntity(dto);
            return salesMapper.toDTO(salesRepository.save(sale));
        } else {
            throw new ResourceNotFoundException("Venta no encontrada con el ID: " + idSale);
        }
    }

    @Override
    public void delete(Long idSale) {
        salesRepository.findById(idSale).ifPresentOrElse(
                sale -> salesRepository.deleteById(idSale),
                () -> { throw new ResourceNotFoundException("Venta no encontrada con el ID: " + idSale); }
        );
    }

    private Sale mapSaleDtoToEntity(SaleDTO dto) {
        Sale sale = salesMapper.toEntity(dto);

        customerRepository.findById(dto.getCustomerId()).ifPresentOrElse(
                sale::setCustomer,
                () -> { throw new ResourceNotFoundException("Cliente " + dto.getCustomerId() + " asociado a la venta "
                        + dto.getIdSale() + " no fue encontrado"); }
        );

        sellerRepository.findById(dto.getSellerId()).ifPresentOrElse(
                sale::setSeller,
                () -> { throw new ResourceNotFoundException("Vendedor " + dto.getSellerId() + " asociado a la venta "
                        + dto.getSellerId() + " no fue encontrado"); }
        );

        sellProducts(dto, sale);

        return sale;
    }

    private void sellProducts(SaleDTO dto, Sale sale) {
        List<SaleProduct> saleProducts = new ArrayList<>();
        double totalAmount = 0.0;

        for (SaleProductDTO spDTO : dto.getProducts()) {
            Product product = productRepository.findById(spDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con ID "
                            + spDTO.getProductId() + " no fue encontrado"));

            if (product.getStock() < spDTO.getQuantity()) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto: " + product.getName());
            }

            product.setStock(product.getStock() - spDTO.getQuantity());
            productRepository.save(product);

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setSale(sale);
            saleProduct.setProduct(product);
            saleProduct.setQuantity(spDTO.getQuantity());

            saleProducts.add(saleProduct);

            // Se multiplica el precio de cada producto * la cantidad para obtener el total de la venta
            totalAmount += product.getPrice() * spDTO.getQuantity();
        }

        sale.setTotalAmount(totalAmount);
        sale.setSaleProducts(saleProducts);
    }
}
