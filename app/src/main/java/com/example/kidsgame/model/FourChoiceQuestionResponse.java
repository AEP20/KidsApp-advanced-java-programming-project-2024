package com.example.kidsgame.model;

        import com.google.gson.annotations.SerializedName;
        import java.util.List;

public class FourChoiceQuestionResponse<T> {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<T> data;

    @SerializedName("status")
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
