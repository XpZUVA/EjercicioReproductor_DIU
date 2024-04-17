import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class WavFileFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String fileName = file.getName();
        return fileName.endsWith(".wav");
    }

    @Override
    public String getDescription() {
        return "Archivos WAV (*.wav)";
    }

    // Método para agregar este filtro a un JFileChooser
    public static void agregarFiltroWav(JFileChooser fileChooser) {
        fileChooser.setFileFilter(new WavFileFilter());
        fileChooser.addChoosableFileFilter(new WavFileFilter());
    }
}
