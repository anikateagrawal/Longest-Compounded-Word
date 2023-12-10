import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Longest_compounded_word {
    public static void main(String[] args) throws FileNotFoundException {

        File f=new File("./src/Input_01.txt");
        File f2=new File("./src/Input_02.txt");

        long start=System.currentTimeMillis();

        Scanner sc=new Scanner(f);
        List<String> words=new ArrayList<>();
        while (sc.hasNextLine())
            words.add(sc.nextLine());

        Queue<String[]> q=new LinkedList<>();
        Trie trie=new Trie();
        for(String s:words){
            List<Integer> indexes=trie.getPrefixes(s);
            for(int i:indexes){
                q.add(new String[]{s,s.substring(i+1)});
            }
            trie.insert(s);
        }

        List<String > com=new ArrayList<>();
        while (!q.isEmpty()){
            String w[]=q.poll();
            if(w[1].equals("")){
                com.add(w[0]);
                continue;
            }
            List<Integer> pr=trie.getPrefixes(w[1]);
            for(int i:pr){
                q.add(new String[]{w[0],w[1].substring(i+1)});
            }
        }

        String longest="",second="";
        int max=0;
        for(String w:com){
            if(w.length()>max){
                max=w.length();
                second=longest;
                longest=w;
            }
        }

        long end=System.currentTimeMillis();
        System.out.println("Longest Compounded Word = "+longest);
        System.out.println("Second Longest Compounded Word = "+second);
        System.out.println("Time Taken to Process "+f+" = "+(end-start)+" ms");
    }
}

class Trie{
    class Node{
        boolean isWord;
        Node children[]=new Node[26];
    }

    Node root;
    public Trie(){
        root=new Node();
    }

    public void insert(String s){
        Node curr=root;
        for(int i=0;i<s.length();i++){
            int in=s.charAt(i)-'a';
            if(curr.children[in]==null){
                curr.children[in]=new Node();
            }
            curr=curr.children[in];
        }
        curr.isWord=true;
    }

    public List<Integer> getPrefixes(String s){
        Node curr=root;
        List<Integer> l=new ArrayList<>();
        for(int i=0;i<s.length();i++){
            int in=s.charAt(i)-'a';
            if(curr.children[in]==null)return l;
            curr=curr.children[in];
            if(curr.isWord)l.add(i);
        }
        return l;
    }
}
