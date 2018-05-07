package cn.harry12800.lnk.remind.dialog;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;

class CarouselImageLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double a = 0.6;
	int w = 0;
	int h = 0;
	int direct = 1;
	int index = 0;
	double location = 0;
	int len = 0;
	BufferedImage byNames[] = new BufferedImage[35];
	int moveClick = 0;
	/**
	 * 判断是否在wait中
	 */
	boolean flag = false;
	Thread thread = null;

	private synchronized void move() {
		while (true) {
			try {
				Thread.sleep(70);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (len == 0)
				return;
			location += location + a;
			if (location >= w) {
				location = w;
				repaint();
				index++;
				if (index == len)
					index = 0;
				location = 0;
				return;
			} else {
				repaint();
			}
		}

	}

	class MoveThread extends Thread {
		private CarouselImageLabel label;

		public MoveThread(CarouselImageLabel label) {
			this.label = label;
		}

		public void run() {
			while (true) {
				if (moveClick > 0) {
					move();
				} else {
					synchronized (label) {
						flag = true;
						try {
							label.wait(4000);
//							System.err.println(this);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						flag = false;
						move();
					}
				}
			}
		}
	}

	public CarouselImageLabel() {
		len = byNames.length;
		thread = new MoveThread(this);
		thread.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		w = getWidth() - 1;
		h = getHeight() - 1;
		Graphics2D g2d = (Graphics2D) g.create();
		if (len > 0)
			drawImage(g2d, byNames[index], location, w, h);
		if (len > 1)
			drawImage(g2d, byNames[(index + 1) % len], (location - w), w, h);
		g2d.dispose();
		super.paintComponent(g);

	}

	private void drawImage(Graphics2D g2d, BufferedImage bufferedImage,
			double location, int width, int height) {
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		int x, y, a, b;
		if (h * 1.0 / height > w * 1.0 / width) {
			if (h > height) {
				y = 0;
				b = height;
				a = (int) (w * 1.0 / (h * 1.0 / height));
				x = (width - a) / 2;
			} else {
				y = (height - h) / 2;
				x = (width - w) / 2;
				a = w;
				b = h;
			}
		} else {
			if (w > width) {
				a = width;
				x = 0;
				b = (int) (h * 1.0 / (w * 1.0 / width));
				y = (height - b) / 2;
			} else {
				y = (height - h) / 2;
				x = (width - w) / 2;
				a = w;
				b = h;
			}
		}
		g2d.drawImage(bufferedImage, (int) location + x, y, a, b, null);

	}

	public void setImages(BufferedImage[] array) {
		this.byNames = array;
		len = byNames.length;
	}

	public void setImages(String path) throws Exception {
		System.out.println(path);
		List<BufferedImage> lists = Lists.newArrayList();
		List<File> traverseDir = FileUtils.traverseDir(path,
				new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".jpg") || name.endsWith(".png");
					}
				});
		for (File file : traverseDir) {
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedImage read = ImageIO.read(fileInputStream);
			lists.add(read);
		}
		this.byNames = lists.toArray(new BufferedImage[0]);
		len = byNames.length;
	}

	public String currentImagePath() {
		return null;
	}

	public synchronized void after() {
		if (flag) {
			moveClick++;
			notify();
			moveClick--;
		}
		;
	}

	public synchronized void before() {

	}
}