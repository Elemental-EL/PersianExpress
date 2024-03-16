package org.example.persianexpress.Objects;

import java.util.Date;

public class Suggestion {
    private int suggestionID;
    private Date suggestionDate;
    private String suggestionContext;

    private Suggestion(){
    }

    public Suggestion(int suggestionID) {
        this.suggestionID = suggestionID;
    }

    public int getSuggestionID() {
        return suggestionID;
    }

    public Date getSuggestionDate() {
        return suggestionDate;
    }

    public void setSuggestionDate(Date suggestionDate) {
        this.suggestionDate = suggestionDate;
    }

    public String getSuggestionContext() {
        return suggestionContext;
    }

    public void setSuggestionContext(String suggestionContext) {
        this.suggestionContext = suggestionContext;
    }
}
