package com.airtribe.TrustDesk.config;

import com.airtribe.TrustDesk.entity.Customer;
import com.airtribe.TrustDesk.entity.EvalCase;
import com.airtribe.TrustDesk.entity.KnowledgeDocument;
import com.airtribe.TrustDesk.entity.Order;
import com.airtribe.TrustDesk.entity.Ticket;
import com.airtribe.TrustDesk.entity.ToolAction;

import com.airtribe.TrustDesk.repository.CustomerRepository;
import com.airtribe.TrustDesk.repository.EvalCaseRepository;
import com.airtribe.TrustDesk.repository.KnowledgeDocumentRepository;
import com.airtribe.TrustDesk.repository.OrderRepository;
import com.airtribe.TrustDesk.repository.TicketRepository;
import com.airtribe.TrustDesk.repository.ToolActionRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Component
public class DataSeeding implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final ToolActionRepository toolActionRepository;
    private final KnowledgeDocumentRepository knowledgeDocumentRepository;
    private final EvalCaseRepository evalCaseRepository;
    private final ObjectMapper objectMapper;

    public DataSeeding(
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            TicketRepository ticketRepository,
            ToolActionRepository toolActionRepository,
            KnowledgeDocumentRepository knowledgeDocumentRepository,
            EvalCaseRepository evalCaseRepository,
            ObjectMapper objectMapper
    ) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.toolActionRepository = toolActionRepository;
        this.knowledgeDocumentRepository = knowledgeDocumentRepository;
        this.evalCaseRepository = evalCaseRepository;
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
        seedKnowledgeDocuments();
        seedEvalCases();

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

    private void seedKnowledgeDocuments() throws Exception {

        if (knowledgeDocumentRepository.count() > 0) {
            System.out.println("Knowledge documents already exist. Skipping.");
            return;
        }

        Path directory = Paths.get("data/knowledge_base");

        if (!Files.exists(directory)) {
            throw new IllegalArgumentException(
                    "Knowledge base directory not found: "
                            + directory.toAbsolutePath()
            );
        }

        try (Stream<Path> files = Files.list(directory)) {

            List<Path> markdownFiles = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".md"))
                    .sorted()
                    .toList();

            for (Path path : markdownFiles) {

                KnowledgeDocument document =
                        parseKnowledgeDocument(path);

                knowledgeDocumentRepository.save(document);

                System.out.println(
                        "Seeded knowledge document: "
                                + document.getDocumentId()
                );
            }

            System.out.println(
                    "Seeded " + markdownFiles.size()
                            + " knowledge documents."
            );
        }
    }

    private KnowledgeDocument parseKnowledgeDocument(
            Path path
    ) throws Exception {

        String content = Files.readString(path);

        String title = extractTitle(content);
        String documentId = extractMetadata(content, "Doc ID");
        String audience = extractMetadata(content, "Audience");
        String version = extractMetadata(content, "Version");

        if (documentId == null || documentId.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing 'Doc ID' in: "
                            + path.toAbsolutePath()
            );
        }

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing Markdown title in: "
                            + path.toAbsolutePath()
            );
        }

        KnowledgeDocument document = new KnowledgeDocument();

        document.setDocumentId(documentId);
        document.setFilename(path.getFileName().toString());
        document.setTitle(title);
        document.setAudience(audience);
        document.setVersion(version);
        document.setContent(content);

        return document;
    }

    private String extractTitle(String content) {

        String[] lines = content.split("\\R");

        for (String line : lines) {

            String trimmed = line.trim();

            if (trimmed.startsWith("# ")) {
                return trimmed.substring(2).trim();
            }
        }

        return null;
    }

    private String extractMetadata(
            String content,
            String field
    ) {

        String[] lines = content.split("\\R");

        String prefix = field + ":";

        for (String line : lines) {

            String trimmed = line.trim();

            if (trimmed.startsWith(prefix)) {
                return trimmed
                        .substring(prefix.length())
                        .trim();
            }
        }

        return null;
    }

    private void seedEvalCases() throws Exception {

        if (evalCaseRepository.count() > 0) {
            System.out.println("Eval cases already exist. Skipping.");
            return;
        }

        List<EvalCase> evalCases = readJson(
                "data/eval_cases.json",
                new TypeReference<List<EvalCase>>() {}
        );

        evalCaseRepository.saveAll(evalCases);

        System.out.println(
                "Seeded " + evalCases.size() + " eval cases."
        );
    }

    private <T> List<T> readJson(
            String filePath,
            TypeReference<List<T>> typeReference
    ) throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "JSON file not found: "
                            + file.getAbsolutePath()
            );
        }

        System.out.println(
                "Reading JSON file: "
                        + file.getAbsolutePath()
        );

        return objectMapper.readValue(
                file,
                typeReference
        );
    }
}