import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSONDecoder {
    public String Decoder(String data){
        String deData="";
        try{
            Object object= JSONValue.parse(data);
            JSONArray ja=(JSONArray) object;

            JSONObject word=(JSONObject) ja.get(0);

            deData=deData+"\n-Word:\t"+word.get("word");

            JSONArray jo1=(JSONArray) word.get("phonetics");
            JSONObject jo2=(JSONObject) jo1.get(0);
            deData="    "+jo2.get("text") + deData;

            JSONArray jo3=(JSONArray) word.get("meanings");
            JSONObject jo4=(JSONObject) jo3.get(0);
            deData=deData+"\n-Part Of Speech:\t"+jo4.get("partOfSpeech");

            JSONArray jo5=(JSONArray) jo4.get("definitions");
            for(int i=0;i<jo5.size();i++){
                JSONObject jo6=(JSONObject) jo5.get(i);
                deData=deData+"\n-Definition:\t"+jo6.get("definition");

                deData=deData+"\n-Example:\t"+jo6.get("example")+"\n\n";
            }

        }
        catch (Exception e){
            deData="error";
        }
        return deData;
    }
}