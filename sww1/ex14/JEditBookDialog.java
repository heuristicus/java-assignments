/*
 * JEditDialog.java
 *
 * Created on 31 January 2004, 20:21
 */

package ex14;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author  Jim McGregor
 */
public class JEditBookDialog extends javax.swing.JDialog implements java.awt.event.ActionListener
{
   private Book book;
   private JTextField bookNumField, positionField,
              persNameField, famNameField, messageField;
   private boolean updated;
   
   /** Creates a new instance of JEditDialog */
   public JEditBookDialog(Frame owner, Book bk)
   {
      super(owner);
      book = bk;
      setModal(true);
      setSize(800,150);
      JPanel headerPanel = new JPanel();
      messageField = new JTextField("*****  Edit Fields as required  *****");
      messageField.setEditable(false);
      headerPanel.add(messageField);
      
      JPanel editPanel = new JPanel();

      bookNumField = new JTextField(10);
      String numStr = "";  int num = book.getBookNumber();
      if (num>0) numStr = String.valueOf(num);
      bookNumField.setText(numStr);
      positionField = new JTextField(10);
      positionField.setText(book.getTitle());
      persNameField = new JTextField(10);
      persNameField.setText(book.getAuthorPersonalName());
      famNameField = new JTextField(10);
      famNameField.setText(book.getAuthorFamilyName());
      editPanel.add(new JLabel("number"));  editPanel.add(bookNumField);
      editPanel.add(new JLabel("title"));  editPanel.add(positionField);
      editPanel.add(new JLabel("author persname"));  editPanel.add(persNameField);
      editPanel.add(new JLabel("author famname"));  editPanel.add(famNameField);

      JPanel buttonPanel = new JPanel();
      JButton updateButton = new JButton("update");
      updateButton.addActionListener(this);
      buttonPanel.add(updateButton);
      JButton cancelButton = new JButton("cancel");
      cancelButton.addActionListener(this);
      buttonPanel.add(cancelButton);
      getContentPane().add(headerPanel,"North");
      getContentPane().add(buttonPanel,"South");
      getContentPane().add(editPanel);
      
      addWindowListener(
           new WindowAdapter()
           {  public void windowClosing(WindowEvent event)
              { dispose(); }
           } );

   }
   
   public void actionPerformed(java.awt.event.ActionEvent e)
   {
      String act = e.getActionCommand();
      if (act=="cancel") {  updated = false;  dispose(); }
      else if (act=="update")
      {  int num;
         try
         { num = Integer.parseInt(bookNumField.getText().trim());
            if (num<=0) throw new NumberFormatException();}
         catch (NumberFormatException ne)
         {  messageField.setText("*  Invalid book number  *");
            repaint();  return;
         }
         book.setBookNumber(num);
         book.setTitle(positionField.getText());
         book.setAuthorPersonalName(persNameField.getText());
         book.setAuthorFamilyName(famNameField.getText());
         updated = true;
         dispose();
      }
   }
   
   public boolean isUpdated()
   {  return updated;  }
   
}
