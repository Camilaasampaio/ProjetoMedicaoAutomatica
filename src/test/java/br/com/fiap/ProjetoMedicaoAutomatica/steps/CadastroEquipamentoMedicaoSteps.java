package br.com.fiap.ProjetoMedicaoAutomatica.steps;

import br.com.fiap.ProjetoMedicaoAutomatica.model.EquipamentoMedicaoModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroApiModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroModel;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroEquipamentoMedicaoService;
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

public class CadastroEquipamentoMedicaoSteps {

    CadastroEquipamentoMedicaoService cadastroEquipamentoMedicaoService = new CadastroEquipamentoMedicaoService();

    @Dado("que eu tenha os seguintes dados do equipamento de medição:")
    public void queEuTenhaOsSeguintesDadosDoEquipamentoDeMedicao(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroEquipamentoMedicaoService.setFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de equipamento de medição")
    public void euEnviarARequisicaoParaOEndpointDeCadastroDeEquipamentoDeMedicao(String endpoint) {
        cadastroEquipamentoMedicaoService.create(endpoint);
    }

    @Então("o status code da resposta da api de equipamento de medição deve ser {int}")
    public void oStatusCodeDaRespostaDaApiDeEquipamentoDeMediçãoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroEquipamentoMedicaoService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de equipamento de medição deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeEquipamentoDeMediçãoDeveRetornarAMensagem(String message) {
        ErroModel errorMessageModel = cadastroEquipamentoMedicaoService.gson.fromJson(
                cadastroEquipamentoMedicaoService.response.jsonPath().prettify(), ErroModel.class);
        Assert.assertEquals(message, errorMessageModel.getErro());
    }

    @E("o corpo de resposta de erro da api de equipamento de medição deve retornar os dados:")
    public void oCorpoDeRespostaDeErroDaApiDeEquipamentoDeMediçãoDeveRetornarOsDados(List<Map<String, String>> rows) {
        ErroApiModel respostaDaApi = cadastroEquipamentoMedicaoService.gson.fromJson(
                cadastroEquipamentoMedicaoService.response.jsonPath().prettify(), ErroApiModel.class);

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

    @Dado("que eu recupere o ID do equipamento de medição criado no contexto")
    public void queEuRecupereOIDDoEquipamentoDeMediçãoCriadoNoContexto() {
        cadastroEquipamentoMedicaoService.retrieveId();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de equipamento de medição")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeEquipamentoDeMedição(String endpoint) {
        cadastroEquipamentoMedicaoService.delete(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de alteração de equipamento de medição com os novos dados:")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeAlteraçãoDeEquipamentoDeMediçãoComOsNovosDados(String endpoint, List<Map<String, String>> rows) {
        cadastroEquipamentoMedicaoService.limparFields();

        for (Map<String, String> columns : rows) {
            cadastroEquipamentoMedicaoService.setFields(columns.get("campo"), columns.get("valor"));
        }

        cadastroEquipamentoMedicaoService.update(endpoint);
    }

    @Dado("que eu use um ID inválido do equipamento de medição")
    public void queEuUseUmIDInválidoDoEquipamentoDeMedição() {
        cadastroEquipamentoMedicaoService.retrieveIdInvalid();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de consulta de equipamento de medição")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeConsultaDeEquipamentoDeMedição(String endpoint) {
        cadastroEquipamentoMedicaoService.get(endpoint);
    }

    @E("o corpo de resposta de sucesso da api de equipamento de medição deve retornar os dados:")
    public void oCorpoDeRespostaDeSucessoDaApiDeEquipamentoDeMediçãoDeveRetornarOsDados(List<Map<String, String>> rows) {
        EquipamentoMedicaoModel respostaDaApi = cadastroEquipamentoMedicaoService.gson.fromJson(
                cadastroEquipamentoMedicaoService.response.jsonPath().prettify(), EquipamentoMedicaoModel.class);

        EquipamentoMedicaoModel equipamentoMedicaoModel = new EquipamentoMedicaoModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "nome" -> equipamentoMedicaoModel.setNome(value);
                case "tipo" -> equipamentoMedicaoModel.setTipo(value);
                case "fabricante" -> equipamentoMedicaoModel.setFabricante(value);
                case "dataInstalacao" -> equipamentoMedicaoModel.setDataInstalacao(value);
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(equipamentoMedicaoModel.getNome(), respostaDaApi.getNome());
        Assert.assertEquals(equipamentoMedicaoModel.getTipo(), respostaDaApi.getTipo());
        Assert.assertEquals(equipamentoMedicaoModel.getFabricante(), respostaDaApi.getFabricante());
        Assert.assertEquals(equipamentoMedicaoModel.getDataInstalacao(), respostaDaApi.getDataInstalacao());
    }

    @E("que o arquivo de contrato esperado é o {string} da API de equipamento de medição")
    public void queOArquivoDeContratoEsperadoÉODaAPIDeEquipamentoDeMedição(String contract) throws IOException, JSONException {
        cadastroEquipamentoMedicaoService.setContract(contract);
    }

    @Então("a resposta da API de equipamento de medição deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaAPIDeEquipamentoDeMediçãoDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
        Set<ValidationMessage> validateResponse = cadastroEquipamentoMedicaoService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de listar equipamento de medição")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeListarEquipamentoDeMedição(String endpoint) {
        cadastroEquipamentoMedicaoService.list(endpoint);
    }
}
