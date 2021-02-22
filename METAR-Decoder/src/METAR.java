import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class METAR 
{
	private String metar, icao, windDirection;
	private ArrayList<String> cloudTypes, remarks;
	private int timeDay, timeHours, timeMinutes, windSpeed, visibility, temperature, dewpoint;
	private double altimeter;
	private ArrayList<Integer> cloudHeights;
	
	public METAR(String airport)
	{
		metar = "";
		icao = airport;
		windDirection = "";
		timeDay = timeHours = timeMinutes = windSpeed = visibility = temperature = dewpoint = 0;
		altimeter = 00.00;
		cloudTypes = new ArrayList<String>();
		cloudHeights = new ArrayList<Integer>();
		remarks = new ArrayList<String>();
		getData();
		parseMETAR();
	}
	
	private void getData()
	{
		try 
		{
			URL url = new URL("https://www.aviationweather.gov/metar/data?ids=" + icao + "&format=raw&date=0&hours=0");
			Scanner in = null;
			try 
			{
				in = new Scanner(url.openStream());
				while(in.hasNextLine())
				{
					if (in.nextLine().equals("<!-- Data starts here -->"))
					{
						metar = in.nextLine();
						for (int i = 0; i < metar.length(); i++)
						{
							if (metar.charAt(i) == '<') 
							{
								metar = metar.substring(0, i);
							}
						}
					}
				}
			} 
			catch (IOException e) 
			{
				System.out.println("Cannot Open Stream");
				e.printStackTrace();
			}
			finally
			{
				in.close();
			}
		} 
		catch (MalformedURLException e) 
		{
			System.out.println("Invalid URL");
			e.printStackTrace();
		}
	}
	
	private void parseMETAR()
	{
		String[] metarSplit = metar.split(" ");
		ArrayList<String> metarList = new ArrayList<String>();
		for (int i = 0; i < metarSplit.length; i++)
		{
			metarList.add(metarSplit[i]);
		}
		
		icao = metarList.remove(0);
		String time = metarList.remove(0);
		timeDay = Integer.parseInt(time.substring(0,2));
		timeHours = Integer.parseInt(time.substring(2,4));
		timeMinutes = Integer.parseInt(time.substring(4,6));
		
		while (metarList.size() > 0)
		{
			if (metarList.get(0).equals("AUTO") || metarList.get(0).equals("RMK"))
			{
				metarList.set(0, "");
			}
			else if (metarList.get(0).equals("CLR"))
			{
				cloudTypes.add("Clear");
				cloudHeights.add(12000);
			}
			else if (metarList.get(0).contains("FEW"))
			{
				cloudTypes.add("Few");
				cloudHeights.add(Integer.parseInt(metarList.get(0).substring(3)) * 100);
			}
			else if (metarList.get(0).contains("SCT"))
			{
				cloudTypes.add("Scattered");
				cloudHeights.add(Integer.parseInt(metarList.get(0).substring(3)) * 100);
			}
			else if (metarList.get(0).contains("BKN"))
			{
				cloudTypes.add("Broken");
				cloudHeights.add(Integer.parseInt(metarList.get(0).substring(3)) * 100);
			}
			else if (metarList.get(0).contains("OVC"))
			{
				cloudTypes.add("Overcast");
				cloudHeights.add(Integer.parseInt(metarList.get(0).substring(3)) * 100);
			}
			else if (metarList.get(0).contains("KT"))
			{
				windDirection = metarList.get(0).substring(0,3);
				if (windDirection.equals("VRB")) windDirection = "Variable";
				windSpeed = Integer.parseInt(metarList.get(0).substring(3,5));
			}
			else if (metarList.get(0).contains("SM"))
			{
				visibility = Integer.parseInt(metarList.get(0).substring(0,metarList.get(0).length() - 2));
			}
			else if (metarList.get(0).contains("/"))
			{
				temperature = Integer.parseInt(metarList.get(0).substring(0,2));
				dewpoint = Integer.parseInt(metarList.get(0).substring(3,5));
			}
			else if (metarList.get(0).contains("A") && metarList.get(0).length() == 5)
			{
				altimeter = Double.parseDouble(metarList.get(0).substring(1,3) + "." + metarList.get(0).substring(3,5));
			}
			else
			{
				remarks.add(metarList.get(0));
			}			
			metarList.remove(0);
		}	
	}
	
	public String getICAO()
	{
		return icao;
	}
	
	public int getHours()
	{
		return timeHours;
	}
	
	public int getMinutes()
	{
		return timeMinutes;
	}
	
	public int getDay()
	{
		return timeDay;
	}
	
	public String getWindDirection()
	{
		return windDirection;
	}
	
	public int getWindSpeed()
	{
		return windSpeed;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public ArrayList<String> getCloudTypes()
	{
		return cloudTypes;
	}
	
	public ArrayList<Integer> getCloudHeights()
	{
		return cloudHeights;
	}
	
	public int getTemperature()
	{
		return temperature;
	}
	
	public int getDewpoint()
	{
		return dewpoint;
	}
	
	public double getAltimeter()
	{
		return altimeter;
	}
	
	public ArrayList<String> getRemarks()
	{
		return remarks;
	}
	
	public String getMETAR()
	{
		return metar;
	}
}
