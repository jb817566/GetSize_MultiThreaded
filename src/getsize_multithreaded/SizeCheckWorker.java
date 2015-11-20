package getsize_multithreaded;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class SizeCheckWorker implements Runnable {

    private String url = "";

    public SizeCheckWorker(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    public void checkLink() {
        String u = "";
        Integer s = 0;
        try {
            s = getSize(url);
                GetSize.dr.add(new Result<String, Integer>(u, s));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int getSize(String con) throws Exception {
        URL cons = new URL(con);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) cons.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            int c = conn.getContentLength();
            System.out.println(GetSize.dr.size());
            return c;
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    @Override
    public void run() {
        System.out.println("Checking " + url);
        checkLink();
        System.out.println("Done check");
//        cdl.countDown();
    }

}
