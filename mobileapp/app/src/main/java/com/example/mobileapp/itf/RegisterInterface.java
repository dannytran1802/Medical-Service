package com.example.mobileapp.itf;

import java.util.List;

public interface RegisterInterface {

    void onSuccess();

    void onError(List<String> errors);

}
