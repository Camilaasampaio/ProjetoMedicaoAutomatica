# language: pt
@regressivo
Funcionalidade: Cadastro de novo equipamento de medição
  Como usuário da API
  Quero cadastrar um novo equipamento de medição
  Para que o registro seja salvo corretamente no sistema

  @padrão
  Cenário: Cadastro bem-sucedido de equipamento de medição
    Dado que eu tenha os seguintes dados do equipamento de medição:
      | campo          | valor                           |
      | nome           | Medidor de acidez da água turbo |
      | tipo           | Medidor de acidez               |
      | fabricante     | Medidores LTDA.                 |
      | dataInstalacao | 2024-10-21                      |
    Quando eu enviar a requisição para o endpoint "/api/equipamento" de cadastro de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Cadastro com erro de campo inválido no equipamento de medição
    Dado que eu tenha os seguintes dados do equipamento de medição:
      | campo      | valor                           |
      | nome       | Medidor de acidez da água turbo |
      | tipo       | Medidor de acidez               |
      | fabricante | Medidores LTDA.                 |
    Quando eu enviar a requisição para o endpoint "/api/equipamento" de cadastro de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 400
    E o corpo de resposta de erro da api de equipamento de medição deve retornar a mensagem "A data de instalação é obrigatória!"
    E que o arquivo de contrato esperado é o "Erro API equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado

  Cenário: Envio para o endpoint errado de equipamento de medição
    Dado que eu tenha os seguintes dados do equipamento de medição:
      | campo          | valor                           |
      | nome           | Medidor de acidez da água turbo |
      | tipo           | Medidor de acidez               |
      | fabricante     | Medidores LTDA.                 |
      | dataInstalacao | 2024-10-21                      |
    Quando eu enviar a requisição para o endpoint "/api/equipamento1" de cadastro de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 404
    E o corpo de resposta de erro da api de equipamento de medição deve retornar os dados:
      | campo     | valor                         |
      | timestamp | 2024-10-22T01:48:36.289+00:00 |
      | status    | 404                           |
      | error     | Not Found                     |
      | path      | /api/equipamento1             |
    E que o arquivo de contrato esperado é o "Erro geral" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado
