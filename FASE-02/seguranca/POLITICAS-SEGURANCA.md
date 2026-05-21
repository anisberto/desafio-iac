# Políticas de Segurança — Fase 2

## 1. Container

| Política | Implementação |
|----------|---------------|
| Usuário não-root | Dockerfile executa como `appuser` |
| Imagem mínima | Base `eclipse-temurin:21-jre-alpine` |
| Health check | Actuator `/actuator/health` |
| Scan de vulnerabilidades | Trivy no pipeline CD |

## 2. Pipeline

| Política | Implementação |
|----------|---------------|
| Secrets | GitHub Secrets (nunca no código) |
| Branch protection | Deploy CD apenas em `main` |
| Artefatos | Imagens versionadas no GHCR |
| Falha em scan | Pipeline interrompe se Trivy encontrar CRITICAL |

## 3. Kubernetes

| Política | Implementação |
|----------|---------------|
| runAsNonRoot | `securityContext` no Deployment |
| Resource limits | CPU/memória definidos |
| Probes | Liveness e readiness configurados |
| Namespace isolado | `desafio-iac` dedicado |

## 4. Rede

| Política | Implementação |
|----------|---------------|
| Security Groups (AWS) | Apenas portas 80/443/8080 expostas |
| K8s Service | ClusterIP interno + LB externo |
| SSH | Restrito via Security Group (Fase 1) |

## 5. Monitoramento

| Política | Implementação |
|----------|---------------|
| Métricas | Prometheus scrape sem autenticação (dev) |
| Grafana | Credenciais padrão apenas em ambiente local |
| Logs | stdout — não expor dados sensíveis |

## 6. Recomendações para Produção

- Rotacionar credenciais Grafana e usar OAuth
- Habilitar TLS no Ingress e ALB
- Usar AWS Secrets Manager / K8s External Secrets
- Implementar Network Policies no Kubernetes
- Habilitar audit logging no cluster
