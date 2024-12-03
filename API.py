from fastapi import FastAPI, Request
from typing import Dict

app = FastAPI()

# Temporary storage (optional)
data_store = []

@app.post("/send_data")
async def receive_data(request: Request):
    data: Dict = await request.json()
    data_store.append(data)  # Save to in-memory store (for testing)
    print(f"Received data: {data}")  # Log to console
    return {"status": "success", "received_data": data}

@app.post("/clear_data")
async def receive_data():
    data_store.clear()
    return {"status": "success", "received_data": data_store}

@app.get("/get_data")
async def get_data():
    """Retrieve all stored data."""
    return {"all_data": data_store}

@app.get("/")
def read_root():
    return {"Hello": "World"}


# @app.get("/classes")
# def send_data(file_name: str, file_content: str):
#     return {"file_name": file_name, "file_content": file_content}