import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository  productRepository;

    @GET
    public List<Product> getAll() {
        return  productRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public  Product get(@PathParam("id") Long id) {
        return productRepository.findById(id);
    }

    @POST
    @Transactional
    public Response create(Product product) {
        productRepository.persist(product);
        return Response.ok(product).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Product product) {
        Product p = productRepository.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        p.setName(product.getName());
        p.setCategory(product.getCategory());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());
        productRepository.persist(p);
        return Response.ok(p).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Product p = productRepository.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productRepository.delete(p);
        return Response.noContent().build();
    }

}