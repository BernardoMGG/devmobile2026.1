from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import sqlite3
from fastapi import HTTPException

app = FastAPI(title="MetaFit API")

class FitnessPlan(BaseModel):
    id: int = None
    nome_completo: str 
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

@app.put("/planos/{nome}")
def atualizar_plano(nome: str, plano_atualizado: FitnessPlan):
    cursor.execute("""
        UPDATE planos SET 
        nome_completo = ?, altura = ?, peso_atual = ?, peso_desejado = ?, 
        rotina_alimentar = ?, perfil_treino = ?, dias_exercicio = ?, 
        resultado_dias = ?, calorias_meta = ?
        WHERE nome_completo = ?
    """, (
        plano_atualizado.nome_completo, 
        plano_atualizado.altura, 
        plano_atualizado.peso_atual, 
        plano_atualizado.peso_desejado, 
        plano_atualizado.rotina_alimentar, 
        plano_atualizado.perfil_treino, 
        plano_atualizado.dias_exercicio, 
        plano_atualizado.resultado_dias, 
        plano_atualizado.calorias_meta, 
        nome
    ))
    conn.commit()
    
    return {"mensagem": f"Plano de {nome} foi atualizado com sucesso!", "dados": plano_atualizado}

@app.delete("/planos/{nome}")
def deletar_plano(nome: str):
    conn = sqlite3.connect("metafit.db") 
    cursor = conn.cursor()
    
    cursor.execute("DELETE FROM planos WHERE nome_completo = ?", (nome,))
    conn.commit()
    
    conn.close()
    
    return {"mensagem": f"Plano de {nome} foi deletado com sucesso!"}