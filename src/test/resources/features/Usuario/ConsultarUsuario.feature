# language: pt
@regressivo
Funcionalidade: Consultar cadastro de usuário
  Como usuário da API
  Quero conseguir consultar um cadastro de usuário
  Para que o registro seja consultado corretamente pelo sistema

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
  Cenário: Deve ser possível consultar um cadastro de usuário
    Dado que eu recupere o ID do usuário criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de consulta de usuário
    Então o status code da resposta da api de usuário deve ser 200
    E o corpo de resposta de sucesso da api de usuário deve retornar os dados:
      | campo | valor            |
      | nome  | Carlos           |
      | email | carlos@gmail.com |
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao tentar consultar um cadastro de usuário que não existe
    Dado que eu use um ID inválido do usuário
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de consulta de usuário
    Então o status code da resposta da api de usuário deve ser 404
    E o corpo de resposta de erro da api de usuário deve retornar a mensagem "Usuário não existe no banco de dados!"
    E que o arquivo de contrato esperado é o "Erro API usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve listar os usuários
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de listar usuários
    Então o status code da resposta da api de usuário deve ser 200
    E que o arquivo de contrato esperado é o "Listar usuários" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado