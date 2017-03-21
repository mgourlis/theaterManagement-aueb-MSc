package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.Purchase;
import gr.aueb.mscis.theater.persistence.JPAUtil;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("purchase")
public class PurchaseResource {
	
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes("application/json")
    public Response createPurchase(NewPurchaseInfo newPurchaseInfo) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

		Purchase purchase = newPurchaseInfo.createNewPurchase(em);
		
//		PurchaseInfo purchaseInfo = new PurchaseInfo();
//		purchaseInfo =
        PurchaseInfo.wrap(purchase);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newPurchaseUri = ub.path("purchase"+"/"+Integer.toString(purchase.getId())).build();

        em.close();
		
		return Response.created(newPurchaseUri).build();
    }
}