# matching_agent.py

from typing import Dict, List, Any

AgentState = Dict[str, Any]

# --- Build Graph (stub for LangGraph integration) ---
def build_graph():
    class DummyGraph:
        def invoke(self, state: AgentState) -> AgentState:
            jd_text = state["conversation"][-1]["content"]
            state["job_requirements"] = extract_requirements(jd_text)

            # Example candidate pool (normally from DB or resumes)
            candidate_pool = [
                {"id": "C1", "skills": ["React", "Python"], "experience": 3},
                {"id": "C2", "skills": ["React"], "experience": 2},
                {"id": "C3", "skills": ["Python", "AWS"], "experience": 1},
            ]

            # Score candidates dynamically
            scored = []
            for c in candidate_pool:
                score, reason, suggestion = score_candidate(c, state["job_requirements"])
                c["score"] = score
                c["reason"] = reason
                c["suggestion"] = suggestion
                scored.append(c)

            # Sort by score
            state["candidates"] = sorted(scored, key=lambda x: x["score"], reverse=True)
            state["report"] = f"Parsed JD: {state['job_requirements']}\nTop candidates ranked."
            return state
    return DummyGraph()

# --- Requirement Extraction ---
def extract_requirements(jd: str) -> Dict[str, List[str]]:
    must_have = []
    nice_to_have = []
    jd_lower = jd.lower()
    if "react" in jd_lower:
        must_have.append("React")
    if "docker" in jd_lower:
        must_have.append("Docker")
    if "aws" in jd_lower:
        nice_to_have.append("AWS")
    if "python" in jd_lower:
        must_have.append("Python")
    return {"must_have": must_have, "nice_to_have": nice_to_have}

# --- Candidate Scoring Function with Suggestions ---
def score_candidate(candidate: Dict[str, Any], requirements: Dict[str, List[str]]) -> (float, str, str):
    score = 0.0
    reasons = []
    suggestion = "Candidate meets most requirements."

    # Must-have skills
    for skill in requirements["must_have"]:
        if skill in candidate["skills"]:
            score += 0.4
            reasons.append(f"Has must-have skill: {skill}")
        else:
            reasons.append(f"Missing must-have skill: {skill}")
            suggestion = f"Should learn {skill} to strengthen profile."

    # Nice-to-have skills
    for skill in requirements["nice_to_have"]:
        if skill in candidate["skills"]:
            score += 0.2
            reasons.append(f"Has nice-to-have skill: {skill}")
        else:
            suggestion = f"Consider gaining {skill} experience."

    # Experience weighting
    if candidate.get("experience", 0) >= 3:
        score += 0.2
        reasons.append("Has 3+ years experience")
    elif candidate.get("experience", 0) >= 2:
        score += 0.1
        reasons.append("Has 2 years experience")
        suggestion = "Could improve by taking on more complex projects."
    else:
        reasons.append("Limited experience")
        suggestion = "Needs more hands-on project work to build experience."

    return min(score, 1.0), "; ".join(reasons), suggestion

# --- Candidate Comparison ---
def compare_candidates(candidate_ids: List[str]) -> str:
    comparison_report = []
    for cid in candidate_ids:
        if cid == "C1":
            comparison_report.append("C1: Strong React + Python, 3 years exp. Suggestion: Ready for hire.")
        elif cid == "C2":
            comparison_report.append("C2: Solid React, 2 years exp. Suggestion: Learn Docker to improve profile.")
        elif cid == "C3":
            comparison_report.append("C3: Python + AWS, only 1 year exp. Suggestion: Gain more React experience.")
        else:
            comparison_report.append(f"{cid}: No detailed profile available.")
    return "\n".join(comparison_report)

# --- Interview Question Generation ---
def generate_interview_questions(candidate_id: str) -> List[str]:
    if candidate_id == "C1":
        return [
            "Describe a complex React project you worked on.",
            "How do you optimize React components for performance?",
            "What’s your experience with Python in backend systems?",
        ]
    elif candidate_id == "C2":
        return [
            "How have you applied React in production projects?",
            "What strategies do you use for debugging React applications?",
            "How would you approach learning Docker quickly?",
        ]
    elif candidate_id == "C3":
        return [
            "Tell me about your exposure to Python and AWS.",
            "How do you handle limited experience when tackling new challenges?",
            "What motivates you to improve your technical skills?",
        ]
    else:
        return [
            "Tell me about your technical background.",
            "What motivates you to learn new skills?",
            "How do you approach problem-solving in projects?",
        ]
