package com.restermans.exceptionMappers;

import com.restermans.exceptions.DataNotFoundException;
import com.restermans.model.Error;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper  implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException e) {

        Error error = new Error(404,e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
