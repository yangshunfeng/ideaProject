package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 972536780 on 2017/8/6.
 */
class A{
    public List<String> get(){
        return new ArrayList<String>();
    }
}

public class Test extends A{
    public ArrayList<String> get(){
        return new  ArrayList<String>();
    }
}
