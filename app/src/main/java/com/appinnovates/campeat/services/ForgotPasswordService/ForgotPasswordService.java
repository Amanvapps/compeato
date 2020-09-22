package com.appinnovates.campeat.services.ForgotPasswordService;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordService{

    private static ForgotPasswordService forgotPasswordService = null;
    private ForgotPasswordServiceViewInterface forgotPasswordServiceDelegate = null;

    public static ForgotPasswordService getInstance(){
        if (forgotPasswordService == null){
            forgotPasswordService = new ForgotPasswordService();
        }
        return forgotPasswordService;
    }

    public void setDelegate(ForgotPasswordServiceViewInterface forgotPasswordServiceDelegate){
        this.forgotPasswordServiceDelegate = forgotPasswordServiceDelegate;
    }

    public void requestResetPassword(final String email){
        forgotPasswordServiceDelegate.showProgress();
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                forgotPasswordServiceDelegate.hideProgress();
                // An email was successfully sent with reset instructions.
                if (e == null) {
                    forgotPasswordServiceDelegate.onForgotPasswordSuccess(email);
                } else {
                    // Something went wrong. Look at the ParseException to see what's up.
                    forgotPasswordServiceDelegate.onForgotPasswordFailure(e.getMessage());
                }
            }
        });

    }
}
