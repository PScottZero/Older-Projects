import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public class Computer
{
	private RAM ram;
	private CPU cpu;
	private JFrame j;
	private Debug d;
	
	public Computer()
	{
		ram = new RAM();
		cpu = new CPU();
		
		j = new JFrame("Debug");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.getContentPane().setPreferredSize(new Dimension(600, 500));
		j.setResizable(false);
		
		d = new Debug();				
		
		j.add(d);
		j.pack();
		j.setVisible(true);
	}
	
	public void update()
	{
		d.setText(cpu + "\n" + ram);
	}
	
	public RAM getRam()
	{
		return ram;
	}
	
	public void step(int address)
	{
		cpu.loadInstruction(address);
		update();
	}
	
	public int getNextAddress()
	{
		return cpu.getIAR();
	}
	
	public void clear()
	{
		ram.clear();
		cpu.clear();
	}
	
	public boolean[] getFlags()
	{
		return cpu.getFlags();
	}
	
	
	
	
	
	public class RAM 
	{
		private Register[][] mem;
		
		public RAM()
		{
			//initializes 64K of RAM
			mem = new Register[16][16];
			
			for (int r = 0; r < mem.length; r++)
				for (int c = 0; c < mem[r].length; c++)
					mem[r][c] = new Register();
			
			for (int r = 0; r < mem.length; r++)
			{
				for (int c = 0; c < mem[r].length; c++)
				{
					Random rand = new Random();
					mem[r][c].setData(rand.nextInt(0x100));
				}
			}
		}
		
		public void setMemory(int address, int data)
		{
			int row = address / 0x10;
			int col = address % 0x10;
			mem[row][col].setData(data);
		}
		
		
		public Register getMemory(int address)
		{
			int row = address / 0x10;
			int col = address % 0x10;
			return mem[row][col];
		}
		
		public void clear()
		{
			for( Register[] rRow : mem )
				for ( Register r : rRow )
					r.setData(0x00);
		}
		
		public String toString()
		{
			int memCount = 0x00;
			String memMap = "";
			for ( Register[] regRow : mem )
			{
				memMap += String.format("%02X", memCount) + ": ";
				for (Register r : regRow )
				{
					memMap += r;
				}
				memCount += 0x10;
				memMap += "\n";
			}
			return memMap;
		}
	}
	

	
	
	
	public class CPU 
	{
		private Register A, B, C, X, Y, Z, IR, IAR;
		private String instruction;
		private boolean carry, equals, zero, greater;
		
		public CPU()
		{
			A = new Register();
			B = new Register();
			C = new Register();
			X = new Register();
			Y = new Register();
			Z = new Register();
			IR = new Register();
			IAR = new Register();
			instruction = "";
			carry = false;
			equals = false;
			zero = false;
			greater = false;
		}
		
		public void loadInstruction(int inst)
		{	
			IAR.setData(inst);
			next();
		}
		
		public void next()
		{
			IR.setData(ram.getMemory(IAR.getData()).getData());
			decode();
			IAR.setData(IAR.getData() + 1);
			interpret();
		}
		
		public int getIAR()
		{
			return IAR.getData();
		}
		
		private void decode()
		{
			String data = Integer.toBinaryString(IR.getData());
			
			while (data.length() < 8)
			{
				data = "0" + data;
			}
			
			instruction = data;
		}
		
		public void interpret()
		{
			if (instruction.charAt(0) == '1')
			{
				if (instruction.substring(0,5).equals("11110"))
				{
					String reg = instruction.substring(5,8);
					int data = ram.getMemory(getRegister(reg).getData()).getData();
					A.setData(data);		
				}
				else if (instruction.substring(0,5).equals("11111"))
				{
					String reg = instruction.substring(5,8);
					int data = A.getData();
					ram.getMemory(getRegister(reg).getData()).setData(data);;	
				}
				else
				{
					String op = instruction.substring(1,4);
					String r1 = instruction.substring(5,8);
					ALU(getOperationCode(op), getRegister(r1).getData());	
				}
			}
			
			else
			{
				int data = 0;
				String bits = instruction.substring(1,4);
				if (instruction.charAt(4) == '1')
				{
					String reg1 = bits;
					String reg2 = instruction.substring(5,8);
					data = getRegister(reg1).getData();
					getRegister(reg2).setData(data);
				}
				else if (bits.equals("001"))
				{
					data = ram.getMemory(ram.getMemory(IAR.getData()).getData()).getData();
					String reg = instruction.substring(5,8);
					getRegister(reg).setData(data);		
					IAR.setData(IAR.getData() + 1);
				}
				else if (bits.equals("010"))
				{
					String reg = instruction.substring(5,8);
					data = getRegister(reg).getData();
					ram.getMemory(ram.getMemory(IAR.getData()).getData()).setData(data);	
					IAR.setData(IAR.getData() + 1);
				}
				else if (bits.equals("011"))
				{
					String condition = instruction.substring(5,8);
					int address = ram.getMemory(IAR.getData()).getData();
					jump(condition, address);
				}
				else if (bits.equals("100"))
				{
					int address = ram.getMemory(IAR.getData()).getData();
					jump("-1", address);
				}
			}
		}
		
		public Register getRegister(String regCode)
		{
			switch(regCode)
			{
				case "000" : return A;
				case "001" : return B;
				case "010" : return C;
				case "011" : return X;
				case "100" : return Y;
				case "101" : return Z;
				default    : return A;
			}
		}
		
		public int getOperationCode(String op)
		{
			switch(op)
			{
				case "000" : return 0;
				case "001" : return 1;
				case "010" : return 2;
				case "011" : return 3;
				case "100" : return 4;
				case "101" : return 5;
				case "110" : return 6;
				default    : return 0;
			}
		}
		
		public void jump(String condition, int address)
		{
			switch(condition)
			{
				case "000" : 
					if (carry == true) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "001" : 
					if (carry == false) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "010" : 
					if (greater == true) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "011" : 
					if (greater == false) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "100" : 
					if (equals == true) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "101" : 
					if (equals == false) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "110" : 
					if (zero == true) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				case "111" : 
					if (zero == false) IAR.setData(address);
					else IAR.setData(IAR.getData() + 1);
					break;
				default : IAR.setData(address);
					break;
			}
		}
		
		public void ALU(int operation, int data1)
		{
			int data2 = A.getData();
			
			if (operation == 0)
			{
				data1 = data2 + data1;
				carry = false;
				
				if (data1 > 0xFF)
				{
					data1 = 0xFF;
					carry = true;
				}
			}
			else if (operation == 1)
			{
				data1 = data2 - data1;
				carry = false;
				
				if (data1 < 0x00)
				{
					data1 = 0x00;
					carry = true;
				}
			}
			else if (operation == 2)
			{
				greater = (data2 > data1);
			}
			else if (operation == 3)
			{
				equals = (data2 == data1);
			}
			else if (operation == 4)
			{
				zero = (data1 == 0);
			}
			else if (operation == 5)
			{
				data1++;
			}
			else if (operation == 6)
			{
				data1--;
			}
			
			A.setData(data1);
		}
		
		public boolean[] getFlags()
		{
			boolean[] flags = { carry, equals, zero, greater };
			return flags;
		}
		
		public void clear()
		{
			A.setData(0x00);
			B.setData(0x00);
			C.setData(0x00);
			X.setData(0x00);
			Y.setData(0x00);
			Z.setData(0x00);
			carry = greater = zero = equals = false;
		}
		
		public String toString()
		{
			return "A: " + A + "  B: " + B + "  C: " + C + "  X: " + X + "  Y: " + Y + "  Z:" + Z;
		}
	}
}
