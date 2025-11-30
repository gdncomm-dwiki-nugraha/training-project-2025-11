package com.training.product.domain.service;

/**
 * Specification Pattern interface.
 * Encapsulates business rules for filtering/selecting objects of type T.
 * Pure Java interface with NO Spring annotations.
 *
 * @param <T> the type of object this specification applies to
 */
public interface Specification<T> {

    /**
     * Check if the given candidate satisfies this specification.
     *
     * @param candidate the object to check
     * @return true if the candidate satisfies the specification, false otherwise
     */
    boolean isSatisfiedBy(T candidate);
}
