package org.example.dto;

import org.example.model.Product;

import java.util.List;
import java.util.Objects;

/**
 * The legacy JavaFX client expects each product as a positional string array:
 * {@code [name, description, price, id, image]}. Keep this format stable.
 */
public final class ProductRows {

    private ProductRows() {
    }

    public static List<String> toRow(Product product) {
        return List.of(
                product.getName(),
                Objects.toString(product.getDescription(), ""),
                product.getPrice().toPlainString(),
                product.getId().toString(),
                Objects.toString(product.getImage(), ""));
    }
}
