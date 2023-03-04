import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;
public class ProductManagement_System {
    private JPanel Main;
    private JTextField txtPrice;
    private JTextField txtName;
    private JTextField txtpid;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtQty;
    private JButton searchButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ProductManagement_System");
        frame.setContentPane(new ProductManagement_System().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public ProductManagement_System() {
        Connect();

    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String name,price,qty;

            name = txtName.getText();
            price = txtPrice.getText();
            qty = txtQty.getText();

            try{
                pst = con.prepareStatement("insert into products(pname,price,qty) values(?,?,?) ");
                pst.setString(1,name);
                pst.setString(2,price);
                pst.setString(3,qty);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record Added!!");

                txtName.setText("");
                txtPrice.setText("");
                txtQty.setText("");
                txtName.requestFocus();

            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
        }
    });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String pid = txtpid.getText();

                    pst = con.prepareStatement("select pname,price,qty from products where pid=?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true){
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else{
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                pid = txtpid.getText();

                try{
                    pst = con.prepareStatement("update products set pname = ?,price = ? ,qty=? where pid = ? ");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated!!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");

                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid;

                pid = txtpid.getText();

                try{
                    pst = con.prepareStatement("delete from products where pid = ? ");

                    pst.setString(1,pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Deleted!!");

                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");

                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    public void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gbproducts","root","");
            System.out.println("Success");
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();

        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
}
