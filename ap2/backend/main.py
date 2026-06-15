from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import sqlite3

app = FastAPI(title="MetaFit API")

class FitnessPlan(BaseModel):
    id: int = None
    nome_completo: str # <-- NOVO CAMPO AQUI
    altura: float
    peso_atual: float
    peso_desejado: float
    rotina_alimentar: str
    perfil_treino: str
    dias_exercicio: int
    resultado_dias: int
    calorias_meta: float

def init_db():
    conn = sqlite3.connect("metafit.db")
    cursor = conn.cursor()
    cursor.execute("""
        CREATE TABLE IF NOT EXISTS planos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome_completo TEXT, altura REAL, peso_atual REAL, peso_desejado REAL,
            rotina_alimentar TEXT, perfil_treino TEXT, dias_exercicio INTEGER,
            resultado_dias INTEGER, calorias_meta REAL
        )
    """)
    conn.commit()
    conn.close()

init_db()

@app.post("/planos", response_model=FitnessPlan)
def save_plan(plan: FitnessPlan):
    conn = sqlite3.connect("metafit.db")
    cursor = conn.cursor()
    cursor.execute("""
        INSERT INTO planos (nome_completo, altura, peso_atual, peso_desejado, rotina_alimentar, perfil_treino, dias_exercicio, resultado_dias, calorias_meta)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """, (plan.nome_completo, plan.altura, plan.peso_atual, plan.peso_desejado, plan.rotina_alimentar, plan.perfil_treino, plan.dias_exercicio, plan.resultado_dias, plan.calorias_meta))
    plan.id = cursor.lastrowid
    conn.commit()
    conn.close()
    return plan

@app.get("/planos", response_model=List[FitnessPlan])
def get_plans():
    conn = sqlite3.connect("metafit.db")
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM planos")
    rows = cursor.fetchall()
    conn.close()
    return [FitnessPlan(id=r[0], nome_completo=r[1], altura=r[2], peso_atual=r[3], peso_desejado=r[4], 
                        rotina_alimentar=r[5], perfil_treino=r[6], dias_exercicio=r[7], 
                        resultado_dias=r[8], calorias_meta=r[9]) for r in rows]