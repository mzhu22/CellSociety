package cellsociety_team08;

import java.util.List;

import javafx.scene.paint.Color;

public class GameOfLife extends RuleSet {

	private static final String GAME_OF_LIFE = "Game of Life";

	private static final State[] possibleStates = new State[] {
			new State("Alive", 0, Color.GREEN, null) };

	public GameOfLife(Object[] params) {
		super(GAME_OF_LIFE, possibleStates, params);
	}

	@Override
	public Patch getNext(Patch patch, List<Patch> neighborhood) {
		
		if (patch.containsCell()) {
			if (neighborhood.size() < 2) {
				patch.myCell = null;
				patch.clear();
			}
			if (neighborhood.size() > 3) {
				patch.myCell = null;
				patch.clear();
			}
		}
		
		if (!patch.containsCell()) {
			if (neighborhood.size() == 3) {
				Cell newCell = new Cell(possibleStates[0], patch.myDimensions);
				patch.fill(newCell);
			}
		}
		
		return patch;

		/*int count = 0;
		Patch currentPatch = neighborhood[1][1];

		for (int i = 0; i < neighborhood.length; i++) {
			for (int j = 0; j < neighborhood[0].length; j++) {
				if (!(i == 1 && j == 1) && neighborhood[i][j] != null
						&& !neighborhood[i][j].isEmpty) {
					count++;
				}
			}
		}
		
		if (currentPatch.isEmpty && count == 3) {
			revive(currentPatch);
			return possibleStates[0];
		}

		if (count < 2)
			kill(currentPatch);
		else if (count > 3)
			kill(currentPatch);	
		else if (count == 2 || count == 3)
			return currentPatch.getNext();
		
		return null; // returns null if cell is newly emp
		*/
	}


	
	/*private void revive(Cell cell) {
		cell.isEmpty = false;
	}*/

	
	


}

