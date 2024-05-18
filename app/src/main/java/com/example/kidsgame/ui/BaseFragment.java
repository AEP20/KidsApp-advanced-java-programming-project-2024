package com.example.kidsgame.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.kidsgame.R;
import com.example.kidsgame.util.LocaleHelper;
import com.example.kidsgame.viewmodel.LanguageViewModel;
import com.example.kidsgame.viewmodel.LoginViewModel;

import java.util.Locale;

public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    private LanguageViewModel languageViewModel;
    private LoginViewModel loginViewModel;
    private Locale currentLocale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        currentLocale = LocaleHelper.getCurrentLocale(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        languageViewModel = new ViewModelProvider(requireActivity()).get(LanguageViewModel.class);

        Button englishButton = view.findViewById(R.id.buttonEnglish);
        Button turkishButton = view.findViewById(R.id.buttonTurkish);

        if (englishButton != null && turkishButton != null) {
            // Listener fonksiyonlarını tanımlayın
            englishButton.setOnClickListener(this::onEnglishButtonClick);
            turkishButton.setOnClickListener(this::onTurkishButtonClick);
        } else {
            Log.e(TAG, "Button not found in view");
        }

        languageViewModel.getLanguage().observe(getViewLifecycleOwner(), locale -> {
            if (locale != null && !locale.equals(currentLocale)) {
                LocaleHelper.setLocale(requireContext(), locale);
                requireActivity().recreate();
            }
        });
    }

    private void onEnglishButtonClick(View view) {
        Log.d(TAG, "English button clicked");
        showAlert(getString(R.string.language_set_to_english));
        languageViewModel.setLanguage(Locale.ENGLISH);
    }

    private void onTurkishButtonClick(View view) {
        Log.d(TAG, "Turkish button clicked");
        showAlert(getString(R.string.language_set_to_turkish));
        languageViewModel.setLanguage(new Locale("tr"));
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.alert))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show();
    }
}
