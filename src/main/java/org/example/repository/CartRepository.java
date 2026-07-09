package org.example.repository;

import org.example.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Element collections cannot be targeted with JPQL, hence the native query.
    @Modifying
    @Query(value = "DELETE FROM cart_products WHERE product_id = :productId", nativeQuery = true)
    void removeProductFromAllCarts(@Param("productId") Long productId);
}
