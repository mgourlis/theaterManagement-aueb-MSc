package gr.aueb.mscis.theater.resource;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.persistence.JPAUtil;

@Path("purchase")
public class PurchaseResource {
	
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes("application/json")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPurchase(NewPurchaseInfo newPurchaseInfo) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

		Purchase purchase = newPurchaseInfo.createNewPurchase(em);
		
//		PurchaseInfo purchaseInfo = new PurchaseInfo();
//		purchaseInfo =
        PurchaseInfo.wrap(purchase);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newPurchaseUri = ub.path(Integer.toString(purchase.getId())).build();

        em.close();
		
		return Response.created(newPurchaseUri).build();
    }
}