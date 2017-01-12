package com.beginner.iciolpan.questionnaire;

/**
 * Created by iciolpan on 11/19/2016.
 */

public class Question {

    int _id;
    String _question, _first_answer, _second_answer,_third_answer, _correct_answer;

    public Question(){}

    public Question(int _id, String _question, String _first_answer, String _second_answer, String _third_answer, String _correct_answer) {
        this._id = _id;
        this._question = _question;
        this._first_answer = _first_answer;
        this._second_answer = _second_answer;
        this._third_answer = _third_answer;
        this._correct_answer = _correct_answer;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_question() {
        return _question;
    }

    public void set_question(String _question) {
        this._question = _question;
    }

    public String get_first_answer() {
        return _first_answer;
    }

    public void set_first_answer(String _first_answer) {
        this._first_answer = _first_answer;
    }

    public String get_second_answer() {
        return _second_answer;
    }

    public void set_second_answer(String _second_answer) {
        this._second_answer = _second_answer;
    }

    public String get_third_answer() {
        return _third_answer;
    }

    public void set_third_answer(String _third_answer) {
        this._third_answer = _third_answer;
    }

    public String get_correct_answer() {
        return _correct_answer;
    }

    public void set_correct_answer(String _correct_answer) {
        this._correct_answer = _correct_answer;
    }
}
