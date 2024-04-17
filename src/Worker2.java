import javax.swing.*;
import javax.sound.sampled.Clip;

public class Worker2 extends SwingWorker<Void, Double> {
    private Clip clip;
    private JSlider slider;

    public Worker2(JSlider slider, Clip clip) {
        this.clip = clip;
        this.slider = slider;

    }


    @Override
    protected Void doInBackground() throws Exception {
        long total = clip.getMicrosecondLength();
        double totalS = total / 1_000_000.0;

        while (clip.getMicrosecondPosition() < total) {
            long restante = clip.getMicrosecondPosition();
            double restanteS = restante / 1_000_000.0;
            double progress = (restanteS * 100) / totalS;
            publish(progress);
        }
        return null;
    }

    @Override
    protected void process(java.util.List<Double> chunks) {
        for (Double chunk : chunks) {
            int progreso = (int) Math.round((Double) chunk);
            slider.setValue(progreso);
        }
    }

}
