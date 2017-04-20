package zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

//����zookeeperʵ�ֲַ�ʽ��
public class Zookeeper_locks {
	private static final int session_time=5000;
	private String zookeeperaddress="localhost:2181";
	private String node="Lock";
	private String subnode="servernunber";
	private ZooKeeper zk;
	private CountDownLatch countdown;
	private String thispath;
	private String listennode;
	public void zookeeperconn() throws Exception{
	    countdown=new CountDownLatch(1);
		try {
			zk=new ZooKeeper(zookeeperaddress,session_time,new Watcher(){

				public void process(WatchedEvent event) {//���ӳɹ���ص�process����
					// TODO Auto-generated method stub
					if(event.getState()==KeeperState.SyncConnected){
						countdown.countDown();//����ͬ������
					}
				}	
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			countdown.await();			//����ͬ����zookeeper�Ự�����Ͷ��󴴽��������첽�ģ�
			                            //���Եȴ����ӳɹ������ִ��
			thispath=zk.create("/"+node+"/"+subnode,null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		    //�ж��Լ��Ƿ����������������˾�ִ����Ӧ�Ĳ���������������
			// waitһС��, �ý��������һЩ  
	        Thread.sleep(10);  
	  
	        // ע��, û�б�Ҫ����"/locks"���ӽڵ�ı仯���  
	        List<String> childrenNodes = zk.getChildren("/" +node, false);  
	  
	        // �б���ֻ��һ���ӽڵ�, �ǿ϶�����thisPath, ˵��client�����  
	        if (childrenNodes.size() == 1) {  
	            doSomething();  
	        } else {  
	            String thisNode = thispath.substring(("/" +node + "/").length());  
	            // ����  
	            Collections.sort(childrenNodes);  
	            int index = childrenNodes.indexOf(thisNode);  
	            if (index == -1) {  
	                // never happened  
	            } else if (index == 0) {  
	                // inddx == 0, ˵��thisNode���б�����С, ��ǰclient�����  
	                doSomething();  
	            } else {  
	                // ���������thisPathǰ1λ�Ľڵ�  
	                this.listennode = "/" + node + "/" + childrenNodes.get(index - 1);  
	                // ��waitPath��ע�������, ��waitPath��ɾ��ʱ, zookeeper��ص���������process����  
	                zk.getData(listennode, true, new Stat());  	 			
			
		}
	        }
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	        private void doSomething() throws Exception {  
	            try {  
	                System.out.println("gain lock: " + thispath);  
	                Thread.sleep(2000);  
	                // do something  
	            } finally {  
	                System.out.println("finished: " + thispath);  
	                // ��thisPathɾ��, ����thisPath��client�����֪ͨ  
	                // �൱���ͷ���  
	                zk.delete(this.thispath, -1);  
	            }  
	        }  
	      
	        public static void main(String[] args) throws Exception {  
	            for (int i = 0; i < 10; i++) {  
	                new Thread() {  
	                    public void run() {  
	                        try {  
	                        	Zookeeper_locks dl = new Zookeeper_locks();  
	                            dl.zookeeperconn();  
	                        } catch (Exception e) {  
	                            e.printStackTrace();  
	                        }  
	                    }  
	                }.start();  
	            }  
	      
	            Thread.sleep(Long.MAX_VALUE);  
	        }  
}
