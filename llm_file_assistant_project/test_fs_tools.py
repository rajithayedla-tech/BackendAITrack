# test_fs_tools.py
"""
Quick CLI tester for fs_tools.py
Run: python test_fs_tools.py
"""

import os
from fs_tools import read_file, list_files, write_file, search_in_file

def demo_list_files():
    print("📂 Listing all files in 'resumes/'...")
    files = list_files("resumes")
    for f in files:
        print(f)

def demo_read_file():
    print("\n📖 Reading 'java-developer-resume-example.pdf'...")
    result = read_file("resumes/java-developer-resume-example.pdf")
    print(result)

def demo_search_file():
    print("\n🔍 Searching for keyword 'Python' in 'python-developer-resume.pdf'...")
    matches = search_in_file("resumes/python-developer-resume.pdf", "Python")
    print(matches)

def demo_search_all_files():
    print("\n🔎 Searching for keyword 'Python' in ALL files under 'resumes/'...")
    files = list_files("resumes")
    results = {}
    for f in files:
        filepath = os.path.join("resumes", f["name"])
        matches = search_in_file(filepath, "Python")
        results[f["name"]] = matches
    # Print results
    for fname, data in results.items():
        print(f"\nFile: {fname}")
        print(data)
    # Save results to summary file
    summary_content = "\n".join(
        [f"{fname}: {data}" for fname, data in results.items()]
    )
    write_file("output/python_search_results.txt", summary_content)
    print("\n✅ Results written to 'output/python_search_results.txt'")

def demo_write_file():
    print("\n✍️ Writing summary to 'output/summary.txt'...")
    status = write_file("output/summary.txt", "This is a test summary for demo purposes.")
    print(status)

if __name__ == "__main__":
    demo_list_files()
    demo_read_file()
    demo_search_file()
    demo_search_all_files()
    demo_write_file()
