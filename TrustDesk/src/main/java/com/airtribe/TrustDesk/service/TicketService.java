package com.airtribe.TrustDesk.service;

import com.airtribe.TrustDesk.entity.Customer;
import com.airtribe.TrustDesk.entity.Order;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.repository.CustomerRepository;
import com.airtribe.TrustDesk.repository.OrderRepository;
import com.airtribe.TrustDesk.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public TicketService(
            TicketRepository ticketRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository) {

        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicket(String ticketId) {

        return ticketRepository.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found: " + ticketId
                        )
                );
    }

    public TicketContext getTicketContext(String ticketId) {

        Ticket ticket = getTicket(ticketId);

        Customer customer = customerRepository
                .findById(ticket.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found: "
                                        + ticket.getCustomerId()
                        )
                );

        Order order = null;

        if (ticket.getOrderId() != null
                && !ticket.getOrderId().isBlank()) {

            order = orderRepository
                    .findById(ticket.getOrderId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Order not found: "
                                            + ticket.getOrderId()
                            )
                    );
        }

        return new TicketContext(
                ticket,
                customer,
                order
        );
    }

    public record TicketContext(
            Ticket ticket,
            Customer customer,
            Order order
    ) {
    }
}