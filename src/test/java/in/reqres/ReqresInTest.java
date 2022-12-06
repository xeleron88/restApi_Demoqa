package in.reqres;

import in.reqres.models.*;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.IdUsersSpecs.idResponseUserSpec;
import static in.reqres.specs.IdUsersSpecs.idUsersSpec;
import static in.reqres.specs.RegisterUnsuccessfulSpecs.*;
import static org.assertj.core.api.Assertions.assertThat;
import static in.reqres.specs.UpdateUserSpecs.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class ReqresInTest {

    @Test
    void idUsersTest() {
        given()
                .spec(idUsersSpec)
                .when()
                .get("")
                .then()
                .spec(idResponseUserSpec)
                .body("data.id", hasItems(1, 2, 3));

    }

    @Test
    void singeUserTest() {

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    void singeUserNotFoundTest() {

        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void updateUserTest() {
        UpdateUserModel data = new UpdateUserModel();
        data.setName("qa_guru_user");
        data.setJob("qa tester");

        UpdateUserResponseModel response = (UpdateUserResponseModel) given()
                .spec(updateRequestUserSpec)
                .body(data)
                .when()
                .post()
                .then()
                .spec(updateResponseUserSpec)
                .extract().as(UpdateUserResponseModel.class);
        assertThat(response.getName()).isEqualTo("qa_guru_user");
        assertThat(response.getJob()).isEqualTo("qa tester");

    }

    @Test
    void registerUnsuccessfulTest() {
        RegisterUnsuccessfulModel data = new RegisterUnsuccessfulModel();
        data.setEmail("sydney@fife");
        RegisterUnsuccessfulModel response = (RegisterUnsuccessfulModel) given()
                .spec(requestRegisterUnsuccessful)
                .body(data)
                .when()
                .post()
                .then()
                .spec(responseRegisterUnsuccessful)
                .extract().as(RegisterUnsuccessfulModel.class);

        assertThat(response.getError()).isEqualTo("Missing password");

    }

}
