package pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wsb.programowaniejava.mariuszbielanczuk.WebConfigurator.customer.Customer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService {
    static final Double PRICINGFACTORSURFACE = 9.95;
    @Autowired private ProductRepository productRepository;

    public List<Product> listAll(){
        return (List<Product>) productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product get(Integer id) throws ProductNotFoudException {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new ProductNotFoudException("No product with ID " + id +" found.");
    }

    public Product getRandom () {
        List<Product> result = productRepository.getRandomProduct();
        return result.get(0);
    }

    public void delete(Integer id) throws ProductNotFoudException {
        Long count = productRepository.countById(id);
        if (count == null || count == 0) {
            throw new ProductNotFoudException("No product with ID " + id +" found.");
        }
        productRepository.deleteById(id);
    }

    public void generate() {
        ProductType[] productTypes = ProductType.values();
        Random r = new Random();
        ProductType type = productTypes[r.nextInt(ProductType.values().length)];
        int width = r.nextInt(1501 - 1000) + 1000;
        int height = r.nextInt(3001 - 2000) + 2000;
        BigDecimal price = calculatePrice(width, height);
//        BigDecimal price = BigDecimal.valueOf(r.nextDouble(500.00) + 199.00);
//        price = price.setScale(2, RoundingMode.CEILING);
        Product product = Product.builder()
                .type(type)
                .name(type.toString()+"_"+width+"_"+height)
                .height(height)
                .width(width)
                .price(price)
                .build();
        productRepository.save(product);
    }

    public static BigDecimal calculatePrice(Integer width, Integer height) {
        BigDecimal price = BigDecimal.valueOf((width * height)/10000 * PRICINGFACTORSURFACE);
        price = price.setScale(2, RoundingMode.UP);
        return price;
    }
}
