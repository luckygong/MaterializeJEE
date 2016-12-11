package com.materialize.jee.platform.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class, 提取集合中元素的属性, 转换字符串到对象等Util函数.
 * 
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
	
	/**
	 * 通过反射, 获得Class定义中声明的父类的第一个泛型参数的类型. 如无法找到, 返回Object.class.
	 *
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenericType(final Class clazz) {
		return getSuperClassGenericType(clazz, 0);
	}

	/**
	 * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>
	 * 
	 * @param clazz
	 *            clazz 需要反射的类,该类必须继承范型父类
	 * @param index
	 *            泛型参数所在索引,从0开始.
	 * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getSuperClassGenericType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();// 得到泛型父类
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			throw new RuntimeException("索引不正确");
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 通过反射,获得方法返回值泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int
	 *            index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getMethodGenericReturnType(Method method, int index) {
		Type returnType = method.getGenericReturnType();
		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("索引不正确");
			}
			return (Class) typeArguments[index];
		}
		return Object.class;
	}

	/**
	 * 通过反射,获得方法返回值第一个泛型参数的实际类型. 如: public Map<String, Buyer> getNames(){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getMethodGenericReturnType(Method method) {
		return getMethodGenericReturnType(method, 0);
	}

	/**
	 * 通过反射,获得方法输入参数第index个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String,
	 * Buyer> maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @param int
	 *            index 第几个输入参数
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
		List<Class> results = new ArrayList<Class>();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (index >= genericParameterTypes.length || index < 0) {
			throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		Type genericParameterType = genericParameterTypes[index];
		if (genericParameterType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericParameterType;
			Type[] parameterArgTypes = aType.getActualTypeArguments();
			for (Type parameterArgType : parameterArgTypes) {
				Class parameterArgClass = (Class) parameterArgType;
				results.add(parameterArgClass);
			}
			return results;
		}
		return results;
	}

	/**
	 * 通过反射,获得方法输入参数第一个输入参数的所有泛型参数的实际类型. 如: public void add(Map<String, Buyer>
	 * maps, List<String> names){}
	 * 
	 * @param Method
	 *            method 方法
	 * @return 输入参数的泛型参数的实际类型集合, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回空集合
	 */
	public static List<Class> getMethodGenericParameterTypes(Method method) {
		return getMethodGenericParameterTypes(method, 0);
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param Field
	 *            field 字段
	 * @param int
	 *            index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getFieldGenericType(Field field, int index) {
		Type genericFieldType = field.getGenericType();

		if (genericFieldType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFieldType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index >= fieldArgTypes.length || index < 0) {
				throw new RuntimeException("索引不正确");
			}
			return (Class) fieldArgTypes[index];
		}
		return Object.class;
	}

	/**
	 * 通过反射,获得Field泛型参数的实际类型. 如: public Map<String, Buyer> names;
	 * 
	 * @param Field
	 *            field 字段
	 * @param int
	 *            index 泛型参数所在索引,从0开始.
	 * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回
	 *         <code>Object.class</code>
	 */
	public static Class getFieldGenericType(Field field) {
		return getFieldGenericType(field, 0);
	}

	/**
	 * 根据实体得到实体的所有属性
	 * 
	 * @param objClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String[] getColumnNames(String objClass) throws ClassNotFoundException {
		String[] wageStrArray = null;
		if (objClass != null) {
			Class class1 = Class.forName(objClass);
			Field[] field = class1.getDeclaredFields();
			wageStrArray = new String[field.length];
			for (int i = 0; i < field.length; i++) {
				wageStrArray[i] = field[i].getName();
			}
		}
		return wageStrArray;
	}

	public static Object[] field2Value(Field[] f, Object o) throws Exception {
		Object[] value = new Object[f.length];
		for (int i = 0; i < f.length; i++) {
			value[i] = f[i].get(o);
		}
		return value;
	}

	/**
	 * 直接读取目标对象属性值,无视private/protected修饰符, 不经过属性的getter函数.
	 * 
	 * @param target
	 *            目标对象.
	 * @param fieldName
	 *            目标对象属性名.
	 * @return
	 */
	public static Object getFieldValue(final Object target, final String fieldName) {
		Field field = getAccessibleField(target, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + target + "]");
		}
		makeAccessible(field);
		Object result = null;
		try {
			result = field.get(target);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置目标对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 * 
	 * @param target
	 *            目标对象.
	 * @param fieldName
	 *            目标对象属性名.
	 * @param value
	 *            属性值.
	 */
	public static void setFieldValue(final Object target, final String fieldName, final Object value) {
		Field field = getAccessibleField(target, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + target + "]");
		}

		makeAccessible(field);

		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 调用目标对象属性Getter方法，读取目标对象属性值.
	 * 
	 * @param target
	 *            目标对象.
	 * @param fieldName
	 *            目标对象属性名.
	 * @return
	 */
	public static Object invokeGetterMethod(Object target, String fieldName) {
		String getterMethodName = "get" + StringUtils.capitalize(fieldName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用目标对象属性Setter方法，设置目标对象属性值.
	 * 
	 * @param target
	 *            目标对象.
	 * @param fieldName
	 *            目标对象属性名.
	 * @param value
	 *            属性值.
	 * @param propertyType
	 *            属性类型，用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object target, String fieldName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(fieldName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.用于一次性调用的情况.
	 * 
	 * @param target
	 *            目标对象.
	 * @param methodName
	 *            目标对象中方法名.
	 * @param parameterTypes
	 *            方法参数类型数组.
	 * @param args
	 *            方法参数值.
	 * @return
	 */
	public static Object invokeMethod(final Object target, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
		Method method = getAccessibleMethod(target, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + target + "]");
		}

		try {
			return method.invoke(target, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 调用目标对象属性Setter方法 ，设置目标对象属性值，使用value的Class来查找Setter方法.
	 * 
	 * @param target
	 *            目标对象.
	 * @param fieldName
	 *            目标对象属性名.
	 * @param value
	 *            属性值.
	 */
	public static void invokeSetterMethod(Object target, String fieldName, Object value) {
		invokeSetterMethod(target, fieldName, value, null);
	}

	/**
	 * 循环向上转型,获取类的所有DeclaredField.
	 * 
	 * @param clazz
	 *            类名
	 * @param fieldName
	 *            属性名
	 * @return
	 */
	public static Field[] getDeclaredFields(final Class clazz) {
		Assert.notNull(clazz);
		List<Field> fidles = new ArrayList<Field>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			fidles.addAll(Arrays.asList(superClass.getDeclaredFields()));
		}
		return fidles.toArray(new Field[fidles.size()]);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @param object
	 *            对象名
	 * @param fieldName
	 *            属性名
	 */
	public static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 * 
	 * @param clazz
	 *            类名
	 * @param fieldName
	 *            属性名
	 * @return
	 */
	public static Field getDeclaredField(final Class clazz, final String fieldName) {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 获取对象的DeclaredField, 循环向上转型, 并强制设置属性为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * @param target
	 *            对象.
	 * @param fieldName
	 *            属性名.
	 * @return Field
	 */
	public static Field getAccessibleField(final Object target, final String fieldName) {
		Assert.notNull(target, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = target.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	public static Field[] getAccessibleField(final Object target) {
		Assert.notNull(target, "object不能为空");
		Class<?> clzz = target.getClass();
		if (clzz != Object.class) {
			Field[] fields = clzz.getDeclaredFields();
			for (Field field : fields) {
				field.getName();
			}
		}
		return null;
	}

	public static Field[] getAccessibleField(final Class clazz) {
		if (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.getName();
			}
		}
		return null;
	}

	/**
	 * 获取对象的DeclaredMethod,循环向上转型, 并强制设置方法为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 
	 * 用于方法需要被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
	 * args)
	 * 
	 * @param target
	 *            目标对象.
	 * @param methodName
	 *            目标对象方法名.
	 * @param parameterTypes
	 *            方法参数值.
	 * @return
	 */
	public static Method getAccessibleMethod(final Object target, final String methodName, final Class<?>... parameterTypes) {
		Assert.notNull(target, "object不能为空");

		for (Class<?> superClass = target.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {// NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 强制转换fileld可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	public static Object getSimpleProperty(Object bean, String propName) throws Exception {
		return bean.getClass().getMethod(getReadMethod(propName)).invoke(bean);
	}

	private static String getReadMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
	}

	/**
	 * 根据类型对具体对象属性赋值
	 * 
	 * @param form
	 *            被赋值对象
	 * @param field
	 *            属性
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean setFieldValue(Object form, Field field, String value) {
		String generic = field.toGenericString();
		if (generic.indexOf(" static ") > 0 || generic.indexOf(" final ") > 0) {
			return false;
		}
		String elemType = field.getType().toString();
		if (elemType.indexOf("boolean") != -1 || elemType.indexOf("Boolean") != -1) {
			try {
				field.set(form, Boolean.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("byte") != -1 || elemType.indexOf("Byte") != -1) {
			try {
				field.set(form, Byte.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("char") != -1 || elemType.indexOf("Character") != -1) {
			try {
				field.set(form, Character.valueOf(value.charAt(0)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("double") != -1 || elemType.indexOf("Double") != -1) {
			try {
				field.set(form, Double.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("float") != -1 || elemType.indexOf("Float") != -1) {
			try {
				field.set(form, Float.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("int") != -1 || elemType.indexOf("Integer") != -1) {
			try {
				field.set(form, Integer.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("long") != -1 || elemType.indexOf("Long") != -1) {
			try {
				field.set(form, Long.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else if (elemType.indexOf("short") != -1 || elemType.indexOf("Short") != -1) {
			try {
				field.set(form, Short.valueOf(value));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			try {
				field.set(form, (Object) value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 动态增加数组长度 Description: Array add length
	 * 
	 * @param oldArray
	 * @param addLength
	 *            增加长度
	 * @return Object
	 */
	public static Object arrayAddLength(Object oldArray, int addLength) {
		Class<?> c = oldArray.getClass();
		if (!c.isArray())
			return null;
		Class<?> componentType = c.getComponentType();
		int length = Array.getLength(oldArray);
		int newLength = length + addLength;
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(oldArray, 0, newArray, 0, length);
		return newArray;
	}

	/**
	 * 得到实体类
	 * 
	 * @param objClass
	 *            实体类包含包名
	 * @return
	 */
	public static Class getEntityClass(String objClass) {
		Class entityClass = null;
		try {
			entityClass = Class.forName(objClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityClass;
	}
	
}
