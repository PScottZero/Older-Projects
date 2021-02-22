
public class Register 
{
	private int data;
	
	public Register()
	{
		data = 0x00;
	}
	
	public int getData()
	{
		return data;
	}
	
	public void setData(int n)
	{
		data = n;
		checkData();
	}
	
	public String toString()
	{
		return String.format("%02X", data) + " ";
	}
	
	private void checkData()
	{
		if ( data < 0x00 )
		{
			data = 0x00;
		}
		else if ( data > 0xFF )
		{
			data = 0xFF;
		}
	}
}
