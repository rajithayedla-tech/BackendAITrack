# 📂 LLM File Assistant Project

## 📌 Overview
This project demonstrates how to integrate **Large Language Models (LLMs)** with structured tool interfaces for file system operations. It allows an LLM to read, list, write, and search files programmatically, making it useful for tasks like resume parsing and keyword extraction.

---

## 🎯 Learning Objectives
- Understand LLM function calling and tool use
- Implement structured tool interfaces
- Handle file I/O operations programmatically
- Parse and validate documents
- Integrate LLMs with custom tools

---

## 🛠️ Project Structure
```
llm-file-assistant/
│── fs_tools.py              # Core file system tools (read, list, write, search)
│── llm_file_assistant.py    # LLM integration with OpenAI function calling
│── requirements.txt          # Python dependencies
│── README.md                 # Project documentation
│── resumes/                  # Sample resume files (PDF, TXT, DOCX)
│   │── resume_john_doe.pdf
│   │── resume_jane_smith.docx
│   │── resume_alex.txt
│   │── ...
│── demo/                     # Demo video or scripts
│   │── demo.mp4

---

## 📂 Part A: Core File System Tools
Implemented in `fs_tools.py`:

- **read_file(filepath: str) → dict**
  - Reads `.pdf`, `.txt`, `.docx` files
  - Extracts text content and metadata
  - Handles errors gracefully

- **list_files(directory: str, extension: str = None) → list**
  - Lists files in a directory
  - Filters by extension
  - Returns metadata (name, size, modified date)

- **write_file(filepath: str, content: str) → dict**
  - Writes content to a file
  - Creates directories if needed
  - Returns success/failure status

- **search_in_file(filepath: str, keyword: str) → dict**
  - Searches for keywords in file content
  - Case-insensitive
  - Returns matches with context

---

## 🤖 Part B: LLM Integration
Implemented in `llm_file_assistant.py`:

- Connects tools with an LLM (OpenAI API)
- Supports natural language queries like:
  - “Read all resumes in the resumes folder”
  - “Find resumes mentioning Python experience”
  - “Create a summary file for resume_john_doe.pdf”

---

## 📦 Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/rajithayedla-tech/llm-file-assistant.git
   cd llm-file-assistant
