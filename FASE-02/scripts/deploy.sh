#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FASE02_DIR="$(dirname "$SCRIPT_DIR")"
ROOT_DIR="$(dirname "$FASE02_DIR")"

MODE="${1:-compose}"
IMAGE_TAG="${IMAGE_TAG:-latest}"
IMAGE_NAME="${IMAGE_NAME:-desafio-iac}"

echo "==> Deploy Desafio-IaC (modo: $MODE)"

build_image() {
  echo "==> Building Docker image..."
  docker build \
    -f "$FASE02_DIR/docker/Dockerfile" \
    -t "$IMAGE_NAME:$IMAGE_TAG" \
    "$ROOT_DIR"
}

deploy_compose() {
  build_image
  echo "==> Starting Docker Compose stack..."
  docker compose -f "$FASE02_DIR/docker-compose.yml" up -d
  echo "==> App:       http://localhost:8080"
  echo "==> Prometheus: http://localhost:9090"
  echo "==> Grafana:   http://localhost:3000"
}

deploy_kubernetes() {
  echo "==> Applying Kubernetes manifests..."
  kubectl apply -f "$FASE02_DIR/kubernetes/"
  kubectl rollout status deployment/desafio-iac -n desafio-iac --timeout=300s
  echo "==> Deploy concluído no namespace desafio-iac"
}

case "$MODE" in
  compose)
    deploy_compose
    ;;
  kubernetes|k8s)
    deploy_kubernetes
    ;;
  build)
    build_image
    ;;
  *)
    echo "Uso: $0 [compose|kubernetes|build]"
    exit 1
    ;;
esac
