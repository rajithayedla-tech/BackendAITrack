const API_BASE_URL = "http://localhost:8080/api";

export async function getTickets() {
  const response = await fetch(`${API_BASE_URL}/tickets`);

  if (!response.ok) {
    throw new Error(
      `Failed to load tickets: ${response.status}`
    );
  }

  return response.json();
}

export async function getTicket(ticketId) {
  const response = await fetch(
    `${API_BASE_URL}/tickets/${ticketId}`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to load ticket: ${response.status}`
    );
  }

  return response.json();
}

export async function getTicketTriage(ticketId) {
  const response = await fetch(
    `${API_BASE_URL}/tickets/${ticketId}/triage`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to load triage: ${response.status}`
    );
  }

  return response.json();
}

export async function getTicketKnowledge(ticketId) {
  const response = await fetch(
    `${API_BASE_URL}/tickets/${ticketId}/knowledge`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to load knowledge: ${response.status}`
    );
  }

  return response.json();
}

export async function getTicketDraft(ticketId) {
  const response = await fetch(
    `${API_BASE_URL}/tickets/${ticketId}/draft`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to load draft: ${response.status}`
    );
  }

  return response.json();
}

export async function getCustomers() {
  const response = await fetch(`${API_BASE_URL}/customers`);

  if (!response.ok) {
    throw new Error(
      `Failed to load customers: ${response.status}`
    );
  }

  return response.json();
}

export async function getCustomer(customerId) {
  const response = await fetch(
    `${API_BASE_URL}/customers/${customerId}`
  );

  if (!response.ok) {
    throw new Error(
      `Failed to load customer: ${response.status}`
    );
  }

  return response.json();
}