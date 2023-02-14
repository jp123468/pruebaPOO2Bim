import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main {
    private JPanel panel1;
    private JTextField textFieldPasajeros;
    private JTextField textFieldMarca;
    private JTextField textFieldPlaca;
    private JButton crearButton;
    private JButton buscarButton;
    private JButton actualizarButton;
    private JButton borrarButton;
    private JTextField textFieldColor;
    private JButton buttonMostar;
    private JTextField textFieldModelo;
    private JTextField textFieldAnio;

    private PreparedStatement ps;

    public Main(){

        buttonMostar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatos();
            }
        });
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("INSERT INTO vehiculo (Marca,Cant_Pas, Anio_Fab, Placa, Color, Modelo) VALUES (?,?,?,?,?)" );
                    try{

                        if( !textFieldMarca.getText().matches("[a-z]") ){
                            throw new SQLException("Ingresa bien los datos");
                        }

                        ps.setString(1, textFieldMarca.getText());
                        ps.setString(2, textFieldPasajeros .getText());
                        ps.setString(3, textFieldAnio.getText());
                        ps.setString(4,  textFieldPlaca.getText());
                        ps.setString(5,  textFieldColor.getText());
                        ps.setString(6,textFieldModelo.getText());

                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos");
                    }

                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Vehiculo Ingresada en el sistema");
                    }else{
                        JOptionPane.showMessageDialog(null, "Vehiculo No Ingresada en el sistema");
                    }
                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }

            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;

                try{
                    conn = getConnection();
                    ps = conn.prepareStatement("UPDATE vehiculo SET Marca =? ,Cant_Pas=? , Anio_Fab=? , Placa=? , Color=? , Modelo=?  WHERE id ="+textFieldMarca.getText() );
                    try{

                        if( !textFieldMarca.getText().matches("[a-z]") ){
                            throw new SQLException("Ingresa bien los datos");
                        }else{
                            ps.setString(1, textFieldMarca.getText());
                            ps.setString(2, textFieldPasajeros .getText());
                            ps.setString(3, textFieldAnio.getText());
                            ps.setString(4,  textFieldPlaca.getText());
                            ps.setString(5,  textFieldColor.getText());
                            ps.setString(6,textFieldModelo.getText());
                        }

                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos");
                    }


                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null, "Vehiculo modificada correctamente");
                    }else{
                        JOptionPane.showMessageDialog(null, "Vehiculo no modificada");
                    }
                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;
                ResultSet rs;
                try{
                    conn = getConnection();


                    ps = conn.prepareStatement("SELECT * FROM vehiculo WHERE Marca= ?" );
                    ps.setString(1, textFieldMarca.getText());

                    rs = ps.executeQuery();

                    try{

                        if( !textFieldMarca.getText().matches("[a-z]")){
                            throw new SQLException("Ingresa bien los datos");
                        }else{
                            if(rs.next()){
                                ps.setString(1, textFieldMarca.getText());
                                ps.setString(2, textFieldPasajeros .getText());
                                ps.setString(3, textFieldAnio.getText());
                                ps.setString(4,  textFieldPlaca.getText());
                                ps.setString(5,  textFieldColor.getText());
                                ps.setString(6,textFieldModelo.getText());
                            }else{
                                System.out.println("NO VALE TU PROGRAMA");
                            }

                        }


                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        JOptionPane.showMessageDialog(null,"Ingrese bien los datos");
                    }



                    conn.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
    }
    public void mostrarDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/prueba","root","POObjetos1.");

            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM vehiculo");

            while(rs.next()){

                JOptionPane.showMessageDialog(null, rs.getString("Marca") + "\n" + rs.getInt(2)
                        +"\n"+ rs.getInt(3) +"\n" +
                        rs.getString("Placa") + "\n"+rs.getString("Color") + "\n"+
                        rs.getString("Modelo"));

            }

            conexion.close();
        }catch (Exception ex){
            System.out.println(ex);
            ex.printStackTrace();
        }

    }

    public static Connection getConnection(){
        Connection conn = null;
        String base = "prueba";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "POObjetos1.";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
        }catch (ClassNotFoundException | SQLException ex){
            System.out.printf("Error: " + ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema");
        frame.setContentPane(new Main().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}