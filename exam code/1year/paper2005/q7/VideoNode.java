/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005.q7;

/**
 *
 * @author Michal
 */
public class VideoNode {

    Video vid;
    VideoNode next;

    public VideoNode(Video vid, VideoNode next) {
        this.vid = vid;
        this.next = next;
    }

    public VideoNode getNext() {
        return next;
    }

    public Video getVid() {
        return vid;
    }

    @Override
    public String toString() {
        return vid.toString();
    }



}
