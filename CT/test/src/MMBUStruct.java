import java.util.ArrayList;

import java.util.*;

public class MMBUStruct {
	
	
	public List<Boolean[]> getmmue(Boolean[][] table) {
		
		List<Boolean[]> result = new ArrayList<Boolean[]>(); 
		
		String[] mmbuVector = new String[table.length];
		//init mmbuVector
		for(int i = 0; i < mmbuVector.length; i++) {
			mmbuVector[i] = "";
		}
		
		printTable(table);
		
		for(int i = 0; i < table.length; i++) {
			List<Boolean[]> neighbours = getNeighbours(table[i], table);
			boolean myResult = table[i][table[i].length-1];
			for (Boolean[] condVec : neighbours) {
				boolean neighboursResult = condVec[condVec.length - 1];
				if(myResult != neighboursResult) {
					mmbuVector[i] = "X";
					result.add(table[i]);
					break;
				} else {
					mmbuVector[i] = "-";
				}
			}
		}
		System.out.println(Arrays.toString(mmbuVector));
		return result;
}
	
	public void printTable(Boolean[][] table) {
		for(int i = 0; i < table.length; i++) {
			for(int j = 0; j < table[0].length; j++) {
				String s = (table[i][j]) ? "1" : "0";
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}

	public boolean isNeighbour(Boolean[] condVec1, Boolean[] condVec2) {
		int collision = 0;
		for (int i = 0; i < condVec1.length - 1; i++ ) {
			if (condVec1[i] != condVec2[i]) {
				collision++;
				if(collision > 1) {
					return false;
				}
			}
		}
		return (collision == 1);
	}
	
	public List<Boolean[]> getNeighbours(Boolean[] condVec, Boolean[][] table) {
		List<Boolean[]> retVal = new ArrayList<>();
		for (int i = 0; i < table.length; i++ ) {
			if( isNeighbour(table[i], condVec) ) {
				retVal.add(table[i]);
			}
		}
		return retVal;
	}
	
}
