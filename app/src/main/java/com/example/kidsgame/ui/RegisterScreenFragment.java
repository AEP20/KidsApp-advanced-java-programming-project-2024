package com.example.kidsgame.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.kidsgame.model.RegisterResponse;
import com.example.kidsgame.util.Constants;
import com.example.kidsgame.util.LocaleHelper;
import com.example.kidsgame.viewmodel.LanguageViewModel;
import com.example.kidsgame.viewmodel.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterScreenFragment extends BaseFragment {

    private NavController navController;
    private EditText usernameInput, passwordInput;
    private TextView registerTitle;
    private LoginViewModel loginViewModel;
    private LanguageViewModel languageViewModel;
    private AuthService authService;
    private Button completeRegistrationButton, navigateLoginButton;

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
        return inflater.inflate(R.layout.fragment_register_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerTitle = view.findViewById(R.id.registerTitle);
        usernameInput = view.findViewById(R.id.usernameInputRegister);
        passwordInput = view.findViewById(R.id.passwordInputRegister);

        completeRegistrationButton = view.findViewById(R.id.completeRegistrationButton);  // Değişiklik yapıldı
        navigateLoginButton = view.findViewById(R.id.navigateLoginButtonRegister);  // Değişiklik yapıldı

        // NavController'u al
        navController = NavHostFragment.findNavController(this);

        // Buton tıklama olayını ayarla
        navigateLoginButton.setOnClickListener(this::onNavigateLoginButtonClick); // zaten hesabım var
        completeRegistrationButton.setOnClickListener(this::onCompleteRegistrationButtonClick); // hesabı şimdi oluitur

        // Dil değişikliği gözlemcisi ekle
        languageViewModel.getLanguage().observe(getViewLifecycleOwner(), locale -> {
            if (locale != null && !locale.equals(LocaleHelper.getCurrentLocale(requireContext()))) {
                LocaleHelper.setLocale(requireContext(), locale);
                requireActivity().recreate();
            }
        });

        // Başlangıçta metinleri ayarla
        updateTexts();
    }

    private void updateTexts() {
        registerTitle.setText(getString(R.string.register_title));
        usernameInput.setHint(getString(R.string.username_hint));
        passwordInput.setHint(getString(R.string.password_hint));
        completeRegistrationButton.setText(getString(R.string.create_account));
        navigateLoginButton.setText(getString(R.string.navigate_login));
    }

    public void onNavigateLoginButtonClick(View v) {
        navController.navigate(R.id.action_registerScreenFragment_to_loginScreenFragment);
    }

    public void onCompleteRegistrationButtonClick(View v) {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            showAlert(getString(R.string.error), getString(R.string.error_empty_username));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showAlert(getString(R.string.error), getString(R.string.error_empty_password));
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);
        authService.register(loginRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showAlert(getString(R.string.alert), getString(R.string.registration_successful));
                    loginViewModel.setUsername(username);
                    navController.navigate(R.id.action_registerScreenFragment_to_homeScreenFragment);
                } else {
                    showAlert(getString(R.string.registration_error), getString(R.string.username_already_exists));
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                showAlert(getString(R.string.error), getString(R.string.login_failure) + t.getMessage());
            }
        });
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }
}
