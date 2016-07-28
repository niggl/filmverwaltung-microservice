package edu.kit.iai.util;

public class Attribute {
	private String name;
	private Object value;
	private int type;
	
	public final static int TYP_STRING = 0;
	public final static int TYP_STRING_ARRAY = 1;
	public final static int TYP_INT = 2;
	public final static int TYP_INT_ARRAY = 3;
	public final static int TYP_BOOLEAN = 4;
	public final static int TYP_BOOLEAN_ARRAY = 5;
	public final static int TYP_DOUBLE = 6;
	public final static int TYP_DOUBLE_ARRAY = 7;
	public final static int TYP_FLOAT = 8;
	public final static int TYP_FLOAT_ARRAY = 9;
	public final static int TYP_SHORT = 10;
	public final static int TYP_SHORT_ARRAY = 11;
	public final static int TYP_LONG = 12;
	public final static int TYP_LONG_ARRAY = 13;
	public final static int TYP_BYTE_ARRAY = 14;
	
	private static String[] typeNames = null; 
	
	private final static String[] xx = new String[]{"a", "b"};
	public static String[] getXX(){
		String[] aa = new String[xx.length];
		System.arraycopy(xx, 0, aa, 0, xx.length);
		return aa;
		
	};
	
	
	public String[] getTypeNames(){
		if(typeNames == null){
			typeNames = new String[15];
			typeNames[TYP_STRING] = "String";
			typeNames[TYP_STRING_ARRAY] = "String_Array";
			typeNames[TYP_INT] = "Integer";
			typeNames[TYP_INT_ARRAY] = "Integer_Array";
			typeNames[TYP_BOOLEAN] = "Boolean";
			typeNames[TYP_BOOLEAN_ARRAY] = "TYP_BOOLEAN_ARRAY";
			typeNames[TYP_DOUBLE] = "Double";
			typeNames[TYP_DOUBLE_ARRAY] = "Double_Array";
			typeNames[TYP_FLOAT] = "Float";
			typeNames[TYP_FLOAT_ARRAY] = "Float_Array";
			typeNames[TYP_SHORT] = "Short";
			typeNames[TYP_SHORT_ARRAY] = "Short_Array";
			typeNames[TYP_LONG] = "Long";
			typeNames[TYP_LONG_ARRAY] = "Long_Array";
			typeNames[TYP_BYTE_ARRAY] = "Byte_Array";
		}
		return typeNames;
	}
	

	
//	public Attribute(String name, Object value, int type) {
//		
//	}
	public Attribute(String name, Object value, int type) throws IllegalArgumentException{
		this.name = name;
		this.value = value;
		this.type = type;
		if( ! checkTypeAndValue() ) throw new IllegalArgumentException("Type and Value mismatch: Value: " + value.getClass() + ", Type: " + type);
//		Saubere Fehlermeldung
	}
	
	private boolean checkTypeAndValue(){
		switch (type) {
		case TYP_STRING:
			if(value instanceof String){
				return true;
			}
			break;
		case TYP_STRING_ARRAY:
			if(value instanceof String[]){
				return true;
			}
			break;
		case TYP_INT:
			if(value instanceof Integer){
				return true;
			}
			break;
		case TYP_INT_ARRAY:
			if(value instanceof Integer[]){
				return true;
			}
			break;
		case TYP_BOOLEAN:
			if(value instanceof Boolean){
				return true;
			}
			break;
		case TYP_BOOLEAN_ARRAY:
			if(value instanceof Boolean[]){
				return true;
			}
			break;
		case TYP_SHORT:
			if(value instanceof Short){
				return true;
			}
			break;
		case TYP_SHORT_ARRAY:
			if(value instanceof Short[]){
				return true;
			}
			break;
		case TYP_LONG:
			if(value instanceof Long){
				return true;
			}
			break;
		case TYP_LONG_ARRAY:
			if(value instanceof Long[]){
				return true;
			}
			break;
		case TYP_DOUBLE:
			if(value instanceof Double){
				return true;
			}
			break;
		case TYP_DOUBLE_ARRAY:
			if(value instanceof Double[]){
				return true;
			}
			break;
		case TYP_FLOAT:
			if(value instanceof Float){
				return true;
			}
			break;
		case TYP_FLOAT_ARRAY:
			if(value instanceof Float[]){
				return true;
			}
			break;
		case TYP_BYTE_ARRAY:
			if(value instanceof Byte[]){
				return true;
			}
			break;
		}
			return false;
	}
	
	public Object getValue(){
		return value;
	}
	
	public Object getName() {
		return name;
	}
	
	public int getType() {
		return type;
	}
	
}
