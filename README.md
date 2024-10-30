# Projeto Medição Automática Qualidade Água e Ar

Aplicação API em Java usando MySql

## Pré-requisitos

- Java 19
- Git
- Docker

## Testes Automatizados

Para executar os testes automatizados primeiro é preciso subir a aplicação e o banco de dados em modo de testes (sem autenticação)

Executar o comando abaixo:
```sh
docker compose -f docker-compose-testes.yaml up --build
```

Obs.: Precisa aguardar o MySql terminar de iniciar, enquanto isso a API vai ficar reiniciando. Isso pode demorar até 1 minuto.

Depois da aplicação estar rodando normalmente é preciso executar os testes automatizados

Primeiro é necessário acessar o caminho:
```
src/test/java/br.com.fiap.ProjetoMedicaoAutomatica/runner/TestRunner
```

Depois é necessário clicar com o botão direito e selecionar 
```
Run 'TestRunner'
```
Exemplo:

![](/assets/images/testes-automatizados.png)

Após os testes serem executados é possível ver o relatório

Para isso é preciso acessar o caminho:
```
target/cucumber-reports.html
```

Para ver o relatório no navegador clique com o botão direito em cucumber-reports.html e clique em Open in > Browser > Chrome

Exemplo de como abrir o relatório:

![](/assets/images/ver-relatorio.png)

Assim é possível ver o relatório como no exemplo abaixo:

![](/assets/images/relatorio-testes.png)

## Build e execução

Para executar a aplicação normalmente é preciso subir a aplicação em modo padrão (com autenticação)

Executar o comando abaixo:

```sh
docker compose up --build
```

Obs.: Precisa aguardar o MySql terminar de iniciar, enquanto isso a API vai ficar reiniciando. Isso pode demorar até 1 minuto.

# Documentação online (OpenAPI)

http://localhost:8080/swagger-ui/index.html

![](/assets/images/apis.png)
