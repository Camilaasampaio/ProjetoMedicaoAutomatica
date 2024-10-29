# language: pt
@regressivo
Funcionalidade: Alterar cadastro de medição de qualidade de ar
  Como usuário da API
  Quero conseguir alterar um cadastro de medição de qualidade de ar
  Para que o registro seja alterado corretamente no sistema

  Contexto: Cadastro bem-sucedido de medição de qualidade de ar
    Dado que antes de cadastrar a medição de qualidade de ar eu tenha um local e um equipamento cadastrados
    Quando eu enviar a requisição para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar
    Então o status code da resposta das apis para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar 201
    Dado que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de ar criados no contexto
    E que eu tenha os seguintes dados da medição de qualidade de ar:
      | campo    | valor                              |
      | data     | 2024-09-07                         |
      | valorSo2 | SO2 esta no nivel normal           |
      | valorNo2 | NO2 esta no nivel abaixo do normal |
    Quando eu enviar a requisição para o endpoint "/api/qualidade/ar" de cadastro de medição de qualidade de ar
    Então o status code da resposta da api de medição de qualidade de ar deve ser 201

  @padrão
  Cenário: Deve ser possível alterar o cadastro de uma medição de qualidade de ar
    Dado que eu recupere o ID de medição de qualidade de ar criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/qualidade/ar" de alteração de medição de qualidade de ar com os novos dados:
      | campo    | valor                                    |
      | data     | 2024-09-07                               |
      | valorSo2 | SO2 esta no nivel alto                   |
      | valorNo2 | NO2 esta no nivel mais alto que o normal |
    Então o status code da resposta da api de medição de qualidade de ar deve ser 200
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de medição de qualidade de ar" da API de medição de qualidade de ar
    Então a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao alterar o cadastro de uma medição de qualidade de ar por campo inválido
    Dado que eu recupere o ID de medição de qualidade de ar criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/qualidade/ar" de alteração de medição de qualidade de ar com os novos dados:
      | campo    | valor                                    |
      | data     | 2024-09-07                               |
      | valorNo2 | NO2 esta no nivel mais alto que o normal |
    Então o status code da resposta da api de medição de qualidade de ar deve ser 400
    E o corpo de resposta de erro da api de medição de qualidade de ar deve retornar a mensagem "O resultado da medição de SO2 é obrigatório!"
    E que o arquivo de contrato esperado é o "Erro API medição de qualidade de ar" da API de medição de qualidade de ar
    Então a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado
