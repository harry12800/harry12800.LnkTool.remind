package cn.harry12800.lnk.remind.dialog;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cn.harry12800.j2se.component.utils.ImageUtils;
import cn.harry12800.j2se.utils.Clip;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;

public class Main {

	public static void main(String[] args) throws IOException {
		ImageUtils.addImage(Main.class);
		List<BufferedImage>  lists = Lists.newArrayList(); 
		String path  = "C:\\Users\\harry12800\\Desktop";
		List<File> traverseDir = FileUtils.traverseDir(path, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jpg")||name.endsWith(".png");
			}
		});
		for (File file : traverseDir) {
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedImage read = ImageIO.read(fileInputStream);
			lists.add(read);
		}
		CarouselImageLabel a = new CarouselImageLabel();
		a.setImages(lists.toArray(new BufferedImage[0]));
		Clip.seeCom(a);
//		for (int i=0 ; i<35; i++) {
//			String string = "0";
//			if(i<10) string+=i;
//			else
//				string  = i+"";
//			byNames[i] = ImageUtils.getByName("image_"+string+".png");
//			System.out.println(byNames[i]);
//		}
	}
}
