/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005.q7;

/**
 *
 * @author Michal
 */
class Video implements Comparable<Video>{

    private String title;
    private int reference;

    public Video(int reference, String title) {
        this.title = title;
        this.reference = reference;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return reference + " : " + title;
    }

    public int compareTo(Video v) {
        return title.compareTo(v.title);
    }



}
