package com.lamp.decoration.foundation.network.redis.utils;

import org.junit.Test;

public class Tests {

	@Test
	public void one() {
		System.out.println((byte) 'a');// 97
		System.out.println((byte) 'A');// 65
		System.out.println((byte) 'z');// 122
		System.out.println((byte) 'Z');// 90
		System.out.println((byte) '0');// 48
		System.out.println((byte) '9');// 57
		//At 20 years of age, the will reigns; at 30, the wit; and at 40, the judgment
		String str = "Cv 79 AGCTU QH CIG、VJG YKnn TGKIPU；Cv 69、VJG YKV；CPF Cv 59、VJG LWFIOGPV";
		char[] chars = str.toCharArray();
		char c=(char)-1;
		boolean b = true;
		for (char by : chars) {
			if ((by <= 57) && (by >= 48)) {
				c = (char)(57 -(by - 48));
			}else if ((by <= 90) && (by >= 65)) {
				if(b) {
					b = false;
					c = (char)(by + (by<= 66 ? 24 : -2));
				}else {
					c = (char)(by + (by<= 66 ? 24 : -2)+32);
				}
			}else if ((by <= 122) && (by >= 91)) {
					c = (char)(by + (by<= 91 ? 24 : -2));
			}else {
				c = by;
			}
			System.out.print(c);
		}
	}
	
	@Test
	public void two() {
		byte[] A = new byte[]{0,1,2,3,4,5};
		int length = A.length;
		int z=1 ,j= 0;
		System.out.println("这是一类");
		for(int i = 1 ; ;  ) {
			if( j < length) {
				System.out.print( A[j]);
			}
			if(j >= length - 1) {
				System.out.println();
				if(z == i) {
					if(i++ == length) {
						break;
					}
					z = 0;
					System.out.println();
					System.out.println("这是一类");
				}
				z++;
				j = z-1;
			}else {
				j = j+i;
			}
		}
	}
	
	public void three() {
		
	}
	
	static class  WorkSpace{
		
		private String conPath;
		
		private String spacePath;
		
		public WorkSpace() {
			
		}
		
		void changeDirectory(String change){
			
		}
		
	}
}
