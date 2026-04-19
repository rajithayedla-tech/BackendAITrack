import os
import openai
from fs_tools import read_file, list_files, write_file, search_in_file

openai.api_key = os.getenv("OPENAI_API_KEY")

tools = {
    "read_file": read_file,
    "list_files": list_files,
    "write_file": write_file,
    "search_in_file": search_in_file
}

def llm_query(prompt: str):
    response = openai.ChatCompletion.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": prompt}],
        tools=[
            {
                "name": "read_file",
                "description": "Read resume files",
                "parameters": {"filepath": "string"}
            },
            {
                "name": "list_files",
                "description": "List files in directory",
                "parameters": {"directory": "string", "extension": "string"}
            },
            {
                "name": "write_file",
                "description": "Write content to file",
                "parameters": {"filepath": "string", "content": "string"}
            },
            {
                "name": "search_in_file",
                "description": "Search keyword in file",
                "parameters": {"filepath": "string", "keyword": "string"}
            }
        ]
    )
    return response
