package org.byters.mockserver;

public class MockServer {

    public static String requestRegisterCodeConfirm(String textCheck) {
        return "{\"registered\":0}";
    }

    public static String requestCountryList() {
        return "{\"data\":[{\"title\":\"Russia\",\"code\":\"+7\"}," +
                "{\"title\":\"USA\",\"code\":\"+1\"}," +
                "{\"title\":\"Other\",\"code\":\"+3\"}]}";

    }

    public static String requestRegisterReferralDescription() {
        return "{\"title\":\"Claim 50 credits\",\"description\":\"blablabla\"}";
    }

    public static String requestRegisterReferral(String linkReferral, int sex, long dateBirthMillis) {
        return "123";
    }

    public static void requestRegisterCode(String phone) {

    }
}
