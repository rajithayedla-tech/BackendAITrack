package com.airtribe.TrustDesk.config;

import com.airtribe.TrustDesk.entity.Customer;
import com.airtribe.TrustDesk.entity.Order;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.entity.ToolAction;

import com.airtribe.TrustDesk.repository.CustomerRepository;
import com.airtribe.TrustDesk.repository.OrderRepository;
import com.airtribe.TrustDesk.repository.TicketRepository;
import com.airtribe.TrustDesk.repository.ToolActionRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DataSeeding implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final ToolActionRepository toolActionRepository;
    private final ObjectMapper objectMapper;

    public DataSeeding(
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            TicketRepository ticketRepository,
            ToolActionRepository toolActionRepository,
            ObjectMapper objectMapper
    ) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.toolActionRepository = toolActionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("========================================");
        System.out.println("TrustDesk data seeding started.");
        System.out.println("========================================");

        seedCustomers();
        seedOrders();
        seedTickets();
        seedToolActions();

        System.out.println("========================================");
        System.out.println("TrustDesk data seeding completed.");
        System.out.println("========================================");
    }

    private void seedCustomers() throws Exception {

        if (customerRepository.count() > 0) {
            System.out.println("Customers already exist. Skipping.");
            return;
        }

        List<Customer> customers = readJson(
                "data/customers.json",
                new TypeReference<List<Customer>>() {}
        );

        customerRepository.saveAll(customers);

        System.out.println("Seeded " + customers.size() + " customers.");
    }

    private void seedOrders() throws Exception {

        if (orderRepository.count() > 0) {
            System.out.println("Orders already exist. Skipping.");
            return;
        }

        List<Order> orders = readJson(
                "data/orders.json",
                new TypeReference<List<Order>>() {}
        );

        orderRepository.saveAll(orders);

        System.out.println("Seeded " + orders.size() + " orders.");
    }

    private void seedTickets() throws Exception {

        if (ticketRepository.count() > 0) {
            System.out.println("Tickets already exist. Skipping.");
            return;
        }

        List<Ticket> tickets = readJson(
                "data/tickets.json",
                new TypeReference<List<Ticket>>() {}
        );

        ticketRepository.saveAll(tickets);

        System.out.println("Seeded " + tickets.size() + " tickets.");
    }

    private void seedToolActions() throws Exception {

        if (toolActionRepository.count() > 0) {
            System.out.println("Tool actions already exist. Skipping.");
            return;
        }

        List<ToolAction> toolActions = readJson(
                "data/tool_actions.json",
                new TypeReference<List<ToolAction>>() {}
        );

        toolActionRepository.saveAll(toolActions);

        System.out.println(
                "Seeded " + toolActions.size() + " tool actions."
        );
    }

    private <T> List<T> readJson(
            String filePath,
            TypeReference<List<T>> typeReference
    ) throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "JSON file not found: " + file.getAbsolutePath()
            );
        }

        System.out.println(
                "Reading JSON file: " + file.getAbsolutePath()
        );

        return objectMapper.readValue(file, typeReference);
    }
}