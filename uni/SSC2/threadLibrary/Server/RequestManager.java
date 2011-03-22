/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class RequestManager {

    ArrayList<Book> bookList;

    public RequestManager(ArrayList<Book> bookList){
        this.bookList = bookList;
    }

    public String getBookList(){
        StringBuilder build = new StringBuilder();
        for (Book book : bookList) {
            build.append(book);
            build.append("\n");
        }
        return build.toString();
    }


}
