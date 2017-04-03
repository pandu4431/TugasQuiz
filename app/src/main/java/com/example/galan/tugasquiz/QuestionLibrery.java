package com.example.galan.tugasquiz;

/**
 * Created by galan on 23/01/2017.
 */

public class QuestionLibrery {

    private String mQuestion[] = {
            "Apa nama ibukota Jawa Barat ?",
            "Apa nama makanan khas Madura ?",
            "Dimana letak candi Borobudur ?",
            "Siapakah Presiden RI Sekarang ?",
            "Bagaimana bentuk bumi ?"
    };

    private String mChoices[][] = {
            {"Cimahi", "Bandung", "Madiun"},
            {"Sate", "Lumpia", "Sarden"},
            {"Klaten", "Yogyakarta", "Wates"},
            {"Ir. Soekarno", "Bj. Habibie", "Joko Widodo"},
            {"Bulat","Segitiga","Tidak Kotak"}
    };

    private String mCorrectAnswer[] = {"Bandung", "Sate", "Klaten", "Joko Widodo","Tidak Kotak"};

    public String getQuestion(int a) {
        String question = mQuestion[a];
        return question;
    }

    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswer[a];
        return answer;
    }

}
