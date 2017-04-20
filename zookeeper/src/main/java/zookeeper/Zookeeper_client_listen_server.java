package zookeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;


public class Zookeeper_client_listen_server {
	
	private String groupNode = "sgroup";  
    private ZooKeeper zk;  
    private Stat stat = new Stat();  
    private volatile List<String> serverList;  

	public void zookeeperconnect() throws KeeperException, InterruptedException{
		
		try {
			 zk=new ZooKeeper("localhost:2181",5000,new Watcher(){				
				public void process(WatchedEvent event) {
					// TODO Auto-generated method stub
					if(event.getType()==EventType.NodeChildrenChanged&&event.getPath().equals(("/"+groupNode))){
						try {
							updateserverList();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (KeeperException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			});
			updateserverList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateserverList() throws KeeperException, InterruptedException, UnsupportedEncodingException{
		 List<String> newServerList = new ArrayList<String>();  
		  
	        // ��ȡ������groupNode���ӽڵ�仯  
	        // watch����Ϊtrue, ��ʾ�����ӽڵ�仯�¼�.   
	        // ÿ�ζ���Ҫ����ע�����, ��Ϊһ��ע��, ֻ�ܼ���һ���¼�, �������������ּ���, ��������ע��  
	        List<String> subList = zk.getChildren("/sgroup", true);  
	        for (String subNode : subList) {  
	            // ��ȡÿ���ӽڵ��¹�����server��ַ  ,server����
	        	//statΪд�����������new һ��stat���󣬴���÷�������÷������data��״̬д��stat����
	            byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);  
	            newServerList.add(new String(data, "utf-8"));  //server�����б�
	        }  
	  
	        // �滻server�б�  
	        serverList = newServerList;  
	  
	        System.out.println("server list updated: " + serverList);  
	}
	
	 /** 
     * client�Ĺ����߼�д����������� 
     * �˴������κδ���, ֻ��client sleep 
     */  
    public void handle() throws InterruptedException {  
        Thread.sleep(Long.MAX_VALUE);  
    }  
  
    public static void main(String[] args) throws Exception {  
    	Zookeeper_client_listen_server ac = new Zookeeper_client_listen_server();  
        ac.zookeeperconnect();
        ac.handle();  
    } 
}

