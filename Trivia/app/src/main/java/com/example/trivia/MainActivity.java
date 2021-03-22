package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsynchResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String QUIZ_DATA = "quiz data";
    private static final String CURRENT_QUESTION_INDEX = "current question index";
    private static final String CURRENT_SCORE = "current score";
    private static final String BEST_SCORE = "best score";
    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex;
    private List<Question> questionList;

    private TextView bestScoreTextVIew;
    private TextView currentScoreTextView;
    private Button resetButton;
    private Button resetBestButton;
    private int currentScore;
    private int bestScore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private SoundPool soundPool;
    private int soundCorrectID, soundIncorrectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionCounterTextView = findViewById(R.id.counter_textView);
        questionTextView = findViewById(R.id.question_textview);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(audioAttributes)
                .build();
        soundCorrectID = soundPool.load(this, R.raw.correct, 1);
        soundIncorrectID = soundPool.load(this, R.raw.incorrect, 1);

        questionList = new QuestionBank().getQuestions(new AnswerListAsynchResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                Log.d("MAIN", "onCreate: " + questionArrayList);

//                questionTextView.setText(questionArrayList
//                        .get(currentQuestionIndex)
//                        .getQuestionText());
                updateQuestion();
            }
        });
//        Log.d("MAIN", "onCreate: " + questionList);


        resetButton = findViewById(R.id.reset_button);
        resetBestButton = findViewById(R.id.reset_best_button);
        bestScoreTextVIew = findViewById(R.id.best_score_textView);
        currentScoreTextView = findViewById(R.id.current_score_text_view);

        resetButton.setOnClickListener(this);
        resetBestButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(QUIZ_DATA, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        currentQuestionIndex = sharedPreferences.getInt(CURRENT_QUESTION_INDEX, 0);
        currentScore = sharedPreferences.getInt(CURRENT_SCORE, 0);
        bestScore = sharedPreferences.getInt(BEST_SCORE, 0);

        bestScoreTextVIew.setText(MessageFormat
                .format("{0} {1}", getString(R.string.best_score_text), bestScore));
        currentScoreTextView.setText(MessageFormat
                .format("{0} {1}", getString(R.string.current_score_text), currentScore));


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bestScore < currentScore)
            editor.putInt(BEST_SCORE, currentScore);
        editor.putInt(CURRENT_SCORE, currentScore);
        editor.putInt(CURRENT_QUESTION_INDEX, currentQuestionIndex);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_button:
                if (currentQuestionIndex > 0)
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                else
                    currentQuestionIndex = questionList.size() - 1;
                updateQuestion();
                break;
            case R.id.next_button:
//                if (currentQuestionIndex < questionList.size() - 1)
//                    currentQuestionIndex++;
//                else
//                    currentQuestionIndex = 0;
                currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
//                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
//                updateQuestion();
                break;
            case R.id.reset_button:
                if (bestScore < currentScore) {
                    bestScore = currentScore;
                    bestScoreTextVIew.setText(MessageFormat
                            .format("{0} {1}", getString(R.string.best_score_text), bestScore));
                }
                currentScore = 0;
                currentQuestionIndex = 0;
                currentScoreTextView.setText(MessageFormat
                        .format("{0} {1}", getString(R.string.current_score_text), 0));
                updateQuestion();
                break;
            case R.id.reset_best_button:
                bestScore = 0;
                editor.putInt(BEST_SCORE, bestScore);
                editor.apply();
                bestScoreTextVIew.setText(MessageFormat
                        .format("{0} {1}", getString(R.string.best_score_text), bestScore));
        }
//        updateQuestion();
    }

    private void updateQuestion() {
        questionTextView.setText(questionList.get(currentQuestionIndex).getQuestionText());
        questionCounterTextView.setText(new StringBuilder()
                .append(currentQuestionIndex + 1)
                .append(" / ")
                .append(questionList.size()).toString());
    }

    private void checkAnswer(boolean answer) {
        if (answer == questionList.get(currentQuestionIndex).getAnswer()) {
            Toast.makeText(this,
                    "Correct, the answer is " + answer + " :)",
                    Toast.LENGTH_SHORT).show();
//            flipAnimation();
            fadeView();
            currentScore++;
            soundPool.play(soundCorrectID, 1, 1, 1, 0, 1);
        }
        else {
            Toast.makeText(this,
                    "Incorrect, the answer is " + !answer + " :(",
                    Toast.LENGTH_SHORT).show();
            shakeAnimation();
            currentScore--;
            soundPool.play(soundIncorrectID, 1, 1, 1, 0, 1);
        }
        currentQuestionIndex++;
        currentScoreTextView.setText(MessageFormat
                .format("{0} {1}", getString(R.string.current_score_text), currentScore));
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                cardView.setBackgroundColor(Color.RED);
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                cardView.setBackgroundColor(Color.WHITE);
                cardView.setCardBackgroundColor(Color.WHITE);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    private void flipAnimation() {
//        ObjectAnimator flip = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flip_animation);
//        final CardView cardView = findViewById(R.id.cardView);
//        flip.setTarget(cardView);
//        flip.start();
//    }

    private void fadeView() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                cardView.setBackgroundColor(Color.GREEN);
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                cardView.setBackgroundColor(Color.WHITE);
                cardView.setCardBackgroundColor(Color.WHITE);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}