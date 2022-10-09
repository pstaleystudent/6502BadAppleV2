package asmencoderv2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

import javax.imageio.ImageIO;

public class asmencoderv2 {
	final static int RUN_LENGTH = 6;
    final static double RUN_MAX = Math.pow(2,RUN_LENGTH);
	public static void main(String[] args) throws IOException {
		final File folder = new File("PATH_DIRECT_HERE");
        FileWriter writer = new FileWriter("out.txt");
        BitSet bits = new BitSet();
        int bitI = 0;
        
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.getName().substring(0,3).equals("out")) {
	        	BufferedImage pic = ImageIO.read(fileEntry);
	        	pic = rotateClockwise90(pic);
	        	pic = mirrorImage(pic); //why? - I don't know
	        	int mode = -1;
	        	byte length = 0;
	        	
	        	for(int x = 0; x < pic.getWidth(); x++ ) {
		            for (int y = 0; y < pic.getHeight(); y++ ) {
		            	
	            		int col = ((pic.getRGB(y,x) == -1) ? 1 : 0);
	            		if (mode == -1) {
	            			mode = col;
	            			length = 1;
	            		} else if (mode == col && length < RUN_MAX - 1) {
	            			length++;
	            		} else {
	            			bits.set(bitI,mode == 1);
	            			bitI++;
	            			//System.out.print(length + " - >");
		            		System.out.print(mode);
	            			boolean[] newB = bits2(length);
	            			for (int i = 5; i >= 0; i--) {
	            				System.out.print(newB[i] ? 1 : 0);
	            				bits.set(bitI,newB[i]);
	            				bitI++;
	            			}
	            			//System.out.println();
	            			mode = col;
	            			length = 1;
	            		}
		            }
	        	}
	        	//force close run
	        	if (length != 0) {
	        		bits.set(bitI,mode == 1);
        			bitI++;
            		System.out.print(mode);
        			boolean[] newB = bits2(length);
        			for (int i = 5; i >= 0; i--) {
        				System.out.print(newB[i] ? 1 : 0);
        				bits.set(bitI,newB[i]);
        				bitI++;
        			}
        			System.out.println();
	        	}
	        }
	    }
	    //System.out.println('\n');
	    //printBits(bits);
        byte[] bytes = toByteArray(bits);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
        	sb.append('$');
            sb.append(String.format("%02X", bytes[i]));
            sb.append(',');
        }
        writer.write(sb.toString());
        writer.close();
	}
	public static BufferedImage rotateClockwise90(BufferedImage src) {
	    int width = src.getWidth();
	    int height = src.getHeight();
	    BufferedImage dest = new BufferedImage(height, width, src.getType());
	    Graphics2D graphics2D = dest.createGraphics();
	    graphics2D.translate((height - width) / 2, (height - width) / 2);
	    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
	    graphics2D.drawRenderedImage(src, null);
	    return dest;
	}
	static boolean[] bits(byte b) {
		  int n = 7;
		  final boolean[] set = new boolean[n];
		  while (--n >= 0) {
		    set[n] = (b & 0x80) != 0;
		    b <<= 1;
		  }
		  return set;
		}
	public static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[(bits.length() + 7) / 8];
	    for (int i=0; i < bits.length(); i++) {
	        if (bits.get(i)) {
	            //bytes[bytes.length-i/8-1] |= 1<<(i%8);
	        	bytes[i/8] |= 1<<(7-i%8);
	        }
	    }
	    return bytes;
	}
	static void printBits(BitSet b) {
		for (int i = 0; i < b.length(); i++)
			System.out.print(b.get(i) ? 1 : 0);
	}
	public static boolean[] bits2(byte b) {
	    boolean[] bits = new boolean[7];
	    for (int i = 6; i >= 0; i--) {
	        bits[i] = (b & (1 << i)) != 0;
	    }
		return bits;
	}
	static BufferedImage mirrorImage(BufferedImage simg) {
        int width = simg.getWidth(); 
        int height = simg.getHeight();
        BufferedImage mimg = new BufferedImage( 
            width, height, BufferedImage.TYPE_INT_ARGB); 
        for (int y = 0; y < height; y++) { 
            for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
                int p = simg.getRGB(lx, y);
                mimg.setRGB(rx, y, p); 
            } 
        } 
        return mimg;
	}
}
