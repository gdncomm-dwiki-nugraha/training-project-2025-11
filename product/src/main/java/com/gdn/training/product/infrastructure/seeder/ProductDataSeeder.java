package com.gdn.training.product.infrastructure.seeder;

import com.gdn.training.product.infrastructure.persistence.entity.ProductEntity;
import com.gdn.training.product.infrastructure.persistence.jpa.ProductJpaRepository;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * Seeder saves to Postgres and indexes each product into Solr.
 * Controlled by product.seed.enabled and product.seed.size.
 */
@Component
public class ProductDataSeeder implements CommandLineRunner {
    // Attributes
    private final ProductJpaRepository repository;
    private final SolrClient solrClient;
    private final boolean seedEnabled;
    private final int seedSize;
    private final Random rnd = new Random();
    private final String solrCollection;

    // Constructor
    public ProductDataSeeder(ProductJpaRepository repository,
            SolrClient solrClient,
            @Value("${product.seed.enabled:false}") boolean seedEnabled,
            @Value("${product.seed.size:50}") int seedSize,
            @Value("${spring.data.solr.host:http://localhost:8983/solr/products}") String solrHost) {
        this.repository = repository;
        this.solrClient = solrClient;
        this.seedEnabled = seedEnabled;
        this.seedSize = seedSize;
        // extract collection from host URL (last path segment)
        String tmp = solrHost;
        if (tmp.endsWith("/"))
            tmp = tmp.substring(0, tmp.length() - 1);
        if (tmp.contains("/"))
            this.solrCollection = tmp.substring(tmp.lastIndexOf('/') + 1);
        else
            this.solrCollection = "products";
    }

    // Methods
    @Override
    public void run(String... args) throws Exception {
        if (!seedEnabled)
            return;
        if (repository.count() > 0)
            return;

        IntStream.range(0, seedSize).forEach(i -> {
            try {
                String id = UUID.randomUUID().toString();
                String sellerId = UUID.randomUUID().toString();
                ProductEntity e = new ProductEntity(
                        id,
                        sellerId,
                        "Product " + (i + 1),
                        "Lorem ipsum product description " + (i + 1),
                        randomCategory(i),
                        (rnd.nextInt(10_000_000) + 1000),
                        rnd.nextInt(500),
                        "ACTIVE",
                        Instant.now());
                repository.save(e);

                // Index to Solr
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("productId", e.getProductId());
                doc.addField("sellerId", e.getSellerId());
                doc.addField("name", e.getName());
                doc.addField("description", e.getDescription());
                doc.addField("category", e.getCategory());
                doc.addField("price", e.getPrice());
                doc.addField("stock", e.getStock());
                doc.addField("status", e.getStatus());
                doc.addField("createdAt", e.getCreatedAt().toEpochMilli());
                solrClient.add(solrCollection, doc);

                if (i % 500 == 0) {
                    solrClient.commit(solrCollection);
                }
            } catch (Exception ex) {
                throw new RuntimeException("Seeder error", ex);
            }
        });

        try {
            solrClient.commit(solrCollection);
        } catch (Exception ex) {
            // ignore commit errors for now
        }
    }

    private String randomCategory(int i) {
        String[] cats = new String[] { "electronics", "fashion", "home", "beauty", "sports" };
        return cats[i % cats.length];
    }
}
