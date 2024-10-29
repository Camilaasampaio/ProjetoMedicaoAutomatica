# language: pt
@regressivo
Funcionalidade: Alterar equipamento de medição
  Como usuário da API
  Quero conseguir alterar um equipamento de medição
  Para que o registro seja alterado corretamente no sistema

  Contexto: Cadastro bem-sucedido de equipamento de medição
    Dado que eu tenha os seguintes dados do equipamento de medição:
      | campo          | valor                           |
      | nome           | Medidor de acidez da água turbo |
      | tipo           | Medidor de acidez               |
      | fabricante     | Medidores LTDA.                 |
      | dataInstalacao | 2024-10-21                      |
    Quando eu enviar a requisição para o endpoint "/api/equipamento" de cadastro de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 201

  @padrão
  Cenário: Deve ser possível alterar um equipamento de medição
    Dado que eu recupere o ID do equipamento de medição criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/equipamento" de alteração de equipamento de medição com os novos dados:
      | campo          | valor                 |
      | nome           | Medidor da água turbo |
      | tipo           | Medidor turbo de água |
      | fabricante     | Medidores S.A.        |
      | dataInstalacao | 2024-10-22            |
    Então o status code da resposta da api de equipamento de medição deve ser 200
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao alterar um equipamento de medição por campo inválido
    Dado que eu recupere o ID do equipamento de medição criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/equipamento" de alteração de equipamento de medição com os novos dados:
      | campo      | valor                 |
      | nome       | Medidor da água turbo |
      | tipo       | Medidor turbo de água |
      | fabricante | Medidores S.A.        |
    Então o status code da resposta da api de equipamento de medição deve ser 400
    E o corpo de resposta de erro da api de equipamento de medição deve retornar a mensagem "A data de instalação é obrigatória!"
    E que o arquivo de contrato esperado é o "Erro API equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado
