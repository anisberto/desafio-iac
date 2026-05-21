#!/usr/bin/env bash
set -euo pipefail

ORG="${SONAR_ORGANIZATION:-anisberto}"
PROJECT_KEY="${SONAR_PROJECT_KEY:-anisberto_desafio-iac}"
PROJECT_NAME="${SONAR_PROJECT_NAME:-Desafio-IaC}"

if [ -z "${SONAR_TOKEN:-}" ]; then
  echo "ERROR: SONAR_TOKEN nao definido."
  exit 1
fi

sonar_api() {
  curl -sS -u "${SONAR_TOKEN}:" "$@"
}

echo "==> Verificando organizacao '${ORG}'..."
ORG_RESPONSE="$(sonar_api "https://sonarcloud.io/api/organizations/search?organizations=${ORG}")"
ORG_COUNT="$(echo "${ORG_RESPONSE}" | python3 -c "import sys,json; data=json.load(sys.stdin); print(len(data.get('organizations', [])))")"

if [ "${ORG_COUNT}" = "0" ]; then
  echo "ERROR: Organizacao '${ORG}' nao encontrada no SonarCloud."
  echo "Organizacoes disponiveis para este token:"
  sonar_api "https://sonarcloud.io/api/user/organizations/search?member=true" | python3 -c "
import sys, json
data = json.load(sys.stdin)
for org in data.get('organizations', []):
    print(f\"  - {org.get('key')} ({org.get('name')})\")
"
  exit 1
fi

echo "==> Verificando projeto '${PROJECT_KEY}'..."
SEARCH_RESPONSE="$(sonar_api "https://sonarcloud.io/api/projects/search?projects=${PROJECT_KEY}&organization=${ORG}")"
PROJECT_COUNT="$(echo "${SEARCH_RESPONSE}" | python3 -c "import sys,json; data=json.load(sys.stdin); print(len(data.get('components', [])))")"

if [ "${PROJECT_COUNT}" != "0" ]; then
  echo "Projeto '${PROJECT_KEY}' ja existe."
  exit 0
fi

echo "==> Criando projeto '${PROJECT_KEY}' na organizacao '${ORG}'..."
CREATE_RESPONSE="$(sonar_api -X POST "https://sonarcloud.io/api/projects/create?organization=${ORG}&project=${PROJECT_KEY}&name=${PROJECT_NAME}")"

if echo "${CREATE_RESPONSE}" | python3 -c "import sys,json; data=json.load(sys.stdin); sys.exit(0 if 'project' in data else 1)"; then
  echo "Projeto criado com sucesso."
else
  echo "ERROR: Falha ao criar projeto."
  echo "${CREATE_RESPONSE}"
  exit 1
fi
