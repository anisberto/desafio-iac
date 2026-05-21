# Fluxograma DevOps — Pipeline Completo

Diagrama integrado das Fases 1 e 2: do desenvolvimento ao deploy em produção.

## Visão Geral

```mermaid
flowchart TB
    subgraph DEV["Desenvolvimento"]
        A[Developer] --> B[Git Push / PR]
    end

    subgraph FASE1["Fase 01 — CI + IaC"]
        B --> C[fase-01-ci.yml]
        C --> D[Maven Build & Test]
        C --> E[Terraform Validate]
        D --> F{Testes OK?}
        F -->|Sim| G[Upload JAR]
        F -->|Não| X[Falha — Notifica Dev]
        E --> H{Terraform OK?}
        H -->|Sim| I[IaC Pronto]
        H -->|Não| X
    end

    subgraph FASE2["Fase 02 — CD + Containers"]
        G --> J[fase-02-cd.yml]
        J --> K[Docker Build]
        K --> L[Trivy Security Scan]
        L --> M{Scan OK?}
        M -->|Sim| N[Push GHCR]
        M -->|Não| X
        N --> O[Deploy K8s / Compose]
        O --> P[Health Check]
    end

    subgraph INFRA["Infraestrutura AWS"]
        I --> Q[Terraform Apply]
        Q --> R[VPC + ALB + EC2]
    end

    subgraph MON["Monitoramento"]
        P --> S[Prometheus]
        S --> T[Grafana Dashboards]
        P --> U[Actuator Metrics]
        U --> S
    end

    subgraph PROD["Produção"]
        P --> V[Aplicação Online]
        R --> V
        T --> W[Alertas]
    end
```

## Pipeline CI (Fase 1)

```mermaid
flowchart LR
    A[Push/PR] --> B[Checkout]
    B --> C[Setup Java 21]
    C --> D[mvn verify]
    D --> E[Upload Artifact]
    B --> F[Terraform fmt]
    F --> G[terraform validate]
```

## Pipeline CD (Fase 2)

```mermaid
flowchart LR
    A[Push main] --> B[Build & Test]
    B --> C[Docker Build]
    C --> D[Trivy Scan]
    D --> E[Push GHCR]
    E --> F[kubectl apply]
    F --> G[Health Check]
```

## Fluxo de Deploy Local

```mermaid
flowchart LR
    A[docker compose up] --> B[App :8080]
    A --> C[Prometheus :9090]
    A --> D[Grafana :3000]
    B --> C
    C --> D
```

## Legenda

| Símbolo | Significado |
|---------|-------------|
| Retângulo | Etapa do pipeline |
| Losango | Decisão / gate |
| Subgrafo | Fase ou ambiente |
