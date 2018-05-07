package cn.harry12800.lnk.remind;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.harry12800.tools.FileUtils;

public class UserData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 人物名称
	 */
	public String name = "测试名称";
	/**
	 * 提醒数据
	 */
	public List<RemindData> remindDataes = new ArrayList<RemindData>();
	/**
	 * 人物数据
	 */
	public Map<String, String> data = new LinkedHashMap<String, String>(0);
	/**
	 * 文件夹位置
	 */
	public File file;
	/**
	 * 配置
	 */
	public String infoPath;
	public String imagePath;
	private int sex = 0;

	public UserData(File file) {
		this.file=file;
		initImageData();
		initInfoData();
		initTimerData();
		
	}
	private void initInfoData()   {
		this.infoPath = file.getAbsolutePath()+File.separator+"info.json";
		File file2 = new File(infoPath);
		if(!file2.exists()){
			try {
				file2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			Map<String, String> file2Map = FileUtils.file2Map(infoPath,"utf-8");
			data.putAll(file2Map);
			String string = data.get("sex");
			if("1".equals(string)){
				sex=1;
			}
			name = data.get("name");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initTimerData() {
		
	}
	public void initImageData() {
		this.imagePath = file.getAbsolutePath()+File.separator+"image";
		File file2 = new File(imagePath);
		if(!file2.exists()){
			file2.mkdir();
		}
	}
	public byte[] image2byte(BufferedImage image,String type) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(image,type, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public void addImage(String path){
	}

	/**
	 * 获取name
	 *	@return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		data.put("name", name);
	}
	/**
	 * 获取remindDatas
	 *	@return the remindDatas
	 */
	public List<RemindData> getRemindDataes() {
		return remindDataes;
	}

	/**
	 * 获取data
	 *	@return the data
	 */
	public Map<String, String> getData() {
		return data;
	}

	public static void main(String[] args) throws Exception {
//		UserData userData = new UserData(new File( ""));
//		userData.addImage(ImageUtils.getByName("desk.jpg") );
//		FileOutputStream asd= new FileOutputStream(new File("C:/1.db")); 
//		ObjectOutputStream sd = new ObjectOutputStream(asd);
//		sd.writeObject(userData);
//		sd.close();
//		FileInputStream stream = new FileInputStream(new File("C:/1.db"));
//		ObjectInputStream sodInputStream = new  ObjectInputStream(stream);
//		Object readObject = sodInputStream.readObject();
//		UserData aData= (UserData) readObject;
//		byte[] sdf = (byte[]) aData.images.get(0);
//		  FileOutputStream fout = new FileOutputStream("C:/1.png");
//          //将字节写入文件
//          fout.write(sdf);
//          fout.close();
//		System.out.println(sdf.length);
//		System.out.println(aData.data);
	}
	
	/**
	 * 获取infoPath
	 *	@return the infoPath
	 */
	public String getInfoPath() {
		return infoPath;
	}
	/**
	 * 设置infoPath
	 * @param infoPath the infoPath to set
	 */
	public void setInfoPath(String infoPath) {
		this.infoPath = infoPath;
	}
	/**
	 * 获取imagePath
	 *	@return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}
	/**
	 * 设置imagePath
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * 设置remindDataes
	 * @param remindDataes the remindDataes to set
	 */
	public void setRemindDataes(List<RemindData> remindDataes) {
		this.remindDataes = remindDataes;
	}
	/**
	 * 设置data
	 * @param data the data to set
	 */
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	/**
	 * 设置sex
	 * @param sex the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	/**
	 * 获取所有相册图片
	 * @return
	 */
	public List<String>  getImages() {
		 return null;
	}
	public int getSex() {
		return this.sex ;
	}
}
