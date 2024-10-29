# language: pt
@regressivo
Funcionalidade: Alterar cadastro de usuário
  Como usuário da API
  Quero conseguir alterar um cadastro de usuário
  Para que o registro seja alterado corretamente no sistema

  Contexto: Cadastro bem-sucedido de usuário
    Dado que eu tenha os seguintes dados do usuário:
      | campo | valor            |
      | nome  | Carlos           |
      | email | carlos@gmail.com |
      | senha | senhasegura      |
      | role  | ADMIN            |
    Quando eu enviar a requisição para o endpoint "/api/usuario" de cadastro de usuário
    Então o status code da resposta da api de usuário deve ser 201

  @padrão
  Cenário: Deve ser possível alterar cadastro de um usuário
    Dado que eu recupere o ID do usuário criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de alteração de usuário com os novos dados:
      | campo | valor                     |
      | nome  | Carlos Alberto            |
      | email | carlos-alberto@google.com |
      | senha | 12345678                  |
      | role  | ADMIN                     |
    Então o status code da resposta da api de usuário deve ser 200
    E que o arquivo de contrato esperado é o "Alterar cadastro de usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado
