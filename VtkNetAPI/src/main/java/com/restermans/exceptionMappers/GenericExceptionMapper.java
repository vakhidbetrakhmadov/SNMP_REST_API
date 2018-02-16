package com.restermans.exceptionMappers;

import com.restermans.model.Error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.StringTokenizer;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {

        String errorMessage = throwable.getMessage();

        try {
            StringTokenizer errorMessageTokenizer = new StringTokenizer(errorMessage);
            errorMessageTokenizer.nextToken(); //HTTP
            int errorCode = Integer.parseInt(errorMessageTokenizer.nextToken());

            StringBuilder errorMessageBuilder = new StringBuilder();
            while(errorMessageTokenizer.hasMoreElements())
                errorMessageBuilder.append(errorMessageTokenizer.nextToken());

            Error error = new Error(errorCode, errorMessageBuilder.toString());
            return Response.status(errorCode).entity(error).build();

        } catch (NumberFormatException nfe) {

            System.out.println(nfe.getMessage());

            Error error = new Error(500, errorMessage);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
