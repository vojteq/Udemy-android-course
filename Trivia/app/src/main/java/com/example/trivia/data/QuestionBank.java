package com.example.trivia.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
        private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
        ArrayList<Question> questions;

    public QuestionBank() {
        questions = new ArrayList<>();
    }

    public List<Question> getQuestions(final AnswerListAsynchResponse callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    questions.add(new Question(
                                            response.getJSONArray(i).getString(0),
                                            response.getJSONArray(i).getBoolean(1)
                                    ));
//                                    Log.d("NUMBER " + i, "onResponse: " + questions.get(i).toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (null != callback)
                                callback.processFinished(questions);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questions;
    }
}
