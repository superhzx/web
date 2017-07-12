package cn.rails.iServer.utils;
import java.io.Serializable;

import cn.rails.iServer.utils.AbstractUUIDGenerator;


public class UUIDHexGenerator extends AbstractUUIDGenerator {
	private String sep = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}

	public Serializable generate() {
		return new StringBuffer(36)
			.append( format( getIP() ) ).append(sep)
			.append( format( getJVM() ) ).append(sep)
			.append( format( getHiTime() ) ).append(sep)
			.append( format( getLoTime() ) ).append(sep)
			.append( format( getCount() ) )
			.toString();
	}
	
	public static String getUUID(){
		return (String)new UUIDHexGenerator().generate();
	}


	public static void main( String[] args ) throws Exception {
		UUIDHexGenerator u=new UUIDHexGenerator();
		for ( int i=0; i<10; i++) {
			String id = (String) u.generate();
			System.out.println(id);
			String id2 = (String) u.generate();
			System.out.println(id2);
		}
	}
}
