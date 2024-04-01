package main;

import awt.PannelloCtrl;


public class Main {

	public static void main (String[] args) {
		try {
			PannelloCtrl pannello = new PannelloCtrl();
			pannello.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
