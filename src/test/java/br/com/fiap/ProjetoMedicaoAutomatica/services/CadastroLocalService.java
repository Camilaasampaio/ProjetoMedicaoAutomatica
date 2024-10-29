package br.com.fiap.ProjetoMedicaoAutomatica.services;

import br.com.fiap.ProjetoMedicaoAutomatica.model.LocalModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class CadastroLocalService {

    public final LocalModel localModel = new LocalModel();
    public String idLocal = "";
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public Response response;

    String baseUrl = "http://localhost:8080";

    public void limparFields() {
        localModel.setId(0);
        localModel.setNome(null);
        localModel.setEndereco(null);
        localModel.setCidade(null);
    }

    public void setFields(String field, String value) {
        switch (field) {
            case "id" -> localModel.setId(Integer.parseInt(value));
            case "nome" -> localModel.setNome(value);
            case "endereco" -> localModel.setEndereco(value);
            case "cidade" -> localModel.setCidade(value);
            default -> throw new IllegalStateException("Campo desconhecido: " + field);
        }
    }

    public void retrieveId() {
        idLocal = String.valueOf(gson.fromJson(response.jsonPath().prettify(), LocalModel.class).getId());
    }

    public void retrieveIdInvalid() {
        idLocal = "0";
    }

    public void create(String endpoint) {
        String url = baseUrl + endpoint;
        String bodyToSend = gson.toJson(localModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public void update(String endpoint) {
        localModel.setId(Integer.parseInt(idLocal));
        String url = baseUrl + endpoint;
        String bodyToSend = gson.toJson(localModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    public void get(String endpoint) {
        String url = baseUrl + endpoint + "/" + idLocal;
        response = given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public void delete(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, idLocal);
        response = given()
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    public void list(String endpoint) {
        String url = baseUrl + endpoint;
        response = given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException, JSONException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException, JSONException {
        switch (contract) {
            case "Cadastro bem-sucedido de local" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-local.json");
            case "Listar locais" -> jsonSchema = loadJsonFromFile(schemasPath + "listar-locais.json");
            case "Erro API local" -> jsonSchema = loadJsonFromFile(schemasPath + "erro-local.json");
            case "Erro geral" -> jsonSchema = loadJsonFromFile(schemasPath + "erro-geral.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException, JSONException {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        return schema.validate(jsonResponseNode);
    }
}
