package br.com.fiap.ProjetoMedicaoAutomatica.steps;

import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroApiModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.LocalModel;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroLocalService;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.json.JSONException;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroLocalSteps {

    CadastroLocalService cadastroLocalService = new CadastroLocalService();

    @Dado("que eu tenha os seguintes dados do local:")
    public void queEuTenhaOsSeguintesDadosDoLocal(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroLocalService.setFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de local")
    public void euEnviarARequisicaoParaOEndpointDeCadastroDeLocal(String endpoint) {
        cadastroLocalService.create(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de local")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeLocal(String endpoint) {
        cadastroLocalService.delete(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de alteração de local com os novos dados:")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeAlteraçãoDeLocalComOsNovosDados(String endpoint, List<Map<String, String>> rows) {
        cadastroLocalService.limparFields();

        for (Map<String, String> columns : rows) {
            cadastroLocalService.setFields(columns.get("campo"), columns.get("valor"));
        }

        cadastroLocalService.update(endpoint);
    }

    @Dado("que eu use um ID inválido do local")
    public void queEuUseUmIDInválidoDoLocal() {
        cadastroLocalService.retrieveIdInvalid();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de consulta de local")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeConsultaDeLocal(String endpoint) {
        cadastroLocalService.get(endpoint);
    }

    @E("o corpo de resposta de sucesso da api de local deve retornar os dados:")
    public void oCorpoDeRespostaDeSucessoDaApiDeLocalDeveRetornarOsDados(List<Map<String, String>> rows) {
        LocalModel respostaDaApi = cadastroLocalService.gson.fromJson(
                cadastroLocalService.response.jsonPath().prettify(), LocalModel.class);

        LocalModel localModel = new LocalModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "id" -> localModel.setId(Integer.parseInt(value));
                case "nome" -> localModel.setNome(value);
                case "endereco" -> localModel.setEndereco(value);
                case "cidade" -> localModel.setCidade(value);
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(localModel.getNome(), respostaDaApi.getNome());
        Assert.assertEquals(localModel.getEndereco(), respostaDaApi.getEndereco());
        Assert.assertEquals(localModel.getCidade(), respostaDaApi.getCidade());
    }

    @Dado("que eu recupere o ID do local criado no contexto")
    public void queEuRecupereOIDDoLocalCriadoNoContexto() {
        cadastroLocalService.retrieveId();
    }

    @Então("o status code da resposta da api de local deve ser {int}")
    public void oStatusCodeDaRespostaDaApiDeLocalDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroLocalService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de local deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeLocalDeveRetornarAMensagem(String message) {
        ErroModel errorMessageModel = cadastroLocalService.gson.fromJson(
                cadastroLocalService.response.jsonPath().prettify(), ErroModel.class);
        Assert.assertEquals(message, errorMessageModel.getErro());
    }

    @E("o corpo de resposta de erro da api de local deve retornar os dados:")
    public void oCorpoDeRespostaDeErroDaApiDeLocalDeveRetornarOsDados(List<Map<String, String>> rows) {
        ErroApiModel respostaDaApi = cadastroLocalService.gson.fromJson(
                cadastroLocalService.response.jsonPath().prettify(), ErroApiModel.class);

        ErroApiModel dadosErro = new ErroApiModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "timestamp" -> dadosErro.setTimestamp(value);
                case "status" -> dadosErro.setStatus(Integer.parseInt(value));
                case "error" -> dadosErro.setError(value);
                case "path" -> dadosErro.setPath(value);
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(dadosErro.getStatus(), respostaDaApi.getStatus());
        Assert.assertEquals(dadosErro.getError(), respostaDaApi.getError());
        Assert.assertEquals(dadosErro.getPath(), respostaDaApi.getPath());

    }

    @E("que o arquivo de contrato esperado é o {string} da API de local")
    public void queOArquivoDeContratoEsperadoÉODaAPIDeLocal(String contract) throws IOException, JSONException {
        cadastroLocalService.setContract(contract);
    }

    @Então("a resposta da API de local deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaAPIDeLocalDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
        Set<ValidationMessage> validateResponse = cadastroLocalService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de listar local")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeListarLocal(String endpoint) {
        cadastroLocalService.list(endpoint);
    }
}
