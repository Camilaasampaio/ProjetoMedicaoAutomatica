# language: pt
@regressivo
Funcionalidade: Cadastro de nova medição de qualidade de ar
  Como usuário da API
  Quero cadastrar uma nova medição de qualidade de ar
  Para que o registro seja salvo corretamente no sistema

  Contexto: Cadastro bem-sucedido de local e equipamento de medição
    Dado que antes de cadastrar a medição de qualidade de ar eu tenha um local e um equipamento cadastrados
    Quando eu enviar a requisição para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar
    Então o status code da resposta das apis para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar 201

  @padrão
  Cenário: Cadastro bem-sucedido de medição de qualidade de ar
    Dado que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de ar criados no contexto
    E que eu tenha os seguintes dados da medição de qualidade de ar:
      | campo    | valor                              |
      | data     | 2024-09-07                         |
      | valorSo2 | SO2 esta no nivel normal           |
      | valorNo2 | NO2 esta no nivel abaixo do normal |
    Quando eu enviar a requisição para o endpoint "/api/qualidade/ar" de cadastro de medição de qualidade de ar
    Então o status code da resposta da api de medição de qualidade de ar deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de medição de qualidade de ar" da API de medição de qualidade de ar
    Então a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Cadastro com erro de campo inválido na medição de qualidade de ar
    Dado que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de ar criados no contexto
    E que eu tenha os seguintes dados da medição de qualidade de ar:
      | campo    | valor                    |
      | data     | 2024-09-07               |
      | valorSo2 | SO2 esta no nivel normal |
    Quando eu enviar a requisição para o endpoint "/api/qualidade/ar" de cadastro de medição de qualidade de ar
    Então o status code da resposta da api de medição de qualidade de ar deve ser 400
    E o corpo de resposta de erro da api de medição de qualidade de ar deve retornar a mensagem "O resultado da medição de NO2 é obrigatório!"
    E que o arquivo de contrato esperado é o "Erro API medição de qualidade de ar" da API de medição de qualidade de ar
    Então a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado

  Cenário: Envio para o endpoint errado de medição de qualidade de ar
    Dado que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de ar criados no contexto
    E que eu tenha os seguintes dados da medição de qualidade de ar:
      | campo    | valor                              |
      | data     | 2024-09-07                         |
      | valorSo2 | SO2 esta no nivel normal           |
      | valorNo2 | NO2 esta no nivel abaixo do normal |
    Quando eu enviar a requisição para o endpoint "/api/qualidade/ar1" de cadastro de medição de qualidade de ar
    Então o status code da resposta da api de medição de qualidade de ar deve ser 404
    E o corpo de resposta de erro da api de medição de qualidade de ar deve retornar os dados:
      | campo     | valor                         |
      | timestamp | 2024-10-22T01:48:36.289+00:00 |
      | status    | 404                           |
      | error     | Not Found                     |
      | path      | /api/qualidade/ar1            |
    E que o arquivo de contrato esperado é o "Erro geral" da API de medição de qualidade de ar
    Então a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado
