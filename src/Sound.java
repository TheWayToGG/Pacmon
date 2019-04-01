import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Sound
{

	/**
     * @param AudioFormat
     * Zmienna s�u�aca do pobrania formatu okre�lonego pliku audio
     */

    private AudioFormat format;

    /**
     * @param samples
     * Tablica byte. S�u�y do przechowaywania danych audio.
     */

    private byte[] samples;

    /**
     * Pobranie pliku muzyczny o zadanej nazwie oraz za�adowanie go do
     * strumienia wej�ciowego.
     *
     * @param filename
     *                  Nazwa pliku muzycznego.
     */

    public Sound(String filename)
    {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(
                    filename));
            format = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda zwracj�ca tablic� byte, kt�ra s�u�y doprzechowywania danych audio.
     *
     * @return Tablica s�u��ca do przechowywania danych audio.
     */

    public byte[] getSamples() {
        return samples;
    }

    /**
     * Ustalenie d�ugo�ci utworu. Tworzony jest wej�ciowy strumie� danych typu
     * AudioInputStream.
     *
     * @param audioStream
     *                  Wej�ciowy strumie� danych.
     * @return
     *                  Tablica s�u��ca do przechowywania danych audio.
     */

    private byte[] getSamples(AudioInputStream audioStream) {
        int length = (int) (audioStream.getFrameLength() * format.getFrameSize());
        byte[] samples = new byte[length];

        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return samples;
    }

    /**
     * G��wna metoda opowiedzielna za muzyk�. Na podstawie danej wej�ciowej
     * ustalmy wielkosc bufora. Parametr change oznacza po jakim czasie nale�y
     * przerwa� muzyk�.
     *
     * @param source
     *              Strumie� wej�ciowy.
     * @param change
     *              Okre�la czas po jakim nast�po wylaczenie aktualnego dzwi�ku.
     */

    public void play(InputStream source, int change)
    {
        int bufferSize = format.getFrameSize() * Math.round(format.getSampleRate() / 10);
        byte[] buffer = new byte[bufferSize];
        SourceDataLine line;

        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, bufferSize);
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
            return;
        }
        line.start();
        try {
            int numBytesRead = 0;
            int i = 0;

            // Pobieranie trwa do ko�ca trwania utworu

            while (i != change) {
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (numBytesRead != -1) {
                    line.write(buffer, 0, numBytesRead);
                }
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        line.flush();
        line.close();
    }

}