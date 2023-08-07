package com.learncamel.exception;
import com.learncamel.route.FaultDto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ServiceException extends RuntimeException{

   private int statusCode;
   private String statusDescription;
   private FaultDto fault;
   
   public ServiceException(int statusCode, String statusDescription, FaultDto fault) {
      this.statusCode = statusCode;
      this.statusDescription = statusDescription;
      this.fault = fault;
   }
}