package br.com.fiap.ProjetoMedicaoAutomatica.steps;

import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroApiModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.QualidadeAguaModel;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroEquipamentoMedicaoService;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroLocalService;
import br.com.fiap.ProjetoMedicaoAutomatica.services.QualidadeAguaService;
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

public class QualidadeAguaSteps {

    CadastroLocalService cadastroLocalService = new CadastroLocalService();
    CadastroEquipamentoMedicaoService cadastroEquipamentoMedicaoService = new CadastroEquipamentoMedicaoService();
    QualidadeAguaService qualidadeAguaService = new QualidadeAguaService();

    @E("que eu tenha os seguintes dados da medição de qualidade de água:")
    public void queEuTenhaOsSeguintesDadosDaMediçãoDeQualidadeDeÁgua(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            qualidadeAguaService.setFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de medição de qualidade de água")
    public void euEnviarARequisicaoParaOEndpointDeCadastroDeMediçãoDeQualidadeDeÁgua(String endpoint) {
        qualidadeAguaService.create(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de medição de qualidade de água")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeMediçãoDeQualidadeDeÁgua(String endpoint) {
        qualidadeAguaService.delete(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de alteração de medição de qualidade de água com os novos dados:")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeAlteraçãoDeMediçãoDeQualidadeDeÁguaComOsNovosDados(String endpoint, List<Map<String, String>> rows) {
        qualidadeAguaService.limparFields();

        for (Map<String, String> columns : rows) {
            qualidadeAguaService.setFields(columns.get("campo"), columns.get("valor"));
        }

        qualidadeAguaService.qualidadeAguaModel.setIdLocal(Integer.parseInt(cadastroLocalService.idLocal));
        qualidadeAguaService.qualidadeAguaModel.setIdEquipamento(Integer.parseInt(cadastroEquipamentoMedicaoService.idEquipamento));

        qualidadeAguaService.update(endpoint);
    }

    @Dado("que eu use um ID inválido de medição de qualidade de água")
    public void queEuUseUmIDInválidoDeMediçãoDeQualidadeDeÁgua() {
        qualidadeAguaService.retrieveIdInvalid();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de consulta de medição de qualidade de água")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeConsultaDeMediçãoDeQualidadeDeÁgua(String endpoint) {
        qualidadeAguaService.get(endpoint);
    }

    @E("o corpo de resposta de sucesso da api de medição de qualidade de água deve retornar os dados:")
    public void oCorpoDeRespostaDeSucessoDaApiDeMediçãoDeQualidadeDeÁguaDeveRetornarOsDados(List<Map<String, String>> rows) {
        QualidadeAguaModel respostaDaApi = qualidadeAguaService.gson.fromJson(
                qualidadeAguaService.response.jsonPath().prettify(), QualidadeAguaModel.class);

        QualidadeAguaModel qualidadeAguaModel = new QualidadeAguaModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "id" -> qualidadeAguaModel.setId(Integer.parseInt(value));
                case "data" -> qualidadeAguaModel.setData(value);
                case "descricaoCloro" -> qualidadeAguaModel.setDescricaoCloro(value);
                case "idLocal" -> qualidadeAguaModel.setIdLocal(Integer.parseInt(value));
                case "idEquipamento" -> qualidadeAguaModel.setIdEquipamento(Integer.parseInt(value));
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(qualidadeAguaModel.getData(), respostaDaApi.getData());
        Assert.assertEquals(qualidadeAguaModel.getDescricaoCloro(), respostaDaApi.getDescricaoCloro());
    }

    @Dado("que eu recupere o ID de medição de qualidade de água criado no contexto")
    public void queEuRecupereOIDDeMediçãoDeQualidadeDeÁguaCriadoNoContexto() {
        qualidadeAguaService.retrieveId();
    }

    @Então("o status code da resposta da api de medição de qualidade de água deve ser {int}")
    public void oStatusCodeDaRespostaDaApiDeMediçãoDeQualidadeDeÁguaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, qualidadeAguaService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de medição de qualidade de água deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeMediçãoDeQualidadeDeÁguaDeveRetornarAMensagem(String message) {
        ErroModel errorMessageModel = qualidadeAguaService.gson.fromJson(
                qualidadeAguaService.response.jsonPath().prettify(), ErroModel.class);
        Assert.assertEquals(message, errorMessageModel.getErro());
    }

    @E("o corpo de resposta de erro da api de medição de qualidade de água deve retornar os dados:")
    public void oCorpoDeRespostaDeErroDaApiDeMediçãoDeQualidadeDeÁguaDeveRetornarOsDados(List<Map<String, String>> rows) {
        ErroApiModel respostaDaApi = qualidadeAguaService.gson.fromJson(
                qualidadeAguaService.response.jsonPath().prettify(), ErroApiModel.class);

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

    @Dado("que antes de cadastrar a medição de qualidade de água eu tenha um local e um equipamento cadastrados")
    public void queAntesDeCadastrarAMediçãoDeQualidadeDeÁguaEuTenhaUmLocalEUmEquipamentoCadastrados() {
        cadastroLocalService.localModel.setNome("Hospital Central");
        cadastroLocalService.localModel.setEndereco("Avenida Central, 1055");
        cadastroLocalService.localModel.setCidade("São Paulo");

        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setNome("Medidor de acidez da água turbo");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setTipo("Medidor de acidez");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setFabricante("Medidores LTDA");
        cadastroEquipamentoMedicaoService.equipamentoMedicaoModel.setDataInstalacao("2024-10-21");
    }

    @Quando("eu enviar a requisição para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de água")
    public void euEnviarARequisiçãoParaCadastrarOLocalEEquipamentoAntesDeCadastrarAMediçãoDeQualidadeDeÁgua() {
        cadastroLocalService.create("/api/local");
        cadastroEquipamentoMedicaoService.create("/api/equipamento");
    }

    @Então("o status code da resposta das apis para cadastrar o local e equipamento antes de cadastrar a medição de qualidade de água {int}")
    public void oStatusCodeDaRespostaDasApisParaCadastrarOLocalEEquipamentoAntesDeCadastrarAMediçãoDeQualidadeDeÁgua(int statusCode) {
        Assert.assertEquals(statusCode, cadastroLocalService.response.statusCode());
        Assert.assertEquals(statusCode, cadastroEquipamentoMedicaoService.response.statusCode());
    }

    @Dado("que eu recupere o ID do local e do equipamento para cadastrar a medição de qualidade de água criados no contexto")
    public void queEuRecupereOIDDoLocalEDoEquipamentoParaCadastrarAMediçãoDeQualidadeDeÁguaCriadosNoContexto() {
        cadastroLocalService.retrieveId();
        cadastroEquipamentoMedicaoService.retrieveId();

        qualidadeAguaService.qualidadeAguaModel.setIdLocal(Integer.parseInt(cadastroLocalService.idLocal));
        qualidadeAguaService.qualidadeAguaModel.setIdEquipamento(Integer.parseInt(cadastroEquipamentoMedicaoService.idEquipamento));
    }

    @E("que o arquivo de contrato esperado é o {string} da API de medição de qualidade de água")
    public void queOArquivoDeContratoEsperadoÉODaAPIDeMediçãoDeQualidadeDeÁgua(String contract) throws IOException, JSONException {
        qualidadeAguaService.setContract(contract);
    }

    @Então("a resposta da API de medição de qualidade de água deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaAPIDeMediçãoDeQualidadeDeÁguaDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
        Set<ValidationMessage> validateResponse = qualidadeAguaService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de listar medições de qualidade de água")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeListarMediçõesDeQualidadeDeÁgua(String endpoint) {
        qualidadeAguaService.list(endpoint);
    }
}
