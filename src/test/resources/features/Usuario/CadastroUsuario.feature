# language: pt
@regressivo
Funcionalidade: Cadastro de novo usuário
  Como usuário da API
  Quero cadastrar um novo usuário
  Para que o registro seja salvo corretamente no sistema

  @padrão
  Cenário: Cadastro bem-sucedido de usuário
    Dado que eu tenha os seguintes dados do usuário:
      | campo | valor            |
      | nome  | Carlos           |
      | email | carlos@gmail.com |
      | senha | senhasegura      |
      | role  | ADMIN            |
    Quando eu enviar a requisição para o endpoint "/api/usuario" de cadastro de usuário
    Então o status code da resposta da api de usuário deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Cadastro com erro de campo inválido no usuário
    Dado que eu tenha os seguintes dados do usuário:
      | campo | valor            |
      | nome  | Carlos           |
      | email | carlos@gmail.com |
      | role  | ADMIN            |
    Quando eu enviar a requisição para o endpoint "/api/usuario" de cadastro de usuário
    Então o status code da resposta da api de usuário deve ser 400
    E o corpo de resposta de erro da api de usuário deve retornar a mensagem "A senha é obrigatória!"
    E que o arquivo de contrato esperado é o "Erro API usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado

  Cenário: Envio para o endpoint errado de usuário
    Dado que eu tenha os seguintes dados do usuário:
      | campo | valor            |
      | nome  | Carlos           |
      | email | carlos@gmail.com |
      | senha | senhasegura      |
      | role  | ADMIN            |
    Quando eu enviar a requisição para o endpoint "/api/usuario1" de cadastro de usuário
    Então o status code da resposta da api de usuário deve ser 404
    E o corpo de resposta de erro da api de usuário deve retornar os dados:
      | campo     | valor                         |
      | timestamp | 2024-10-22T01:48:36.289+00:00 |
      | status    | 404                           |
      | error     | Not Found                     |
      | path      | /api/usuario1                 |
    E que o arquivo de contrato esperado é o "Erro geral" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado
