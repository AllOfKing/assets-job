package com.rookie.opcua.utils.hbase.job;

import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

 public class HBasePool {                                                                                 
    private static final String HBASE_MASTER = "hbase.master";                                          
 	private static final String HBASE_ZOOKEEPER_PORT = "hbase.zookeeper.property.clientPort";           
 	private static final String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";                      
 	private static final String HBASE_ZOOKEEPER_ZNODE_PARENT = "zookeeper.znode.parent";                
 	private volatile static HBasePool hbaseProxy;                                                                
 	private static Connection hbaseConnection; 
 	//private static HTablePool hbasePool;                                                               
	private	HBaseAdmin  hbaseAdmin = null;
	private HBasePool() {
	}
	public static void init(){
		Configuration conf = null;
		InputStream is = null;
		if(conf == null){
			conf = HBaseConfiguration.create();
			
		}
		try {
			is = HBasePool.class.getClassLoader().getResourceAsStream(
					"platform-site.xml");
			conf.addResource(is);
//			conf.set("hbase.client.scanner.timeout.period", "600000");
//			conf.set("hbase.rqc.timeout", "600000");
			if(hbaseConnection == null){
				hbaseConnection = ConnectionFactory.createConnection(conf); 
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();                                                                          
		}finally {
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
 	public static HBasePool getInstance() {                                                                 
 		// double-checked locking                                                                         
 		if (hbaseProxy == null) {                                                                              
 			synchronized (HBasePool.class) {                                                                    
 				hbaseProxy = new HBasePool();  
 				init();
 			}                                                                                               
 		}                                                                                                 
 		return hbaseProxy;                                                                                     
 	}                                                                                                   
                                                                                                      
                                                                                                      
 	public Connection getConnection() {                                                                 
 		return hbaseConnection;                                                                                
 	}    
 	
 	public HBaseAdmin getHbaseAdmin() {                                                                 
 		return hbaseAdmin;                                                                                
 	}
 }                                                                                                    