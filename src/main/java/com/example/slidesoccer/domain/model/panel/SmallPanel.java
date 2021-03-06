package com.example.slidesoccer.domain.model.panel;

import java.util.ArrayList;
import java.util.List;

import com.example.slidesoccer.domain.model.move.Move;
import com.example.slidesoccer.domain.model.space.Spaces;
import com.example.slidesoccer.domain.type.position.Position;

public class SmallPanel extends Panel {

	public SmallPanel(Position position) {
		super(PanelType.SmallPanel, position);
	}

	@Override
	public List<Position> positions() {
		return List.of(position);
	}

	@Override
	public List<Move> getCanMove(Spaces spaces) {
		ArrayList<Move> moves = new ArrayList<>();
		
		if(spaces.hasUp(position)) 
			moves.add(new Move(this, position.up()));
		if(spaces.hasDown(position)) 
			moves.add(new Move(this, position.down()));
		if(spaces.hasRight(position)) 
			moves.add(new Move(this, position.right()));
		if(spaces.hasLeft(position)) 
			moves.add(new Move(this, position.left()));
		
		return moves;
	}
	
	public SmallPanel getMovedPanel(Position position) {
		return new SmallPanel(position);
	}

	@Override
	public Panel getMoved(Position position) {
		return new SmallPanel(position);
	}
	
}
