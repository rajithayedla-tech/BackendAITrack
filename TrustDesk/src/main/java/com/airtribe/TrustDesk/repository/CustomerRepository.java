package com.airtribe.TrustDesk.repository;

import com.airtribe.TrustDesk.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}