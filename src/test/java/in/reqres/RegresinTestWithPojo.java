package in.reqres;

import in.reqres.models.UpdateUserModel;
import in.reqres.models.UpdateUserPojoModel;
import in.reqres.models.UpdateUserResponseModel;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.UpdateUserSpecs.updateRequestUserSpec;
import static in.reqres.specs.UpdateUserSpecs.updateResponseUserSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegresinTestWithPojo {
    @Test
    void updateUserTestWithPojo() {
        UpdateUserPojoModel data = new UpdateUserPojoModel();
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
}
