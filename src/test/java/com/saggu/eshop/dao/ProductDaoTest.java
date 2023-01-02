package com.saggu.eshop.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.saggu.eshop.dto.ProductDto;
import org.junit.jupiter.api.Test;

class ProductDaoTest {

    @Test
    void givenPrePopulatedData_getProducts_ShouldReturnAProductList() throws InterruptedException {
        ProductDao dao = new ProductDao("Test");
        assertThat(dao.getProducts().size()).isEqualTo(3);
    }

    @Test
    void givenANewProductDto_addProduct_ShouldAddAndReturnDtoWithProdId()
            throws InterruptedException {
        ProductDao dao = new ProductDao("Test");
        assertThat(dao.getProducts().size()).isEqualTo(3);
        ProductDto productSamsung =
                new ProductDto("Sony 4K TV 75", 3049.99, "Sony LED 4k Smart TV");

        ProductDto createdDto = dao.addProduct(productSamsung);

        assertThat(dao.getProducts().size()).isEqualTo(4);
        assertThat(createdDto.productId()).isNotNull();
    }
}
