# SonarCloud

## Configuracao

O projeto esta integrado ao [SonarCloud](https://sonarcloud.io) com:

| Propriedade | Valor |
|-------------|-------|
| Organization | `anisberto` |
| Project Key | `anisberto_desafio-iac` |
| Arquivo | `Desafio-IaC/sonar-project.properties` |

## Secret no GitHub

O token **nao fica no codigo**. Configure no repositorio:

1. GitHub → **Settings** → **Secrets and variables** → **Actions**
2. **New repository secret**
3. Name: `SONAR_TOKEN`
4. Value: token gerado em [SonarCloud > My Account > Security](https://sonarcloud.io/account/security)

Ou via CLI:

```bash
gh secret set SONAR_TOKEN --repo anisberto/desafio-iac
```

## Importar projeto no SonarCloud

Se ainda nao importou:

1. Acesse [sonarcloud.io](https://sonarcloud.io)
2. **+** → **Analyze new project**
3. Selecione `anisberto/desafio-iac`
4. Use a organization `anisberto` e project key `anisberto_desafio-iac`

## Pipelines

SonarCloud roda nos workflows:

- `.github/workflows/fase-01-ci.yml`
- `.github/workflows/fase-02-cd.yml` (job build-and-test)

Comando executado: `./mvnw verify sonar:sonar`
