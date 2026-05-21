# Fase 2: Entrega Contínua, Monitoramento e Segurança

> Pasta exclusiva da **Fase 2**. Não misture arquivos de outras fases aqui.

## Conteúdo desta pasta

```
FASE-02/
├── README.md                       # Este arquivo
├── PLANEJAMENTO.md                 # Planejamento da Fase 2
├── RELATORIO-FINAL.md              # Relatório final do projeto (base para PDF)
├── docs/
│   └── FLUXOGRAMA-DEVOPS.md        # Fluxograma do pipeline completo
├── docker/
│   └── Dockerfile                  # Containerização da aplicação
├── docker-compose.yml              # Orquestração local (app + monitoramento)
├── kubernetes/                     # Manifests K8s para deploy
│   ├── namespace.yaml
│   ├── configmap.yaml
│   ├── deployment.yaml
│   ├── service.yaml
│   └── ingress.yaml
├── monitoring/
│   ├── prometheus/
│   │   └── prometheus.yml
│   └── grafana/
│       └── datasources.yml
├── seguranca/
│   └── POLITICAS-SEGURANCA.md      # Políticas e práticas de segurança
└── scripts/
    └── deploy.sh                   # Script de deploy automatizado
```

## Pipeline CD

Workflow: [`.github/workflows/fase-02-cd.yml`](../.github/workflows/fase-02-cd.yml)

## Executar localmente com Docker

```bash
# Build da imagem
docker build -f FASE-02/docker/Dockerfile -t desafio-iac:latest .

# Subir stack completa (app + Prometheus + Grafana)
docker compose -f FASE-02/docker-compose.yml up -d
```

| Serviço   | URL                          |
|-----------|------------------------------|
| App       | http://localhost:8080        |
| Prometheus| http://localhost:9090        |
| Grafana   | http://localhost:3000        |

## Deploy em Kubernetes

```bash
kubectl apply -f FASE-02/kubernetes/
```

## Documentação

- [PLANEJAMENTO.md](PLANEJAMENTO.md) — escopo e plano da Fase 2
- [RELATORIO-FINAL.md](RELATORIO-FINAL.md) — relatório consolidado (exportar para PDF)
- [docs/FLUXOGRAMA-DEVOPS.md](docs/FLUXOGRAMA-DEVOPS.md) — fluxograma CI/CD completo
