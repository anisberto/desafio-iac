# Fase 1: Configuração Inicial e Automação

> Pasta exclusiva da **Fase 1**. Artefatos das demais fases ficam em `FASE-02/`, etc.

## Conteúdo desta pasta

```
FASE-01/
├── PLANEJAMENTO.md     # Documentação de planejamento (CI + IaC)
└── terraform/          # Scripts Terraform (AWS)
```

## Pipeline CI

Workflow: [`.github/workflows/fase-01-ci.yml`](../.github/workflows/fase-01-ci.yml)

## Infraestrutura

```bash
cd FASE-01/terraform
cp terraform.tfvars.example terraform.tfvars
terraform init
terraform plan
terraform apply
```

Consulte [PLANEJAMENTO.md](PLANEJAMENTO.md) para detalhes completos.
