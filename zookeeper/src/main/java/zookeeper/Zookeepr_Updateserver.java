package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;


//�ֲ�ʽϵͳ����˴��룬ÿ����һ������ˣ������zookeeper��sgroup�ڵ��½���һ����ʱ�����ӽڵ㡣���������˱ĵ��ˣ�
//��÷������zookeeper�µĽڵ�ͻ���ʧ����Ϊ�����Ľڵ�Ϊ��ʱ�ڵ㡣����ͻ��˶�zookeeper��sgroup�ڵ���м����Ļ�
//�򵱸ýڵ�仯ʱ���ͻ��˾Ϳ�֪������˵ĸ����ı仯��

public class Zookeepr_Updateserver {

	    private String groupNode = "sgroup";  
	    private String subNode = "sub";  
	  
	    /** 
	     * ����zookeeper 
	     * @param address server�ĵ�ַ 
	     */  
	    public void connectZookeeper(String address) throws Exception {  
	        ZooKeeper zk = new ZooKeeper("localhost:2181", 5000, new serverwatcher()); 
	        // ��"/sgroup"�´����ӽڵ�  
	        // �ӽڵ����������ΪEPHEMERAL_SEQUENTIAL, ��������һ����ʱ�ڵ�, �����ӽڵ�����ƺ������һ�����ֺ�׺  
	        // ��server�ĵ�ַ���ݹ������´������ӽڵ���  
	        String createdPath = zk.create("/" + groupNode + "/" + subNode, address.getBytes("utf-8"),   
	            Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);  
	        //Ids.OPEN_ACL_UNSAFE��ʾ���趨Ȩ��
	        //CreateMode.EPHEMERAL_SEQUENTIAL��ʾ���������Ե���ʱ�ڵ�
	        System.out.println("create: " + createdPath);  
	    }  
	      
	    /** 
	     * server�Ĺ����߼�д����������� 
	     * �˴������κδ���, ֻ��server sleep 
	     */  
	    public void handle() throws InterruptedException {  
	        Thread.sleep(Long.MAX_VALUE);  
	    }  
	      
	    public static void main(String[] args) throws Exception {  
	        // �ڲ�����ָ��server�ĵ�ַ  
	        if (args.length == 0) {  
	            System.err.println("The first argument must be server address");  
	            System.exit(1);  
	        }  
	          
	        Zookeepr_Updateserver as = new Zookeepr_Updateserver();  
	        as.connectZookeeper(args[0]);  
	        as.handle();  
	    }  
	}  

  class serverwatcher implements Watcher{

	public void process(WatchedEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("do noting��");
	}
  }
