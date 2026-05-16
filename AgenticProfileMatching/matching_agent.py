# matching_agent.py
# LangGraph-based agent for candidate-job matching

from typing import TypedDict, List, Dict, Any
from langgraph.graph import StateGraph, START, END

# -----------------------------
# Agent State Definition
# -----------------------------
class AgentState(TypedDict):
    conversation: List[Dict[str, Any]]
    job_requirements: Dict[str, List[str]]
    candidates: List[Dict[str, Any]]
    report: str


# -----------------------------
# Tool Stubs
# -----------------------------
def extract_requirements(jd: str) -> Dict[str, List[str]]:
    """Parse JD into must-have and nice-to-have requirements."""
    # TODO: Implement NLP parsing logic
    return {"must_have": ["Python", "React"], "nice_to_have": ["AWS", "Docker"]}


def compare_candidates(candidate_ids: List[str]) -> str:
    """Compare candidates head-to-head."""
    # TODO: Implement comparison logic
    return f"Comparison report for {candidate_ids}"


def generate_interview_questions(candidate_id: str) -> List[str]:
    """Generate screening questions for a candidate."""
    # TODO: Implement question generation
    return [f"Tell me about your experience with React, Candidate {candidate_id}."]


# -----------------------------
# Workflow Node Functions
# -----------------------------
def parse_jd(state: AgentState) -> dict:
    jd_text = state["conversation"][-1]["content"]
    job_requirements = extract_requirements(jd_text)
    return {"job_requirements": job_requirements}


def search_resumes(state: AgentState) -> dict:
    candidates = [
        {"id": "C1", "score": 0.85, "reason": "Strong React + 3 years exp"},
        {"id": "C2", "score": 0.78, "reason": "Good Python, less React"}
    ]
    return {"candidates": candidates}


def rank_candidates(state: AgentState) -> dict:
    ranked = sorted(state["candidates"], key=lambda x: x["score"], reverse=True)
    return {"candidates": ranked}


def generate_report(state: AgentState) -> dict:
    report_lines = [
        f"Candidate {c['id']} - Score: {c['score']} - Reason: {c['reason']}"
        for c in state["candidates"]
    ]
    return {"report": "\n".join(report_lines)}


def human_feedback(state: AgentState) -> dict:
    # For now, no changes
    return {}


# -----------------------------
# Graph Construction
# -----------------------------
def build_graph():
    graph = StateGraph(AgentState)

    graph.add_node("parse_jd", parse_jd)
    graph.add_node("search_resumes", search_resumes)
    graph.add_node("rank_candidates", rank_candidates)
    graph.add_node("generate_report", generate_report)
    graph.add_node("human_feedback", human_feedback)

    # Entry point
    graph.add_edge(START, "parse_jd")

    graph.add_edge("parse_jd", "search_resumes")
    graph.add_edge("search_resumes", "rank_candidates")
    graph.add_edge("rank_candidates", "generate_report")
    graph.add_edge("generate_report", "human_feedback")
    graph.add_edge("human_feedback", END)

    return graph.compile()


# -----------------------------
# CLI Chat Interface
# -----------------------------
def run_cli():
    state: AgentState = {
        "conversation": [],
        "job_requirements": {},
        "candidates": [],
        "report": ""
    }
    graph = build_graph()

    while True:
        user_input = input("You: ")
        if user_input.lower() in ["exit", "quit"]:
            break
        state["conversation"].append({"role": "user", "content": user_input})
        state = graph.invoke(state)
        print("Agent Report:\n", state["report"])


if __name__ == "__main__":
    run_cli()
