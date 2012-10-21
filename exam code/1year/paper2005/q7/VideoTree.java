/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005.q7;

import java.util.TreeSet;

/**
 *
 * @author Michal
 */
public class VideoTree {

    TreeSet<Video> videoList;

    public static void main(String[] args) {
        VideoTree vids = new VideoTree();
        vids.add(new Video(123, "Gone with the Wind"));
        vids.add(new Video(456, "Casablanca"));
        vids.add(new Video(789, "Dr. No"));
        System.out.println(vids);
    }

    public VideoTree() {
        this.videoList = new TreeSet<Video>();
    }

    public void add(Video v) {
        videoList.add(v);
    }

    @Override
    public String toString() {
        String s = "";
        for (Video video : videoList) {
            s += video + "\n";
        }
        return s;
    }

}
