# MetaFit - Planejamento Físico Inteligente

## Descrição da Proposta
O MetaFit é um aplicativo Android desenvolvido para auxiliar usuários na criação de um planejamento físico personalizado. Através da coleta de dados biométricos (altura, peso atual, peso desejado) e preferências de rotina (alimentação e estilo de treino), o aplicativo realiza o cálculo de déficit/superávit calórico e gera uma recomendação de treino focada no objetivo do usuário. Todo o histórico de consultas é persistido em um banco de dados relacional através de uma API REST própria.

## Tecnologias Utilizadas
* **Frontend Mobile:** Kotlin, Android Studio, Material Design (XML), Fragments, RecyclerView, CardView.
* **Comunicação de Rede:** Retrofit2, Gson.
* **Backend API:** Python, FastAPI, Pydantic.
* **Banco de Dados:** SQLite.
* **Documentação de API:** Swagger UI / OpenAPI.

## Instruções de Execução

### Rodando a API (Backend)
1. Certifique-se de ter o Python instalado.
2. Navegue até a pasta `backend` no terminal.
3. Instale as dependências: `pip install fastapi uvicorn pydantic`
4. Inicie o servidor: `python -m uvicorn main:app --reload`
5. A API estará rodando em `http://localhost:8000`.

### Rodando o Aplicativo Android
1. Abra o projeto no Android Studio.
2. Aguarde a sincronização do Gradle (que inclui as dependências do Retrofit).
3. **Atenção:** Se for rodar no emulador, o `BASE_URL` no `ApiService.kt` deve ser `http://10.0.2.2:8000/`. Se for rodar em dispositivo físico via USB, altere para o endereço IP local da máquina executando a API.
4. Clique em "Run" (Shift + F10).

## Prints/Screenshots do Aplicativo
*(Adicione aqui os links ou imagens das 5 telas do seu aplicativo rodando)*
* [Tela Inicial]
* [Tela de Questionário (Fragments)]
* [Tela de Resultado]
* [Tela de Histórico]

## Descrição da API
A API foi construída em FastAPI e realiza a persistência dos dados gerados pelo aplicativo.
* `POST /planos`: Recebe um objeto JSON contendo os dados do usuário (nome, peso, rotina, etc) e persiste no banco SQLite. Retorna o objeto salvo com seu respectivo ID.
* `GET /planos`: Retorna uma lista em formato JSON com todo o histórico de planos salvos no banco de dados, populando o RecyclerView do Android.

**Link da Documentação Swagger (Local):** `http://localhost:8000/docs`
