# app.py - Microservicio de Predicción con gráfico (FastAPI)
from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Optional
import random
import matplotlib.pyplot as plt
import io
import base64

app = FastAPI(title="Microservicio de Predicción Visual - Python")

# Modelo de datos que recibe desde Java
class ReservaHistorica(BaseModel):
    id_sala: Optional[int]
    id_articulo: Optional[int]
    duracion_horas: float

@app.post("/api/predict")
def predecir_demanda(historial: List[ReservaHistorica]):
    """
    Simula una predicción de demanda y genera un gráfico base64
    que Java puede mostrar directamente.
    """
    # 1️⃣ Lógica de simulación
    demanda_base = sum(r.duracion_horas for r in historial)
    prediccion_final = demanda_base * 1.5 + random.uniform(1, 5)

    # 2️⃣ Crear gráfico con Matplotlib
    plt.figure(figsize=(4,3))
    plt.bar(["Histórico", "Predicción"], [demanda_base, prediccion_final],
            color=["#4CAF50", "#2196F3"])
    plt.title("Demanda proyectada (horas)")
    plt.ylabel("Horas")
    plt.grid(axis="y", linestyle="--", alpha=0.7)

    # Guardar imagen en memoria
    buffer = io.BytesIO()
    plt.savefig(buffer, format="png", bbox_inches="tight")
    plt.close()

    # Convertir a base64
    grafico_b64 = base64.b64encode(buffer.getvalue()).decode("utf-8")

    # 3️⃣ Retornar respuesta completa
    return {
        "status": "SUCCESS",
        "demanda_total_historica": round(demanda_base, 2),
        "demanda_proyectada_horas": round(prediccion_final, 2),
        "grafico_base64": grafico_b64,
        "mensaje": "Predicción generada correctamente desde Python."
    }

@app.get("/")
def home():
    return {"message": "Servicio de Predicción Python activo en puerto 5000."}
