package com.example.mobileapp.itf;

import com.example.mobileapp.model.Account;

import java.util.List;

public interface ProfileInterface {

    void onSuccess(Account account);

    void onError(List<String> errors);

}
