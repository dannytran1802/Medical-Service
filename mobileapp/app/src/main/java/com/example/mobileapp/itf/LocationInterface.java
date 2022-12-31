package com.example.mobileapp.itf;

import java.util.List;

public interface LocationInterface {

    void onSuccessLocation();

    void onError(List<String> errors);

}
