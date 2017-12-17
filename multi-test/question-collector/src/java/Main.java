import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import question.Question;

public class Main {
	private static final Main INSTANCE = new Main();
	private static final String QUESTION_LOG = "QuestionLog.log";
	private static final boolean IS_TEST = true;
	private static final String SCHEMA_LOC = "pics/schema.jpg";
	private PrintWriter pw;

	public static Main getInstance() {
		return INSTANCE;
	}

	private String directory;
	int fileIndex;

	private Main() {
		directory = createSessionDirectory();
		File file = new File(QUESTION_LOG);
		try {
			file.createNewFile();
			FileWriter w = new FileWriter(file);
			pw = new PrintWriter(w);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void resetQuestionSchema(Rectangle newBounds) {
		try {
			Robot robot = new Robot();
			Rectangle rectArea = newBounds;
			BufferedImage img = robot.createScreenCapture(rectArea);
			ImageIO.write(img, "jpg", new File(SCHEMA_LOC));
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}

	}

	public void main(App app) {
		try {
			fileIndex = 1;
			boolean capping = false;
			boolean currentQuestionCapped = false;
			BufferedImage cap1 = ImageIO.read(new File(SCHEMA_LOC));
			while (true) {
				Robot robot;
				robot = new Robot();
				Rectangle rectArea = app.bounds;
				BufferedImage img = robot.createScreenCapture(rectArea);
				if (ImgDiffPercent.getDifferencePercent(cap1, img) < 7.5) {
					if (!capping) {
						capping = true;
						currentQuestionCapped = false;
						System.out.println("new Question");
						TimeTracker.init();
					}
					if (TimeTracker.getTimeSeconds(TimeTracker.getTimestamp()) > 2 && !currentQuestionCapped) {
						handleImage(img);
						currentQuestionCapped = true;
					}
				} else {
					if (capping) {
						capping = false;
						TimeTracker.storeTime(TimeTracker.runTime);
						System.out.println("stopped capping at " + TimeTracker.runTime);
					}
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleImage(BufferedImage img) {
		String path = saveImage(img);
		try {
			Question q = ReadText.detectText(path);
			writeQuestionToFile(q);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileIndex++;
	}

	private void writeQuestionToFile(Question q) {
		pw.println(q.getLoggableString());
		pw.flush();
	}

	private String saveImage(BufferedImage img) {
		String fileName = getImageFile(fileIndex).getPath();
		try {
			ImageIO.write(img, "jpg", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private File getImageFile(int fileIndex) {
		try {
			File file = new File(directory + "/Question_" + fileIndex + ".jpg");
			if (!file.exists())
				file.createNewFile();
			return file;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String createSessionDirectory() {
		String path = IS_TEST ? "HQ Tests/" + new SimpleDateFormat("m-d-H+M+S").format(new Date())
				: "HQ Sessions/" + new SimpleDateFormat("M-d ha").format(new Date());
		File file = new File(path);
		int val = 1;
		while (file.isDirectory()) {
			file = new File(path + " (" + val++ + ")");
		}
		file.mkdirs();
		return file.getPath();
	}
}
