package io;

import java.io.File;

public class DecompilerTest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//long startTime = System.currentTimeMillis();
		PackDecompiler a = new PackDecompiler();
		a.decompile(new File("U:\\game\\Minecraft国际版\\.minecraft"), "1.12","U:\\1.12pack");

		while (a.getProgress()!=100){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("a:"+a.getProgress()+"%;");
		}
		System.out.println("解压缩jar中");
		//long endTime = System.currentTimeMillis();
		//long usedTime = (endTime-startTime)/1000;
		//System.out.println(usedTime);
	}

}
