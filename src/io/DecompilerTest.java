package io;

import java.io.File;

public class DecompilerTest {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		//long startTime = System.currentTimeMillis();
		PackDecompiler a = new PackDecompiler();
		a.decompile(new File("U:\\game\\Minecraft���ʰ�\\.minecraft"), "1.12","U:\\1.12pack");

		while (a.getProgress()!=100){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("a:"+a.getProgress()+"%;");
		}
		System.out.println("��ѹ��jar��");
		//long endTime = System.currentTimeMillis();
		//long usedTime = (endTime-startTime)/1000;
		//System.out.println(usedTime);
	}

}