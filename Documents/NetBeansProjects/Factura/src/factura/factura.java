package factura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class factura {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/EvidenciaProyecto";
        String user = "root";
        String password = "123456789";

        String[] opciones = {"Registrar Factura", "Consultar Factura", "Actualizar Factura", "Eliminar Factura"};

        int opcionSeleccionada = JOptionPane.showOptionDialog(
                null, "Seleccione una opción:", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            switch (opcionSeleccionada) {
                case 0:
                    // Registrar Factura
                    insertData(connection, 1, "2024-05-02", "pago", 12345, 1, 1, 1);
                    displayData(connection);
                    break;
                case 1:
                    // Consultar Factura
                    ConsultarFactura(connection);
                    break;
                case 2:
                    // Actualizar Factura
                    int idFacturaActualizar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la Factura a actualizar"));
                    String nuevaFecha = JOptionPane.showInputDialog("Ingrese la nueva fecha:");
                    String nuevoEstado = JOptionPane.showInputDialog("Ingrese el nuevo estado:");
                    int nuevoNumero = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el nuevo número:"));
                    ActualizarFactura(connection, idFacturaActualizar, nuevaFecha, nuevoEstado, nuevoNumero);
                    break;
                case 3:
                    // Eliminar Factura
                    int idFacturaEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la Factura a eliminar"));
                    EliminarFactura(connection, idFacturaEliminar);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar datos en la tabla factura
    private static void insertData(Connection connection, int codFactura, String fecFactura, String idEstado, int numFactura, int idComanda, int idUsuario, int idInformacion)
            throws SQLException {
        String insertQuery = "INSERT INTO factura(codFactura, fecFactura, idEstado, numFactura, Comanda_ID_Comanda, Comanda_Usuarios_ID_Usuarios, Comanda_Usuarios_Informacion_ID_Informacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, codFactura);
            preparedStatement.setString(2, fecFactura);
            preparedStatement.setString(3, idEstado);
            preparedStatement.setInt(4, numFactura);
            preparedStatement.setInt(5, idComanda);
            preparedStatement.setInt(6, idUsuario);
            preparedStatement.setInt(7, idInformacion);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                System.out.println("Failed to insert data.");
            }
        }
    }

    // Método para mostrar datos de la tabla factura
    private static void displayData(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM factura";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int codFactura = resultSet.getInt("codFactura");
                String fecFactura = resultSet.getString("fecFactura");
                String idEstado = resultSet.getString("idEstado");
                int numFactura = resultSet.getInt("numFactura");
                int comandaID = resultSet.getInt("Comanda_ID_Comanda");

                System.out.printf("CodFactura: %d, FecFactura: %s, IdEstado: %s, NumFactura: %d,%n" +
                                "Comanda_ID_Comanda: %d",
                        codFactura, fecFactura, idEstado, numFactura,
                        comandaID);
            }
        }
    }

    // Método para consultar datos de la tabla factura
    public static void ConsultarFactura(Connection connection) throws SQLException {
        String sqlConsulta = "SELECT * FROM factura";
        PreparedStatement statementConsulta = connection.prepareStatement(sqlConsulta);
        ResultSet resultSet = statementConsulta.executeQuery();
        while (resultSet.next()) {
            System.out.println("CodFactura: " + resultSet.getInt("codFactura"));
            System.out.println("FecFactura: " + resultSet.getString("fecFactura"));
            System.out.println("IdEstado: " + resultSet.getString("idEstado"));
            System.out.println("NumFactura: " + resultSet.getInt("numFactura"));
            System.out.println("Comanda_ID_Comanda: " + resultSet.getInt("Comanda_ID_Comanda"));
            System.out.println("Comanda_Usuarios_ID_Usuarios: " + resultSet.getInt("Comanda_Usuarios_ID_Usuarios"));
            System.out.println("Comanda_Usuarios_Informacion_ID_Informacion: " + resultSet.getInt("Comanda_Usuarios_Informacion_ID_Informacion"));
        }
    }

    // Método para actualizar datos de la tabla factura
    public static void ActualizarFactura(Connection connection, int idFactura, String nuevaFecha, String nuevoEstado, int nuevoNumero) throws SQLException {
        String sqlUpdate = "UPDATE factura SET fecFactura = ?, idEstado = ?, numFactura = ? WHERE codFactura = ?";
        try (PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate)) {
            statementUpdate.setString(1, nuevaFecha);
            statementUpdate.setString(2, nuevoEstado);
            statementUpdate.setInt(3, nuevoNumero);
            statementUpdate.setInt(4, idFactura);
            int rowsAffected = statementUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Factura actualizada exitosamente.");
            } else {
                System.out.println("No se pudo actualizar la factura.");
            }
        }
    }

    // Método para eliminar datos de la tabla factura
    public static void EliminarFactura(Connection connection, int idFactura) throws SQLException {
        String sqlDelete = "DELETE FROM factura WHERE codFactura = ?";
        try (PreparedStatement statementDelete = connection.prepareStatement(sqlDelete)) {
            statementDelete.setInt(1, idFactura);
            int rowsAffected = statementDelete.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Factura eliminada exitosamente.");
            } else {
                System.out.println("No se pudo eliminar la factura.");
            }
        }
    }
}
