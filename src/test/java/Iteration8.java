import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(DataProviderRunner.class)
public class Iteration8 {

    @DataProvider
    public static Object[][] createTestDataObject() {
        return new Object[][] {
            {"500", "50", "13122", true},
            {"10000", "100", "13122", true},
            {"9999", "99", "13122", true}
        };
    }

    @Test
    @UseDataProvider("createTestDataObject")
    public void DoLoanRequest_UsingSpecifiedAmounts_ShouldBeAcceptedOrDeniedAsExpected
        (String loanAmount, String downPayment, String fromAccountId, boolean expectedStatus) {

        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            queryParam("customerId", "12212").
            queryParam("amount",loanAmount).
            queryParam("downPayment", downPayment).
            queryParam("fromAccountId", fromAccountId).
        when().
            post("http://localhost:8080/parabank/services/bank/requestLoan").
        then().
            log().body().
        and().
            assertThat().
            body("approved", equalTo(expectedStatus));
    }
}
