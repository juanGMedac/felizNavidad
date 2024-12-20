import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PostalNavidena {
    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Feliz Navidad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        // Panel personalizado para la imagen
        JPanel imagenPanel = new JPanel() {
            private BufferedImage imagen = null;
            {
                try {
                    File file = new File("MEDAC_MD_felicitacion_ES_2024.jpg");
                    System.out.println("Intentando cargar desde: " + file.getAbsolutePath());

                    if (file.exists()) {
                        imagen = ImageIO.read(file);
                        System.out.println("Imagen cargada exitosamente");
                    } else {
                        file = new File("src/MEDAC_MD_felicitacion_ES_2024.jpg");
                        System.out.println("Intentando cargar desde: " + file.getAbsolutePath());

                        if (file.exists()) {
                            imagen = ImageIO.read(file);
                            System.out.println("Imagen cargada exitosamente desde src");
                        } else {
                            System.out.println("No se encontró la imagen en ninguna ubicación");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error al cargar la imagen: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    double ratio = 1.0 * imagen.getWidth() / imagen.getHeight();
                    int newWidth = getWidth();
                    int newHeight = getHeight();

                    if (getWidth() / ratio > getHeight()) {
                        newWidth = (int) (getHeight() * ratio);
                    } else {
                        newHeight = (int) (getWidth() / ratio);
                    }

                    int x = (getWidth() - newWidth) / 2;
                    int y = (getHeight() - newHeight) / 2;

                    g.drawImage(imagen, x, y, newWidth, newHeight, this);
                } else {
                    g.setColor(Color.RED);
                    g.drawString("No se pudo cargar la imagen", 10, 30);
                    System.out.println("No hay imagen para mostrar");
                }
            }
        };

        imagenPanel.setPreferredSize(new Dimension(400, 300));
        panel.add(imagenPanel, BorderLayout.CENTER);

        // Panel para el botón con margen inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Crear botón "Leer más"
        JButton botonFelicitar = new JButton("Leer más...");
        botonFelicitar.setPreferredSize(new Dimension(120, 30));
        botonFelicitar.setBackground(new Color(51, 122, 183));
        botonFelicitar.setForeground(Color.WHITE);
        botonFelicitar.setFocusPainted(false);
        botonFelicitar.setBorderPainted(false);
        botonFelicitar.setFont(new Font("Arial", Font.PLAIN, 14));

        // Añadir efecto hover
        botonFelicitar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonFelicitar.setBackground(new Color(40, 96, 144));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonFelicitar.setBackground(new Color(51, 122, 183));
            }
        });

        botonFelicitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread hilo = new Thread(() -> {
                    try {
                        String mensaje = "<html><div style='text-align: center; width: 300px;'>" +
                                         "<p>¡Queridos futuros desarrollador@s!</p>" +
                                         "<p>En estas fechas tan especiales, queremos desearos no solo felices fiestas, " +
                                         "sino también que vuestro código siempre compile a la primera, " +
                                         "que vuestros bugs sean fáciles de encontrar, " +
                                         "y que vuestras ideas fluyan como un bucle infinito de creatividad.</p>" +
                                         "<p>Que el 2025 venga con más commits exitosos que errores, " +
                                         "más soluciones que problemas, y... " +
                                         "<p>QUE NO SE OS MUERA DE HAMBRE NINGÚN FILÓSOFO. </p>" +
                                         "<p>¡Felices fiestas y próspero código nuevo! 🎄💻✨</p>" +
                                         "</div></html>";

                        JOptionPane.showMessageDialog(frame,
                                mensaje,
                                "Mensaje de vuestros profes de DAM",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        Thread.currentThread().interrupt();
                    }
                });
                hilo.start();
            }
        });

        buttonPanel.add(botonFelicitar);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Hacer que la ventana sea redimensionable
        frame.setResizable(true);

        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}