import streamlit as st
from matching_agent import build_graph, AgentState, compare_candidates, generate_interview_questions

st.title("Agentic Profile Matching Demo")

# --- Initialize session state once ---
if "agent_state" not in st.session_state:
    st.session_state.agent_state = {
        "conversation": [],
        "job_requirements": {},
        "candidates": [],
        "report": ""
    }
    st.session_state.graph = build_graph()

# --- Multi-round screening with explainability ---
def multi_round_screening(state: AgentState):
    top10 = state["candidates"][:10]
    analyzed = []
    for c in top10:
        strengths = f"Strong in {c['reason']}"
        gaps = "Needs more leadership experience" if c["score"] < 0.8 else "Minor skill gaps"
        suggestion = c.get("suggestion", "No suggestion available")
        c["analysis"] = {"strengths": strengths, "gaps": gaps, "suggestion": suggestion}
        analyzed.append(c)

    recommendations = []
    for c in analyzed:
        decision = "Hire" if c["score"] >= 0.8 else "Borderline"
        recommendations.append(
            f"{c['id']} → {decision}\n"
            f"Strengths: {c['analysis']['strengths']}\n"
            f"Gaps: {c['analysis']['gaps']}\n"
            f"Suggestion: {c['analysis']['suggestion']}\n"
        )
    return "\n".join(recommendations)

# --- Input box ---
user_input = st.text_input("Enter job description or query:")

if st.button("Run Agent"):
    st.session_state.agent_state["conversation"].append({"role": "user", "content": user_input})
    st.session_state.agent_state = st.session_state.graph.invoke(st.session_state.agent_state)

    st.subheader("Agent Report")
    st.text_area("Report", st.session_state.agent_state["report"], height=200)

    st.subheader("Multi-Round Screening (Explainability)")
    screening_report = multi_round_screening(st.session_state.agent_state)
    st.text_area("Screening Results", screening_report, height=400)

# --- Interactive Buttons ---
st.subheader("Interactive Features")

if st.button("Compare Top 2 Candidates"):
    if "candidates" in st.session_state.agent_state and len(st.session_state.agent_state["candidates"]) >= 2:
        ids = [c["id"] for c in st.session_state.agent_state["candidates"][:2]]
        comparison = compare_candidates(ids)
        st.text_area("Comparison", comparison, height=200)
    else:
        st.warning("No candidates available yet. Please run the agent first.")

if st.button("Generate Interview Questions for Top Candidate"):
    if "candidates" in st.session_state.agent_state and len(st.session_state.agent_state["candidates"]) >= 1:
        top_id = st.session_state.agent_state["candidates"][0]["id"]
        questions = generate_interview_questions(top_id)
        st.text_area("Interview Questions", "\n".join(questions), height=200)
    else:
        st.warning("No candidates available yet. Please run the agent first.")
