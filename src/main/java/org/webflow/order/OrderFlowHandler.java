package org.webflow.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.execution.repository.NoSuchFlowExecutionException;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;


/**
 * Flow handler handles the order flow execution.
 * @author bluo
 *
 */
public class OrderFlowHandler extends AbstractFlowHandler {
	
    private static final String DEFAULT_URL = "/products/search";

    @Override
    public String handleExecutionOutcome(FlowExecutionOutcome outcome, HttpServletRequest request,
	    HttpServletResponse response) {

    		return DEFAULT_URL;
    	}

    @Override
    public String handleException(FlowException e, HttpServletRequest request, 
    		HttpServletResponse response) {
	if (e instanceof NoSuchFlowExecutionException) {
	    return DEFAULT_URL;
	} else {
	    throw e;
	}
    }

}
