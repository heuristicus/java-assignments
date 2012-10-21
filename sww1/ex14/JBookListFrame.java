package ex14;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class JBookListFrame extends JFrame implements ActionListener {

    private static final int MAX = 4;  //initial max value for empty list
    private static final int BY_TITLE = 0, BY_AUTHOR = 1;
    private BookList bookList;
    private JTable table;
    private int currentViewType;
    private JTableHeader tableHeader;
    private boolean sortReverse = false;

    public static void main(String[] args) throws java.io.IOException {
        JBookListFrame frame = new JBookListFrame();
        frame.setVisible(true);
    }

    /**
     * Constructor for this class.  Creates a JTable object using the bookList and places it into a panel
     * to which are added buttons for various functions.
     * @throws java.io.IOException
     */
    public JBookListFrame() throws java.io.IOException {

        setTitle("Book Collection");
        bookList = new BookList(MAX, "books.txt");
        bookList.listByNumber();
        currentViewType = BY_TITLE;
        table = new JTable(bookList);
        tableHeader = table.getTableHeader();
        table.setTableHeader(tableHeader);
        tableHeader.addMouseListener(new MouseAdapter() {

            /**
             * This mouseClicked event detects clicks on the table headers, and sorts the array
             * depending on which header is clicked.  If the same header is clicked twice, it
             * will sort it in reverse.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                TableColumnModel model = table.getColumnModel();
                int modelIndex = model.getColumn(model.getColumnIndexAtX(e.getX())).getModelIndex();
                if (bookList.isSortReverse() == false){
                    bookList.setSortReverse(true);
                } else {
                    bookList.setSortReverse(false);
                }

                switch (modelIndex) {
                    case 0:
                        bookList.listByNumber();
                        break;
                    case 1:
                        bookList.listByTitle();
                        break;
                    case 2:
                        bookList.listByAuthorFirstName();
                        break;
                    case 3:
                        bookList.listByAuthor();
                        break;
                }

            }
        });
        JPanel buttonPanel = new JPanel();
        addButton("list by title", buttonPanel);
        addButton("list by author", buttonPanel);
        addButton("edit", buttonPanel);
        addButton("new", buttonPanel);
        addButton("delete", buttonPanel);
        addButton("load", buttonPanel);
        addButton("save", buttonPanel);
        getContentPane().add(buttonPanel, "South");
        getContentPane().add(new JScrollPane(table), "Center");
        addWindowListener(
                new WindowAdapter() {

                    public void windowClosing(WindowEvent event) {
                        System.exit(0);
                    }
                });
        setSize(650, 250);
        setVisible(true);
    }

    /**
     * Adds a button with an actionListener to a panel.
     * @param name
     * @param pan
     */
    private void addButton(String name, JPanel pan) {
        JButton theButton = new JButton(name);
        theButton.addActionListener(this);
        pan.add(theButton);
    }

    /**
     * Performs various actions depending on which button on the panel is pressed.
     * @param e
     */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        String act = e.getActionCommand();
        if (act.equals("list by title")) {
            sortReverse = bookList.isSortReverse();
            bookList.setSortReverse(false);
            bookList.listByTitle();
            currentViewType = BY_TITLE;
            bookList.setSortReverse(sortReverse);
        } else if (act.equals("list by author")) {
            sortReverse = bookList.isSortReverse();
            bookList.setSortReverse(false);
            bookList.listByAuthor();
            currentViewType = BY_AUTHOR;
            bookList.setSortReverse(sortReverse);
        } else if (act.equals("edit")) {
            int r = table.getSelectedRow();
            if (r >= 0) {
                JDialog d = new JEditBookDialog(this, bookList.getBook(r));
                d.setVisible(true);
                sortReverse = bookList.isSortReverse();
                bookList.setSortReverse(false);
                if (currentViewType == BY_TITLE) {
                    bookList.listByTitle();
                } else {
                    bookList.listByAuthor();
                }
                bookList.setSortReverse(sortReverse);
            }
        } else if (act.equals("new")) {
            Book newBook = new Book(0, "", "", "");
            JEditBookDialog d = new JEditBookDialog(this, newBook);
            d.setVisible(true);
            if (d.isUpdated()) {
                bookList.add(newBook);
            }
            if (currentViewType == BY_TITLE) {
                bookList.listByTitle();
            } else {
                bookList.listByAuthor();
            }
            table.tableChanged(new TableModelEvent(bookList));
        } else if (act.equals("load")) {
            loadDialog();
        } else if (act.equals("save")) {
            saveDialog();
        } else if (act.equals("delete")) {
            bookList.remove(table.getSelectedRow());
        }
        repaint();
    }

    /**
     * Loads a file from the specified location.
     */
    public void loadDialog() {
        JFileChooser chooser = new JFileChooser(".");
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("txt");
        filter.setDescription("TXT files");
        chooser.setFileFilter(filter);
        int optionSelected = chooser.showOpenDialog(this);
        if (optionSelected == JFileChooser.APPROVE_OPTION) {
            String fileName = chooser.getSelectedFile().getName();
            try {
                bookList.loadFrom(fileName);
            } catch (Exception e) {
                System.out.println("loading failed!");
            }
            table.tableChanged(new TableModelEvent(bookList));
        }
    }

    /**
     * Saves the file to a specified location.
     */
    public void saveDialog() {
        JFileChooser chooser = new JFileChooser(".");
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("txt");
        filter.setDescription("TXT files");
        chooser.setFileFilter(filter);
        int optionSelected = chooser.showSaveDialog(this);
        if (optionSelected == JFileChooser.APPROVE_OPTION) {
            String fileName = chooser.getSelectedFile().getName();
            try {
                bookList.saveTo(fileName);
            } catch (Exception e) {
                System.out.println("saving failed! " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
