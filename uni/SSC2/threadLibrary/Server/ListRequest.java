/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author michal
 */
public class ListRequest {

    public static String execute(Map bookList) {
        StringBuilder build = new StringBuilder();
        Set keys = bookList.keySet();
        for (Object object : keys) {
            build.append(bookList.get(object));
            build.append("\n");
        }
        return build.toString();
    }
}
