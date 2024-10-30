# language: pt
@regressivo
Funcionalidade: Alterar cadastro de medição de qualidade de água
  Como usuário da API
  Quero conseguir alterar um cadastro de medição de qualidade de água
  Para que o registro seja alterado corretamente no sistema

  Contexto: Cadastro bem-sucedido de medição de qualidade de água
    Dado que antes de cadastrar a medição de qualidade de água eu tenha um local e um equipamento cadastrados
    Quando eu enviar a requisição para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de água
    Então o status code da resposta das apis para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de água 201
    Dado que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de água criados no contexto
    E que eu tenha os seguintes dados da medição de qualidade de água:
      | campo          | valor        |
      | data           | 2024-05-25   |
      | descricaoCloro | Nivel normal |
    Quando eu enviar a requisição para o endpoint "/api/qualidade/agua" de cadastro de medição de qualidade de água
    Então o status code da resposta da api de medição de qualidade de água deve ser 201

  @padrão
  Cenário: Deve ser possível alterar o cadastro de uma medição de qualidade de água
    Dado que eu recupere o ID de medição de qualidade de água criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/qualidade/agua" de alteração de medição de qualidade de água com os novos dados:
      | campo          | valor                 |
      | data           | 2024-10-12            |
      | descricaoCloro | Nível acima do normal |
    Então o status code da resposta da api de medição de qualidade de água deve ser 200
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de medição de qualidade de água" da API de medição de qualidade de água
    Então a resposta da API de medição de qualidade de água deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao alterar o cadastro de uma medição de qualidade de água por campo inválido
    Dado que eu recupere o ID de medição de qualidade de água criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/qualidade/agua" de alteração de medição de qualidade de água com os novos dados:
      | campo          | valor                 |
      | descricaoCloro | Nível acima do normal |
    Então o status code da resposta da api de medição de qualidade de água deve ser 400
    E o corpo de resposta de erro da api de medição de qualidade de água deve retornar a mensagem "A data de medição é obrigatória!"
    E que o arquivo de contrato esperado é o "Erro API medição de qualidade de água" da API de medição de qualidade de água
    Então a resposta da API de medição de qualidade de água deve estar em conformidade com o contrato selecionado