package com.rookie.opcua.utils;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {
	
	/**
	 * 拷贝属性类型
	 * @param source 源对象
	 * @param target 目标对象
	 * @param ignoreProperties 拷贝忽略的属性
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void copyProperties(Object source, Object target, String[] ignoreProperties) throws IllegalArgumentException, IllegalAccessException {
		Field[] sourcefs = source.getClass().getDeclaredFields();
		Field[] targetfs = target.getClass().getDeclaredFields();
		if(ignoreProperties==null) {
			ignoreProperties = new String[]{};
		}
		List<String> ignorePropertieslist = Arrays.asList(ignoreProperties);
		
		for (Field field : targetfs) {
			if(!java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
				if(!ignorePropertieslist.contains(field.getName())) {
					for (Field f : sourcefs) {
						if((field.getType()+" "+field.getName()).equals(f.getType()+" "+f.getName())){
							f.setAccessible(true);
							field.setAccessible(true);
							Object fieldValue= f.get(source);
							if(fieldValue != null){
								field.set(target, fieldValue);
							}
							break;
						}
					}
					
				}
			}
		}
	}
	
	//修改类属性
	public static Map<String,Object> editProperties(Object source, Object compare, String[] ignoreProperties, String addStr) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Map<String,Object> map = new HashMap<String,Object>(70);
		Field[] comparefs = compare.getClass().getDeclaredFields();
		if(ignoreProperties==null) {
			ignoreProperties = new String[]{};
		}
		List<String> ignorePropertieslist = Arrays.asList(ignoreProperties);
		
		for(Field f : comparefs) {
			if(!java.lang.reflect.Modifier.isFinal(f.getModifiers())) {
				if(!ignorePropertieslist.contains(f.getName())) {
					Field sourcef = source.getClass().getDeclaredField(f.getName());
					sourcef.setAccessible(true);
					Object sourcefieldValue =sourcef.get(source);//源类字段值
					if(sourcefieldValue==null) {
						sourcefieldValue = "";
					}
					Field comparef = compare.getClass().getDeclaredField(f.getName());
					comparef.setAccessible(true);
					Object targetfieldValue = comparef.get(compare);//比较类字段值
					if(targetfieldValue==null) {
						targetfieldValue = "";
					}
					
					if(!sourcefieldValue.equals(targetfieldValue) ) {
						System.out.println(targetfieldValue.toString());
						if(StringUtils.isNotBlank(targetfieldValue.toString()) &&targetfieldValue.toString().contains(addStr)){
							map.put(f.getName(),targetfieldValue);
						}else{
							map.put(f.getName(), targetfieldValue+addStr);
						}

					}else {
						map.put(f.getName(), targetfieldValue);
					}
					
				}
			}
		}
		return map;
	}
	
	
	//构建Entity InsertSql
	public static String buildEntityInsertSql(Object entity, String batchId, String[] ignoreProperties) throws IllegalArgumentException, IllegalAccessException {
		String tableName = entity.getClass().getAnnotation(Table.class).name();
		String sql = "insert into "+tableName+"_"+batchId;
		Field[] fields = entity.getClass().getDeclaredFields();
		if(ignoreProperties==null) {
			ignoreProperties = new String[]{};
		}
		List<String> ignorePropertieslist = Arrays.asList(ignoreProperties);
		
		String columnSql = "(";
		String valSql = " values (";
		
		for(int i=0;i<fields.length;i++) {
			Field f = fields[i];
			if(!java.lang.reflect.Modifier.isFinal(f.getModifiers())) {
				if(!ignorePropertieslist.contains(f.getName())) {
					f.setAccessible(true);
					if(f.get(entity)!=null) {
						columnSql += f.getAnnotation(Column.class).name()+",";
						valSql += "'"+f.get(entity)+"',";
					}
				}
			}
		}
		
		columnSql = columnSql.substring(0, columnSql.length()-1)+")";
		valSql = valSql.substring(0, valSql.length()-1)+")";
		
		return sql+columnSql+valSql;
	}
	
	//构建Entity InsertSql
	public static String buildEntityUpdateSql(Object entity, String batchId,String entityId, String[] ignoreProperties) throws IllegalArgumentException, IllegalAccessException {
		String tableName = entity.getClass().getAnnotation(Table.class).name();
		String sql = "update "+tableName+"_"+batchId+" ";
		Field[] fields = entity.getClass().getDeclaredFields();
		if(ignoreProperties==null) {
			ignoreProperties = new String[]{};
		}
		List<String> ignorePropertieslist = Arrays.asList(ignoreProperties);

		String setSql = " set ";
	
		for(int i=0;i<fields.length;i++) {
			Field f = fields[i];
			if(!java.lang.reflect.Modifier.isFinal(f.getModifiers())) {
				if(!ignorePropertieslist.contains(f.getName())) {
					f.setAccessible(true);
					if(f.get(entity)!=null) {
						setSql += " " + f.getAnnotation(Column.class).name()+" = " + "'"+f.get(entity)+"',";
					}
				}
			}
		}
		
		setSql = setSql.substring(0, setSql.length()-1);
		
		String whereSql = " where id = '"+entityId+"'";
		
		return sql+setSql+whereSql;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InterruptedException, InvocationTargetException {
//		AssetsInfo assetsInfo = new AssetsInfo();
//		assetsInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()-100));
//		System.out.println(assetsInfo.getLastUpdateTime());
//		AssetsInfoTmp tmp = new AssetsInfoTmp();
//		System.out.println(tmp.getLastUpdateTime());
//		copyProperties(assetsInfo, tmp, new String[]{"lastUpdateTime"});
//		System.out.println(tmp.getLastUpdateTime());
	}
	
	/**
	 * 比较两个对象的字段及值
	 * @param source 比较对象
	 * @param target 被比较对象
	 * @param ignoreProperties 忽略比较的字段
	 * @return true:两个对象值相同，false:两个对象值存在不同
	 * @date 2019年1月23日
	 */
	public static boolean compareProperties(Object source, Object target, String[] ignoreProperties) throws IllegalArgumentException, IllegalAccessException {
		Field[] sourcefs = source.getClass().getDeclaredFields();
		Field[] targetfs = target.getClass().getDeclaredFields();
		if(ignoreProperties==null) {
			ignoreProperties = new String[]{};
		}
		List<String> ignorePropertieslist = Arrays.asList(ignoreProperties);
		
		for (Field field : targetfs) {
			if(!java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
				if(!ignorePropertieslist.contains(field.getName())) {
					field.setAccessible(true);
					Object targetVal = field.get(target);
					for (Field f : sourcefs) {
						if((field.getType()+" "+field.getName()).equals(f.getType()+" "+f.getName())){
							f.setAccessible(true);
							Object fieldValue= f.get(source);
							if(targetVal == null || fieldValue == null){
								if(targetVal != fieldValue){
									return false;
								}
							}else if(!String.valueOf(targetVal).equals(String.valueOf(fieldValue))){
								return false;
							}
						}
					}
					
				}
			}
		}
		return true;
	}
}
