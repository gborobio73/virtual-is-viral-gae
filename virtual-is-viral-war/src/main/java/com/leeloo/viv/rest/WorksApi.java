package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import java.util.List;

import com.google.gson.Gson;

@Path("works")
public class WorksApi {

    @GET
    @Path("/all")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllWorks() {
        Gson gson = new Gson();
        List<Work> works = new WorkRepository().getAllWorks();
        //return gson.toJson(works);
        return Response.ok().entity(gson.toJson(works)).build();
    }

    @GET
    @Path("/user/{user}")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getUserWorks(@PathParam("user") String user) {
        Gson gson = new Gson();
        List<Work> works = new WorkRepository().getUserWorks(user);
        return gson.toJson(works);
    }

    @GET
    @Path("/work/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getWork(@PathParam("id") String id) {
        Gson gson = new Gson();
        Work work = new WorkRepository().getWork(id);
        return gson.toJson(work);
    }
}
