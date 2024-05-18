package com.example.kidsgame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.kidsgame.R;
import com.example.kidsgame.model.FourChoiceQuestion;
import com.example.kidsgame.model.FourChoiceQuestionResponse;
import com.example.kidsgame.api.AuthService;
import com.example.kidsgame.api.RetrofitClient;
import com.example.kidsgame.util.Constants;
import com.example.kidsgame.util.LocaleHelper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizScreenOneFragment extends Fragment {

    private NavController navController;
    private ImageView myImageView;
    private TextView questionTextView;
    private Button choiceAButton, choiceBButton, choiceCButton, choiceDButton, backToMenuButtonOne;
    private AuthService authService;
    private List<FourChoiceQuestion> questions;
    private int currentQuestionIndex = 0;
    private ConstraintLayout constraintLayout;

    private static final String[] motivationalMessages = {
            "Great job!",
            "Well done!",
            "Keep it up!",
            "You're amazing!",
            "Fantastic!",
            "Excellent!",
            "You're doing great!",
            "Awesome!",
            "Impressive!",
            "Outstanding!"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrofit istemcisi oluştur
        Retrofit retrofit = RetrofitClient.getClient(Constants.BASE_URL);
        authService = retrofit.create(AuthService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_screen_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backToMenuButtonOne = view.findViewById(R.id.backToMenuButtonOne);
        myImageView = view.findViewById(R.id.myImageView);
        questionTextView = view.findViewById(R.id.questionTextView);
        choiceAButton = view.findViewById(R.id.choiceAButton);
        choiceBButton = view.findViewById(R.id.choiceBButton);
        choiceCButton = view.findViewById(R.id.choiceCButton);
        choiceDButton = view.findViewById(R.id.choiceDButton);
        constraintLayout = view.findViewById(R.id.frameLayout3);

        navController = NavHostFragment.findNavController(this);

        backToMenuButtonOne.setOnClickListener(this::onbackToMenuButtonClick);

        // Soruları API'den çek ve göster
        fetchQuestions();
    }

    private void fetchQuestions() {
        String language = LocaleHelper.getCurrentLocale(getContext()).getLanguage();
        authService.getQuestions("clock", language).enqueue(new Callback<FourChoiceQuestionResponse<FourChoiceQuestion>>() {
            @Override
            public void onResponse(Call<FourChoiceQuestionResponse<FourChoiceQuestion>> call, Response<FourChoiceQuestionResponse<FourChoiceQuestion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    questions = response.body().getData();
                    if (!questions.isEmpty()) {
                        // İlk soruyu göster
                        displayQuestion(currentQuestionIndex);
                    }
                } else {
                    showAlert("QuizScreenOneFragment", "Failed to fetch questions: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<FourChoiceQuestionResponse<FourChoiceQuestion>> call, Throwable t) {
                showAlert("QuizScreenOneFragment", "Failed to fetch questions: " + t.getMessage());
            }
        });
    }

    private void displayQuestion(int index) {
        if (index < questions.size()) {
            FourChoiceQuestion question = questions.get(index);
            questionTextView.setText(question.getQuestionString());
            choiceAButton.setText("A: " + question.getChoiceA());
            choiceBButton.setText("B: " + question.getChoiceB());
            choiceCButton.setText("C: " + question.getChoiceC());
            choiceDButton.setText("D: " + question.getChoiceD());

            choiceAButton.setOnClickListener(v -> checkAnswer("A", question));
            choiceBButton.setOnClickListener(v -> checkAnswer("B", question));
            choiceCButton.setOnClickListener(v -> checkAnswer("C", question));
            choiceDButton.setOnClickListener(v -> checkAnswer("D", question));

            // Görseli yükleyip ImageView'da göster
            try {
                InputStream inputStream = getActivity().getAssets().open("clockQuestionsAssets/" + question.getQuestion() + ".png");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                // ImageView boyutlarını ayarla
                ViewGroup.LayoutParams layoutParams = myImageView.getLayoutParams();
                layoutParams.width = 600; // genişliği artır
                layoutParams.height = 600; // yüksekliği artır
                myImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Görsel Yükleme Hatası", "Görsel Yükleme Hatası: " + e.getMessage());
            }
        } else {
            showAlert("Quiz Completed", "You have completed all questions. Returning to main menu.");
            navController.navigate(R.id.action_quizScreenOneFragment_to_homeScreenFragment);
        }
    }

    private void checkAnswer(String selectedAnswer, FourChoiceQuestion question) {
        if (selectedAnswer.equals(question.getCorrectAnswer())) {
            showMotivationalMessage();
            currentQuestionIndex++;
            displayQuestion(currentQuestionIndex);
        } else {
            showAlert("Incorrect Answer", "That is not the correct answer. Please try again.");
        }
    }

    private void showMotivationalMessage() {
        Random random = new Random();
        String message = motivationalMessages[random.nextInt(motivationalMessages.length)];

        TextView motivationalTextView = new TextView(getContext());
        motivationalTextView.setText(message);
        motivationalTextView.setTextSize(18);
        motivationalTextView.setTextColor(getResources().getColor(R.color.purple_500));
        motivationalTextView.setId(View.generateViewId()); // ID atandı
        motivationalTextView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        constraintLayout.addView(motivationalTextView);

        // Rastgele bir konum belirle
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(motivationalTextView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, random.nextInt(800));
        constraintSet.connect(motivationalTextView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, random.nextInt(800));
        constraintSet.applyTo(constraintLayout);

        // Mesajın kaybolması için bir handler kullan
        Handler handler = new Handler();
        handler.postDelayed(() -> constraintLayout.removeView(motivationalTextView), 1500);
    }

    public void onbackToMenuButtonClick(View v) {
        navController.navigate(R.id.action_quizScreenOneFragment_to_homeScreenFragment);
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Tamam", null)
                .show();
    }
}
