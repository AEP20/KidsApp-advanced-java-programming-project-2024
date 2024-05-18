package com.example.kidsgame.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kidsgame.R;
import com.example.kidsgame.api.AuthService;
import com.example.kidsgame.api.RetrofitClient;
import com.example.kidsgame.model.LoginRequest;
import com.example.kidsgame.model.LoginResponse;
import com.example.kidsgame.util.Constants;
import com.example.kidsgame.util.LocaleHelper;
import com.example.kidsgame.viewmodel.LanguageViewModel;
import com.example.kidsgame.viewmodel.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginScreenFragment extends BaseFragment {

    private EditText usernameInput, passwordInput;
    private NavController navController;
    private LoginViewModel loginViewModel;
    private AuthService authService;
    private LanguageViewModel languageViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        languageViewModel = new ViewModelProvider(requireActivity()).get(LanguageViewModel.class);

        // Retrofit istemcisi oluştur
        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL);
        authService = retrofit.create(AuthService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameInput = view.findViewById(R.id.usernameInputLogin);
        passwordInput = view.findViewById(R.id.passwordInputLogin);
        TextView loginTitle = view.findViewById(R.id.loginTitle);
        Button loginButton = view.findViewById(R.id.buttonLogin);
        Button navigateRegistrationButton = view.findViewById(R.id.navigateRegistrationButtonRegister);

        navController = NavHostFragment.findNavController(this);

        loginTitle.setText(getString(R.string.login_title));
        usernameInput.setHint(getString(R.string.username_hint));
        passwordInput.setHint(getString(R.string.password_hint));
        loginButton.setText(getString(R.string.login_button));
        navigateRegistrationButton.setText(getString(R.string.register_button));

        loginButton.setOnClickListener(this::onLoginButtonClick);
        navigateRegistrationButton.setOnClickListener(this::onNavigateRegisterButtonClick);

        // Dil değişikliği gözlemcisi ekle
        languageViewModel.getLanguage().observe(getViewLifecycleOwner(), locale -> {
            if (locale != null && !locale.equals(LocaleHelper.getCurrentLocale(requireContext()))) {
                LocaleHelper.setLocale(requireContext(), locale);
                requireActivity().recreate();
            }
        });
    }

    public void onLoginButtonClick(View v) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            showAlert(getString(R.string.error_empty_username));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showAlert(getString(R.string.error_empty_password));
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);
        authService.login(loginRequest).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginViewModel.setUsername(username);
                    navController.navigate(R.id.action_loginScreenFragment_to_homeScreenFragment);

                    // Log the response here
                    LoginResponse loginResponse = response.body();
                    Log.d("LoginResponse", "Response code: " + response.code());
                    Log.d("LoginResponse", "Response message: " + response.message());
                    Log.d("LoginResponse", "Response body: " + loginResponse.toString());
                } else {
                    showAlert(getString(R.string.login_error));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showAlert(getString(R.string.login_failure) + t.getMessage());
            }
        });
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.error))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }

    public void onNavigateRegisterButtonClick(View v) {
        navController.navigate(R.id.action_loginScreenFragment_to_registerScreenFragment);
    }
}
