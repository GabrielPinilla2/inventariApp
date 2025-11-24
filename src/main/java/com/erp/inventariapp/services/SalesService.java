package com.erp.inventariapp.services;

import com.erp.inventariapp.dto.SalesChartDTO;
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
    
    //public List<SalesChartDTO> getMonthlySales() {
    //    return salesRepository.getMonthlySales();
    //}

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
            System.out.println("Actualizando venta con ID: " + idSale);
            Sale existingSale = optionalSale.get();

            // Actualizar datos básicos
            validateSellerAndCustomer(dto, existingSale);

            // Actualizar productos y total usando la lógica de diferencia
            sellProducts(dto, existingSale);

            return salesMapper.toDTO(salesRepository.save(existingSale));
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

        validateSellerAndCustomer(dto, sale);
        sellProducts(dto, sale);

        return sale;
    }

    private void validateSellerAndCustomer(SaleDTO dto, Sale sale) {
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
    }

    private void sellProducts(SaleDTO dto, Sale sale) {
        // Usar la lista existente para evitar problemas con orphanRemoval
        List<SaleProduct> originalSaleProducts = sale.getSaleProducts() != null ? sale.getSaleProducts() : new ArrayList<>();
        double totalAmount = 0.0;

        // Para saber qué productos ya no están en la edición
        List<Long> dtoProductIds = dto.getProducts().stream().map(SaleProductDTO::getProductId).toList();

        // Eliminar SaleProducts que ya no están en la edición y devolver stock
        originalSaleProducts.removeIf(originalSP -> {
            boolean toRemove = !dtoProductIds.contains(originalSP.getProduct().getIdproduct());
            if (toRemove) {
                Product product = originalSP.getProduct();
                product.setStock(product.getStock() + originalSP.getQuantity());
                productRepository.save(product);
            }
            return toRemove;
        });

        // Actualizar o crear SaleProduct según corresponda
        for (SaleProductDTO spDTO : dto.getProducts()) {
            Product product = productRepository.findById(spDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con ID "
                            + spDTO.getProductId() + " no fue encontrado"));

            // Buscar si ya existía este producto en la venta
            SaleProduct originalSP = originalSaleProducts.stream()
                .filter(sp -> sp.getProduct().getIdproduct().equals(product.getIdproduct()))
                .findFirst().orElse(null);

            int originalQty = originalSP != null ? originalSP.getQuantity() : 0;
            int newQty = spDTO.getQuantity();
            int diffQty = newQty - originalQty;

            // Validar stock solo si se va a aumentar la cantidad
            if (diffQty > 0 && product.getStock() < diffQty) {
                throw new IllegalArgumentException("No hay suficiente stock para el producto: " + product.getName());
            }

            // Actualizar stock según la diferencia
            product.setStock(product.getStock() - diffQty);
            productRepository.save(product);

            // Actualizar o crear SaleProduct
            if (originalSP != null) {
                originalSP.setQuantity(newQty);
            } else {
                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setSale(sale);
                saleProduct.setProduct(product);
                saleProduct.setQuantity(newQty);
                originalSaleProducts.add(saleProduct);
            }

            // Sumar al total el valor actualizado de este producto
            totalAmount += product.getPrice() * newQty;
        }

        System.out.println("Total amount: " + totalAmount);
        sale.setTotalAmount(totalAmount);
        // No reemplazar la lista, solo modificarla
    }
    
}
