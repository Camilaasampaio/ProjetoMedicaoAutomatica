package br.com.fiap.ProjetoMedicaoAutomatica.steps;

import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroApiModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.ErroModel;
import br.com.fiap.ProjetoMedicaoAutomatica.model.UsuarioModel;
import br.com.fiap.ProjetoMedicaoAutomatica.services.CadastroUsuarioService;
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

public class CadastroUsuarioSteps {

    CadastroUsuarioService cadastroUsuarioService = new CadastroUsuarioService();

    @Dado("que eu tenha os seguintes dados do usuário:")
    public void queEuTenhaOsSeguintesDadosDoUsuário(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroUsuarioService.setFields(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de usuário")
    public void euEnviarARequisicaoParaOEndpointDeCadastroDeUsuário(String endpoint) {
        cadastroUsuarioService.create(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de deleção de usuário")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeDeleçãoDeUsuário(String endpoint) {
        cadastroUsuarioService.delete(endpoint);
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de alteração de usuário com os novos dados:")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeAlteraçãoDeUsuárioComOsNovosDados(String endpoint, List<Map<String, String>> rows) {
        cadastroUsuarioService.limparFields();

        for (Map<String, String> columns : rows) {
            cadastroUsuarioService.setFields(columns.get("campo"), columns.get("valor"));
        }

        cadastroUsuarioService.update(endpoint);
    }

    @Dado("que eu use um ID inválido do usuário")
    public void queEuUseUmIDInválidoDoUsuário() {
        cadastroUsuarioService.retrieveIdInvalid();
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de consulta de usuário")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeConsultaDeUsuário(String endpoint) {
        cadastroUsuarioService.get(endpoint);
    }

    @E("o corpo de resposta de sucesso da api de usuário deve retornar os dados:")
    public void oCorpoDeRespostaDeSucessoDaApiDeUsuárioDeveRetornarOsDados(List<Map<String, String>> rows) {
        UsuarioModel respostaDaApi = cadastroUsuarioService.gson.fromJson(
                cadastroUsuarioService.response.jsonPath().prettify(), UsuarioModel.class);

        UsuarioModel usuarioModel = new UsuarioModel();

        for (Map<String, String> columns : rows) {
            String field = columns.get("campo");
            String value = columns.get("valor");

            switch (field) {
                case "usuarioId" -> usuarioModel.setUsuarioId(Integer.parseInt(value));
                case "nome" -> usuarioModel.setNome(value);
                case "email" -> usuarioModel.setEmail(value);
                case "senha" -> usuarioModel.setSenha(value);
                case "role" -> usuarioModel.setRole(value);
                default -> throw new IllegalStateException("Campo desconhecido: " + field);
            }
        }

        Assert.assertEquals(usuarioModel.getNome(), respostaDaApi.getNome());
        Assert.assertEquals(usuarioModel.getEmail(), respostaDaApi.getEmail());
        Assert.assertEquals(usuarioModel.getSenha(), respostaDaApi.getSenha());
        Assert.assertEquals(usuarioModel.getRole(), respostaDaApi.getRole());
    }

    @Dado("que eu recupere o ID do usuário criado no contexto")
    public void queEuRecupereOIDDoUsuárioCriadoNoContexto() {
        cadastroUsuarioService.retrieveId();
    }

    @Então("o status code da resposta da api de usuário deve ser {int}")
    public void oStatusCodeDaRespostaDaApiDeUsuárioDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroUsuarioService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api de usuário deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeUsuárioDeveRetornarAMensagem(String message) {
        ErroModel errorMessageModel = cadastroUsuarioService.gson.fromJson(
                cadastroUsuarioService.response.jsonPath().prettify(), ErroModel.class);
        Assert.assertEquals(message, errorMessageModel.getErro());
    }

    @E("o corpo de resposta de erro da api de usuário deve retornar os dados:")
    public void oCorpoDeRespostaDeErroDaApiDeUsuárioDeveRetornarOsDados(List<Map<String, String>> rows) {
        ErroApiModel respostaDaApi = cadastroUsuarioService.gson.fromJson(
                cadastroUsuarioService.response.jsonPath().prettify(), ErroApiModel.class);

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

    @E("que o arquivo de contrato esperado é o {string} da API de usuário")
    public void queOArquivoDeContratoEsperadoÉODaAPIDeUsuário(String contract) throws IOException, JSONException {
        cadastroUsuarioService.setContract(contract);
    }

    @Então("a resposta da API de usuário deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaAPIDeUsuárioDeveEstarEmConformidadeComOContratoSelecionado() throws IOException, JSONException {
        Set<ValidationMessage> validateResponse = cadastroUsuarioService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Quando("eu enviar a requisição com o ID para o endpoint {string} de listar usuários")
    public void euEnviarARequisiçãoComOIDParaOEndpointDeListarUsuários(String endpoint) {
        cadastroUsuarioService.list(endpoint);
    }
}
