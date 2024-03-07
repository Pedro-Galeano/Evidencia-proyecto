package detallefactura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Detallefactura {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/EvidenciaProyecto";
        String user = "root";
        String password = "123456789";

        String[] opciones = {"Registrar Detalle Factura", "Consultar Detalle Factura", "Actualizar Detalle Factura", "Eliminar Detalle Factura"};

        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null, "Seleccione una opción:", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            switch (opcionSeleccionada) {
                case 0:
                    // Registrar Detalle Factura
                    insertData(connection, 1, 1, "Langostinos", 10, "C001", 7, 1, 1, 1, 1);
                    displayData(connection);
                    break;
                case 1:
                    // Consultar Detalle Factura
                    ConsultarDetallefactura(connection);
                    break;
                case 2:
                    // Actualizar Detalle Factura
                    int idDetallefacturaActualizar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del Detalle Factura a actualizar"));
                    int nuevoNumDetallefactura = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo número de Detalle Factura:"));
                    String nuevoCodPlato = JOptionPane.showInputDialog("Ingrese el nuevo código de plato:");
                    double nuevoValorUnitario = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el nuevo valor unitario:"));
                    String nuevoCodigoComanda = JOptionPane.showInputDialog("Ingrese el nuevo código de comanda:");
                    int nuevaIdFactura = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo ID de Factura:"));
                    int nuevaIdComanda = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo ID de Comanda:"));
                    int nuevaIdUsuario = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo ID de Usuario:"));
                    int nuevaIdInformacion = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo ID de Información:"));
                    int nuevaIdMenu = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo ID de Menú:"));

                    ActualizarDetallefactura(connection, idDetallefacturaActualizar, nuevoNumDetallefactura, nuevoCodPlato,
                            nuevoValorUnitario, nuevoCodigoComanda, nuevaIdFactura, nuevaIdComanda, nuevaIdUsuario,
                            nuevaIdInformacion, nuevaIdMenu);
                    break;
                    case 3:
                    // Eliminar Detalle Factura
                    int idDetallefacturaEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del Detalle Factura a eliminar"));
                    EliminarDetallefactura(connection, idDetallefacturaEliminar);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar datos en la tabla detallefactura
    private static void insertData(Connection connection, int idDetalleFactura, int numDetalleFactura, String codPlato,
                                   double valorUnitario, String codigoComanda, int idFactura, int idComanda, int idUsuario,
                                   int idInformacion, int idMenu)
            throws SQLException {
        String insertQuery = "INSERT INTO detallefactura(ID_DetalleFactura, numDetalleFactura, codPlato, valorUnitario, " +
                "codigoComanda, Factura_ID_Factura, Factura_Comanda_ID_Comanda, Factura_Comanda_Usuarios_ID_Usuarios, " +
                "Factura_Comanda_Usuarios_Informacion_ID_Informacion, Menu_ID_Menu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, idDetalleFactura);
            preparedStatement.setInt(2, numDetalleFactura);
            preparedStatement.setString(3, codPlato);
            preparedStatement.setDouble(4, valorUnitario);
            preparedStatement.setString(5, codigoComanda);
            preparedStatement.setInt(6, idFactura);
            preparedStatement.setInt(7, idComanda);
            preparedStatement.setInt(8, idUsuario);
            preparedStatement.setInt(9, idInformacion);
            preparedStatement.setInt(10, idMenu);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        }
    }

    // Método para mostrar datos de la tabla detallefactura
    private static void displayData(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM detallefactura";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int idDetalleFactura = resultSet.getInt("ID_DetalleFactura");
                int numDetalleFactura = resultSet.getInt("numDetalleFactura");
                String codPlato = resultSet.getString("codPlato");
                double valorUnitario = resultSet.getDouble("valorUnitario");
                String codigoComanda = resultSet.getString("codigoComanda");
                int idFactura = resultSet.getInt("Factura_ID_Factura");
                int idComanda = resultSet.getInt("Factura_Comanda_ID_Comanda");
                int idUsuario = resultSet.getInt("Factura_Comanda_Usuarios_ID_Usuarios");
                int idInformacion = resultSet.getInt("Factura_Comanda_Usuarios_Informacion_ID_Informacion");
                int idMenu = resultSet.getInt("Menu_ID_Menu");

                System.out.printf("ID_DetalleFactura: %d, numDetalleFactura: %d, codPlato: %s, valorUnitario: %.2f,%n" +
                                "codigoComanda: %s, Factura_ID_Factura: %d, Factura_Comanda_ID_Comanda: %d,%n" +
                                "Factura_Comanda_Usuarios_ID_Usuarios: %d, Factura_Comanda_Usuarios_Informacion_ID_Informacion: %d,%n" +
                                "Menu_ID_Menu: %d%n",
                        idDetalleFactura, numDetalleFactura, codPlato, valorUnitario,
                        codigoComanda, idFactura, idComanda, idUsuario,
                        idInformacion, idMenu);
            }
        }
    }

    // Método para consultar datos de la tabla detallefactura
    public static void ConsultarDetallefactura(Connection connection) throws SQLException {
    String sqlConsulta = "SELECT * FROM detallefactura";
    try (PreparedStatement statementConsulta = connection.prepareStatement(sqlConsulta);
         ResultSet resultSet = statementConsulta.executeQuery()) {
        while (resultSet.next()) {
            int idDetalleFactura = resultSet.getInt("ID_DetalleFactura");
            int numDetalleFactura = resultSet.getInt("numDetalleFactura");
            String codPlato = resultSet.getString("codPlato");
            double valorUnitario = resultSet.getDouble("valorUnitario");
            String codigoComanda = resultSet.getString("codigoComanda");
            int idFactura = resultSet.getInt("Factura_ID_Factura");
            int idComanda = resultSet.getInt("Factura_Comanda_ID_Comanda");
            int idUsuario = resultSet.getInt("Factura_Comanda_Usuarios_ID_Usuarios");
            int idInformacion = resultSet.getInt("Factura_Comanda_Usuarios_Informacion_ID_Informacion");
            int idMenu = resultSet.getInt("Menu_ID_Menu");

            System.out.printf("ID_DetalleFactura: %d, numDetalleFactura: %d, codPlato: %s, valorUnitario: %.2f,%n" +
                            "codigoComanda: %s, Factura_ID_Factura: %d, Factura_Comanda_ID_Comanda: %d,%n" +
                            "Factura_Comanda_Usuarios_ID_Usuarios: %d, Factura_Comanda_Usuarios_Informacion_ID_Informacion: %d,%n" +
                            "Menu_ID_Menu: %d%n",
                    idDetalleFactura, numDetalleFactura, codPlato, valorUnitario,
                    codigoComanda, idFactura, idComanda, idUsuario,
                    idInformacion, idMenu);
        }
    }
}

    // Método para actualizar datos de la tabla detallefactura
    public static void ActualizarDetallefactura(Connection connection, int idDetalleFactura, int nuevoNumDetalleFactura,
                                               String nuevoCodPlato, double nuevoValorUnitario, String nuevoCodigoComanda,
                                               int nuevaIdFactura, int nuevaIdComanda, int nuevaIdUsuario,
                                               int nuevaIdInformacion, int nuevaIdMenu)
            throws SQLException {
        String sqlUpdate = "UPDATE detallefactura SET numDetalleFactura = ?, codPlato = ?, valorUnitario = ?, " +
                "codigoComanda = ?, Factura_ID_Factura = ?, Factura_Comanda_ID_Comanda = ?, " +
                "Factura_Comanda_Usuarios_ID_Usuarios = ?, Factura_Comanda_Usuarios_Informacion_ID_Informacion = ?, " +
                "Menu_ID_Menu = ? WHERE ID_DetalleFactura = ?";
        try (PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate)) {
            statementUpdate.setInt(1, nuevoNumDetalleFactura);
            statementUpdate.setString(2, nuevoCodPlato);
            statementUpdate.setDouble(3, nuevoValorUnitario);
            statementUpdate.setString(4, nuevoCodigoComanda);
            statementUpdate.setInt(5, nuevaIdFactura);
            statementUpdate.setInt(6, nuevaIdComanda);
            statementUpdate.setInt(7, nuevaIdUsuario);
            statementUpdate.setInt(8, nuevaIdInformacion);
            statementUpdate.setInt(9, nuevaIdMenu);
            statementUpdate.setInt(10, idDetalleFactura);

            int rowsAffected = statementUpdate.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Detalle Factura actualizado exitosamente.");
            } else {
                System.out.println("No se pudo actualizar el Detalle Factura.");
            }
        }
    }

    // Método para eliminar datos de la tabla detallefactura
    public static void EliminarDetallefactura(Connection connection, int idDetalleFactura) throws SQLException {
        String sqlDelete = "DELETE FROM detallefactura WHERE ID_DetalleFactura = ?";
        try (PreparedStatement statementDelete = connection.prepareStatement(sqlDelete)) {
            statementDelete.setInt(1, idDetalleFactura);
            int rowsAffected = statementDelete.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Detalle Factura eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el Detalle Factura.");
            }
        }
    }
}

