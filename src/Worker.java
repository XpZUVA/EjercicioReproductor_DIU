import javax.swing.*;
import javax.sound.sampled.Clip;

public class Worker extends SwingWorker<Void, Double> {
    private Clip clip;
    private JLabel label;

    public Worker(JLabel label, Clip clip) {
        this.clip = clip;
        this.label = label;
    }


    @Override
    protected Void doInBackground() throws Exception {
        long total = clip.getMicrosecondLength();
        double totalS = total / 1_000_000.0;

        while (clip.getMicrosecondPosition() < total) {
            long restante = total - clip.getMicrosecondPosition();
            double restanteS = restante / 1_000_000.0;
            publish(restanteS);
        }
        return null;
    }

    @Override
    protected void process(java.util.List<Double> chunks) {
        for (Double chunk : chunks) {
            int progreso = (int) Math.round((Double) chunk);
            label.setText(String.valueOf(progreso) + " seg");
        }
    }

}
