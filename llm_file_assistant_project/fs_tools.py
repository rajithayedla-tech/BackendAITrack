import os
import datetime
import docx
import PyPDF2

def read_file(filepath: str) -> dict:
    try:
        ext = os.path.splitext(filepath)[1].lower()
        content = ""
        if ext == ".txt":
            with open(filepath, "r", encoding="utf-8") as f:
                content = f.read()
        elif ext == ".pdf":
            with open(filepath, "rb") as f:
                reader = PyPDF2.PdfReader(f)
                content = "\n".join([page.extract_text() for page in reader.pages if page.extract_text()])
        elif ext == ".docx":
            doc = docx.Document(filepath)
            content = "\n".join([para.text for para in doc.paragraphs])
        else:
            return {"error": f"Unsupported file type: {ext}"}
        
        metadata = {
            "filename": os.path.basename(filepath),
            "size": os.path.getsize(filepath),
            "modified": datetime.datetime.fromtimestamp(os.path.getmtime(filepath)).isoformat()
        }
        return {"content": content, "metadata": metadata}
    except Exception as e:
        return {"error": str(e)}

def list_files(directory: str, extension: str = None) -> list:
    try:
        files = []
        for fname in os.listdir(directory):
            if extension and not fname.lower().endswith(extension.lower()):
                continue
            fpath = os.path.join(directory, fname)
            if os.path.isfile(fpath):
                files.append({
                    "name": fname,
                    "size": os.path.getsize(fpath),
                    "modified": datetime.datetime.fromtimestamp(os.path.getmtime(fpath)).isoformat()
                })
        return files
    except Exception as e:
        return [{"error": str(e)}]

def write_file(filepath: str, content: str) -> dict:
    try:
        os.makedirs(os.path.dirname(filepath), exist_ok=True)
        with open(filepath, "w", encoding="utf-8") as f:
            f.write(content)
        return {"status": "success", "filepath": filepath}
    except Exception as e:
        return {"status": "failure", "error": str(e)}

def search_in_file(filepath: str, keyword: str) -> dict:
    try:
        data = read_file(filepath)
        if "error" in data:
            return data
        content = data["content"].lower()
        keyword = keyword.lower()
        matches = []
        for line in data["content"].splitlines():
            if keyword in line.lower():
                matches.append(line.strip())
        return {"matches": matches, "count": len(matches)}
    except Exception as e:
        return {"error": str(e)}
