
class abc
{
	int num;
	
	boolean valueSet = false;
	
	
	public synchronized void put(int num)
	{
		while(valueSet)
		{
			try { wait(); } catch(Exception e) {}
		} 
		
		System.out.println("Put : " + num);
		this.num = num;
		valueSet = true;
		notify(); //consumer thread
	}
	public synchronized void get()
	{
		while(!valueSet)
		{
			try { wait(); } catch(Exception e) {}
		}
		System.out.println("Get : " + num);
		valueSet = false;
		notify(); //producer thread
	}	
}


class Producer implements Runnable 
{
	abc abc;
	
	public Producer(abc abc)
	{
		this.abc = abc;
		Thread t = new Thread(this,"Producer");
		t.start();
	}
	public void run()
	{
		int i = 0;
		while(true)
		{
			abc.put(i++);
			try {Thread.sleep(1000);} catch (Exception e) {}
		}
	}
}


class Consumer implements Runnable
{
	abc abc;
	
	public Consumer(abc abc)
	{
		this.abc = abc;
		Thread t = new Thread(this,"Consumer");
		t.start();
	}
	public void run()
	{
		while(true)
		{
			abc.get();
			try {Thread.sleep(1000);} catch (Exception e) {}
		}
	}
	
}


public class InterThread 
{
	public static void main(String[] args) 
	{
		abc abc = new abc();
		new Producer(abc);
		new Consumer(abc);

	}

}
