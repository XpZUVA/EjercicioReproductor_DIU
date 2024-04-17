import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Componentes extends JFrame {

    public JButton abrir = new JButton();
    public JButton reproducir = new JButton();
    public JButton pausar = new JButton();
    public JButton reiniciar = new JButton();

    public JSlider tiempo = new JSlider();
    public JLabel tiempoRestante = new JLabel("seg");

    private Clip clip;
    private File audio;



    public Componentes() {

        int i = 1;
        UIManager.LookAndFeelInfo looks[];
        looks = UIManager.getInstalledLookAndFeels();
        try{
            UIManager.setLookAndFeel(looks[i].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch(Exception e) {}

        //super("Componentes");
        setSize(520, 110);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Reproductor de Audio");
        setVisible(true);



        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panel.add(abrir);
        panel.add(reproducir);
        panel.add(pausar);
        panel.add(reiniciar);
        panel.add(tiempo);
        panel.add(tiempoRestante);
        add(panel);



        abrir.setIcon(new ImageIcon(getClass().getResource("/main/resources/abrir.png")));
        reproducir.setIcon(new ImageIcon(getClass().getResource("/main/resources/play.png")));
        pausar.setIcon(new ImageIcon(getClass().getResource("/main/resources/pausa.png")));
        reiniciar.setIcon(new ImageIcon(getClass().getResource("/main/resources/stop.png")));

        abrir.setPreferredSize(new Dimension(50, 50));
        reproducir.setPreferredSize(new Dimension(50, 50));
        pausar.setPreferredSize(new Dimension(50, 50));
        reiniciar.setPreferredSize(new Dimension(50, 50));

        reproducir.setEnabled(false);
        pausar.setEnabled(false);
        reiniciar.setEnabled(false);
        tiempo.setValue(0);

        abrir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reproducir.setEnabled(true);
                JFileChooser file = new JFileChooser();
                WavFileFilter.agregarFiltroWav(file);
                file.showOpenDialog(abrir);
                audio = file.getSelectedFile();
                try{
                    clip = AudioSystem.getClip();
                    AudioInputStream sound = AudioSystem.getAudioInputStream(audio);
                    clip.open(sound);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "ERROR\nNo hay fichero de audio", "alerta",
                            JOptionPane.ERROR_MESSAGE);
                }
            }});

        reproducir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clip.start();
                reproducir.setEnabled(false);
                pausar.setEnabled(true);
                reiniciar.setEnabled(true);
                Worker worker = new Worker(tiempoRestante, clip);
                Worker2 worker2 = new Worker2(tiempo, clip);
                worker2.execute();
                worker.execute();
            }
        });

        pausar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.stop();
                reproducir.setEnabled(true);
                pausar.setEnabled(false);
                reiniciar.setEnabled(false);
            }
        });

        reiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clip.setMicrosecondPosition(0);
                clip.stop();
                clip.setMicrosecondPosition(0);
                reproducir.setEnabled(true);
                pausar.setEnabled(false);
                reiniciar.setEnabled(false);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Runnable(){
            public void run(){
                new Componentes();
            }
        });
    }
}
