package br.com.fiap.ProjetoMedicaoAutomatica.services;

import br.com.fiap.ProjetoMedicaoAutomatica.model.QualidadeArModel;
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

public class QualidadeArService {

    public final QualidadeArModel qualidadeArModel = new QualidadeArModel();
    public String idAr = "";
    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public Response response;

    String baseUrl = "http://localhost:8080";

    public void limparFields() {
        qualidadeArModel.setId(0);
        qualidadeArModel.setData(null);
        qualidadeArModel.setValorSo2(null);
        qualidadeArModel.setValorNo2(null);
        qualidadeArModel.setIdLocal(0);
        qualidadeArModel.setIdEquipamento(0);
    }

    public void setFields(String field, String value) {
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

    public void retrieveId() {
        idAr = String.valueOf(gson.fromJson(response.jsonPath().prettify(), QualidadeArModel.class).getId());
    }

    public void retrieveIdInvalid() {
        idAr = "0";
    }

    public void create(String endpoint) {
        String url = baseUrl + endpoint;
        String bodyToSend = gson.toJson(qualidadeArModel);
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
        qualidadeArModel.setId(Integer.parseInt(idAr));
        String url = baseUrl + endpoint;
        String bodyToSend = gson.toJson(qualidadeArModel);
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
        String url = baseUrl + endpoint + "/" + idAr;
        response = given()
                .accept(ContentType.JSON)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public void delete(String endPoint) {
        String url = String.format("%s%s/%s", baseUrl, endPoint, idAr);
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
            case "Cadastro bem-sucedido de medição de qualidade de ar" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-medicao-qualidade-ar.json");
            case "Listar medições de qualidade de ar" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "listar-medicao-qualidade-ar.json");
            case "Erro API medição de qualidade de ar" ->
                    jsonSchema = loadJsonFromFile(schemasPath + "erro-medicao-qualidade-ar.json");
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
