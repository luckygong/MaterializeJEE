package report;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import org.apache.hadoop.hbase.util.Bytes;

public class Test {

	private static Object converCelltValue(byte[] value, Class<?> resultType){
		Object result = null;
        try{
        	if(Boolean.class.equals(resultType) || boolean.class.equals(resultType)){
        		Constructor<?> cons = Boolean.class.getConstructor(boolean.class);
        		result = cons.newInstance(Bytes.toBoolean(value));
        	}
        	if(Character.class.equals(resultType) || char.class.equals(resultType)){
        		String tmp = Bytes.toString(value);
        		result = tmp.charAt(0);
        	}
        	if(String.class.equals(resultType)){
        		result = Bytes.toString(value);
        	}
        	if(Short.class.equals(resultType) || short.class.equals(resultType)){
        		Constructor<?> cons = Short.class.getConstructor(short.class);
        		result = cons.newInstance(Bytes.toShort(value));
        	}
        	if(Integer.class.equals(resultType) || int.class.equals(resultType)){
        		Constructor<?> cons = Integer.class.getConstructor(int.class);
        		result = cons.newInstance(Bytes.toInt(value));
        	}
        	if(Long.class.equals(resultType) || long.class.equals(resultType)){
        		Constructor<?> cons = Long.class.getConstructor(long.class);
        		result = cons.newInstance(Bytes.toLong(value));
        	}
        	if(Float.class.equals(resultType) || float.class.equals(resultType)){
        		Constructor<?> cons = Float.class.getConstructor(float.class);
        		result = cons.newInstance(Bytes.toFloat(value));
        	}
        	if(Double.class.equals(resultType) || double.class.equals(resultType)){
        		Constructor<?> cons = resultType.getConstructor(double.class);
        		result = cons.newInstance(Bytes.toDouble(value));
        	}
        	if(BigDecimal.class.equals(resultType)){
        		Constructor<?> cons = Double.class.getConstructor(BigDecimal.class);
        		result = cons.newInstance(Bytes.toBigDecimal(value));
        	}
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	public static void main(String[] args) {
		char value = 'A';
		byte[] bytes = Bytes.toBytes(String.valueOf(value));
		System.out.println(converCelltValue(bytes, char.class));
	}

}

