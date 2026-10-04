package com.airtribe.TrustDesk.repository;

import com.airtribe.TrustDesk.entity.EvalCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvalCaseRepository
        extends JpaRepository<EvalCase, String> {

    EvalCase findByTicketId(String ticketId);
}