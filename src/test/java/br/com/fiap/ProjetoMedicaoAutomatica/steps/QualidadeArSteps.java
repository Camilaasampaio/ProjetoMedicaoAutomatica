package br.com.fiap.ProjetoMedicaoAutomatica.steps;

import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroApiModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.QualidadeArModel;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroEquipamentoMedicaoService;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroLocalService;
import br.com.fiap.ProjetoMedicaoAutomatica.services.QualidadeArService;
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

public class QualidadeArSteps {

    CadastroLocalService cadastroLocalService = new CadastroLocalService();
    CadastroEquipamentoMedicaoService cadastroEquipamentoMedicaoService = new CadastroEquipamentoMedicaoService();
    QualidadeArService qualidadeArService = new QualidadeArService();

    @E("que eu tenha os seguintes dados da medição de qualidade de ar:")
    public void queEuTenhaOsSeguintesDadosDaMediçãoDeQualidadeDeAr(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            qualidadeArService.setFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de medição de qualidade de ar")
    public void euEnviarARequisicaoParaOEndpointDeCadastroDeMediçãoDeQualidadeDeAr(String endpoint) {
        qualidadeArService.create(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de medição de qualidade de ar")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeMediçãoDeQualidadeDeAr(String endpoint) {
        qualidadeArService.delete(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de alteração de medição de qualidade de ar com os novos dados:")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeAlteraçãoDeMediçãoDeQualidadeDeArComOsNovosDados(String endpoint, List<Map<String, String>> rows) {
        qualidadeArService.limparFields();

        for (Map<String, String> columns : rows) {
            qualidadeArService.setFields(columns.get("campo"), columns.get("valor"));
        }

        qualidadeArService.qualidadeArModel.setIdLocal(Integer.parseInt(cadastroLocalService.idLocal));
        qualidadeArService.qualidadeArModel.setIdEquipamento(Integer.parseInt(cadastroEquipamentoMedicaoService.idEquipamento));

        qualidadeArService.update(endpoint);
    }

    @Dado("que eu use um ID inválido de medição de qualidade de ar")
    public void queEuUseUmIDInválidoDeMediçãoDeQualidadeDeAr() {
        qualidadeArService.retrieveIdInvalid();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de consulta de medição de qualidade de ar")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeConsultaDeMediçãoDeQualidadeDeAr(String endpoint) {
        qualidadeArService.get(endpoint);
    }

    @E("o corpo de resposta de sucesso da api de medição de qualidade de ar deve retornar os dados:")
    public void oCorpoDeRespostaDeSucessoDaApiDeMediçãoDeQualidadeDeArDeveRetornarOsDados(List<Map<String, String>> rows) {
        QualidadeArModel respostaDaApi = qualidadeArService.gson.fromJson(
                qualidadeArService.response.jsonPath().prettify(), QualidadeArModel.class);

        QualidadeArModel qualidadeArModel = new QualidadeArModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "id" -> qualidadeArModel.setId(Integer.parseInt(value));
                case "data" -> qualidadeArModel.setData(value);
                case "valorSo2" -> qualidadeArModel.setValorSo2(value);
                case "valorNo2" -> qualidadeArModel.setValorNo2(value);
                case "idLocal" -> qualidadeArModel.setIdLocal(Integer.parseInt(value));
                case "idEquipamento" -> qualidadeArModel.setIdEquipamento(Integer.parseInt(value));
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(qualidadeArModel.getData(), respostaDaApi.getData());
        Assert.assertEquals(qualidadeArModel.getValorSo2(), respostaDaApi.getValorSo2());
        Assert.assertEquals(qualidadeArModel.getValorNo2(), respostaDaApi.getValorNo2());
    }

    @Dado("que eu recupere o ID de medição de qualidade de ar criado no contexto")
    public void queEuRecupereOIDDeMediçãoDeQualidadeDeArCriadoNoContexto() {
        qualidadeArService.retrieveId();
    }

    @Então("o status code da resposta da api de medição de qualidade de ar deve ser {int}")
    public void oStatusCodeDaRespostaDaApiDeMediçãoDeQualidadeDeArDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, qualidadeArService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de medição de qualidade de ar deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeMediçãoDeQualidadeDeArDeveRetornarAMensagem(String message) {
        ErroModel errorMessageModel = qualidadeArService.gson.fromJson(
                qualidadeArService.response.jsonPath().prettify(), ErroModel.class);
        Assert.assertEquals(message, errorMessageModel.getErro());
    }

    @E("o corpo de resposta de erro da api de medição de qualidade de ar deve retornar os dados:")
    public void oCorpoDeRespostaDeErroDaApiDeMediçãoDeQualidadeDeArDeveRetornarOsDados(List<Map<String, String>> rows) {
        ErroApiModel respostaDaApi = qualidadeArService.gson.fromJson(
                qualidadeArService.response.jsonPath().prettify(), ErroApiModel.class);

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

    @Dado("que antes de cadastrar a medição de qualidade de ar eu tenha um local e um equipamento cadastrados")
    public void queAntesDeCadastrarAMediçãoDeQualidadeDeArEuTenhaUmLocalEUmEquipamentoCadastrados() {
        cadastroLocalService.localModel.setNome("Hospital Central");
        cadastroLocalService.localModel.setEndereco("Avenida Central, 1055");
        cadastroLocalService.localModel.setCidade("São Paulo");

        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setNome("Medidor de acidez da ar turbo");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setTipo("Medidor de acidez");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setFabricante("Medidores LTDA");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setDataInstalacao("2024-10-21");
    }

    @Quando("eu enviar a requisição para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar")
    public void euEnviarARequisiçãoParaCadastrarOLocalEEquipamentoAntesDeCadastrarAMediçãoDeQualidadeDeAr() {
        cadastroLocalService.create("/api/local");
        cadastroEquipamentoMedicaoService.create("/api/equipamento");
    }

    @Então("o status code da resposta das apis para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de ar {int}")
    public void oStatusCodeDaRespostaDasApisParaCadastrarOLocalEEquipamentoAntesDeCadastrarAMediçãoDeQualidadeDeAr(int statusCode) {
        Assert.assertEquals(statusCode, cadastroLocalService.response.statusCode());
        Assert.assertEquals(statusCode, cadastroEquipamentoMedicaoService.response.statusCode());
    }

    @Dado("que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de ar criados no contexto")
    public void queEuRecupereOIDDoLocalEDoEquipamentoParaCadastrarAMediçãoDeQualidadeDeArCriadosNoContexto() {
        cadastroLocalService.retrieveId();
        cadastroEquipamentoMedicaoService.retrieveId();

        qualidadeArService.qualidadeArModel.setIdLocal(Integer.parseInt(cadastroLocalService.idLocal));
        qualidadeArService.qualidadeArModel.setIdEquipamento(Integer.parseInt(cadastroEquipamentoMedicaoService.idEquipamento));
    }

    @E("que o arquivo de contrato esperado é o {string} da API de medição de qualidade de ar")
    public void queOArquivoDeContratoEsperadoÉODaAPIDeMediçãoDeQualidadeDeAr(String contract) throws IOException, JSONException {
        qualidadeArService.setContract(contract);
    }

    @Então("a resposta da API de medição de qualidade de ar deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaAPIDeMediçãoDeQualidadeDeArDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
        Set<ValidationMessage> validateResponse = qualidadeArService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de listar medições de qualidade de ar")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeListarMediçõesDeQualidadeDeAr(String endpoint) {
        qualidadeArService.list(endpoint);
    }
}
