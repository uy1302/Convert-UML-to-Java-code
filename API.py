from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

# Create the FastAPI app
app = FastAPI()

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/classes")
def send_data(file_name: str, file_content: str):
    return {"file_name": file_name, "file_content": file_content}