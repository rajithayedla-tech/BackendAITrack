package com.airtribe.TrustDesk.repository;

import com.airtribe.TrustDesk.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}