package org.webflow.test.product;

import org.easymock.EasyMock;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;
import org.webflow.domain.OrderItemImpl;
import org.webflow.domain.ProductImpl;
import org.webflow.domain.User;
import org.webflow.order.OrderServiceImpl;

public class OrderingFlowTest extends AbstractXmlFlowExecutionTests {

    private OrderServiceImpl orderService;

    protected void setUp() {
    	orderService = EasyMock.createMock(OrderServiceImpl.class);
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
	return resourceFactory.createFileResource("src/main/webapp/WEB-INF/products/ordering/ordering-flow.xml");
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
	builderContext.registerBean("orderService", orderService);
    }

    public void testStartOrderingFlow() {
	OrderItemImpl orderItem = createTestOrdering();

	EasyMock.expect(orderService.createOrderItem(1L, "keith")).andReturn(orderItem);

	EasyMock.replay(orderService);

	MutableAttributeMap<String> input = new LocalAttributeMap<String>();
	input.put("productId", "1");
	MockExternalContext context = new MockExternalContext();
	context.setCurrentUser("keith");
	startFlow(input, context);

	assertCurrentStateEquals("enterOrderingDetails");
	assertResponseWrittenEquals("enterOrderingDetails", context);
	assertTrue(getRequiredFlowAttribute("orderItem") instanceof OrderItemImpl);

	EasyMock.verify(orderService);
    }

    public void testEnterOrderingDetails_Proceed() {
	setCurrentState("enterOrderingDetails");
	getFlowScope().put("orderItem", createTestOrdering());

	MockExternalContext context = new MockExternalContext();
	context.setEventId("proceed");
	resumeFlow(context);

	assertCurrentStateEquals("reviewOrdering");
	assertResponseWrittenEquals("reviewOrdering", context);
    }

    public void testReviewOrdering_Confirm() {
	setCurrentState("reviewOrdering");
	getFlowScope().put("orderItem", createTestOrdering());
	MockExternalContext context = new MockExternalContext();
	context.setEventId("confirm");
	resumeFlow(context);
	assertFlowExecutionEnded();
	assertFlowExecutionOutcomeEquals("orderingConfirmed");
    }

    private OrderItemImpl createTestOrdering() {
	ProductImpl product = new ProductImpl();
	product.setId(1L);;
	product.setName("Jameson Inn");
	User user = new User("keith", "pass", "Keith Donald");
	OrderItemImpl orderItem = new OrderItemImpl(product, user,1);
	return orderItem;
    }

}
