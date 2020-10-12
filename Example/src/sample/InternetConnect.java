package sample;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InternetConnect {
    public String getOnlineData(String word){
        String data="";
        String decodeData="";
        try{

            URL url=new URL("https://api.dictionaryapi.dev/api/v2/entries/en/"+word);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            if(con.getResponseCode()==200){
                InputStream im=con.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(im));
                String line=br.readLine();
                while(line!=null){
                    data=data+line+"\n";
                    line=br.readLine();
                }
                br.close();
                JSONDecoder jd=new JSONDecoder();
                decodeData=jd.Decoder(data);
            }
            else{
                decodeData="error";
                //System.out.println("Error");
            }
        }
        catch (Exception e){
            try{
                decodeData="error";
                System.out.println(e);
            }
            catch (Exception e1){
                System.out.println(e1);
            }
        }
        return decodeData;
    }
}
