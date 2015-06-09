package org.webflow.test.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.webflow.domain.CategoryImpl;
import org.webflow.domain.ProductImpl;
import org.webflow.order.CategoryImplRepository;
import org.webflow.order.ProductImplRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan({"org.webflow"})
@ContextConfiguration(classes = UnitTestRestServiceWebConfig.class)
@WebAppConfiguration
public class ProductRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private CategoryImpl category;

    private List<ProductImpl> productList = new ArrayList<ProductImpl>();

    @Autowired
    private ProductImplRepository productRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CategoryImplRepository categoryRepository;
   
    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
               
        this.category=categoryRepository.findByNameContainingIgnoringCase("TV");

        this.productList=this.productRepository.findByCategory(this.category);
        
        }
    
    	String categoryName="TV";
    @Test
    public void readSingleProduct() throws Exception {
        mockMvc.perform(get("/" + categoryName + "/products/"
                + this.productList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.productList.get(0).getId().intValue())));               
    }

    @SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
