# Lauro IaC - Desafio DevOps

Projeto DevOps completo para a aplicação Spring Boot **Desafio-IaC**, organizado por fases.

## Estrutura do Repositório

```
lauro-iac/
├── Desafio-IaC/                        # Aplicação Java Spring Boot (compartilhada)
├── FASE-01/                            # Fase 1: CI + IaC
│   ├── PLANEJAMENTO.md
│   ├── README.md
│   └── terraform/                      # Scripts Terraform (AWS)
├── FASE-02/                            # Fase 2: CD + Containers + Monitoramento
│   ├── PLANEJAMENTO.md
│   ├── RELATORIO-FINAL.md
│   ├── README.md
│   ├── docker/
│   ├── docker-compose.yml
│   ├── kubernetes/
│   ├── monitoring/
│   ├── seguranca/
│   ├── scripts/
│   └── docs/
├── .github/workflows/
│   ├── fase-01-ci.yml                  # Pipeline CI
│   └── fase-02-cd.yml                  # Pipeline CD
└── README.md
```

## Fases

| Fase | Pasta | Conteúdo |
|------|-------|----------|
| **Fase 1** | [`FASE-01/`](FASE-01/) | CI, Terraform, testes automatizados |
| **Fase 2** | [`FASE-02/`](FASE-02/) | CD, Docker, K8s, monitoramento, segurança |

> Cada fase tem pasta própria. Não misture artefatos entre fases.

## Aplicação Spring Boot

```bash
cd Desafio-IaC
./mvnw spring-boot:run    # http://localhost:8080
./mvnw test               # rodar testes
```

| Endpoint | Descrição |
|----------|-----------|
| `GET /api/health` | Status da aplicação |
| `GET /api/info` | Informações da aplicação |
| `GET /actuator/health` | Health check |
| `GET /actuator/prometheus` | Métricas Prometheus |
| `GET /swagger-ui.html` | Documentação OpenAPI |

## Pipelines

| Workflow | Gatilho | Função |
|----------|---------|--------|
| `fase-01-ci.yml` | Push/PR → `main`, `develop` | Build, testes, validação Terraform |
| `fase-02-cd.yml` | Push → `main` | Build Docker, scan, push GHCR, deploy |

## Deploy rápido (Fase 2)

```bash
chmod +x FASE-02/scripts/deploy.sh
./FASE-02/scripts/deploy.sh compose     # Docker Compose local
./FASE-02/scripts/deploy.sh kubernetes  # Kubernetes
```

## Documentação

- [FASE-01/PLANEJAMENTO.md](FASE-01/PLANEJAMENTO.md) — CI + IaC
- [FASE-02/PLANEJAMENTO.md](FASE-02/PLANEJAMENTO.md) — CD + Containers
- [FASE-02/RELATORIO-FINAL.md](FASE-02/RELATORIO-FINAL.md) — Relatório final (exportar PDF)
- [FASE-02/docs/FLUXOGRAMA-DEVOPS.md](FASE-02/docs/FLUXOGRAMA-DEVOPS.md) — Fluxograma completo

## Repositório GitHub

Após publicar, atualize os links em `FASE-01/PLANEJAMENTO.md` e `FASE-02/RELATORIO-FINAL.md`.
