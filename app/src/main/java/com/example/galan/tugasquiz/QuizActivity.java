package com.example.galan.tugasquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private QuestionLibrery mQuestionLibrary = new QuestionLibrery();
    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoices1;
    private EditText sAnswer;
    private Button mNext;
    private Button mCek;
    private TextView mblank;
    private TextView hint;
    private String mAnswer;
    private int mScore = 0;
    private int mQuestinNumber = 0;

    public void updateQuestion() {

        if (mQuestinNumber != 5) {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestinNumber));
            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestinNumber);
            String clues = mAnswer;
            String text2 = clues.replaceAll("[a-zA-Z0-9]", "*");
            mblank.setText(text2);
            mQuestinNumber++;
        } else {
            Intent myIntent = new Intent(QuizActivity.this, ScoreActivity.class);
            myIntent.putExtra("result", mScore);
            startActivity(myIntent);

            Toast.makeText(QuizActivity.this, "SELAMAT, SUDAH SELESAI, POIN ANDA", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builders = new AlertDialog.Builder(QuizActivity.this);
            builders.setMessage("Apakah Anda Ingin Mengulanginya ?");
            builders.setCancelable(true);
            builders.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    reset();
                }
            });
            builders.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog alert = builders.create();
            alert.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoices1 = (Button) findViewById(R.id.submit);
        sAnswer = (EditText) findViewById(R.id.jawaban);
        mblank = (TextView) findViewById(R.id.blank);
        mNext = (Button) findViewById(R.id.next);
        mCek = (Button) findViewById(R.id.scorecek);
        hint = (TextView) findViewById(R.id.hintNum);
        updateQuestion();

        sAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAnswer.getText().clear();
            }
        });

        mCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(QuizActivity.this, ScoreActivity.class);
                myIntent.putExtra("result", mScore);
                startActivity(myIntent);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            int xas = 10;

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                builder.setMessage("Apakah Anda Yakin Ingin Pakai Hint ?");
                builder.setCancelable(true);
                builder.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (xas != 0) {
                            int y = 0;
                            String clues = mAnswer;
                            int iC = clues.length();
                            Random x = new Random();
                            int b = x.nextInt(iC);
                            while (y != b) {
                                char clone = clues.charAt(b);
                                String text2 = (String) mblank.getText();
                                StringBuilder xs = new StringBuilder(text2);
                                xs.setCharAt(b, clone);
                                mblank.setText(xs);
                                xas--;
                                hint.setText("" + xas);
                                y = b;
                            }

                        } else {
                            Toast.makeText(QuizActivity.this, "YAHHH, HINTNYA HABIS DEH", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        sAnswer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    getCorrect();
                }
                return false;
            }
        });


        mButtonChoices1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCorrect();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                break;
            case R.id.reset:
                reset();
                break;
            case R.id.setting:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateScore(int point) {
        mScoreView.setText("" + mScore);
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setMessage("Apakah Anda Yakin Ingin Keluar");
        builder.setCancelable(true);
        builder.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void reset() {
        mQuestinNumber = 0;
        mScore = 0;
        mScoreView.setText("0");
        mQuestionView.setText(mQuestionLibrary.getQuestion(0));
        mAnswer = mQuestionLibrary.getCorrectAnswer(0);
        String clues = mAnswer;
        String text2 = clues.replaceAll("[a-zA-Z0-9]", "*");
        mblank.setText(text2);
    }

    public void share() {
        Intent shareActions = new Intent(Intent.ACTION_SEND);
        shareActions.setType("text/plain");
        shareActions.putExtra(Intent.EXTRA_SUBJECT, "Lontong Quiz");
        shareActions.putExtra(Intent.EXTRA_TEXT, "[ " + mScore + " ]" + " Ini Skorku di Quiz Lontong, mana skormu ?");
        startActivity(Intent.createChooser(shareActions, "Share Via"));
    }

    public void getCorrect() {
        String xs = sAnswer.getText().toString();
        if (xs.equalsIgnoreCase(mAnswer)) {
            mScore = mScore + 10;
            updateScore(mScore);
            updateQuestion();
            sAnswer.getText().clear();
            Toast.makeText(QuizActivity.this, "CORRECT ANSWER, GOOD", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(QuizActivity.this, "WRONG ANSWER", Toast.LENGTH_SHORT).show();
        }
    }
}
