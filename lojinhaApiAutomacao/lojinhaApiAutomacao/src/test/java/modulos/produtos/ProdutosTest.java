package modulos.produtos;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

//anotações do Junit
@DisplayName("Testes de API Rest do módulo de Produtos")

//Classe de teste - Finaliza com a palavra "Test"
public class ProdutosTest {
    @Test
    @DisplayName("Validar os limites proibidos do valor do produto")

    //método de teste - começa com a palavra "test"
    public void testValidarLimitesProibidosValorProduto(){
        //Configurando os dados da API Rest
        baseURI = "https://serverest.dev";
        //port = 8080; - caso seja necessário usar PORTA para acessar a aplicação
        //basePath = "/login";

        //Obter o token do usuário
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"email\": \"jonathaspe@qa.com.br\",\n" +
                        "  \"password\": \"teste123\"\n" +
                        "}")
            .when()
                .post("/login")
            .then()
                .extract()
                .path("authorization");
        System.out.println(token);

        //Tentar inserir um produto com o valor 0.00 e validar que a mensagem de erro foi apresentada e o
        //status code 401

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"nome\": \"Teste produto\",\n" +
                        "  \"preco\": 0,\n" +
                        "  \"descricao\": \"Mouse\",\n" +
                        "  \"quantidade\": 5\n" +
                        "}")
            .when()
                .post("/produtos")
            .then()
                .assertThat()
                    .body("message", equalTo("preco deve ser um número positivo"))
                .statusCode(400);
    }
}
