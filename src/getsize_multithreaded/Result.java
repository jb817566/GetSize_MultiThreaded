package getsize_multithreaded;

public class Result<T extends java.lang.String, U extends java.lang.Integer> {

    private T url = null;
    private U size = null;

    public Result(T t, U u) {
        url = t;
        size = u;
    }

    public T getURL() {
        return url;
    }
    
    public U getSize(){
        return size;
    }
    
    @Override
    public String toString(){
        return url + "," + getSize();
    }
}
