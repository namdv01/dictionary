package sample;

public class Word {
    protected String word;
    protected String tranWord;
    protected String pronounWord;
    public Word(String word){
        this.word=word;
        this.pronounWord="";
        this.tranWord="";
    }
    public Word(String word,String tranWord,String pronounWord){
        this.word=word;
        this.tranWord=tranWord;
        this.pronounWord=pronounWord;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranWord() {
        return tranWord;
    }

    public void setTranWord(String tranWord) {
        this.tranWord = tranWord;
    }

    public String getPronounWord() {
        return pronounWord;
    }

    public void setPronounWord(String pronounWord) {
        this.pronounWord = pronounWord;
    }
}
