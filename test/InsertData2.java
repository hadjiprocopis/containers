import	java.util.Arrays;

public class   InsertData2 {
        String name;
        int v[];
        public  InsertData2(String n, int av[]){ this.name = n; this.v = av; }
        public  String  toString(){ return "'"+name+"' : "+Arrays.toString(v); }
}
