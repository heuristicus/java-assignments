/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q7;

/**
 *
 * @author Michal
 */
public class VideoList {

    private VideoNode first;

    public static void main(String[] args) {
        VideoList vids = new VideoList();
        vids.add(new Video(123, "Gone with the Wind"));
        vids.add(new Video(456, "Casablanca"));
        vids.add(new Video(789, "Dr. No"));
        System.out.println(vids);
    }

    public VideoList() {
        first = null;
    }

    public void add(Video v) {
        if (first == null) {
            first = new VideoNode(v, null);
        } else {
            VideoNode node = first;
            while (node.next != null) {
                node = node.next;
            }
            VideoNode newNode = new VideoNode(v, null);
            node.next = newNode;
        }
    }

    @Override
    public String toString() {
        String s = "";
        VideoNode curNode = first;
        while (curNode != null) {
            s += curNode.toString() + "\n";
            curNode = curNode.next;
        }
        return s;
    }
}
