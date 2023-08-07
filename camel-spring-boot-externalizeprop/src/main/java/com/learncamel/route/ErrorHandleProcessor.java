package com.learncamel.route;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.learncamel.exception.ServiceException;
/**
 * @author Z332655
 */
@Component
@Slf4j
public class ErrorHandleProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("processing error");
        Exception exception= exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
        //   exchange.setProperty(Exchange.ROUTE_STOP,Boolean.TRUE);
        FaultDto fault = new FaultDto();
        fault.setType(exception.getClass().getSimpleName());
        fault.setTitle(exception.getMessage());
       int statusCode = (int) exchange.getIn().getHeader("statusCode");
       String statusDescription = exchange.getIn().getHeader("statusDescription").toString();
        throw new ServiceException(statusCode, statusDescription,fault);
    }
}