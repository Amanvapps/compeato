package com.appinnovates.campeat.services.ForgotPasswordService;

public interface ForgotPasswordServiceViewInterface {
    void onForgotPasswordSuccess(String email);
    void onForgotPasswordFailure(String message);
    void showProgress();
    void hideProgress();
}
