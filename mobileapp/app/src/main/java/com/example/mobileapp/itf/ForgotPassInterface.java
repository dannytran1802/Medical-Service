package com.example.mobileapp.itf;

import java.util.List;

public interface ForgotPassInterface {

    void onSuccess();

    void onError(List<String> errors);

}
