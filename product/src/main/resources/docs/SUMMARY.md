# Product Service — Stage 1 Summary

- Service role: Read-only product search + lookup for cart-service and frontend.
- Stage 1 provides scaffolding:
  - Domain (pure Java) with Product model and specification skeleton.
  - Application use-case interfaces.
  - Infrastructure: JPA entity + repository, Solr client config, gRPC skeleton, data seeder.
  - Seeder controlled by product.seed.enabled and product.seed.size.
- Next steps (Stage 2): implement use-case implementations, Solr indexing for seeded data, gRPC impl calling usecases, pagination + Specification Pattern logic.
