package main;

public class point {
    int x;
    int y;

    public point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public point(String s){
        this.x = s.charAt(1)-49;
        this.y = s.charAt(0)-97;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    @Override
    public String toString() {
        return ""+(char)(y+97)+(char)(x+49)+" ("+x+","+y+")";
    }
}
