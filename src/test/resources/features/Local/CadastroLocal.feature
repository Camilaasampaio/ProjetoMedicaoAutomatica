# language: pt
@regressivo
Funcionalidade: Cadastro de novo local
  Como usuário da API
  Quero cadastrar um novo local
  Para que o registro seja salvo corretamente no sistema

  @padrão
  Cenário: Cadastro bem-sucedido de local
    Dado que eu tenha os seguintes dados do local:
      | campo    | valor                 |
      | nome     | Hospital Central      |
      | endereco | Avenida Central, 1055 |
      | cidade   | São Paulo             |
    Quando eu enviar a requisição para o endpoint "/api/local" de cadastro de local
    Então o status code da resposta da api de local deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Cadastro com erro de campo inválido no local
    Dado que eu tenha os seguintes dados do local:
      | campo  | valor            |
      | nome   | Hospital Central |
      | cidade | São Paulo        |
    Quando eu enviar a requisição para o endpoint "/api/local" de cadastro de local
    Então o status code da resposta da api de local deve ser 400
    E o corpo de resposta de erro da api de local deve retornar a mensagem "O endereço do local é obrigatório!"
    E que o arquivo de contrato esperado é o "Erro API local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado

  Cenário: Envio para o endpoint errado de local
    Dado que eu tenha os seguintes dados do local:
      | campo    | valor                 |
      | nome     | Hospital Central      |
      | endereco | Avenida Central, 1055 |
      | cidade   | São Paulo             |
    Quando eu enviar a requisição para o endpoint "/api/local1" de cadastro de local
    Então o status code da resposta da api de local deve ser 404
    E o corpo de resposta de erro da api de local deve retornar os dados:
      | campo     | valor                         |
      | timestamp | 2024-10-22T01:48:36.289+00:00 |
      | status    | 404                           |
      | error     | Not Found                     |
      | path      | /api/local1                   |
    E que o arquivo de contrato esperado é o "Erro geral" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado