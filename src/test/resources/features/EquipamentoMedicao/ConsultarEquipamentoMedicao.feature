# language: pt
@regressivo
Funcionalidade: Consultar equipamento de medição
  Como usuário da API
  Quero conseguir consultar um equipamento de medição
  Para que o registro seja consultado corretamente pelo sistema

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
  Cenário: Deve ser possível consultar um equipamento de medição
    Dado que eu recupere o ID do equipamento de medição criado no contexto
    Quando eu enviar a requisição com o ID para o endpoint "/api/equipamento" de consulta de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 200
    E o corpo de resposta de sucesso da api de equipamento de medição deve retornar os dados:
      | campo          | valor                           |
      | nome           | Medidor de acidez da água turbo |
      | tipo           | Medidor de acidez               |
      | fabricante     | Medidores LTDA.                 |
      | dataInstalacao | 2024-10-21                      |
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve falhar ao tentar consultar um equipamento de medição que não existe
    Dado que eu use um ID inválido do equipamento de medição
    Quando eu enviar a requisição com o ID para o endpoint "/api/equipamento" de consulta de equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 404
    E o corpo de resposta de erro da api de equipamento de medição deve retornar a mensagem "Equipamento de Medição não encontrado no banco de dados!"
    E que o arquivo de contrato esperado é o "Erro API equipamento de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado

  @padrão
  Cenário: Deve listar os equipamentos de medição
    Quando eu enviar a requisição com o ID para o endpoint "/api/equipamento" de listar equipamento de medição
    Então o status code da resposta da api de equipamento de medição deve ser 200
    E que o arquivo de contrato esperado é o "Listar equipamentos de medição" da API de equipamento de medição
    Então a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado