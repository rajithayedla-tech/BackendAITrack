import { useEffect, useState } from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  NavLink,
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  LayoutDashboard,
  Ticket,
  Users,
  ShoppingCart,
  BookOpen,
  Wrench,
  ClipboardCheck,
  Search,
  RefreshCw,
  AlertTriangle,
  CheckCircle,
  Clock,
  Mail,
  MessageSquare,
  ChevronRight,
  ArrowLeft,
} from "lucide-react";

import {
  getTickets,
  getTicket,
  getTicketTriage,
  getTicketKnowledge,
  getTicketDraft,
  getCustomers,
} from "./api";

import "./App.css";


function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Sidebar />

        <main className="main-content">
          <Header />

          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/tickets" element={<Tickets />} />
            <Route path="/tickets/:ticketId" element={<TicketDetails />} />
            <Route path="/customers" element={<Customers />} />
            <Route path="/orders" element={<PlaceholderPage icon={<ShoppingCart size={48} />} title="Orders" description="Order management will appear here." />} />
            <Route path="/knowledge" element={<Knowledge />} />
            <Route path="/tool-actions" element={<PlaceholderPage icon={<Wrench size={48} />} title="Tool Actions" description="Tool actions will appear here." />} />
            <Route path="/evaluations" element={<PlaceholderPage icon={<ClipboardCheck size={48} />} title="Evaluations" description="Evaluation results will appear here." />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );
}


/* =========================================================
   SIDEBAR
   ========================================================= */

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="logo">
        <div className="logo-mark">
          T
        </div>

        <div>
          <div className="logo-title">TrustDesk</div>
          <div className="logo-subtitle">AI Support Console</div>
        </div>
      </div>

      <nav className="sidebar-nav">
        <NavItem
          icon={<LayoutDashboard size={18} />}
          label="Dashboard"
          path="/"
        />

        <NavItem
          icon={<Ticket size={18} />}
          label="Tickets"
          path="/tickets"
        />

        <NavItem
          icon={<Users size={18} />}
          label="Customers"
          path="/customers"
        />

        <NavItem
          icon={<ShoppingCart size={18} />}
          label="Orders"
          path="/orders"
        />

        <NavItem
          icon={<BookOpen size={18} />}
          label="Knowledge"
          path="/knowledge"
        />

        <NavItem
          icon={<Wrench size={18} />}
          label="Tool Actions"
          path="/tool-actions"
        />

        <NavItem
          icon={<ClipboardCheck size={18} />}
          label="Evaluations"
          path="/evaluations"
        />
      </nav>

      <div className="sidebar-footer">
        <div className="system-status">
          <span className="status-dot"></span>
          <span>System Operational</span>
        </div>
      </div>
    </aside>
  );
}


function NavItem({ icon, label, path }) {
  return (
    <NavLink
      to={path}
      className={({ isActive }) =>
        `nav-item ${isActive ? "active" : ""}`
      }
    >
      {icon}
      <span>{label}</span>
    </NavLink>
  );
}


/* =========================================================
   HEADER
   ========================================================= */

function Header() {
  return (
    <header className="top-header">
      <div>
        <h1>TrustDesk</h1>
        <p>AI-powered customer support operations</p>
      </div>

      <div className="header-right">
        <div className="header-status">
          <span className="status-dot"></span>
          Backend Connected
        </div>
      </div>
    </header>
  );
}


/* =========================================================
   DASHBOARD
   ========================================================= */

function Dashboard() {
  const navigate = useNavigate();

  return (
    <div className="dashboard">

      <div className="page-header">
        <div>
          <h2>Support Dashboard</h2>
          <p>Monitor tickets and AI-assisted support decisions.</p>
        </div>

        <button
          className="primary-button"
          onClick={() => navigate("/tickets")}
        >
          <Ticket size={17} />
          View Tickets
        </button>
      </div>


      <div className="stats-grid">

        <StatCard
          icon={<Ticket size={22} />}
          label="Open Tickets"
          value="8"
        />

        <StatCard
          icon={<AlertTriangle size={22} />}
          label="Escalations"
          value="4"
        />

        <StatCard
          icon={<CheckCircle size={22} />}
          label="AI Resolved"
          value="4"
        />

        <StatCard
          icon={<Clock size={22} />}
          label="Avg. Response"
          value="1.8m"
        />

      </div>


      <div className="dashboard-grid">

        <div className="dashboard-card">

          <div className="card-header">
            <div>
              <h3>Recent Ticket</h3>
              <p>Latest customer support activity</p>
            </div>

            <span className="status open">open</span>
          </div>

          <div className="ticket-preview">

            <div className="ticket-preview-top">
              <span className="ticket-id">
                TKT-9001
              </span>

              <span className="channel">
                <Mail size={15} />
                email
              </span>
            </div>

            <h3>Received damaged earbuds</h3>

            <p>
              Hi, my BlueBuds Air arrived with the left
              earbud cracked. The package was delivered
              on June 24. Can I get a replacement?
            </p>

            <div className="ticket-preview-meta">
              <span>Aisha Rao</span>
              <span>ord_5001</span>
              <span>₹8,999</span>
            </div>

            <button
              className="text-button"
              onClick={() => navigate("/tickets/tkt_9001")}
            >
              Open ticket
              <ChevronRight size={16} />
            </button>

          </div>

        </div>


        <div className="dashboard-card">

          <div className="card-header">
            <div>
              <h3>AI Analysis</h3>
              <p>Latest ticket intelligence</p>
            </div>
          </div>

          <div className="analysis-grid">

            <AnalysisItem
              label="Category"
              value="Shipping"
            />

            <AnalysisItem
              label="Priority"
              value="Medium"
            />

            <AnalysisItem
              label="Escalation"
              value="No"
            />

            <AnalysisItem
              label="Confidence"
              value="94%"
            />

          </div>

        </div>

      </div>

    </div>
  );
}


function StatCard({ icon, label, value }) {
  return (
    <div className="stat-card">
      <div className="stat-icon">
        {icon}
      </div>

      <div>
        <div className="stat-value">
          {value}
        </div>

        <div className="stat-label">
          {label}
        </div>
      </div>
    </div>
  );
}


function AnalysisItem({ label, value }) {
  return (
    <div className="analysis-item">
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}


/* =========================================================
   TICKETS
   ========================================================= */

function Tickets() {
  const navigate = useNavigate();

  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadTickets() {
    try {
      setLoading(true);
      setError("");

      const data = await getTickets();

      setTickets(data);
    } catch (err) {
      console.error("Failed to load tickets:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadTickets();
  }, []);

  if (loading) {
    return (
      <div className="page-placeholder">
        <RefreshCw
          size={36}
          className="placeholder-icon"
        />

        <h2>Loading tickets...</h2>

        <p>
          Loading tickets from the TrustDesk backend.
        </p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-placeholder">

        <AlertTriangle
          size={42}
          className="placeholder-icon"
        />

        <h2>Unable to load tickets</h2>

        <p>{error}</p>

        <button
          className="primary-button"
          onClick={loadTickets}
        >
          <RefreshCw size={16} />
          Retry
        </button>

      </div>
    );
  }

  return (
    <div className="tickets-page">

      <div className="page-header">

        <div>
          <h2>Tickets</h2>

          <p>
            {tickets.length} customer support tickets
          </p>
        </div>

        <button
          className="secondary-button"
          onClick={loadTickets}
        >
          <RefreshCw size={16} />
          Refresh
        </button>

      </div>


      <div className="tickets-list">

        {tickets.map((ticket) => (

          <div
            key={ticket.ticketId}
            className="ticket-card"
            onClick={() =>
              navigate(`/tickets/${ticket.ticketId}`)
            }
          >

            <div className="ticket-card-header">

              <div>

                <div className="ticket-id">
                  {ticket.ticketId}
                </div>

                <h3>
                  {ticket.subject}
                </h3>

              </div>

              <span
                className={`status ${ticket.status}`}
              >
                {ticket.status}
              </span>

            </div>


            <p className="ticket-body">
              {ticket.body}
            </p>


            <div className="ticket-meta">

              <span>
                Customer: {ticket.customerId}
              </span>

              <span>
                Order: {ticket.orderId || "N/A"}
              </span>

              <span>
                Channel: {ticket.channel}
              </span>

              <span>
                {formatDate(ticket.createdAt)}
              </span>

            </div>


            <div className="ticket-card-footer">

              <div className="expected-tags">

                <span className="category-tag">
                  {ticket.expectedCategory}
                </span>

                <span className="priority-tag">
                  {ticket.expectedPriority}
                </span>

              </div>

              <ChevronRight size={18} />

            </div>

          </div>

        ))}

      </div>

    </div>
  );
}

/* =========================================================
   CUSTOMERS
   ========================================================= */

function Customers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  async function loadCustomers() {
    try {
      setLoading(true);
      setError("");

      const data = await getCustomers();

      setCustomers(data);
    } catch (err) {
      console.error("Failed to load customers:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadCustomers();
  }, []);

  if (loading) {
    return (
      <div className="page-placeholder">
        <RefreshCw
          size={36}
          className="placeholder-icon"
        />

        <h2>Loading customers...</h2>

        <p>
          Loading customers from the TrustDesk backend.
        </p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page-placeholder">

        <AlertTriangle
          size={42}
          className="placeholder-icon"
        />

        <h2>Unable to load customers</h2>

        <p>{error}</p>

        <button
          className="primary-button"
          onClick={loadCustomers}
        >
          <RefreshCw size={16} />
          Retry
        </button>

      </div>
    );
  }

  return (
    <div className="customers-page">

      <div className="page-header">

        <div>
          <h2>Customers</h2>

          <p>
            {customers.length} customers in TrustDesk
          </p>
        </div>

        <button
          className="secondary-button"
          onClick={loadCustomers}
        >
          <RefreshCw size={16} />
          Refresh
        </button>

      </div>


      <div className="customers-grid">

        {customers.map((customer) => (

          <div
            key={customer.customerId}
            className="customer-card"
          >

            <div className="customer-card-header">

              <div className="customer-avatar">
                {customer.name
                  ? customer.name.charAt(0).toUpperCase()
                  : "?"}
              </div>

              <div className="customer-title">

                <h3>
                  {customer.name}
                </h3>

                <span className="customer-id">
                  {customer.customerId}
                </span>

              </div>

              {customer.verified && (
                <CheckCircle
                  size={19}
                  className="verified-icon"
                />
              )}

            </div>


            <div className="customer-details">

              <div className="customer-detail">

                <Mail size={16} />

                <span>
                  {customer.email}
                </span>

              </div>


              <div className="customer-detail">

                <span className="detail-label">
                  Tier
                </span>

                <span className="category-tag">
                  {customer.tier}
                </span>

              </div>


              <div className="customer-detail">

                <span className="detail-label">
                  Country
                </span>

                <span>
                  {customer.country}
                </span>

              </div>


              <div className="customer-detail">

                <span className="detail-label">
                  Verified
                </span>

                <span>
                  {customer.verified ? "Yes" : "No"}
                </span>

              </div>

            </div>


            {customer.tags &&
              customer.tags.length > 0 && (

                <div className="customer-tags">

                  {customer.tags.map((tag) => (

                    <span
                      key={tag}
                      className="priority-tag"
                    >
                      {tag}
                    </span>

                  ))}

                </div>

              )}

          </div>

        ))}

      </div>

    </div>
  );
}


/* =========================================================
   TICKET DETAILS
   ========================================================= */

function TicketDetails() {
  const { ticketId } = useParams();
  const navigate = useNavigate();

  const [ticket, setTicket] = useState(null);
  const [triage, setTriage] = useState(null);
  const [knowledge, setKnowledge] = useState([]);
  const [draft, setDraft] = useState(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadTicketDetails() {
      try {
        setLoading(true);
        setError("");

        const [
          ticketData,
          triageData,
          knowledgeData,
          draftData,
        ] = await Promise.all([
          getTicket(ticketId),
          getTicketTriage(ticketId),
          getTicketKnowledge(ticketId),
          getTicketDraft(ticketId),
        ]);

        setTicket(ticketData);
        setTriage(triageData);
        setKnowledge(knowledgeData);
        setDraft(draftData);

      } catch (err) {
        console.error(
          "Failed to load ticket details:",
          err
        );

        setError(err.message);

      } finally {
        setLoading(false);
      }
    }

    loadTicketDetails();
  }, [ticketId]);


  if (loading) {
    return (
      <div className="page-placeholder">

        <RefreshCw
          size={36}
          className="placeholder-icon"
        />

        <h2>Loading ticket...</h2>

        <p>
          Loading ticket details and AI analysis.
        </p>

      </div>
    );
  }


  if (error) {
    return (
      <div className="page-placeholder">

        <AlertTriangle
          size={42}
          className="placeholder-icon"
        />

        <h2>Unable to load ticket</h2>

        <p>{error}</p>

        <button
          className="secondary-button"
          onClick={() => navigate("/tickets")}
        >
          <ArrowLeft size={16} />
          Back to Tickets
        </button>

      </div>
    );
  }


  return (
    <div className="ticket-details-page">

      <button
        className="back-button"
        onClick={() => navigate("/tickets")}
      >
        <ArrowLeft size={17} />
        Back to Tickets
      </button>


      <div className="page-header">

        <div>

          <div className="ticket-id">
            {ticket.ticketId}
          </div>

          <h2>{ticket.subject}</h2>

          <p>
            {ticket.customerId}
            {" • "}
            {ticket.channel}
            {" • "}
            {formatDate(ticket.createdAt)}
          </p>

        </div>

        <span className={`status ${ticket.status}`}>
          {ticket.status}
        </span>

      </div>


      {/* CUSTOMER MESSAGE */}

      <div className="detail-card">

        <div className="card-header">
          <div>
            <h3>Customer Message</h3>
          </div>
        </div>

        <p className="customer-message">
          {ticket.body}
        </p>

        <div className="detail-meta">

          <span>
            Customer: {ticket.customerId}
          </span>

          <span>
            Order: {ticket.orderId || "N/A"}
          </span>

        </div>

      </div>


      {/* AI TRIAGE */}

      <div className="detail-card">

        <div className="card-header">

          <div>
            <h3>AI Triage</h3>
            <p>Generated by TrustDesk AI</p>
          </div>

          {triage?.escalationRequired && (
            <span className="warning-badge">
              <AlertTriangle size={15} />
              Human Escalation
            </span>
          )}

        </div>


        {triage && (

          <div className="analysis-grid">

            <AnalysisItem
              label="Category"
              value={triage.category}
            />

            <AnalysisItem
              label="Priority"
              value={triage.priority}
            />

            <AnalysisItem
              label="Escalation"
              value={
                triage.escalationRequired
                  ? "Required"
                  : "Not Required"
              }
            />

          </div>

        )}


        {triage?.reason && (
          <div className="reason-box">
            <strong>Reason</strong>
            <p>{triage.reason}</p>
          </div>
        )}

      </div>


      {/* KNOWLEDGE */}

      <div className="detail-card">

        <div className="card-header">

          <div>
            <h3>Retrieved Knowledge</h3>
            <p>
              Relevant knowledge retrieved from Qdrant
            </p>
          </div>

        </div>


        <div className="knowledge-list">

          {knowledge.length === 0 ? (

            <p>No relevant knowledge found.</p>

          ) : (

            knowledge.map((item) => {

              const isAdversarial =
                item.documentId ===
                "KB-ADVERSARIAL-001";

              return (

                <div
                  key={item.documentId}
                  className={`knowledge-item ${
                    isAdversarial
                      ? "knowledge-warning"
                      : ""
                  }`}
                >

                  <div className="knowledge-item-header">

                    <div>

                      <strong>
                        {item.title}
                      </strong>

                      <div className="knowledge-id">
                        {item.documentId}
                      </div>

                    </div>

                    <span className="score">
                      {(item.score * 100).toFixed(1)}%
                    </span>

                  </div>


                  {isAdversarial && (

                    <div className="adversarial-warning">

                      <AlertTriangle size={17} />

                      <span>
                        This document contains
                        untrusted instructions and
                        must not override TrustDesk
                        policies.
                      </span>

                    </div>

                  )}


                  <p>
                    {item.snippet}
                  </p>

                </div>

              );
            })

          )}

        </div>

      </div>


      {/* AI DRAFT */}

      <div className="detail-card">

        <div className="card-header">

          <div>
            <h3>AI Response Draft</h3>
            <p>
              Suggested response generated by TrustDesk
            </p>
          </div>

        </div>


        {draft?.draft && (

          <div className="draft-box">

            <pre>
              {cleanDraft(draft.draft)}
            </pre>

          </div>

        )}

      </div>

    </div>
  );
}


/* =========================================================
   KNOWLEDGE
   ========================================================= */

function Knowledge() {
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadKnowledge() {
      try {
        setLoading(true);

        const response = await fetch(
          "http://localhost:8080/api/knowledge"
        );

        if (!response.ok) {
          throw new Error(
            `Failed to load knowledge: ${response.status}`
          );
        }

        const data = await response.json();

        setDocuments(data);

      } catch (err) {
        setError(err.message);

      } finally {
        setLoading(false);
      }
    }

    loadKnowledge();
  }, []);


  if (loading) {
    return (
      <div className="page-placeholder">
        <RefreshCw size={36} />
        <h2>Loading knowledge...</h2>
      </div>
    );
  }


  if (error) {
    return (
      <div className="page-placeholder">
        <AlertTriangle size={42} />
        <h2>Unable to load knowledge</h2>
        <p>{error}</p>
      </div>
    );
  }


  return (
    <div className="knowledge-page">

      <div className="page-header">

        <div>
          <h2>Knowledge Base</h2>

          <p>
            {documents.length} knowledge documents
          </p>
        </div>

      </div>


      <div className="knowledge-list">

        {documents.map((document) => (

          <div
            key={document.documentId}
            className="detail-card"
          >

            <div className="card-header">

              <div>

                <h3>
                  {document.title}
                </h3>

                <div className="knowledge-id">
                  {document.documentId}
                </div>

              </div>

              <BookOpen size={20} />

            </div>


            <p>
              {document.content}
            </p>

          </div>

        ))}

      </div>

    </div>
  );
}


/* =========================================================
   PLACEHOLDER
   ========================================================= */

function PlaceholderPage({
  icon,
  title,
  description,
}) {
  return (
    <div className="page-placeholder">

      <div className="placeholder-icon">
        {icon}
      </div>

      <h2>{title}</h2>

      <p>{description}</p>

    </div>
  );
}


/* =========================================================
   HELPERS
   ========================================================= */

function formatDate(value) {
  if (!value) {
    return "";
  }

  try {
    return new Date(value).toLocaleString();
  } catch {
    return value;
  }
}


function cleanDraft(value) {
  if (!value) {
    return "";
  }

  return value
    .replace(/\\n/g, "\n")
    .replace(/\\"/g, '"')
    .replace(/\\u2019/g, "’");
}


export default App;