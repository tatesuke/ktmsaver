package com.tatesuke.ktmsaver.ui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * ファイル保存ダイアログ
 * 
 * @author tatesuke
 */
public class KTMFileDialog {

	private JFrame frame;
	private JFileChooser dialog;

	public KTMFileDialog() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			// 握りつぶし
		}
		try {
			UIManager
					.setLookAndFeel("apple.laf.AquaLookAndFeel");
		} catch (Exception e) {
			// 握りつぶし
		}
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// 握りつぶし
		}

		frame = new JFrame();
		frame.setTitle("KTMSaver Save As...");
		JLabel label = new JLabel("showing KTM savedialog...");
		frame.add(label);
		frame.setUndecorated(true);
		frame.pack();

		dialog = new JFileChooser();
	}

	/**
	 * ファイル保存ダイアログを表示する。
	 * <p>
	 * ユーザが拡張子を指定しなかった場合、".html"を補う。
	 * </p>
	 * 
	 * @param baseDir
	 *            初期ディレクトリ名(null可能)
	 * @param name
	 *            初期ファイル名（null不可）
	 * @return ユーザが指定したファイル。キャンセルした場合は場合null
	 * @throws InvocationTargetException
	 *             　ダイアログの表示に失敗した場合
	 * @throws InterruptedException
	 *             ダイアログの表示に失敗した場合
	 */
	public File getFile(File baseDir, String name)
			throws InvocationTargetException, InterruptedException {

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				if (baseDir != null) {
					dialog.setCurrentDirectory(baseDir);
				}

				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				dialog.showSaveDialog(frame);
				frame.setVisible(false);
			}
		});

		while (frame.isVisible()) {
			Thread.sleep(50);
		}

		File file = dialog.getSelectedFile();

		if (file != null) {
			String ext = getExtension(file.getName());
			if (ext == null) {
				file = new File(file.getParentFile(), file.getName() + ".html");
			}
			return file;
		} else {
			return null;
		}
	}

	private String getExtension(String fileName) {
		String ext = null;
		int dotIndex = fileName.lastIndexOf('.');

		if ((dotIndex > 0) && (dotIndex < fileName.length() - 1)) {
			ext = fileName.substring(dotIndex + 1).toLowerCase();
		}

		return ext;
	}

}
