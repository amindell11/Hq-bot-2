package file;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Logger {
	private String fileName;
	private File logFile;
	private PrintWriter writer;
	private boolean initialized;

	public Logger(String fileName) {
		this.fileName = fileName;
		initialized = false;
	}

	/**
	 * initializes the Logger will fail to initialize if the set filename does not
	 * exist or is not writeable
	 * 
	 * @return whether the logger is successfully initialized
	 */
	public boolean init() {
		logFile = new File(fileName);
		if (logFile.isFile()) {
			try {
				writer = new PrintWriter(logFile);
				initialized = true;
			} catch (FileNotFoundException e) {
				System.out.println(
						"ERROR: file does not exist or is not writeable, failed to initialize. please choose another fileName");
			}
		}
		return initialized;
	}

	public void log(String text) {
		if (initialized) {
			writer.println(text);
			writer.flush();
		} else {
			throw new LoggerNotInitializedException();
		}
	}

	private class LoggerNotInitializedException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String getMessage() {
			return "Logger was never initialized";
		}

	}
}
