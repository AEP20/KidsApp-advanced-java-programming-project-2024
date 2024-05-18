package com.example.kidsgame.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.kidsgame.R;
import com.example.kidsgame.util.LocaleHelper;
import com.example.kidsgame.viewmodel.LanguageViewModel;
import com.example.kidsgame.viewmodel.LoginViewModel;

import java.util.Random;

public class HomeScreenFragment extends BaseFragment {

    private LoginViewModel loginViewModel;
    private LanguageViewModel languageViewModel;
    private TextView usernameText;
    private NavController navController;
    private Button buttonHome;

    private String[] clockAssets = {
            "clockQuestionsAssets/WallClock_1245_about.png",
            "clockQuestionsAssets/WallClock_1625.png", "clockQuestionsAssets/WallClock_1705_about.png", "clockQuestionsAssets/WallClock_1950.png",
            "clockQuestionsAssets/WallClock_2130_about.png"
    };

    private String[] mathAssets = {
            "mathQuestionsAssets/math_question3x7.png", "mathQuestionsAssets/math_question4x3.png", "mathQuestionsAssets/math_question6x7.png",
            "mathQuestionsAssets/math_question7x7.png", "mathQuestionsAssets/math_question8x6.png", "mathQuestionsAssets/math_question8x9.png",
            "mathQuestionsAssets/math_question9x5.png", "mathQuestionsAssets/math_question10x6.png"
    };

    private String[] seasonsAssets = {
            "seasonsQuestionsAssets/Fall_1.gif", "seasonsQuestionsAssets/Fall_2.gif", "seasonsQuestionsAssets/Spring_1.gif", "seasonsQuestionsAssets/Spring_2.gif",
            "seasonsQuestionsAssets/Summer_1.gif", "seasonsQuestionsAssets/Summer_2.gif", "seasonsQuestionsAssets/Winter_1.gif", "seasonsQuestionsAssets/Winter_2.gif"
    };

    private String[] directionAssets = {
            "directionQuestionsAssets/blue_pencil_at_the_back_of_red_pencil.png", "directionQuestionsAssets/blue_pencil_in_front_of_coffe.png",
            "directionQuestionsAssets/computer_at_the_left_of_coffe.png", "directionQuestionsAssets/deer_at_the_back_of_wolf.png",
            "directionQuestionsAssets/orange_car_at_the_right_of_red_car.png", "directionQuestionsAssets/wolf_in_front_of_deer.png",
            "directionQuestionsAssets/yellow_car_at_the_left_of_blue_car.png"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        languageViewModel = new ViewModelProvider(requireActivity()).get(LanguageViewModel.class);

        // Önceden seçilen dili ayarla
        LocaleHelper.setLocale(requireContext(), languageViewModel.getLanguage().getValue());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameText = view.findViewById(R.id.homeUsernameTextView);
        buttonHome = view.findViewById(R.id.buttonHome);

        // NavController'u al
        navController = NavHostFragment.findNavController(this);

        // Kullanıcı bilgilerini ViewModel'den al ve TextView'lere set et
        loginViewModel.getUsername().observe(getViewLifecycleOwner(), username -> usernameText.setText(username));


            buttonHome.setText(R.string.logout_button);

        buttonHome.setOnClickListener(this::onLogoutButtonClick);

        // GridLayout'taki butonları ayarla
        int[] buttonIds = {R.id.gridButton1, R.id.gridButton2, R.id.gridButton3, R.id.gridButton4};
        String[][] assets = {clockAssets, mathAssets, seasonsAssets, directionAssets};

        for (int i = 0; i < buttonIds.length; i++) {
            LinearLayout buttonLayout = view.findViewById(buttonIds[i]);
            setButtonImage(buttonLayout, assets[i], i);
            buttonLayout.setOnClickListener(this::onGridButtonClick);
        }
    }

    private void setButtonImage(LinearLayout buttonLayout, String[] assets, int index) {
        Random random = new Random();
        String asset = assets[random.nextInt(assets.length)];
        String assetPath = "file:///android_asset/" + asset;

        ImageView imageView = buttonLayout.findViewById(getResources().getIdentifier("gridButtonImage" + (index + 1), "id", getContext().getPackageName()));
        TextView textView = buttonLayout.findViewById(getResources().getIdentifier("gridButtonText" + (index + 1), "id", getContext().getPackageName()));

        // Görselin boyutunu ayarla ve scaleType'ı CENTER_CROP yap
        int sizeInDp = 100;
        int sizeInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, getResources().getDisplayMetrics());
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = sizeInPx;
        layoutParams.height = sizeInPx;
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(this)
                .load(assetPath)
                .into(imageView);

        // Dil ayarlarına göre buton metinlerini belirleyin
        if (LocaleHelper.getCurrentLocale(requireContext()).getLanguage().equals("tr")) {
            switch (index) {
                case 0:
                    textView.setText(R.string.quiz_clock);
                    break;
                case 1:
                    textView.setText(R.string.quiz_math);
                    break;
                case 2:
                    textView.setText(R.string.quiz_seasons);
                    break;
                case 3:
                    textView.setText(R.string.quiz_directions);
                    break;
            }
        } else {
            switch (index) {
                case 0:
                    textView.setText(R.string.quiz_clock);
                    break;
                case 1:
                    textView.setText(R.string.quiz_math);
                    break;
                case 2:
                    textView.setText(R.string.quiz_seasons);
                    break;
                case 3:
                    textView.setText(R.string.quiz_directions);
                    break;
            }
        }
    }

    public void onLogoutButtonClick(View v) {
        // Kullanıcı çıkış yaptığında ViewModel'deki bilgileri temizle
        loginViewModel.clearData();

        // Login ekranına geri dön
        navController.navigate(R.id.action_homeScreenFragment_to_loginScreenFragment);
    }

    public void onGridButtonClick(View v) {
        int id = v.getId();
        if (id == R.id.gridButton1) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenOneFragment);
        } else if (id == R.id.gridButton2) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenTwoFragment);
        } else if (id == R.id.gridButton3) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenThreeFragment);
        } else if (id == R.id.gridButton4) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenFourFragment);
        }
        else if (id == R.id.gridButton5) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenFiveFragment);
        } else if (id == R.id.gridButton6) {
            navController.navigate(R.id.action_homeScreenFragment_to_quizScreenSixFragment);
        }
    }
}
