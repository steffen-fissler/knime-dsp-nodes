package associates.ai.knime.dsp.nodes.wavreader.utils;

import java.io.Serializable;

public class WavFileException extends Exception implements Serializable {
  private static final long serialVersionUID = -8205991676526101583L;

  public WavFileException() {
    super();
  }

  WavFileException(String message) {
    super(message);
  }

  public WavFileException(String message, Throwable cause) {
    super(message, cause);
  }

  public WavFileException(Throwable cause) {
    super(cause);
  }
}
