package org.majimena.petz.web.api.product;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;
import org.junit.Test;
import org.majimena.petz.datatype.TaxType;
import org.majimena.petz.domain.Clinic;
import org.majimena.petz.domain.Product;
import org.majimena.petz.repository.ClinicRepository;
import org.majimena.petz.repository.ProductRepository;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @see ProductValidator
 */
public class ProductValidatorTest {

    @Tested
    private ProductValidator sut = new ProductValidator();

    @Injectable
    private ProductRepository productRepository;

    @Injectable
    private ClinicRepository clinicRepository;

    protected static Product newProduct() {
        return Product.builder()
                .id("product1")
                .clinic(Clinic.builder().id("1").build())
                .name("12345678901234567890123456789012345678901234567890")
                .price(new BigDecimal(123456789))
                .taxType(TaxType.EXCLUSIVE)
                .taxRate(BigDecimal.valueOf(0.08))
                .description("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890").build();
    }

    @Test
    public void 対象が指定されていなくてもエラーにならないこと() throws Exception {
        Product data = newProduct();
        Errors errors = new BindException(data, "product");

        sut.validate(Optional.ofNullable(null), errors);

        assertThat(errors, is(notNullValue()));
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void 正常時はエラーにならないこと() throws Exception {
        Product data = newProduct();
        Errors errors = new BindException(data, "product");

        new NonStrictExpectations() {{
            clinicRepository.findOne("1");
            result = Clinic.builder().id("1").build();
            productRepository.findOne("product1");
            result = Product.builder().id("product1").build();
        }};

        sut.validate(Optional.of(data), errors);

        assertThat(errors, is(notNullValue()));
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void IDが指定されていない場合でもエラーにならないこと() throws Exception {
        Product data = newProduct();
        Errors errors = new BindException(data, "product");

        new NonStrictExpectations() {{
            clinicRepository.findOne("1");
            result = Clinic.builder().id("1").build();
        }};

        data.setId(null);
        sut.validate(Optional.of(data), errors);

        assertThat(errors, is(notNullValue()));
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void クリニックが存在しない場合はエラーになること() throws Exception {
        Product data = newProduct();
        Errors errors = new BindException(data, "product");

        new NonStrictExpectations() {{
            clinicRepository.findOne("1");
            result = null;
            productRepository.findOne("product1");
            result = Product.builder().id("product1").build();
        }};

        sut.validate(Optional.of(data), errors);

        assertThat(errors, is(notNullValue()));
        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getAllErrors().size(), is(1));
        assertThat(errors.getAllErrors().get(0).getCode(), is("PTZ_001999"));
    }

    @Test
    public void プロダクトが存在しない場合はエラーになること() throws Exception {
        Product data = newProduct();
        Errors errors = new BindException(data, "product");

        new NonStrictExpectations() {{
            clinicRepository.findOne("1");
            result = Clinic.builder().id("1").build();
            productRepository.findOne("product1");
            result = null;
        }};

        sut.validate(Optional.of(data), errors);

        assertThat(errors, is(notNullValue()));
        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getAllErrors().size(), is(1));
        assertThat(errors.getAllErrors().get(0).getCode(), is("PTZ_999998"));
    }
}