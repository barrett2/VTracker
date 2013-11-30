package com.example.vtracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Vtracker extends Activity {	

	//variables 
		private int TotalRuns;
		private int start[];
		private int len[];
		private int currentRun;
		private int currentPoint;
		private int lastLine;
		private Handler handler;
		
		
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_vtracker);
	        
	        //link variables to display
	    	Button btnPrev = (Button) findViewById(R.id.button3);
	    	Button btnNext = (Button) findViewById(R.id.button5);
	    	Button btnStepBack = (Button) findViewById(R.id.button2);
	    	Button btnStepForward = (Button) findViewById(R.id.button1);
	    	Button btnPlay = (Button) findViewById(R.id.button4);
	    	TextView txtR = (TextView) findViewById(R.id.textView1);
	    	TextView txtM = (TextView) findViewById(R.id.textView2);
	    	TextView txtRun = (TextView) findViewById(R.id.textView3);
	    	TextView txtMph = (TextView) findViewById(R.id.textView4);
	    	TextView txtTotalRuns = (TextView) findViewById(R.id.textView5);
	    	TextView txtTRuns = (TextView) findViewById(R.id.tRuns);
	    	TextView txtLat = (TextView) findViewById(R.id.lat);
	    	TextView txtLon = (TextView) findViewById(R.id.lon);
	    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	    	TextView txtVLon = (TextView) findViewById(R.id.longitude);
	    	
	    	//font styles and colors
	    	btnPrev.setBackgroundColor(Color.DKGRAY); //button color
	    	btnNext.setBackgroundColor(Color.DKGRAY);
	    	btnStepBack.setBackgroundColor(Color.DKGRAY);
	    	btnStepForward.setBackgroundColor(Color.DKGRAY);
	    	btnPlay.setBackgroundColor(Color.DKGRAY);
	    	btnPrev.setTextSize(20.0f);				//button text size
	    	btnNext.setTextSize(20.0f);
	    	btnStepBack.setTextSize(20.0f);
	    	btnStepForward.setTextSize(20.0f);
	    	btnPlay.setTextSize(20.0f);
	    	btnPrev.setTextColor(Color.GREEN); 		//button text color
	    	btnNext.setTextColor(Color.GREEN);
	    	btnStepBack.setTextColor(Color.GREEN);
	    	btnStepForward.setTextColor(Color.GREEN);
	    	btnPlay.setTextColor(Color.GREEN);
	    	txtR.setTextSize(20.0f);		//text size
	    	txtM.setTextSize(20.0f);
	    	txtRun.setTextSize(20.0f);
	    	txtMph.setTextSize(20.0f);
	    	txtTotalRuns.setTextSize(20.0f);	
	    	txtTRuns.setTextSize(20.0f);
	    	txtLat.setTextSize(20.0f);
	    	txtLon.setTextSize(20.0f);
	    	txtVLat.setTextSize(20.0f);
	    	txtVLon.setTextSize(20.0f);
	    	txtR.setTextColor(Color.GREEN);   	//text color
	    	txtM.setTextColor(Color.GREEN);    	
	    	txtRun.setTextColor(Color.GREEN);    	
	    	txtMph.setTextColor(Color.GREEN);    	
	    	txtTotalRuns.setTextColor(Color.GREEN);
	    	txtTRuns.setTextColor(Color.GREEN);
	    	txtLat.setTextColor(Color.GREEN);
	    	txtLon.setTextColor(Color.GREEN);
	    	txtVLat.setTextColor(Color.GREEN);
	    	txtVLon.setTextColor(Color.GREEN);
	    	
	    	
	    	//create click listeners for buttons
	    	btnPrev.setOnClickListener(prevRunListener);
	    	btnNext.setOnClickListener(nextRunListener);
	    	btnStepBack.setOnClickListener(stepBackListener);
	    	btnStepForward.setOnClickListener(stepForwardListener);
	    	btnPlay.setOnClickListener(playRunListener);
	    	
	    	
	    	//display initial values
	    	try {
	    		currentRun = 0;
	    		currentPoint = 0;
	    		TotalRuns = getR();
	    		start = new int[TotalRuns];
	    		getStarts();
	    		len = new int[TotalRuns];
	    		getLengths();
	    		lastLine = getL();
	    		
	    		txtRun.setText(Integer.toString(currentRun+1));
	    		String mph = get_mph();
	   			txtMph.setText(get_mph());
	   			meterDisplay(Float.parseFloat(mph));
	   			seekBarDisplay();
	    		txtTotalRuns.setText(Integer.toString(TotalRuns));
	    		txtVLat.setText(get_lat());
	    		txtVLon.setText(get_lon());
	    	} catch (Exception e) {
	    		Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
	    	} 
	    }//end onCreate()
	    

	    
	    
	    //button listeners   
	    //previous run
	    private OnClickListener prevRunListener = new OnClickListener() {
	       	public void onClick(View v) {
	       		try {
	       			TextView txtRun = (TextView) findViewById(R.id.textView3);
	       			TextView txtMph = (TextView) findViewById(R.id.textView4);
	    	    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	    	    	TextView txtVLon = (TextView) findViewById(R.id.longitude);	       			
	       			if(currentRun>0) {
	       				currentRun--;
	       				currentPoint=0;
	       			}
	       			txtRun.setText(Integer.toString(currentRun+1));
	       			String mph = get_mph();
	       			txtMph.setText(get_mph());
	       			meterDisplay(Float.parseFloat(mph));
	       			seekBarDisplay();
		    		txtVLat.setText(get_lat());
		    		txtVLon.setText(get_lon());	       			
	       		} catch (Exception e) {
	        		Toast.makeText(getBaseContext(), e.getMessage(),
	    				Toast.LENGTH_SHORT).show();
	        	}
	       	}//end onClick
	    };//end onClickListener
	    
	    //next run
	    private OnClickListener nextRunListener = new OnClickListener() {
	        	public void onClick(View v) {
	        		try {
	           			TextView txtRun = (TextView) findViewById(R.id.textView3);
	           			TextView txtMph = (TextView) findViewById(R.id.textView4);	
	        	    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	        	    	TextView txtVLon = (TextView) findViewById(R.id.longitude);	           			
	           			if((currentRun+1)<TotalRuns) {
	           				currentRun++;
	           				currentPoint=0;
	           			}
	           			txtRun.setText(Integer.toString(currentRun+1));
	           			String mph = get_mph();
	           			txtMph.setText(get_mph());
	           			meterDisplay(Float.parseFloat(mph));
	           			seekBarDisplay();
	    	    		txtVLat.setText(get_lat());
	    	    		txtVLon.setText(get_lon());	           			
	           		} catch (Exception e) {
	            		Toast.makeText(getBaseContext(), e.getMessage(),
	        				Toast.LENGTH_SHORT).show();
	            	}
	        	}//end onClick
	     };//end onClickListener
	        
	     //step forward
	     private OnClickListener stepForwardListener = new OnClickListener() {
	        	public void onClick(View v) {
	        		try {
	           			TextView txtMph = (TextView) findViewById(R.id.textView4);
	        	    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	        	    	TextView txtVLon = (TextView) findViewById(R.id.longitude);
	           			currentPoint++;
	           			if((currentRun+1)==TotalRuns) {
	           				if((currentPoint+start[currentRun]+1) > (lastLine-1)) {        				
	           					currentPoint--;
	           				}
	           			}
	           			else if((currentRun+1)<TotalRuns) {
	           				if((currentPoint+start[currentRun]+1) == start[currentRun+1]) {
	           					currentPoint--;
	           				}
	           			}
	           			String mph = get_mph();
	           			txtMph.setText(get_mph());
	           			meterDisplay(Float.parseFloat(mph));
	           			seekBarDisplay();
	    	    		txtVLat.setText(get_lat());
	    	    		txtVLon.setText(get_lon());	           			
	           		} catch (Exception e) {
	            		Toast.makeText(getBaseContext(), e.getMessage(),
	        				Toast.LENGTH_SHORT).show();
	            	}
	        	
	        	}//end onClick
	     };//end onClickListener
	        
	     //step backward
	     private OnClickListener stepBackListener = new OnClickListener() {
	      	public void onClick(View v) {
	      		try {
	         			TextView txtMph = (TextView) findViewById(R.id.textView4);
	        	    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	        	    	TextView txtVLon = (TextView) findViewById(R.id.longitude);	         			
	         			if(currentPoint>0) {
	         				currentPoint--;
	         			}
	         			String mph = get_mph();
	         			txtMph.setText(get_mph());
	         			meterDisplay(Float.parseFloat(mph));
	         			seekBarDisplay();
	    	    		txtVLat.setText(get_lat());
	    	    		txtVLon.setText(get_lon());	         			
	         		} catch (Exception e) {
	          		Toast.makeText(getBaseContext(), e.getMessage(),
	      				Toast.LENGTH_SHORT).show();
	          	}
	      	}//end onClick
	    };//end onClickListener
	     
	    
	    //play run
	    private OnClickListener playRunListener = new OnClickListener() {
	        public void onClick(View v) {
	        	try { 
	        		//create handler so we can update UI from a new thread
	        		handler = new Handler();
	        		//create runnable function to pass to handler
	        		final Runnable r = new Runnable()
	        		{
	        		    public void run() 
	        		    {
	        		    	//display to UI
	        		    	TextView txtMph = (TextView) findViewById(R.id.textView4);
	        		    	TextView txtVLat = (TextView) findViewById(R.id.latitude);
	        		    	TextView txtVLon = (TextView) findViewById(R.id.longitude);	        		    	
			           		String mph = get_mph();
			           		txtMph.setText(get_mph());
			           		meterDisplay(Float.parseFloat(mph));
			           		seekBarDisplay();	
				    		txtVLat.setText(get_lat());
				    		txtVLon.setText(get_lon());			           		
	        		    }
	        		}; //end of runnable	
	        		    	 	
	        		//create thread to play run on    	
	        		Thread thread = new Thread()
	        		{
	        		    @Override
	        		    public void run() {
	        		    	//initial delay
	        		    	try { 
	        		    		sleep(diff());
	        		    	} catch (Exception e) {}
	        		    	
	        		    	//stepForward after each delay
	        		    	while(true)
	        			    {
	        		    		//gives handler info to update UI
	        		    		handler.post(r); 
	        		    		//update current point and check that run is not over
	        			        currentPoint++;
	        		           	if((currentRun+1)==TotalRuns) {
	        		           		if((currentPoint+start[currentRun]+1) > (lastLine-1)) {        				
	        		           			currentPoint--;
	        		           			break;
	        		           		}
	        		           	}
	        		           	else if((currentRun+1)<TotalRuns) {
	        		           		if((currentPoint+start[currentRun]+1) == start[currentRun+1]) {
	        		           			currentPoint--;
	        		           			break;
	        		           		}
	        		           	}
	        		           	
	        		           	//delay for animation
	        		           	try { 
	        		           		sleep(2000);
	        		           	} catch (Exception e) {}
	        			    }//end loop
	        		    }//end run()
	        		}; //end create new thread

	        		//run thread to play runs
	        		thread.start();       		
	        		
	        	} catch (Exception e) {
	            		Toast.makeText(getBaseContext(), e.getMessage(),
	        				Toast.LENGTH_SHORT).show();
	         	}
	        	}//end onClick
	      };//end onClickListener
	      
	        
	    	
	    
	    


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.vtracker, menu);
	        return true;
	    }
	   
	    //functions to read data
		//get the number of runs
		public int getR() {
			int runs=0;
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				//calculate runs, lines, and longest run
				while ((tempRow = myReader.readLine()) != null) {
					if(tempRow.equals("Start")) {
						runs++;
					}
				}
				myReader.close();
			}
			catch (Exception e) { }
			return runs;
		} 
	    
		//get the number of lines
		public int getL() {
			int lines=0;
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				//calculate lines
				while (myReader.readLine() != null) {
					lines++;
				}
				myReader.close();
			}
			catch (Exception e) { }
			return lines;
		}
		
		//get starting lines for runs (index)
		public void getStarts() {
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int line=0, r=0;
				//calculate line where runs begin 
				while ((tempRow = myReader.readLine()) != null) {
					if(tempRow.equals("Start")) {
						start[r]=line;
						r++;
					}
					line++;
				}
				myReader.close();
			}
			catch (Exception e) { }
		} 
		
		//get lengths for runs (index)
		public void getLengths() {
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int length=0, r=0;
				boolean first = true;
				//calculate line where runs begin 
				while ((tempRow = myReader.readLine()) != null) {
					if(tempRow.equals("Start") && first) {
						first=false;
					}
					else if(tempRow.equals("Start")) {
						len[r]=length;
						length=0;
						r++;
					}
					else {
						length++;
					}
				}
				len[r]=length;
				myReader.close();
			}
			catch (Exception e) { }
		}
		
		//read mph from SD at current point in run
		public String get_mph() {
			String m="";
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int line=0;
				int stopline=start[currentRun]+currentPoint+1;
				while (line!=stopline) {
					tempRow = myReader.readLine();
					line++;
				}
				tempRow = myReader.readLine();
				myReader.close();
				m=tempRow.substring(tempRow.lastIndexOf(",")+1);
			}
			catch (Exception e) { }
			return m;	
		}
		
		//read time from SD at current point in run
		public String get_time() {
			String t="";
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int line=0;
				int stopline=start[currentRun]+currentPoint+1;
				while (line!=stopline) {
					tempRow = myReader.readLine();
					line++;
				}
				tempRow = myReader.readLine();
				myReader.close();
				t=tempRow.substring(6,12);
			}
			catch (Exception e) { }
			return t;	
		}
		
		//read latitude from SD at current point in run
		public String get_lat() {
			String m="";
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int line=0;
				int stopline=start[currentRun]+currentPoint+1;
				while (line!=stopline) {
					tempRow = myReader.readLine();
					line++;
				}
				tempRow = myReader.readLine();
				myReader.close();
				m=tempRow.substring(13,tempRow.indexOf(","));
			} 
			catch (Exception e) { }
			return m;	
		}
		
		//read longitude from SD at current point in run
		public String get_lon() {
			String m="";
			try {
				File myFile = new File("/mnt/extSdCard/data2.txt");
				FileInputStream fIn = new FileInputStream(myFile);
				InputStreamReader myInStreamReader = new InputStreamReader(fIn);
				BufferedReader myReader = new BufferedReader(myInStreamReader);
				String tempRow = "";
				int line=0;
				int stopline=start[currentRun]+currentPoint+1;
				while (line!=stopline) {
					tempRow = myReader.readLine();
					line++;
				}
				tempRow = myReader.readLine();
				myReader.close();
				m=tempRow.substring(tempRow.indexOf(",")+1,tempRow.lastIndexOf(","));
			} 
			catch (Exception e) { }
			return m;	
		}
		
		//functions to control widgets
		//displays mph on speedometer, precision (+or-)1.25mph 
		public void meterDisplay(float mph) {
			ImageView meter = (ImageView) findViewById(R.id.imageView1);
			int intMph = Math.round(mph);
			//Search time: best case 5 compares, worst case 11 compares, avg 7.57 compares 
			if((intMph>-1)&&(intMph<121)) {
				if(intMph<62) {
					if(intMph<32) {
						if(intMph <17) {
							if(intMph<2) meter.setImageResource(R.drawable.meter0);
							if((intMph>=2) && (intMph<4)) meter.setImageResource(R.drawable.meter3);
							if((intMph>=4) && (intMph<7)) meter.setImageResource(R.drawable.meter5);
							if((intMph>=7) && (intMph<9)) meter.setImageResource(R.drawable.meter8);
							if((intMph>=9) && (intMph<12)) meter.setImageResource(R.drawable.meter10);
							if((intMph>=12) && (intMph<14)) meter.setImageResource(R.drawable.meter13);
							if((intMph>=14) && (intMph<17)) meter.setImageResource(R.drawable.meter15);
						} else {
							if((intMph>=17) && (intMph<19)) meter.setImageResource(R.drawable.meter18);
							if((intMph>=19) && (intMph<22)) meter.setImageResource(R.drawable.meter20);	
							if((intMph>=22) && (intMph<24)) meter.setImageResource(R.drawable.meter23);
							if((intMph>=24) && (intMph<27)) meter.setImageResource(R.drawable.meter25);
							if((intMph>=27) && (intMph<29)) meter.setImageResource(R.drawable.meter28);
							if((intMph>=29) && (intMph<32)) meter.setImageResource(R.drawable.meter30);	
						}
					} else {
						if(intMph<47) {
							if((intMph>=32) && (intMph<34)) meter.setImageResource(R.drawable.meter33);
							if((intMph>=34) && (intMph<37)) meter.setImageResource(R.drawable.meter35);
							if((intMph>=37) && (intMph<39)) meter.setImageResource(R.drawable.meter38);
							if((intMph>=39) && (intMph<42)) meter.setImageResource(R.drawable.meter40);	
							if((intMph>=42) && (intMph<44)) meter.setImageResource(R.drawable.meter43);
							if((intMph>=44) && (intMph<47)) meter.setImageResource(R.drawable.meter45);
						} else {
							if((intMph>=47) && (intMph<49)) meter.setImageResource(R.drawable.meter48);
							if((intMph>=49) && (intMph<52)) meter.setImageResource(R.drawable.meter50);			
							if((intMph>=52) && (intMph<54)) meter.setImageResource(R.drawable.meter53);
							if((intMph>=54) && (intMph<57)) meter.setImageResource(R.drawable.meter55);
							if((intMph>=57) && (intMph<59)) meter.setImageResource(R.drawable.meter58);
							if((intMph>=59) && (intMph<62)) meter.setImageResource(R.drawable.meter60);	
						}
					}
				}
				else {
					if(intMph<92) {
						if(intMph<77) {
							if((intMph>=62) && (intMph<64)) meter.setImageResource(R.drawable.meter63);
							if((intMph>=64) && (intMph<67)) meter.setImageResource(R.drawable.meter65);
							if((intMph>=67) && (intMph<69)) meter.setImageResource(R.drawable.meter68);
							if((intMph>=69) && (intMph<72)) meter.setImageResource(R.drawable.meter70);	
							if((intMph>=72) && (intMph<74)) meter.setImageResource(R.drawable.meter73);
							if((intMph>=74) && (intMph<77)) meter.setImageResource(R.drawable.meter75);
						} else {
							if((intMph>=77) && (intMph<79)) meter.setImageResource(R.drawable.meter78);
							if((intMph>=79) && (intMph<82)) meter.setImageResource(R.drawable.meter80);	
							if((intMph>=82) && (intMph<84)) meter.setImageResource(R.drawable.meter83);
							if((intMph>=84) && (intMph<87)) meter.setImageResource(R.drawable.meter85);
							if((intMph>=87) && (intMph<89)) meter.setImageResource(R.drawable.meter88);
							if((intMph>=89) && (intMph<92)) meter.setImageResource(R.drawable.meter90);	
						}
					}
					else {
						if(intMph<107) {
							if((intMph>=92) && (intMph<94)) meter.setImageResource(R.drawable.meter93);
							if((intMph>=94) && (intMph<97)) meter.setImageResource(R.drawable.meter95);
							if((intMph>=97) && (intMph<99)) meter.setImageResource(R.drawable.meter98);
							if((intMph>=99) && (intMph<102)) meter.setImageResource(R.drawable.meter100);		
							if((intMph>=102) && (intMph<104)) meter.setImageResource(R.drawable.meter103);
							if((intMph>=104) && (intMph<107)) meter.setImageResource(R.drawable.meter105);
						} else {
							if((intMph>=107) && (intMph<109)) meter.setImageResource(R.drawable.meter108);
							if((intMph>=109) && (intMph<112)) meter.setImageResource(R.drawable.meter110);	
							if((intMph>=112) && (intMph<114)) meter.setImageResource(R.drawable.meter113);
							if((intMph>=114) && (intMph<117)) meter.setImageResource(R.drawable.meter115);
							if((intMph>=117) && (intMph<119)) meter.setImageResource(R.drawable.meter118);
							if(intMph>=119) meter.setImageResource(R.drawable.meter120);	
						}
					}
				}
			}
			
		}

		//displays progress of run on seekbar
		public void seekBarDisplay() {
			SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
			seekBar.setMax(100);
			float tempLen = (float)len[currentRun];
			int increment = Math.round(100*(1/(tempLen-1)));
			seekBar.setProgress(currentPoint*increment);
		}

		//calculates the time difference between two time strings
		public int change(String a, String b) {
	    	//need to calculate time difference and
	    	//return as an integer hh mm ss c
	    	int csec2 = Integer.parseInt(b.substring(6,7));
	    	int csec1 = Integer.parseInt(a.substring(6,7));
	    	int sec2 = Integer.parseInt(b.substring(4,6));
	    	int sec1 = Integer.parseInt(a.substring(4,6));
	    	int min2 = Integer.parseInt(b.substring(2,4));
	    	int min1 = Integer.parseInt(a.substring(2,4));
	    	int hour2 = Integer.parseInt(b.substring(0,2));
	    	int hour1 = Integer.parseInt(a.substring(0,2));

	    	csec2 = csec2*10; csec1 = csec1*10;
	    	sec2 = sec2*1000; sec1 = sec1*1000;
	    	min2 = min2*60000; min1 = min1*60000;
	    	hour2 = hour2*3600000; hour1 = hour1*3600000;
	    	int time2 = csec2+sec2+min2+hour2;
	    	int time1 = csec1+sec1+min1+hour1;

	    	if(time1 < time2) return time2-time1;
	    	else return 0;
		}

		//gets the time interval between data points
		public int diff() {
	    	//check time for end mark
	    	if((currentPoint+1) == len[currentRun])
	            return -1;
	    	else {
	            String temp1 = get_time();
	            currentPoint++;
	            String temp2 = get_time();
	            currentPoint--;
	            return change(temp1, temp2);
	    	}
		}
	}
