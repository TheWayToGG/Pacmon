import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Sound
{

	/**
     * @param AudioFormat
     * Zmienna s³u¿aca do pobrania formatu okreœlonego pliku audio
     */

    private AudioFormat format;

    /**
     * @param samples
     * Tablica byte. S³u¿y do przechowaywania danych audio.
     */

    private byte[] samples;

    /**
     * Pobranie pliku muzyczny o zadanej nazwie oraz za³adowanie go do
     * strumienia wejœciowego.
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
     * Metoda zwracj¹ca tablic¹ byte, która s³u¿y doprzechowywania danych audio.
     *
     * @return Tablica s³u¿¹ca do przechowywania danych audio.
     */

    public byte[] getSamples() {
        return samples;
    }

    /**
     * Ustalenie d³ugoœci utworu. Tworzony jest wejœciowy strumieñ danych typu
     * AudioInputStream.
     *
     * @param audioStream
     *                  Wejœciowy strumieñ danych.
     * @return
     *                  Tablica s³u¿¹ca do przechowywania danych audio.
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
     * G³ówna metoda opowiedzielna za muzykê. Na podstawie danej wejœciowej
     * ustalmy wielkosc bufora. Parametr change oznacza po jakim czasie nale¿y
     * przerwaæ muzykê.
     *
     * @param source
     *              Strumieñ wejœciowy.
     * @param change
     *              Okreœla czas po jakim nast¹po wylaczenie aktualnego dzwiêku.
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

            // Pobieranie trwa do koñca trwania utworu

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