package sample;

import java.io.*;
import java.util.*;

public class Dictionary {
    protected ArrayList<Word> list;

    public Dictionary() throws FileNotFoundException {
        list = new ArrayList<Word>();

        File file;
        file = new File("dataWordforDictionary.txt");

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String w = scanner.next();
            Word word = new Word(w);
            list.add(word);
        }
    }

    public Dictionary(Word word) {
        list.add(word);
    }

    public static Comparator<Word> compare = new Comparator<Word>() {
        @Override
        public int compare(Word o1, Word o2) {

            return o1.getWord().compareTo(o2.getWord());

        }
    };

    public boolean searchTranWord(String word) {
//        int left=0,right=list.size()-1;
//        int middle;
//        while(left<=right){
//            middle=(left+right)/2;
//            if(list.get(middle).getWord().compareTo(word)==0) return true;
//            if(list.get(middle).getWord().compareTo(word)>0){
//                right=middle-1;
//            }
//            else{
//                left=middle+1;
//            }
//        }
//        return false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWord().compareTo(word) == 0) return true;
        }
        return false;
    }

    public void sortDictionary() {
        Collections.sort(list, compare);
    }

    public void removeWord(Word word) {
        list.remove(word);
    }

    public void fixWord(Word word, Word fixWord) {
        if (searchTranWord(word.getWord()) == true) {
            list.remove(word);
            list.add(fixWord);
        }
    }
    public void sout(){
        for(Word i:list){
            System.out.println("\n"+i.getWord());
        }
    }
    public void addWord(Word word) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        list.add(word);
        try {
            fw = new FileWriter("dataWordforDictionary.txt", true);
            bw = new BufferedWriter(fw);
            String content = "\n" + word.getWord();
            bw.write(content);
            System.out.println("Ghi them noi dung file xong!");
            list.add(word);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }


        }


    }
}
