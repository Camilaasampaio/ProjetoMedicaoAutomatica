# language: pt
@regressivo
Funcionalidade: Alterar cadastro de local
  Como usuário da API
  Quero conseguir alterar um cadastro de local
  Para que o registro seja alterado corretamente no sistema

  Contexto: Cadastro bem-sucedido de local
    Dado que eu tenha os seguintes dados do local:
      | campo    | valor                 |
      | nome     | Hospital Central      |
      | endereco | Avenida Central, 1055 |
      | cidade   | São Paulo             |
    Quando eu enviar a requisição para o endpoint "/api/local" de cadastro de local
    Então o status code da resposta da api de local deve ser 201

  @padrão
  Cenário: Deve ser possível alterar cadastro de um local
    Dado que eu recupere o ID do local criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/local" de alteração de local com os novos dados:
      | campo    | valor                          |
      | nome     | Hospital Central de São Paulo  |
      | endereco | Avenida Central, 1055, bloco B |
      | cidade   | São Paulo - SP                 |
    Então o status code da resposta da api de local deve ser 200
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao alterar cadastro de um local por campo inválido
    Dado que eu recupere o ID do local criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/local" de alteração de local com os novos dados:
      | campo    | valor                             |
      | nome     | Hospital Central de São Paulo Sul |
      | endereco | Avenida Central, 1055, bloco C    |
    Então o status code da resposta da api de local deve ser 400
    E o corpo de resposta de erro da api de local deve retornar a mensagem "O nome da cidade é obrigatório!"
    E que o arquivo de contrato esperado é o "Erro API local" da API de local
    Então a resposta da API de local deve estar em conformidade com o contrato selecionado
