package org.example.customer.model;

public record CustomerRequest(
        String firstName,
        String lastName,
        String email
) {
}
