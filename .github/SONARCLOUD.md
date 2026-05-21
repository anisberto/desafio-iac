# SonarCloud

## Configuracao

| Propriedade | Valor |
|-------------|-------|
| Organization | `anisberto` |
| Project Key | `anisberto_desafio-iac` |
| Arquivo | `Desafio-IaC/sonar-project.properties` |

## Secret no GitHub

O token **nao fica no codigo**. Configure em:

**GitHub → Settings → Secrets and variables → Actions → SONAR_TOKEN**

Gere o token em [SonarCloud > My Account > Security](https://sonarcloud.io/account/security).

## Erro "Project not found"

Esse erro ocorre quando o projeto ainda nao existe no SonarCloud. O pipeline executa automaticamente o script:

`.github/scripts/ensure-sonar-project.sh`

Ele cria o projeto antes da analise Maven.

### Se a organization estiver errada

A organization no SonarCloud pode ser diferente do usuario GitHub. Se o script falhar, ele lista as organizations disponiveis no log do CI.

Ajuste nos workflows:

```yaml
env:
  SONAR_ORGANIZATION: sua-org-sonarcloud
  SONAR_PROJECT_KEY: anisberto_desafio-iac
```

Ou crie manualmente em [sonarcloud.io](https://sonarcloud.io):

1. **+** → **Analyze new project**
2. Selecione `anisberto/desafio-iac`
3. Confirme organization e project key

## Pipelines

- `.github/workflows/fase-01-ci.yml`
- `.github/workflows/fase-02-cd.yml`

Comando: `./mvnw verify sonar:sonar`
