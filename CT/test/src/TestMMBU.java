import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

public class TestMMBU {
	
	@Test
	public void testmmbu() {
		MMBUStruct mmbuObj = new MMBUStruct();
		Boolean[][] table =	{ 
				  {false, false, false, false},
				  {false, false, true,  false},
				  {false, true,  false, false},
				  {false, true,  true,  false},
				  {true,  false, false, false},
				  {true,  false, true,  true},
				  {true,  true,  false, true},
				  {true,  true,  true,  false}
		};
		
		List<Boolean[]> result = mmbuObj.getmmue(table);
		Boolean[][] resultExpected = {
				{false, false, true,  false},
				{false, true,  false, false},
				{true,  false, false, false},
				{true,  false, true,  true},
				{true,  true,  false, true},
				{true,  true,  true,  false}
		};
		
		assertEquals(resultExpected.length, result.size());
		
		for(int i = 0; i < result.size(); i++) {
			for(int j = 0; j < result.get(0).length; j++) {
				assertEquals(resultExpected[i][j], result.get(i)[j]);
			}
		}
	}
}
