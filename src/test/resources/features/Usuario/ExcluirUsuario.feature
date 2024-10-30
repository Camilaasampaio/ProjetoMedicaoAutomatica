# language: pt
@regressivo
Funcionalidade: Excluir cadastro de usuário
  Como usuário da API
  Quero conseguir deletar um cadastro de usuário
  Para que o registro seja apagado corretamente no sistema

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
  Cenário: Deve ser possível deletar um cadastro de usuário
    Dado que eu recupere o ID do usuário criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de deleção de usuário
    Então o status code da resposta da api de usuário deve ser 204

  @padrão
  Cenário: Deve falhar ao tentar deletar um cadastro de usuário que não existe
    Dado que eu use um ID inválido do usuário
    Quando eu enviar a requisição com o ID para o endpoint "/api/usuario" de deleção de usuário
    Então o status code da resposta da api de usuário deve ser 404
    E o corpo de resposta de erro da api de usuário deve retornar a mensagem "Usuário não existe no banco de dados!"
    E que o arquivo de contrato esperado é o "Erro API usuário" da API de usuário
    Então a resposta da API de usuário deve estar em conformidade com o contrato selecionado
