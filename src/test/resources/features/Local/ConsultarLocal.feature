# language: pt
@regressivo
Funcionalidade: Consultar cadastro de local
  Como usuário da API
  Quero conseguir consultar um cadastro de local
  Para que o registro seja consultado corretamente pelo sistema

  Contexto: Cadastro bem-sucedido de local
    Dado que eu tenha os seguintes dados do local:
      | campo    | valor                 |
      | nome     | Hospital Central      |
      | endereco | Avenida Central, 1055 |
      | cidade   | São Paulo             |
    Quando eu enviar a requisição para o endpoint "/api/local" de cadastro de local
    Então o status code da resposta da api de local deve ser 201

  @padrão
  Cenário: Deve ser possível consultar um cadastro de local
    Dado que eu recupere o ID do local criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/local" de consulta de local
    Então o status code da resposta da api de local deve ser 200
    E o corpo de resposta de sucesso da api de local deve retornar os dados:
      | campo    | valor                 |
      | nome     | Hospital Central      |
      | endereco | Avenida Central, 1055 |
      | cidade   | São Paulo             |
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao tentar consultar um cadastro de local que não existe
    Dado que eu use um ID inválido do local
    Quando eu enviar a requisição com o ID para o endpoint "/api/local" de consulta de local
    Então o status code da resposta da api de local deve ser 404
    E o corpo de resposta de erro da api de local deve retornar a mensagem "Local não encontrado no banco de dados!"
    E que o arquivo de contrato esperado é o "Erro API local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve listar os locais
    Quando eu enviar a requisição com o ID para o endpoint "/api/local" de listar local
    Então o status code da resposta da api de local deve ser 200
    E que o arquivo de contrato esperado é o "Listar locais" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado