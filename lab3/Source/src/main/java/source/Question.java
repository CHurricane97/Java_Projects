package source;

public class Question {

    private String content;
    private String answer;
    private String answerGood;
    private String answerBad;

    public String getAnswerGood() {
        return answerGood;
    }

    public void setAnswerGood(String answerGood) {
        this.answerGood = answerGood;
    }

    public String getAnswerBad() {
        return answerBad;
    }

    public void setAnswerBad(String answerBad) {
        this.answerBad = answerBad;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
