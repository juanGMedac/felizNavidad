import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;

public class PostalNavidena {
    private static Clip clip;
    private static boolean isMuted = false;

    public static void main(String[] args) {
        // Iniciar la música
        try {
            // Cargar el archivo de audio desde resources
            InputStream audioStream = PostalNavidena.class.getResourceAsStream("/navidad.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error al cargar la música: " + e.getMessage());
            e.printStackTrace();
        }

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
                    // Cargar la imagen desde dentro del JAR
                    InputStream inputStream = PostalNavidena.class.getResourceAsStream("/MEDAC_MD_felicitacion_ES_2024.jpg");
                    if (inputStream != null) {
                        imagen = ImageIO.read(inputStream);
                        System.out.println("Imagen cargada exitosamente desde el JAR");
                    } else {
                        System.out.println("No se pudo encontrar la imagen en el JAR");
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

        // Panel para los botones (cambiamos a BorderLayout)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Panel izquierdo para el botón de sonido
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Botón de sonido con texto simple y más ancho
        JButton soundButton = new JButton("ON");
        soundButton.setPreferredSize(new Dimension(60, 30));
        soundButton.setBackground(new Color(51, 122, 183));
        soundButton.setForeground(Color.WHITE);
        soundButton.setFocusPainted(false);
        soundButton.setBorderPainted(false);
        soundButton.setFont(new Font("Arial", Font.BOLD, 12));

        soundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null) {
                    if (isMuted) {
                        clip.start();
                        soundButton.setText("ON");
                    } else {
                        clip.stop();
                        soundButton.setText("OFF");
                    }
                    isMuted = !isMuted;
                }
            }
        });

        // Efecto hover para el botón de sonido
        soundButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                soundButton.setBackground(new Color(40, 96, 144));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                soundButton.setBackground(new Color(51, 122, 183));
            }
        });

        // Panel central para el botón Leer más
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

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

        // Añadir botones a sus respectivos paneles
        leftPanel.add(soundButton);
        centerPanel.add(botonFelicitar);

        // Añadir los paneles al panel principal de botones
        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Gestionar cierre de la ventana y detener música
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (clip != null) {
                    clip.stop();
                    clip.close();
                }
            }
        });

        // Hacer que la ventana sea redimensionable
        frame.setResizable(true);

        // Centrar la ventana en la pantalla
        frame.setLocationRelativeTo(null);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}