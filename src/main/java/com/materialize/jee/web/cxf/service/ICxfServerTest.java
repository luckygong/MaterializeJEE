package com.materialize.jee.web.cxf.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/")
@Produces({ MediaType.APPLICATION_JSON})//返回格式
public interface ICxfServerTest {
	@POST
	@Path("/test/{phone}")
    public String sayHello(@PathParam("phone")String phone);
}